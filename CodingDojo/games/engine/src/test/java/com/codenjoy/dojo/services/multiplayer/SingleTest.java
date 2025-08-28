package com.codenjoy.dojo.services.multiplayer;

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


import com.codenjoy.dojo.services.Game;
import com.codenjoy.dojo.services.Joystick;
import com.codenjoy.dojo.services.hero.HeroData;
import com.codenjoy.dojo.services.printer.BoardReader;
import com.codenjoy.dojo.services.printer.Printer;
import com.codenjoy.dojo.services.printer.PrinterFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class SingleTest {

    private GameField field;
    private GamePlayer player;
    private PrinterFactory factory;
    private Joystick joystick;
    private Game game;
    private PlayerHero hero;
    private HeroData heroData;

    @Before
    public void setup() {
        field = mock(GameField.class);
        player = mock(GamePlayer.class);
        factory = mock(PrinterFactory.class);
        joystick = mock(Joystick.class);
        hero = mock(PlayerHero.class);
        heroData = mock(HeroData.class);

        when(player.getHero()).thenReturn(hero);

        game = new Single(player, factory);
        game.on(field);
    }

    @Test
    public void callJoystickFromPlayer_ifRealizedInPlayer() {
        // given
        when(player.getJoystick()).thenReturn(joystick);

        // when
        Joystick joystick = game.getJoystick();

        // then
        assertSame(this.joystick, joystick);
    }

    @Test
    public void callJoystickFromHero_ifNotRealizedInPlayer() {
        // given
        when(player.getJoystick()).thenReturn(null);

        // when
        Joystick joystick = game.getJoystick();

        // then
        assertSame(this.hero, joystick);
    }

    @Test
    public void callHeroDataFromPlayer_ifRealizedInPlayer() {
        // given
        when(player.getHeroData()).thenReturn(heroData);

        // when
        HeroData heroData = game.getHero();

        // then
        assertSame(this.heroData, heroData);
    }

    @Test
    public void callHeroDataFromHero_ifNotRealizedInPlayer_singleplayer() {
        // given
        when(player.getHeroData()).thenReturn(null);
        when(hero.getX()).thenReturn(3);
        when(hero.getY()).thenReturn(5);

        // when
        HeroData heroData = game.getHero();

        // then
        assertEquals("HeroDataImpl(level=0, coordinate=[3,5], " +
                        "isMultiplayer=false, additionalData=null)",
                heroData.toString());
    }

    @Test
    public void callHeroDataFromHero_ifNotRealizedInPlayer_multiplayer() {
        // given
        game = new Single(player, factory, MultiplayerType.MULTIPLE);
        game.on(field);

        when(player.getHeroData()).thenReturn(null);
        when(hero.getX()).thenReturn(7);
        when(hero.getY()).thenReturn(9);

        // when
        HeroData heroData = game.getHero();

        // then
        assertEquals("HeroDataImpl(level=0, coordinate=[7,9], " +
                        "isMultiplayer=true, additionalData=null)",
                heroData.toString());
    }

    @Test
    public void shouldRemovePlayerFromLastField_whenCreateNewGame() {
        // given
        game = new Single(player, factory, MultiplayerType.MULTIPLE);
        game.on(field);

        // when
        game.on(mock(GameField.class));

        // then
        verify(field).remove(player);
    }

    @Test
    public void shouldNotRemovePlayerFromLastField_whenCreateNewGameInTheSameField() {
        // given
        game = new Single(player, factory, MultiplayerType.MULTIPLE);
        game.on(field);

        // when
        game.on(field);

        // then
        verify(field, never()).remove(player);
    }

    @Test
    public void shouldNotRemovePlayerFromLastField_whenTryToCreateNewGameInNullField() {
        // given
        game = new Single(player, factory, MultiplayerType.MULTIPLE);
        game.on(field);

        // when
        game.on(null);

        // then
        verify(field, never()).remove(player);
    }

    @Test
    public void shouldGetBoardAsString() {
        // given
        Printer printer = mock(Printer.class);
        BoardReader boardReader = mock(BoardReader.class);
        when(factory.getPrinter(any(BoardReader.class), any(GamePlayer.class))).thenReturn(printer);
        when(printer.print(any())).thenReturn("board");
        when(field.reader()).thenReturn(boardReader);

        game = new Single(player, factory, MultiplayerType.MULTIPLE);
        game.on(field);

        // when
        Object board = game.getBoardAsString(new Object[] { true, 1, "string" });

        // then
        assertEquals("board", board.toString());
        verify(printer).print(new Object[] { true, 1, "string" });
    }

    @Test
    public void shouldSameBoard() {
        // given
        game = new Single(player, factory, MultiplayerType.MULTIPLE);
        game.on(field);

        when(field.sameBoard()).thenReturn(true);

        // when
        boolean result = game.sameBoard();

        // then
        assertEquals(true, result);
        verify(field).sameBoard();
    }
}
