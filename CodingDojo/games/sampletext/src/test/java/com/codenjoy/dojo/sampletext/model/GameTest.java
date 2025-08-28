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
import com.codenjoy.dojo.sampletext.services.GameSettings;
import com.codenjoy.dojo.services.EventListener;
import com.codenjoy.dojo.services.dice.MockDice;
import com.codenjoy.dojo.utils.JsonUtils;
import com.codenjoy.dojo.utils.smart.SmartAssert;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.codenjoy.dojo.sampletext.services.GameSettings.Keys.QUESTIONS;
import static com.codenjoy.dojo.utils.smart.SmartAssert.assertEquals;
import static org.mockito.Mockito.mock;

public class GameTest {

    private SampleText game;
    private Hero hero;
    private MockDice dice;
    private EventListener listener;
    private Player player;
    private GameSettings settings;

    @Before
    public void setup() {
        dice = new MockDice();
        settings = new TestGameSettings();
    }

    @After
    public void after() {
        SmartAssert.checkResult();
    }

    private void dice(Integer... next) {
        dice.then(next);
    }

    private void givenQA(String... questionAnswers) {
        settings.string(QUESTIONS,
                StringUtils.joinWith("\n", questionAnswers));
        game = new SampleText(dice, settings);
        listener = mock(EventListener.class);
        player = new Player(listener, settings);
        game.newGame(player);
        hero = player.getHero();
    }

    private void thenHistory(String expected) {
        assertEquals(expected,
                JsonUtils.prettyPrint(player.examiner().getHistory()));
    }

    @Test
    public void shouldNoAnswersAtStart() {
        givenQA("question1=answer1",
                "question2=answer2",
                "question3=answer3");

        thenHistory("[]");

        thenQuestions("[\n" +
                "  'question1'\n" +
                "]");
    }

    @Test
    public void shouldNoAnswersAtStartAfterTick() {
        givenQA("question1=answer1",
                "question2=answer2",
                "question3=answer3");

        // when
        game.tick();

        thenHistory("[]");

        thenQuestions("[\n" +
                "  'question1'\n" +
                "]");
    }

    @Test
    public void should_invalid() {
        givenQA("question1=answer1",
                "question2=answer2",
                "question3=answer3");

        // when
        hero.message("[wrong-answer]");
        game.tick();

        thenHistory("[\n" +
                "  {\n" +
                "    'questionAnswers':[\n" +
                "      {\n" +
                "        'answer':'wrong-answer',\n" +
                "        'expected':'answer1',\n" +
                "        'question':'question1',\n" +
                "        'valid':false\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "]");

        thenQuestions("[\n" +
                "  'question1'\n" +
                "]");
    }

    @Test
    public void should_invalid_invalid() {
        givenQA("question1=answer1",
                "question2=answer2",
                "question3=answer3");

        // when
        hero.message("[wrong-answer1]");
        game.tick();

        hero.message("[wrong-answer1, wrong-answer2]");
        game.tick();

        thenHistory("[\n" +
                "  {\n" +
                "    'questionAnswers':[\n" +
                "      {\n" +
                "        'answer':'wrong-answer1',\n" +
                "        'expected':'answer1',\n" +
                "        'question':'question1',\n" +
                "        'valid':false\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    'questionAnswers':[\n" +
                "      {\n" +
                "        'answer':'wrong-answer1',\n" +
                "        'expected':'answer1',\n" +
                "        'question':'question1',\n" +
                "        'valid':false\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "]");

        thenQuestions("[\n" +
                "  'question1'\n" +
                "]");
    }

    @Test
    public void should_invalid_valid() {
        givenQA("question1=answer1",
                "question2=answer2",
                "question3=answer3");

        // when
        hero.message("[wrong-answer]");
        game.tick();

        hero.message("[answer1]");
        game.tick();

        thenHistory("[\n" +
                "  {\n" +
                "    'questionAnswers':[\n" +
                "      {\n" +
                "        'answer':'wrong-answer',\n" +
                "        'expected':'answer1',\n" +
                "        'question':'question1',\n" +
                "        'valid':false\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    'questionAnswers':[\n" +
                "      {\n" +
                "        'answer':'answer1',\n" +
                "        'question':'question1',\n" +
                "        'valid':true\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "]");

        thenQuestions("[\n" +
                "  'question1',\n" +
                "  'question2'\n" +
                "]");
    }

    @Test
    public void should_invalid_valid_tick() {
        givenQA("question1=answer1",
                "question2=answer2",
                "question3=answer3");

        // when
        hero.message("[wrong-answer]");
        game.tick();

        hero.message("[answer1]");
        game.tick();

        game.tick();

        thenHistory("[\n" +
                "  {\n" +
                "    'questionAnswers':[\n" +
                "      {\n" +
                "        'answer':'wrong-answer',\n" +
                "        'expected':'answer1',\n" +
                "        'question':'question1',\n" +
                "        'valid':false\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    'questionAnswers':[\n" +
                "      {\n" +
                "        'answer':'answer1',\n" +
                "        'question':'question1',\n" +
                "        'valid':true\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "]");

        thenQuestions("[\n" +
                "  'question1',\n" +
                "  'question2'\n" +
                "]");
    }

    @Test
    public void should_invalid_valid_valid() {
        givenQA("question1=answer1",
                "question2=answer2",
                "question3=answer3");

        // when
        hero.message("[wrong-answer]");
        game.tick();

        hero.message("[answer1]");
        game.tick();

        hero.message("[answer1, answer2]");
        game.tick();

        thenHistory("[\n" +
                "  {\n" +
                "    'questionAnswers':[\n" +
                "      {\n" +
                "        'answer':'wrong-answer',\n" +
                "        'expected':'answer1',\n" +
                "        'question':'question1',\n" +
                "        'valid':false\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    'questionAnswers':[\n" +
                "      {\n" +
                "        'answer':'answer1',\n" +
                "        'question':'question1',\n" +
                "        'valid':true\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    'questionAnswers':[\n" +
                "      {\n" +
                "        'answer':'answer1',\n" +
                "        'question':'question1',\n" +
                "        'valid':true\n" +
                "      },\n" +
                "      {\n" +
                "        'answer':'answer2',\n" +
                "        'question':'question2',\n" +
                "        'valid':true\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "]");

        thenQuestions("[\n" +
                "  'question1',\n" +
                "  'question2',\n" +
                "  'question3'\n" +
                "]");
    }

    @Test
    public void should_invalid_valid_tick_valid_tick() {
        givenQA("question1=answer1",
                "question2=answer2",
                "question3=answer3");

        // when
        hero.message("[wrong-answer]");
        game.tick();

        hero.message("[answer1]");
        game.tick();

        game.tick();

        hero.message("[answer1, answer2]");
        game.tick();

        game.tick();

        thenHistory("[\n" +
                "  {\n" +
                "    'questionAnswers':[\n" +
                "      {\n" +
                "        'answer':'wrong-answer',\n" +
                "        'expected':'answer1',\n" +
                "        'question':'question1',\n" +
                "        'valid':false\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    'questionAnswers':[\n" +
                "      {\n" +
                "        'answer':'answer1',\n" +
                "        'question':'question1',\n" +
                "        'valid':true\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    'questionAnswers':[\n" +
                "      {\n" +
                "        'answer':'answer1',\n" +
                "        'question':'question1',\n" +
                "        'valid':true\n" +
                "      },\n" +
                "      {\n" +
                "        'answer':'answer2',\n" +
                "        'question':'question2',\n" +
                "        'valid':true\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "]");

        thenQuestions("[\n" +
                "  'question1',\n" +
                "  'question2',\n" +
                "  'question3'\n" +
                "]");
    }

    @Test
    public void shouldAfterLastQuestion() {
        givenQA("question1=answer1",
                "question2=answer2",
                "question3=answer3");

        // when
        hero.message("[answer1]");
        game.tick();

        hero.message("[answer1, answer2]");
        game.tick();

        hero.message("[answer1, answer2, answer3]");
        game.tick();

        hero.message("[answer1, answer2, answer3, answer4]");
        game.tick();

        thenHistory("[\n" +
                "  {\n" +
                "    'questionAnswers':[\n" +
                "      {\n" +
                "        'answer':'answer1',\n" +
                "        'question':'question1',\n" +
                "        'valid':true\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    'questionAnswers':[\n" +
                "      {\n" +
                "        'answer':'answer1',\n" +
                "        'question':'question1',\n" +
                "        'valid':true\n" +
                "      },\n" +
                "      {\n" +
                "        'answer':'answer2',\n" +
                "        'question':'question2',\n" +
                "        'valid':true\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    'questionAnswers':[\n" +
                "      {\n" +
                "        'answer':'answer1',\n" +
                "        'question':'question1',\n" +
                "        'valid':true\n" +
                "      },\n" +
                "      {\n" +
                "        'answer':'answer2',\n" +
                "        'question':'question2',\n" +
                "        'valid':true\n" +
                "      },\n" +
                "      {\n" +
                "        'answer':'answer3',\n" +
                "        'question':'question3',\n" +
                "        'valid':true\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    'questionAnswers':[]\n" +
                "  }\n" +
                "]");

        thenQuestions("[]");
    }

    private void thenQuestions(String expected) {
        assertEquals(expected,
                JsonUtils.prettyPrint(player.levels().getQuestions()));
    }
}
