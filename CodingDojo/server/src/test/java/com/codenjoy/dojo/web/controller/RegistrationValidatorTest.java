package com.codenjoy.dojo.web.controller;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2012 - 2022 Codenjoy
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import com.codenjoy.dojo.CodenjoyContestApplication;
import com.codenjoy.dojo.config.Constants;
import com.codenjoy.dojo.config.TestSqliteDBLocations;
import com.codenjoy.dojo.services.GameType;
import com.codenjoy.dojo.services.Player;
import com.codenjoy.dojo.services.PlayerService;
import com.codenjoy.dojo.services.dao.Registration;
import com.codenjoy.dojo.services.room.RoomService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.Errors;
import org.springframework.validation.MapBindingResult;

import java.util.Arrays;
import java.util.HashMap;

import static java.util.stream.Collectors.joining;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CodenjoyContestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(Constants.DATABASE_TYPE)
@ContextConfiguration(initializers = TestSqliteDBLocations.class)
@TestPropertySource(properties = {
        "registration.nickname.allowed=false",
        "registration.password.min-length=5"
})
public class RegistrationValidatorTest {

    @Autowired
    private RegistrationValidator validator;

    @MockBean
    private Registration registration;

    @SpyBean
    private Validator commonValidator;

    @Autowired
    private RoomService roomService;

    @Autowired
    private PlayerService playerService;

    private Player player;
    private Errors errors;

    @Before
    public void setup() {
        reset(commonValidator, registration);

        errors = makeErrors();
        player = new Player(){{
            setEmail("someuser@sample.org");
            setReadableName("Readable Name");
            setPassword("12345");
            setPasswordConfirmation("12345");
            setGame("game");
            setRoom("room");
        }};
        setupRoomService("game", "room", "room2");
        playerService.openRegistration();
    }

    private void setupRoomService(String game, String... rooms) {
        roomService.removeAll();
        GameType gameType = mock(GameType.class);
        when(gameType.name()).thenReturn(game);
        Arrays.stream(rooms).forEach(room ->
            roomService.create(room, gameType));
    }

    @Test
    public void shouldPassValidUser() {
        // when
        validator.validate(player, errors);

        // then
        assertEquals("",
                errors.getAllErrors().stream()
                        .map(DefaultMessageSourceResolvable::getCode)
                        .collect(joining("\n")));
    }

    @Test
    public void shouldValidateNicknameStructure() {
        // given
        player.setReadableName("Unreadablename");

        // when
        validator.validate(player, errors);
        
        // then
        assertError("readableName",
                "registration.nickname.invalid");
    }

    @Test
    public void shouldValidateRoomRegistrationIsActive() {
        // given
        roomService.setOpened("room", false);
        assertEquals(true, roomService.isOpened());

        // when
        validator.validate(player, errors);

        // then
        assertError("email",
                "registration.room.suspended");
    }

    @Test
    public void shouldValidateSiteRegistrationIsActive() {
        // given
        playerService.closeRegistration();
        assertEquals(true, roomService.isOpened());

        // when
        validator.validate(player, errors);

        // then
        assertError("email",
                "registration.suspended");
    }

    @Test
    public void shouldValidateUsernameUniqueness() {
        // given
        String nonUniqueName = "Nonunique Name";
        when(registration.nameIsUsed(nonUniqueName)).thenReturn(true);
        player.setReadableName(nonUniqueName);

        // when
        validator.validate(player, errors);
        
        // then
        assertError("readableName",
                "registration.nickname.alreadyUsed");
    }

    @Test
    public void shouldValidateEmailFormat() {
        // given
        player.setEmail("BAD_EMAIL");

        // when
        validator.validate(player, errors);

        // then
        assertError("email",
                "registration.email.invalid");
    }

    @Test
    public void shouldValidateEmailUniqueness() {
        // given
        String nonUniqueEmail = "duplicate@sample.org";
        when(registration.emailIsUsed(nonUniqueEmail)).thenReturn(true);
        player.setEmail(nonUniqueEmail);

        // when
        validator.validate(player, errors);
        
        // then
        assertError("email",
                "registration.email.alreadyUsed");
    }

    @Test
    public void shouldRejectEmptyPassword() {
        // given
        player.setPassword("");

        // when
        validator.validate(player, errors);
        
        // then
        assertError("password",
                "registration.password.empty\n" +
                "registration.password.length");
    }

    @Test
    public void shouldRejectShortPassword() {
        // given
        player.setPassword("1234");

        // when
        validator.validate(player, errors);
        
        // then
        assertError("password",
                "registration.password.length");
    }

    @Test
    public void shouldCheckPasswordConfirmation() {
        // given
        player.setPassword("12345");
        player.setPasswordConfirmation("1234");

        // when
        validator.validate(player, errors);
        
        // then
        assertError("passwordConfirmation",
                "registration.password.invalidConfirmation");
    }

    @Test
    public void shouldValidateGameName() {
        // given
        when(commonValidator.isGameName("game", Validator.CANT_BE_NULL)).thenReturn(false);

        // when
        validator.validate(player, errors);

        // then
        assertError("game",
                "registration.game.invalid");
    }

    @Test
    public void shouldValidateRoomName() {
        // given
        String invalidRoomName = "invalidRoom";
        when(commonValidator.isRoomName(invalidRoomName, Validator.CANT_BE_NULL)).thenReturn(false);
        player.setRoom(invalidRoomName);

        // when
        validator.validate(player, errors);

        // then
        assertError("room",
                "registration.room.invalid");
    }

    private void assertError(String field, String expected) {
        assertEquals(expected,
                errors.getFieldErrors(field).stream()
                        .map(DefaultMessageSourceResolvable::getCode)
                        .collect(joining("\n")));
    }

    private static Errors makeErrors() {
        return new MapBindingResult(new HashMap<>(), "game");
    }
}
