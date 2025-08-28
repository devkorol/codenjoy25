package com.codenjoy.dojo.client;

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

import static com.codenjoy.dojo.client.AbstractLayeredBoard.Layers.*;
import static org.junit.Assert.assertEquals;

public class AbstractLayeredBoardTest {

    private AbstractLayeredBoard board;

    public static AbstractLayeredBoard board(String boardString) {
        return (AbstractLayeredBoard)new AbstractLayeredBoard<Element>(){
            @Override
            public Element[] elements() {
                return Element.values();
            }
        }.forString(boardString);
    }

    @Before
    public void before() {
        board = board("{\"layers\":[\"" +
                "1111" +
                "1221" +
                "1331" +
                "1111" +
                "\", \"" +
                "    " +
                " 4  " +
                "  4 " +
                "    " +
                "\"]}");
    }

    @Test
    public void shouldWork_toString() {
        assertEquals(
                "Board:\n" +
                "1111\n" +
                "1221\n" +
                "1331\n" +
                "1111\n" +
                "\n" +
                "    \n" +
                " 4  \n" +
                "  4 \n" +
                "    \n", board.toString());
    }

    @Test
    public void shouldWork_toString_whenThreeLayers() {
        board = board("{\"layers\":[\"" +
                "1111" +
                "1221" +
                "1331" +
                "1111" +
                "\", \"" +
                "    " +
                " 4  " +
                "  4 " +
                "    " +
                "\", \"" +
                "    " +
                "  5 " +
                " 5  " +
                "    " +
                "\"]}");

        assertEquals(
                "[[1, 1, 1, 1], " +
                "[1, 2, 3, 1], " +
                "[1, 2, 3, 1], " +
                "[1, 1, 1, 1]]",
                Arrays.deepToString(board.layer(LAYER1).field()));

        assertEquals(
                "[[ ,  ,  ,  ], " +
                "[ , 4,  ,  ], " +
                "[ ,  , 4,  ], " +
                "[ ,  ,  ,  ]]",
                Arrays.deepToString(board.layer(LAYER2).field()));

        assertEquals(
                "[[ ,  ,  ,  ], " +
                "[ ,  , 5,  ], " +
                "[ , 5,  ,  ], " +
                "[ ,  ,  ,  ]]",
                Arrays.deepToString(board.layer(LAYER3).field()));
    }

    @Test
    public void shouldWork_toString_whenOneLayer() {
        board = board(
                "1111" +
                "1221" +
                "1331" +
                "1111");

        assertEquals(
                "Board:\n" +
                "1111\n" +
                "1221\n" +
                "1331\n" +
                "1111\n", board.toString());
    }

    @Test
    public void shouldWork_canGetSourceJson() {
        board = board("{\"layers\":[\"" +
                "1111" +
                "1221" +
                "1331" +
                "1111" +
                "\", \"" +
                "    " +
                " 4  " +
                "  4 " +
                "    " +
                "\"], \"key\":\"value\"}");

        assertEquals("value", board.source.get("key"));
        assertEquals("[\"1111122113311111\",\"     4    4     \"]", board.source.getJSONArray("layers").toString());
    }

    @Test
    public void shouldWork_field() {
        assertEquals(
                "[[1, 1, 1, 1], " +
                "[1, 2, 3, 1], " +
                "[1, 2, 3, 1], " +
                "[1, 1, 1, 1]]",
                Arrays.deepToString(board.layer(LAYER1).field()));

        assertEquals(
                "[[ ,  ,  ,  ], " +
                "[ , 4,  ,  ], " +
                "[ ,  , 4,  ], " +
                "[ ,  ,  ,  ]]",
                Arrays.deepToString(board.layer(LAYER2).field()));
    }

    @Test
    public void shouldWork_getAt_layer1() {
        assertEquals(Element.ONE, board.layer(LAYER1).getAt( 0, 0));
        assertEquals(Element.TWO, board.layer(LAYER1).getAt( 2, 1));
        assertEquals(Element.THREE, board.layer(LAYER1).getAt( 2, 2));
    }

    @Test
    public void shouldWork_getAt_layer2() {
        assertEquals(Element.FOUR, board.layer(LAYER2).getAt( 2, 2));
        assertEquals(Element.NONE, board.layer(LAYER2).getAt( 1, 2));
    }

    @Test
    public void shouldWork_isAt_layer1() {
        AbstractLayeredBoard.GetLayer layer = board.layer(LAYER1);

        assertEquals(true, layer.isAt( 0, 0, Element.ONE));
        assertEquals(false, layer.isAt( 1, 1, Element.ONE));
        assertEquals(false, layer.isAt( 2, 2, Element.ONE));

        assertEquals(true, layer.isAt( 1, 1, Element.TWO, Element.THREE));
        assertEquals(true, layer.isAt( 2, 2, Element.TWO, Element.THREE));
        assertEquals(false, layer.isAt( 2, 2, Element.TWO, Element.ONE));
    }

    @Test
    public void shouldWork_isAt_layer2() {
        assertEquals(true, board.layer(LAYER2).isAt( 1, 1, Element.FOUR));
        assertEquals(false, board.layer(LAYER2).isAt( 1, 2, Element.FOUR));
        assertEquals(false, board.layer(LAYER2).isAt( 2, 2, Element.NONE));

        assertEquals(true, board.layer(LAYER2).isAt( 1, 1, Element.FOUR, Element.NONE));
        assertEquals(true, board.layer(LAYER2).isAt( 2, 2, Element.FOUR, Element.NONE));
        assertEquals(false, board.layer(LAYER2).isAt( 2, 2, Element.TWO, Element.ONE));
    }

    @Test
    public void shouldWork_isNear_layer1() {
        assertEquals(true, board.layer(LAYER1).isNear( 1, 1, Element.ONE));
        assertEquals(false, board.layer(LAYER1).isNear( 5, 5, Element.TWO));
    }

    @Test
    public void shouldWork_isNear_layer2() {
        assertEquals(true, board.layer(LAYER2).isNear( 1, 1, Element.NONE));
        assertEquals(false, board.layer(LAYER2).isNear( 0, 3, Element.FOUR));
    }

    @Test
    public void shouldWork_getNear_layer1() {
        assertEquals("[1, 1, 1, 1, 3, 1, 2, 3]", board.layer(LAYER1).getNear( 1, 1).toString());
        assertEquals("[2, 3, 1, 2, 1, 1, 1, 1]", board.layer(LAYER1).getNear( 2, 2).toString());
        assertEquals("[3, 1, 1]", board.layer(LAYER1).getNear( 3, 3).toString());
        assertEquals("[]", board.layer(LAYER1).getNear( 5, 5).toString());
        assertEquals("[1]", board.layer(LAYER1).getNear( -1, -1).toString());
    }

    @Test
    public void shouldWork_getNear_layer2() {
        assertEquals("[ ,  ,  ,  ,  ,  ,  , 4]", board.layer(LAYER2).getNear( 1, 1).toString());
        assertEquals("[4,  ,  ,  ,  ,  ,  ,  ]", board.layer(LAYER2).getNear( 2, 2).toString());
        assertEquals("[4,  ,  ]", board.layer(LAYER2).getNear( 3, 3).toString());
        assertEquals("[]", board.layer(LAYER2).getNear( 5, 5).toString());
        assertEquals("[ ]", board.layer(LAYER2).getNear( -1, -1).toString());
    }

    @Test
    public void shouldWork_isOutOfField() {
        assertEquals(true, board.isOutOf(-1, 1));
        assertEquals(true, board.isOutOf(1, -1));
        assertEquals(true, board.isOutOf(4, 1));
        assertEquals(true, board.isOutOf(1, 4));

        assertEquals(false, board.isOutOf(0, 1));
        assertEquals(false, board.isOutOf(1, 0));
        assertEquals(false, board.isOutOf(3, 1));
        assertEquals(false, board.isOutOf(1, 3));
    }

    @Test
    public void shouldWork_countNear_layer1() {
        assertEquals(2, board.layer(LAYER1).countNear( 0, 0, Element.ONE));
        assertEquals(1, board.layer(LAYER1).countNear( 0, 0, Element.TWO));
        assertEquals(0, board.layer(LAYER1).countNear( 0, 0, Element.THREE));

        assertEquals(5, board.layer(LAYER1).countNear( 1, 1, Element.ONE));
        assertEquals(1, board.layer(LAYER1).countNear( 1, 1, Element.TWO));
        assertEquals(2, board.layer(LAYER1).countNear( 1, 1, Element.THREE));

        assertEquals(5, board.layer(LAYER1).countNear( 2, 2, Element.ONE));
        assertEquals(2, board.layer(LAYER1).countNear( 2, 2, Element.TWO));
        assertEquals(1, board.layer(LAYER1).countNear( 2, 2, Element.THREE));

        assertEquals(2, board.layer(LAYER1).countNear( 3, 3, Element.ONE));
        assertEquals(0, board.layer(LAYER1).countNear( 3, 3, Element.TWO));
        assertEquals(1, board.layer(LAYER1).countNear( 3, 3, Element.THREE));

        assertEquals(0, board.layer(LAYER1).countNear( -1, -1, Element.THREE));
    }

    @Test
    public void shouldWork_countNear_layer2() {
        assertEquals(0, board.layer(LAYER2).countNear( 0, 0, Element.ONE));
        assertEquals(1, board.layer(LAYER2).countNear( 0, 0, Element.FOUR));
        assertEquals(2, board.layer(LAYER2).countNear( 0, 0, Element.NONE));

        assertEquals(0, board.layer(LAYER2).countNear( 1, 1, Element.ONE));
        assertEquals(1, board.layer(LAYER2).countNear( 1, 1, Element.FOUR));
        assertEquals(7, board.layer(LAYER2).countNear( 1, 1, Element.NONE));

        assertEquals(0, board.layer(LAYER2).countNear( 2, 1, Element.ONE));
        assertEquals(2, board.layer(LAYER2).countNear( 2, 1, Element.FOUR));
        assertEquals(6, board.layer(LAYER2).countNear( 2, 1, Element.NONE));

        assertEquals(0, board.layer(LAYER2).countNear( 3, 3, Element.ONE));
        assertEquals(1, board.layer(LAYER2).countNear( 3, 3, Element.FOUR));
        assertEquals(2, board.layer(LAYER2).countNear( 3, 3, Element.NONE));

        assertEquals(0, board.layer(LAYER2).countNear( -1, -1, Element.THREE));
    }

    @Test
    public void shouldWork_oneElement_get_layer1() {
        assertEquals("[[0,0], [0,1], [0,2], [0,3], [1,0], [1,3], " +
                        "[2,0], [2,3], [3,0], [3,1], [3,2], [3,3]]",
                board.layer(LAYER1).get( Element.ONE).toString());

        assertEquals("[[1,1], [2,1]]",
                board.layer(LAYER1).get( Element.TWO).toString());

        assertEquals("[[1,2], [2,2]]",
                board.layer(LAYER1).get( Element.THREE).toString());

        assertEquals("[]",
                board.layer(LAYER1).get( Element.NONE).toString());
    }

    @Test
    public void shouldWork_severalElements_get_layer1() {
        assertEquals("[[0,0], [0,1], [0,2], [0,3], [1,0], [1,1], " +
                        "[1,2], [1,3], [2,0], [2,1], [2,2], [2,3], " +
                        "[3,0], [3,1], [3,2], [3,3]]",
                board.layer(LAYER1).get( Element.ONE, Element.TWO, Element.THREE).toString());
    }

    @Test
    public void shouldWork_oneElement_get_layer2() {
        assertEquals("[]",
                board.layer(LAYER2).get( Element.ONE).toString());

        assertEquals("[[0,0], [0,1], [0,2], [0,3], " +
                        "[1,0], [1,2], [1,3], " +
                        "[2,0], [2,1], [2,3], " +
                        "[3,0], [3,1], [3,2], [3,3]]",
                board.layer(LAYER2).get( Element.NONE).toString());

        assertEquals("[[1,1], [2,2]]",
                board.layer(LAYER2).get( Element.FOUR).toString());
    }

    @Test
    public void shouldWork_severalElements_getFirst_layer2() {
        assertEquals("[0,0]",
                board.layer(LAYER2).getFirst( Element.ONE, Element.NONE, Element.FOUR).toString());
    }

    @Test
    public void shouldWork_oneElement_getFirst_layer1() {
        assertEquals("[0,0]",
                board.layer(LAYER1).getFirst( Element.ONE).toString());

        assertEquals("[1,1]",
                board.layer(LAYER1).getFirst( Element.TWO).toString());

        assertEquals("[1,2]",
                board.layer(LAYER1).getFirst( Element.THREE).toString());

        assertEquals(null,
                board.layer(LAYER1).getFirst( Element.NONE));
    }

    @Test
    public void shouldWork_severalElements_getFirst_layer1() {
        assertEquals("[0,0]",
                board.layer(LAYER1).getFirst( Element.ONE, Element.TWO, Element.THREE).toString());
    }

    @Test
    public void shouldWork_oneElement_getFirst_layer2() {
        assertEquals(null,
                board.layer(LAYER2).getFirst( Element.ONE));

        assertEquals("[0,0]",
                board.layer(LAYER2).getFirst( Element.NONE).toString());

        assertEquals("[1,1]",
                board.layer(LAYER2).getFirst( Element.FOUR).toString());
    }

    @Test
    public void shouldWork_severalElements_get_layer2() {
        assertEquals("[[0,0], [0,1], [0,2], [0,3], [1,0], " +
                        "[1,1], [1,2], [1,3], [2,0], [2,1], " +
                        "[2,2], [2,3], [3,0], [3,1], [3,2], [3,3]]",
                board.layer(LAYER2).get( Element.ONE, Element.NONE, Element.FOUR).toString());
    }

    @Test
    public void shouldWork_size() {
        assertEquals(4, board.size());
    }

    @Test
    public void shouldWork_set_layer1() {
        // given
        assertEquals("[[1,2], [2,2]]",
                board.layer(LAYER1).get( Element.THREE).toString());

        // when
        board.layer(LAYER1).set( 1, 1, Element.THREE.ch());

        // then
        assertEquals("[[1,1], [1,2], [2,2]]",
                board.layer(LAYER1).get( Element.THREE).toString());
    }

    @Test
    public void shouldWork_set_layer2() {
        // given
        assertEquals("[]",
                board.layer(LAYER2).get( Element.THREE).toString());

        // when
        board.layer(LAYER2).set( 1, 1, Element.THREE.ch());

        // then
        assertEquals("[[1,1]]",
                board.layer(LAYER2).get( Element.THREE).toString());
    }
}