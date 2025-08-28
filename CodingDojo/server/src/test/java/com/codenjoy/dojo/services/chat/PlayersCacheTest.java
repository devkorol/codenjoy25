package com.codenjoy.dojo.services.chat;

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
import com.codenjoy.dojo.services.dao.Registration;
import com.codenjoy.dojo.services.helper.Helpers;
import com.codenjoy.dojo.utils.smart.SmartAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static com.codenjoy.dojo.utils.smart.SmartAssert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CodenjoyContestApplication.class)
@ActiveProfiles(Constants.DATABASE_TYPE)
@ContextConfiguration(initializers = TestSqliteDBLocations.class)
public class PlayersCacheTest {

    @SpyBean
    private Registration registration;

    @Autowired
    private PlayersCache cache;

    @Autowired
    private Helpers with;

    @Before
    public void setup() {
        with.clean.removeAll();
    }

    @After
    public void after() {
        SmartAssert.checkResult();
    }

    @Test
    public void shouldGetName_firstTime() {
        // given
        with.login.register("player", "ip", "room", "first");
        reset(registration);
        cache.removeAll();

        // when
        String name = cache.name("player");

        // then
        assertEquals("player-name", name);

        verify(registration, times(1)).getNameById("player");
    }

    @Test
    public void shouldGetName_secondTime() {
        // given
        shouldGetName_firstTime();
        reset(registration);

        when(registration.getNameById(anyString())).thenReturn("new_name");

        // when
        String name = cache.name("player");

        // then
        assertEquals("player-name", name);

        verify(registration, never()).getNameById("player");
    }

    @Test
    public void shouldGetName_notRegisteredUser() {
        // given
        with.login.register("player-ai", "ip", "room", "first");
        registration.remove("player-ai"); // no registration recprds
        reset(registration);
        cache.removeAll();

        // when
        String name = cache.name("player-ai");

        // then
        assertEquals("player[player-ai]", name);

        verify(registration, times(1)).getNameById("player-ai");
    }
}
