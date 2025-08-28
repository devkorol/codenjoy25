package com.codenjoy.dojo.sampletext.model;

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


import com.codenjoy.dojo.sampletext.TestGameSettings;
import com.codenjoy.dojo.sampletext.services.Event;
import com.codenjoy.dojo.sampletext.services.GameRunner;
import com.codenjoy.dojo.sampletext.services.GameSettings;
import com.codenjoy.dojo.services.EventListener;
import com.codenjoy.dojo.services.Game;
import com.codenjoy.dojo.services.dice.MockDice;
import com.codenjoy.dojo.services.multiplayer.Single;
import com.codenjoy.dojo.services.printer.PrinterFactory;
import com.codenjoy.dojo.utils.JsonUtils;
import com.codenjoy.dojo.utils.smart.SmartAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.codenjoy.dojo.sampletext.services.GameSettings.Keys.QUESTIONS;
import static com.codenjoy.dojo.utils.smart.SmartAssert.assertEquals;
import static org.mockito.Mockito.*;

public class MultiplayerTest {

    private EventListener listener1;
    private EventListener listener2;
    private EventListener listener3;
    private Game game1;
    private Game game2;
    private Game game3;
    private MockDice dice;
    private SampleText field;

    // появляется другие игроки, игра становится мультипользовательской
    @Before
    public void setup() {
        GameSettings settings = new TestGameSettings()
                .string(QUESTIONS,
                        "question1=answer1\n" +
                        "question2=answer2\n" +
                        "question3=answer3");

        dice = new MockDice();
        field = new SampleText(dice, settings);
        PrinterFactory factory = new GameRunner().getPrinterFactory();

        listener1 = mock(EventListener.class);
        game1 = new Single(new Player(listener1, settings), factory);
        game1.on(field);

        listener2 = mock(EventListener.class);
        game2 = new Single(new Player(listener2, settings), factory);
        game2.on(field);

        listener3 = mock(EventListener.class);
        game3 = new Single(new Player(listener3, settings), factory);
        game3.on(field);

        dice(1, 4);
        game1.newGame();

        dice(2, 2);
        game2.newGame();

        dice(3, 4);
        game3.newGame();
    }

    @After
    public void after() {
        SmartAssert.checkResult();
    }

    private void dice(Integer... next) {
        dice.then(next);
    }

    private void asrtFl1(String expected) {
        assertField(expected, game1);
    }

    private void assertField(String expected, Game game) {
        assertEquals(expected,
                JsonUtils.prettyPrint(game.getBoardAsString()));
    }

    private void asrtFl2(String expected) {
        assertField(expected, game2);
    }

    private void asrtFl3(String expected) {
        assertField(expected, game3);
    }

    // рисуем несколько игроков
    @Test
    public void shouldPrint() {
        asrtFl1("{\n" +
                "  'history':[],\n" +
                "  'level':0,\n" +
                "  'nextQuestion':'question1',\n" +
                "  'questions':[\n" +
                "    'question1'\n" +
                "  ]\n" +
                "}");

        asrtFl2("{\n" +
                "  'history':[],\n" +
                "  'level':0,\n" +
                "  'nextQuestion':'question1',\n" +
                "  'questions':[\n" +
                "    'question1'\n" +
                "  ]\n" +
                "}");

        asrtFl3("{\n" +
                "  'history':[],\n" +
                "  'level':0,\n" +
                "  'nextQuestion':'question1',\n" +
                "  'questions':[\n" +
                "    'question1'\n" +
                "  ]\n" +
                "}");
    }

    // Каждый игрок может упраыляться за тик игры независимо,
    // все их последние ходы применяются после тика любой борды
    @Test
    public void shouldJoystick() {
        // when
        game1.getJoystick().message("[wrong-message]");
        game1.getJoystick().message("[answer1]");

        game2.getJoystick().message("[answer1, answer2]");

        game3.getJoystick().message("[answer1, answer2, answer3]");

        field.tick();

        // then
        asrtFl1("{\n" +
                "  'history':[\n" +
                "    {\n" +
                "      'answer':'answer1',\n" +
                "      'question':'question1',\n" +
                "      'valid':true\n" +
                "    }\n" +
                "  ],\n" +
                "  'level':0,\n" +
                "  'nextQuestion':'question2',\n" +
                "  'questions':[\n" +
                "    'question1',\n" +
                "    'question2'\n" +
                "  ]\n" +
                "}");

        asrtFl2("{\n" +
                "  'history':[\n" +
                "    {\n" +
                "      'answer':'answer1',\n" +
                "      'question':'question1',\n" +
                "      'valid':true\n" +
                "    }\n" +
                "  ],\n" +
                "  'level':0,\n" +
                "  'nextQuestion':'question2',\n" +
                "  'questions':[\n" +
                "    'question1',\n" +
                "    'question2'\n" +
                "  ]\n" +
                "}");

        asrtFl3("{\n" +
                "  'history':[\n" +
                "    {\n" +
                "      'answer':'answer1',\n" +
                "      'question':'question1',\n" +
                "      'valid':true\n" +
                "    }\n" +
                "  ],\n" +
                "  'level':0,\n" +
                "  'nextQuestion':'question2',\n" +
                "  'questions':[\n" +
                "    'question1',\n" +
                "    'question2'\n" +
                "  ]\n" +
                "}");
    }

    // игроков можно удалять из игры
    @Test
    public void shouldRemove() {
        game3.close();

        field.tick();

        asrtFl1("{\n" +
                "  'history':[],\n" +
                "  'level':0,\n" +
                "  'nextQuestion':'question1',\n" +
                "  'questions':[\n" +
                "    'question1'\n" +
                "  ]\n" +
                "}");

        asrtFl2("{\n" +
                "  'history':[],\n" +
                "  'level':0,\n" +
                "  'nextQuestion':'question1',\n" +
                "  'questions':[\n" +
                "    'question1'\n" +
                "  ]\n" +
                "}");

        try {
            asrtFl3("{\n" +
                    "  'history':[],\n" +
                    "  'level':0,\n" +
                    "  'nextQuestion':'question1',\n" +
                    "  'questions':[\n" +
                    "    'question1'\n" +
                    "  ]\n" +
                    "}");
        } catch (IllegalStateException e) {
            assertEquals("No board for this player", e.getMessage());
        }
    }

    // игрока можно ресетнуть
    @Test
    public void shouldKill() {
        // given
        game1.getJoystick().message("[answer1]");
        game2.getJoystick().message("[answer1]");
        game3.getJoystick().message("[answer1]");

        field.tick();

        asrtFl1("{\n" +
                "  'history':[\n" +
                "    {\n" +
                "      'answer':'answer1',\n" +
                "      'question':'question1',\n" +
                "      'valid':true\n" +
                "    }\n" +
                "  ],\n" +
                "  'level':0,\n" +
                "  'nextQuestion':'question2',\n" +
                "  'questions':[\n" +
                "    'question1',\n" +
                "    'question2'\n" +
                "  ]\n" +
                "}");

        asrtFl2("{\n" +
                "  'history':[\n" +
                "    {\n" +
                "      'answer':'answer1',\n" +
                "      'question':'question1',\n" +
                "      'valid':true\n" +
                "    }\n" +
                "  ],\n" +
                "  'level':0,\n" +
                "  'nextQuestion':'question2',\n" +
                "  'questions':[\n" +
                "    'question1',\n" +
                "    'question2'\n" +
                "  ]\n" +
                "}");

        asrtFl3("{\n" +
                "  'history':[\n" +
                "    {\n" +
                "      'answer':'answer1',\n" +
                "      'question':'question1',\n" +
                "      'valid':true\n" +
                "    }\n" +
                "  ],\n" +
                "  'level':0,\n" +
                "  'nextQuestion':'question2',\n" +
                "  'questions':[\n" +
                "    'question1',\n" +
                "    'question2'\n" +
                "  ]\n" +
                "}");

        // when
        game1.newGame();
        field.tick();

        asrtFl1("{\n" +
                "  'history':[],\n" +
                "  'level':0,\n" +
                "  'nextQuestion':'question1',\n" +
                "  'questions':[\n" +
                "    'question1'\n" +
                "  ]\n" +
                "}");

        asrtFl2("{\n" +
                "  'history':[\n" +
                "    {\n" +
                "      'answer':'answer1',\n" +
                "      'question':'question1',\n" +
                "      'valid':true\n" +
                "    }\n" +
                "  ],\n" +
                "  'level':0,\n" +
                "  'nextQuestion':'question2',\n" +
                "  'questions':[\n" +
                "    'question1',\n" +
                "    'question2'\n" +
                "  ]\n" +
                "}");

        asrtFl3("{\n" +
                "  'history':[\n" +
                "    {\n" +
                "      'answer':'answer1',\n" +
                "      'question':'question1',\n" +
                "      'valid':true\n" +
                "    }\n" +
                "  ],\n" +
                "  'level':0,\n" +
                "  'nextQuestion':'question2',\n" +
                "  'questions':[\n" +
                "    'question1',\n" +
                "    'question2'\n" +
                "  ]\n" +
                "}");
    }

    // игрок может ответить правильно и неправильно
    @Test
    public void shouldEvents() {
        // given
        game1.getJoystick().message("[answer1]");
        game2.getJoystick().message("[answer2]");
        game3.getJoystick().message("[answer3]");

        field.tick();

        asrtFl1("{\n" +
                "  'history':[\n" +
                "    {\n" +
                "      'answer':'answer1',\n" +
                "      'question':'question1',\n" +
                "      'valid':true\n" +
                "    }\n" +
                "  ],\n" +
                "  'level':0,\n" +
                "  'nextQuestion':'question2',\n" +
                "  'questions':[\n" +
                "    'question1',\n" +
                "    'question2'\n" +
                "  ]\n" +
                "}");

        asrtFl2("{\n" +
                "  'history':[\n" +
                "    {\n" +
                "      'answer':'answer2',\n" +
                "      'expected':'answer1',\n" +
                "      'question':'question1',\n" +
                "      'valid':false\n" +
                "    }\n" +
                "  ],\n" +
                "  'level':0,\n" +
                "  'nextQuestion':'question1',\n" +
                "  'questions':[\n" +
                "    'question1'\n" +
                "  ]\n" +
                "}");

        asrtFl3("{\n" +
                "  'history':[\n" +
                "    {\n" +
                "      'answer':'answer3',\n" +
                "      'expected':'answer1',\n" +
                "      'question':'question1',\n" +
                "      'valid':false\n" +
                "    }\n" +
                "  ],\n" +
                "  'level':0,\n" +
                "  'nextQuestion':'question1',\n" +
                "  'questions':[\n" +
                "    'question1'\n" +
                "  ]\n" +
                "}");

        // then
        verify(listener1).event(Event.WIN);
        verify(listener2).event(Event.LOSE);
        verify(listener3).event(Event.LOSE);

        // when
        field.tick();

        // then
        verifyNoMoreInteractions(listener1);
        verifyNoMoreInteractions(listener2);
        verifyNoMoreInteractions(listener3);
    }
}
