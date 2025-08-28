package com.codenjoy.dojo.games.excitebike;

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

import com.codenjoy.dojo.services.Direction;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class BoardTest {

    private Board board;

    private void givenBr(String board) {
        this.board = (Board) new Board().forString(board);
    }

    @Test(expected = IllegalArgumentException.class)
    public void valueOf_shouldThrowIllegalArgumentException_ifElementWithGivenCharDoesntExist() {
        // given
        char givenChar = '%';
        Board board = (Board) new Board().forString(" ");

        // when
        board.valueOf(givenChar);
    }

    @Test
    public void getMe_shouldReturnPointWithBikeElement() {
        givenBr("■■■■■" +
                " B > " +
                "  Ḃ |" +
                " ḃ ▼ " +
                "■■■■■");

        // when then
        assertEquals("[1,1]", board.getHero().toString());
    }

    @Test
    public void getMe_shouldReturnPointWithBikeFallenElement() {
        givenBr("b■■■■" +
                "   > " +
                "  Ḃ |" +
                " Ā ▼ " +
                "■■■■■");

        // when then
        assertEquals("[0,0]", board.getHero().toString());
    }

    @Test
    public void isGameOver_shouldReturnTrueIfThereIsBikeFallenElement() {
        givenBr("■■■■■" +
                "   > " +
                "  Ḃ |" +
                " Ā ▼ " +
                "■■■■■");

        // when then
        assertEquals(true, board.isGameOver());
    }

    @Test
    public void isGameOver_shouldReturnFalseIfThereIsNoBikeFallenElement() {
        givenBr("■■■■■" +
                " B >I" +
                "A Ḃ |" +
                " Ā ▼ " +
                "■■■■■");

        // when then
        assertEquals(false, board.isGameOver());
    }

    @Test
    public void checkNearMe_shouldReturnTrueIfThereIsOneOfExpectedElementsNearMeAtRightDirection() {
        givenBr("■■■■■" +
                " B>  " +
                "  Ḃ |" +
                " Ā ▼ " +
                "■■■■■");

        // when then
        assertEquals(true, board.checkNearMe(Direction.RIGHT, Element.ACCELERATOR));
    }

    @Test
    public void checkNearMe_shouldReturnTrueIfThereIsOneOfExpectedElementsNearMeAtDownDirection() {
        givenBr("■■■■■" +
                " Ā   " +
                " Ḃ | " +
                " B ▼ " +
                "■■■■■");

        // when then
        assertEquals(false, board.checkNearMe(Direction.UP, Element.OTHER_BIKE));
    }

    @Test
    public void checkNearMe_shouldReturnTrueIfThereIsOneOfExpectedElementsNearMeAtLeftDirection() {
        givenBr("■■■■■" +
                "╝B>  " +
                " Ḃ | " +
                " Ā ▼ " +
                "■■■■■");

        // when then
        assertEquals(true, board.checkNearMe(Direction.LEFT, Element.SPRINGBOARD_RIGHT_DOWN));
    }

    @Test
    public void checkNearMe_shouldReturnTrueIfThereIsOneOfExpectedElementsNearMeAtUpDirection() {
        givenBr("■■■■■" +
                "╝Ī>  " +
                " Ḃ | " +
                " B ▼ " +
                "■■■■■");

        // when then
        assertEquals(false, board.checkNearMe(Direction.DOWN, Element.FENCE));
    }

    @Test
    public void checkNearMe_shouldReturnTrueIfThereIsOneOfExpectedElementsNearMeAtRightRightUpDirection() {
        givenBr("■■■■■" +
                " B   " +
                "  Ḃ>|" +
                " Ā ▼ " +
                "■■■■■");

        // when then
        assertEquals(true, board.checkNearMe(Arrays.asList(Direction.RIGHT, Direction.RIGHT, Direction.UP), Element.ACCELERATOR));
    }

    @Test
    public void checkAtMe_shouldReturnTrue_ifAtMeIsGivenElement() {
        givenBr("■■■■■" +
                "╝Ī>  " +
                " Ḃ | " +
                " A ▼ " +
                "■■■■■");

        // when then
        assertEquals(true, board.checkAtMe(Element.BIKE_AT_ACCELERATOR));
    }

    @Test
    public void checkAtMe_shouldReturnFalse_ifAtMeIsNotGivenElement() {
        givenBr("■■■■■" +
                "╝Ī>  " +
                " Ḃ | " +
                " B ▼ " +
                "■■■■■");

        // when then
        assertEquals(false, board.checkAtMe(Element.BIKE_AT_ACCELERATOR));
    }
}