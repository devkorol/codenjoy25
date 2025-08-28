package com.codenjoy.dojo.services.questionanswer.levels;

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


import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LevelsPoolImplTest {

    private Level level1 = mock(Level.class);
    private Level level2 = mock(Level.class);

    @Before
    public void setUp() throws Exception {
        setupLevel(level1, "d1", "q1=a1", "q2=a2", "q3=a3");
        setupLevel(level2, "d2", "q4=a4", "q5=a5");
    }

    private void setupLevel(Level level, String description, String... data) {
        List<String> questions = Arrays.stream(data)
                .map(qa -> qa.split("=")[0])
                .collect(toList());
        List<String> answers = Arrays.stream(data)
                .map(qa -> qa.split("=")[1])
                .collect(toList());
        when(level.getQuestions()).thenReturn(questions);
        when(level.getAnswers()).thenReturn(answers);
        when(level.size()).thenReturn(data.length);
        when(level.description()).thenReturn(Arrays.asList(description));
    }

    @Test
    public void shouldQuestionOneByOne_whenSingleLevel() {
        // given
        List<Level> levels = Arrays.asList(level1);
        LevelsPoolImpl pool = new LevelsPoolImpl(levels);
        pool.firstLevel();

        // then
        assertEquals("[q1]", pool.getQuestions().toString());
        assertEquals("q1", pool.getNextQuestion());
        assertEquals("a1", pool.getExpectedAnswer());
        assertEquals(false, pool.isLevelFinished());

        // when
        pool.nextQuestion();

        // then
        assertEquals("[q1, q2]", pool.getQuestions().toString());
        assertEquals("q2", pool.getNextQuestion());
        assertEquals("a2", pool.getExpectedAnswer());
        assertEquals(false, pool.isLevelFinished());

        // when
        pool.nextQuestion();

        // then
        assertEquals("[q1, q2, q3]", pool.getQuestions().toString());
        assertEquals("q3", pool.getNextQuestion());
        assertEquals("a3", pool.getExpectedAnswer());
        assertEquals(true, pool.isLevelFinished());

        // when
        pool.nextLevel();

        // then
        assertEquals("[]", pool.getQuestions().toString());
        assertEquals(null, pool.getNextQuestion());
        assertEquals(null, pool.getExpectedAnswer());
        assertEquals(true, pool.isLevelFinished());
    }

    @Test
    public void shouldQuestionOneByOne_whenMultipleLevel() {
        // given
        List<Level> levels = Arrays.asList(level1, level2);
        LevelsPoolImpl pool = new LevelsPoolImpl(levels);
        pool.firstLevel();

        // then
        assertEquals("[q1]", pool.getQuestions().toString());
        assertEquals("q1", pool.getNextQuestion());
        assertEquals("a1", pool.getExpectedAnswer());
        assertEquals(false, pool.isLevelFinished());

        // when
        pool.nextQuestion();

        // then
        assertEquals("[q1, q2]", pool.getQuestions().toString());
        assertEquals("q2", pool.getNextQuestion());
        assertEquals("a2", pool.getExpectedAnswer());
        assertEquals(false, pool.isLevelFinished());

        // when
        pool.nextQuestion();

        // then
        assertEquals("[q1, q2, q3]", pool.getQuestions().toString());
        assertEquals("q3", pool.getNextQuestion());
        assertEquals("a3", pool.getExpectedAnswer());
        assertEquals(true, pool.isLevelFinished());

        // when
        pool.nextLevel();

        // then
        assertEquals("[q4]", pool.getQuestions().toString());
        assertEquals("q4", pool.getNextQuestion());
        assertEquals("a4", pool.getExpectedAnswer());
        assertEquals(false, pool.isLevelFinished());

        // when
        pool.nextQuestion();

        // then
        assertEquals("[q4, q5]", pool.getQuestions().toString());
        assertEquals("q5", pool.getNextQuestion());
        assertEquals("a5", pool.getExpectedAnswer());
        assertEquals(true, pool.isLevelFinished());

        // when
        pool.nextLevel();

        // then
        assertEquals("[]", pool.getQuestions().toString());
        assertEquals(null, pool.getNextQuestion());
        assertEquals(null, pool.getExpectedAnswer());
        assertEquals(true, pool.isLevelFinished());
    }

    @Test
    public void shouldQuestionOneByOne_whenMultipleLevel_reverseOrder() {
        // given
        List<Level> levels = Arrays.asList(level2, level1);
        LevelsPoolImpl pool = new LevelsPoolImpl(levels);
        pool.firstLevel();

        // then
        assertEquals("[q4]", pool.getQuestions().toString());
        assertEquals("q4", pool.getNextQuestion());
        assertEquals("a4", pool.getExpectedAnswer());
        assertEquals(false, pool.isLevelFinished());

        // when
        pool.nextQuestion();

        // then
        assertEquals("[q4, q5]", pool.getQuestions().toString());
        assertEquals("q5", pool.getNextQuestion());
        assertEquals("a5", pool.getExpectedAnswer());
        assertEquals(true, pool.isLevelFinished());

        // when
        pool.nextLevel();

        // then
        assertEquals("[q1]", pool.getQuestions().toString());
        assertEquals("q1", pool.getNextQuestion());
        assertEquals("a1", pool.getExpectedAnswer());
        assertEquals(false, pool.isLevelFinished());

        // when
        pool.nextQuestion();

        // then
        assertEquals("[q1, q2]", pool.getQuestions().toString());
        assertEquals("q2", pool.getNextQuestion());
        assertEquals("a2", pool.getExpectedAnswer());
        assertEquals(false, pool.isLevelFinished());

        // when
        pool.nextQuestion();

        // then
        assertEquals("[q1, q2, q3]", pool.getQuestions().toString());
        assertEquals("q3", pool.getNextQuestion());
        assertEquals("a3", pool.getExpectedAnswer());
        assertEquals(true, pool.isLevelFinished());

        // when
        pool.nextLevel();

        // then
        assertEquals("[]", pool.getQuestions().toString());
        assertEquals(null, pool.getNextQuestion());
        assertEquals(null, pool.getExpectedAnswer());
        assertEquals(true, pool.isLevelFinished());
    }

    @Test
    public void shouldGetAnswersFromLevel() {
        // given
        List<Level> levels = Arrays.asList(level1);
        LevelsPoolImpl pool = new LevelsPoolImpl(levels);
        pool.firstLevel();

        when(level1.getAnswers()).thenReturn(Arrays.asList("a1", "a2", "a3"));

        // then
        assertEquals("[q1]", pool.getQuestions().toString());
        assertEquals("[a1]", pool.getAnswers().toString());

        // when
        pool.nextQuestion();

        // then
        assertEquals("[q1, q2]", pool.getQuestions().toString());
        assertEquals("[a1, a2]", pool.getAnswers().toString());

        // when
        pool.nextQuestion();

        // then
        assertEquals("[q1, q2, q3]", pool.getQuestions().toString());
        assertEquals("[a1, a2, a3]", pool.getAnswers().toString());

        // when
        assertEquals(true, pool.isLevelFinished());
        pool.nextLevel();

        // then
        assertEquals("[]", pool.getQuestions().toString());
        assertEquals("[]", pool.getAnswers().toString());
    }

    @Test
    public void shouldWork_isLastQuestion_caseOneLevel() {
        // given
        List<Level> levels = Arrays.asList(level1);
        LevelsPoolImpl pool = new LevelsPoolImpl(levels);
        pool.firstLevel();

        // then
        assertEquals("[q1]", pool.getQuestions().toString());
        assertEquals(false, pool.isLevelFinished());

        // when
        pool.nextQuestion();

        // then
        assertEquals("[q1, q2]", pool.getQuestions().toString());
        assertEquals(false, pool.isLevelFinished());

        // when
        pool.nextQuestion();

        // then
        assertEquals("[q1, q2, q3]", pool.getQuestions().toString());
        assertEquals(true, pool.isLevelFinished());

        // when
        pool.nextLevel();

        // then
        assertEquals("[]", pool.getQuestions().toString());
        assertEquals(true, pool.isLevelFinished());
    }

    @Test
    public void shouldWork_isLastQuestion_caseTwoLevels() {
        // given
        List<Level> levels = Arrays.asList(level1, level2);
        LevelsPoolImpl pool = new LevelsPoolImpl(levels);
        pool.firstLevel();

        // then
        assertEquals("[q1]", pool.getQuestions().toString());
        assertEquals(false, pool.isLevelFinished());

        // when
        pool.nextQuestion();

        // then
        assertEquals("[q1, q2]", pool.getQuestions().toString());
        assertEquals(false, pool.isLevelFinished());

        // when
        pool.nextQuestion();

        // then
        assertEquals("[q1, q2, q3]", pool.getQuestions().toString());
        assertEquals(true, pool.isLevelFinished());

        // when
        pool.nextLevel();

        // then
        assertEquals("[q4]", pool.getQuestions().toString());
        assertEquals(false, pool.isLevelFinished());

        // when
        pool.nextQuestion();

        // then
        assertEquals("[q4, q5]", pool.getQuestions().toString());
        assertEquals(true, pool.isLevelFinished());

        // when
        pool.nextLevel();

        // then
        assertEquals("[]", pool.getQuestions().toString());
        assertEquals(true, pool.isLevelFinished());
    }

    @Test
    public void shouldWork_getLevelIndex_getQuestionIndex() {
        // given
        List<Level> levels = Arrays.asList(level1, level2);
        LevelsPoolImpl pool = new LevelsPoolImpl(levels);
        pool.firstLevel();

        // then
        assertEquals("[q1]", pool.getQuestions().toString());
        assertEquals(0, pool.getLevelIndex());
        assertEquals(0, pool.getQuestionIndex());

        // when
        pool.nextQuestion();

        // then
        assertEquals("[q1, q2]", pool.getQuestions().toString());
        assertEquals(0, pool.getLevelIndex());
        assertEquals(1, pool.getQuestionIndex());

        // when
        pool.nextQuestion();

        // then
        assertEquals("[q1, q2, q3]", pool.getQuestions().toString());
        assertEquals(0, pool.getLevelIndex());
        assertEquals(2, pool.getQuestionIndex());

        // when
        assertEquals(true, pool.isLevelFinished());
        pool.nextLevel();

        // then
        assertEquals("[q4]", pool.getQuestions().toString());
        assertEquals(1, pool.getLevelIndex());
        assertEquals(0, pool.getQuestionIndex());

        // when
        pool.nextQuestion();

        // then
        assertEquals("[q4, q5]", pool.getQuestions().toString());
        assertEquals(1, pool.getLevelIndex());
        assertEquals(1, pool.getQuestionIndex());

        // when
        assertEquals(true, pool.isLevelFinished());
        pool.nextLevel();

        // then
        assertEquals("[]", pool.getQuestions().toString());
        assertEquals(2, pool.getLevelIndex());
        assertEquals(0, pool.getQuestionIndex());
    }

    @Test
    public void shouldgetDescriptionFromLevel() {
        // given
        List<Level> levels = Arrays.asList(level1, level2);
        LevelsPoolImpl pool = new LevelsPoolImpl(levels);
        pool.firstLevel();

        // then
        assertState(pool, "[q1]", "[d1]");

        // when
        pool.nextQuestion();

        // then
        assertState(pool, "[q1, q2]", "[d1]");

        // when
        pool.nextQuestion();

        // then
        assertState(pool, "[q1, q2, q3]", "[d1]");

        // when
        assertEquals(true, pool.isLevelFinished());
        pool.nextLevel();

        // then
        assertState(pool, "[q4]", "[d2]");

        // when
        pool.nextQuestion();

        // then
        assertState(pool, "[q4, q5]", "[d2]");

        // when
        assertEquals(true, pool.isLevelFinished());
        pool.nextLevel();

        // then
        assertState(pool, "[]", "[No more Levels. You win!]");
    }

    private void assertState(LevelsPoolImpl pool, String questions, String description) {
        assertEquals(questions, pool.getQuestions().toString());
        assertEquals(description, pool.getDescription().toString());
    }
}