package com.codenjoy.dojo.services.algs;

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


import com.codenjoy.dojo.client.AbstractBoard;
import com.codenjoy.dojo.services.Direction;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.printer.CharElement;
import com.codenjoy.dojo.utils.TestUtils;
import org.junit.Test;

import static com.codenjoy.dojo.services.algs.DeikstraFindWayTest.Element.*;
import static org.junit.Assert.assertEquals;

public class DeikstraFindWayTest {

    @Test
    public void testFindShortestWay() {
        asrtWay("XXXXXXX\n" +
                "XS**  X\n" +
                "X  *  X\n" +
                "X  ** X\n" +
                "X   **X\n" +
                "X    FX\n" +
                "XXXXXXX\n");
    }

    @Test
    public void testFindShortestWay_ifThereAreTwoWays_equalsByDistance() {
        asrtWay("XXXXXXX\n" +
                "XS****X\n" +
                "X XXX*X\n" +
                "X XXX*X\n" +
                "X XXX*X\n" +
                "X    FX\n" +
                "XXXXXXX\n");
    }

    @Test
    public void testFindShortestWay_ifThereAreTwoWays_longAndShort_case1() {
        asrtWay("XXXXXXX\n" +
                "XS***FX\n" +
                "X XXX X\n" +
                "X  X  X\n" +
                "XX X XX\n" +
                "XX   XX\n" +
                "XXXXXXX\n");
    }

    @Test
    public void testFindShortestWay_ifThereAreTwoWays_longAndShort_case2() {
        asrtWay("XXXXXXX\n" +
                "XF***SX\n" +
                "X XXX X\n" +
                "X  X  X\n" +
                "XX X XX\n" +
                "XX   XX\n" +
                "XXXXXXX\n");
    }

    @Test
    public void testFindShortestWay_ifThereAreTwoWays_longAndShort_case3() {
        asrtWay("XXXXXXX\n" +
                "XX   XX\n" +
                "XX X XX\n" +
                "X  X  X\n" +
                "X XXX X\n" +
                "XS***FX\n" +
                "XXXXXXX\n");
    }

    @Test
    public void testFindShortestWay_ifThereAreTwoWays_longAndShort_case4() {
        asrtWay("XXXXXXX\n" +
                "XX   XX\n" +
                "XX X XX\n" +
                "X  X  X\n" +
                "X XXX X\n" +
                "XF***SX\n" +
                "XXXXXXX\n");
    }

    @Test
    public void testFindShortestWayWhenBrickOnWay() {
        asrtWay("XXXXXXX\n" +
                "XS**  X\n" +
                "X  *  X\n" +
                "XO ** X\n" +
                "X   **X\n" +
                "X    FX\n" +
                "XXXXXXX\n");

        asrtWay("XXXXXXX\n" +
                "XS**  X\n" +
                "X  *  X\n" +
                "XO ** X\n" +
                "X O **X\n" +
                "X    FX\n" +
                "XXXXXXX\n");

        asrtWay("XXXXXXX\n" +
                "XS**  X\n" +
                "X  *  X\n" +
                "XO ** X\n" +
                "X O **X\n" +
                "X   OFX\n" +
                "XXXXXXX\n");

        asrtWay("XXXXXXX\n" +
                "XS**  X\n" +
                "X  ** X\n" +
                "XO O* X\n" +
                "X O **X\n" +
                "X   OFX\n" +
                "XXXXXXX\n");

        asrtWay("XXXXXXX\n" +
                "XS**  X\n" +
                "X O*  X\n" +
                "XO *O X\n" +
                "X O***X\n" +
                "X   OFX\n" +
                "XXXXXXX\n");

        asrtWay("XXXXXXX\n" +
                "XS**  X\n" +
                "X O*  X\n" +
                "X  *O X\n" +
                "X O***X\n" +
                "X   OFX\n" +
                "XXXXXXX\n");

        asrtWay("XXXXXXX\n" +
                "XS    X\n" +
                "X*OO  X\n" +
                "X***O X\n" +
                "X O***X\n" +
                "X   OFX\n" +
                "XXXXXXX\n");

        asrtWay("XXXXXXX\n" +
                "XS*** X\n" +
                "X OO**X\n" +
                "X O O*X\n" +
                "X O  *X\n" +
                "X   OFX\n" +
                "XXXXXXX\n");

        asrtWay("XXXXXXX\n" +
                "XS****X\n" +
                "X OOO*X\n" +
                "X O O*X\n" +
                "X O  *X\n" +
                "X   OFX\n" +
                "XXXXXXX\n");

        asrtWay("XXXXXXX\n" +
                "XS   OX\n" +
                "X*OOO X\n" +
                "X*O O X\n" +
                "X*O***X\n" +
                "X***OFX\n" +
                "XXXXXXX\n");

        asrtWay("XXXXXXX\n" +
                "XS*** X\n" +
                "X  O* X\n" +
                "XO O* X\n" +
                "X O **X\n" +
                "X   OFX\n" +
                "XXXXXXX\n");

        asrtWay("XXXXXXX\n" +
                "XS**  X\n" +
                "X O*  X\n" +
                "X  ***X\n" +
                "X   O*X\n" +
                "X    FX\n" +
                "XXXXXXX\n");

        asrtWay("XXXXXXX\n" +
                "XS O  X\n" +
                "X*O   X\n" +
                "X*****X\n" +
                "X   O*X\n" +
                "X    FX\n" +
                "XXXXXXX\n");

        asrtWay("XXXXXXX\n" +
                "XS O  X\n" +
                "X*O   X\n" +
                "X**O  X\n" +
                "X **O X\n" +
                "X  **FX\n" +
                "XXXXXXX\n");

        asrtWay("XXXXXXX\n" +
                "XS O  X\n" +
                "X*O   X\n" +
                "X* O  X\n" +
                "X*O O X\n" +
                "X****FX\n" +
                "XXXXXXX\n");
    }

    @Test
    public void testFindShortestWayWhenNoWay() {
        asrtWay("XXXXXXX\n" +
                "XSX   X\n" +
                "XXX   X\n" +
                "X     X\n" +
                "X     X\n" +
                "X    FX\n" +
                "XXXXXXX\n");

        asrtWay("XXXXXXX\n" +
                "XSO   X\n" +
                "XOO   X\n" +
                "X     X\n" +
                "X     X\n" +
                "X    FX\n" +
                "XXXXXXX\n");

        asrtWay("XXXXXXX\n" +
                "XS O  X\n" +
                "X  O  X\n" +
                "XOOO  X\n" +
                "X     X\n" +
                "X    FX\n" +
                "XXXXXXX\n");

        asrtWay("XXXXXXX\n" +
                "XS X  X\n" +
                "X  X  X\n" +
                "XXXX  X\n" +
                "X     X\n" +
                "X    FX\n" +
                "XXXXXXX\n");
    }

    @Test
    public void testFindShortestWayWhenOnlyOneDirectionAllowed() {
        String board =
                "XXXXXXX\n" +
                "XS˅F˂OX\n" +
                "X˃˅O˄OX\n" +
                "XO˅O˄OX\n" +
                "XO˃˃˄OX\n" +
                "XOOOOOX\n" +
                "XXXXXXX\n";

        assertP(board,
                "{[1,4]=[RIGHT], \n" +
                "[1,5]=[RIGHT, DOWN], \n" +
                "[2,2]=[RIGHT], \n" +
                "[2,3]=[DOWN], \n" +
                "[2,4]=[DOWN], \n" +
                "[2,5]=[DOWN], \n" +
                "[3,2]=[RIGHT], \n" +
                "[4,2]=[UP], \n" +
                "[4,3]=[UP], \n" +
                "[4,4]=[UP], \n" +
                "[4,5]=[LEFT]}");

        asrtWay(board,
                "XXXXXXX\n" +
                "XS*F*OX\n" +
                "X˃*O*OX\n" +
                "XO*O*OX\n" +
                "XO***OX\n" +
                "XOOOOOX\n" +
                "XXXXXXX\n");
    }

    @Test
    public void testOnlyRight() {
        String board =
                "XXXXX\n" +
                "XXXXX\n" +
                "XS˃FX\n" +
                "XXXXX\n" +
                "XXXXX\n";

        assertP(board,
                "{[1,2]=[RIGHT], \n" +
                "[2,2]=[RIGHT]}");

        asrtWay(board,
                "XXXXX\n" +
                "XXXXX\n" +
                "XS*FX\n" +
                "XXXXX\n" +
                "XXXXX\n");
    }

    @Test
    public void testOnlyLeft() {
        String board =
                "XXXXX\n" +
                "XXXXX\n" +
                "XF˂SX\n" +
                "XXXXX\n" +
                "XXXXX\n";

        assertP(board,
                "{[2,2]=[LEFT], \n" +
                "[3,2]=[LEFT]}");

        asrtWay(board,
                "XXXXX\n" +
                "XXXXX\n" +
                "XF*SX\n" +
                "XXXXX\n" +
                "XXXXX\n");
    }

    @Test
    public void testOnlyUp() {
        String board =
                "XXXXX\n" +
                "XXFXX\n" +
                "XX˄XX\n" +
                "XXSXX\n" +
                "XXXXX\n";

        assertP(board,
                "{[2,1]=[UP], \n" +
                "[2,2]=[UP]}");

        asrtWay(board,
                "XXXXX\n" +
                "XXFXX\n" +
                "XX*XX\n" +
                "XXSXX\n" +
                "XXXXX\n");
    }

    @Test
    public void testOnlyDown() {
        String board =
                "XXXXX\n" +
                "XXSXX\n" +
                "XX˅XX\n" +
                "XXFXX\n" +
                "XXXXX\n";

        assertP(board,
                "{[2,2]=[DOWN], \n" +
                "[2,3]=[DOWN]}");

        asrtWay(board,
                "XXXXX\n" +
                "XXSXX\n" +
                "XX*XX\n" +
                "XXFXX\n" +
                "XXXXX\n");
    }


    enum Element implements CharElement {
        ONLY_UP('˄'),
        ONLY_DOWN('˅'),
        ONLY_LEFT('˂'),
        ONLY_RIGHT('˃'),

        WAY('*'),

        START('S'),
        FINISH('F'),

        WALL('X'),
        BRICK('O'),
        NONE(' ');

        final char ch;

        Element(char ch) {
            this.ch = ch;
        }

        @Override
        public char ch() {
            return ch;
        }
    }

    private void asrtWay(String expected) {
        asrtWay(expected, expected);
    }

    private void asrtWay(String map, String expected) {
        assertEquals(expected,
                TestUtils.printWay(map,
                        START, FINISH,
                        NONE, WAY,
                        TestUtils.getBoard(Element.values()),
                        this::getPossible));
    }

    private void assertP(String inputBoard, String expected) {
        assertEquals(expected,
                TestUtils.getWay(inputBoard,
                        Element.values(),
                        this::getPossible));
    }

    private <T extends AbstractBoard> DeikstraFindWay.Possible getPossible(T board) {
        return new DeikstraFindWay.Possible() {
            @Override
            public boolean possible(Point from, Direction where) {
                if (board.isAt(from, FINISH)) return false;
                Point dest = where.change(from);
                if (board.isAt(dest, START)) return false;

                if (board.isAt(from, ONLY_UP, ONLY_DOWN, ONLY_LEFT, ONLY_RIGHT)) {
                    if (where == Direction.UP && !board.isAt(from, ONLY_UP)) return false;
                    if (where == Direction.DOWN && !board.isAt(from, ONLY_DOWN)) return false;
                    if (where == Direction.LEFT && !board.isAt(from, ONLY_LEFT)) return false;
                    if (where == Direction.RIGHT && !board.isAt(from, ONLY_RIGHT)) return false;
                }
                return true;
            }

            @Override
            public boolean possible(Point point) {
                if (board.isAt(point, BRICK)) return false;
                if (board.isAt(point, WALL)) return false;
                return true;
            }
        };
    }

}
