package com.codenjoy.dojo.services.dice;

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

import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MockDiceTest {

    @Test
    public void shouldReturnAllNumbers_thenDefaultLastValue() {
        // given
        MockDice dice = new MockDice();

        // when
        dice.then(1, 2, 3, 4, 5);

        // then
        assertEquals(1, dice.next(100));
        assertEquals(2, dice.next(100));
        assertEquals(3, dice.next(100));
        assertEquals(4, dice.next(100));
        assertEquals(5, dice.next(100));
        assertEquals(5, dice.next(100));
        assertEquals(5, dice.next(100));
        assertEquals(5, dice.next(100));
        assertEquals(5, dice.next(100));
    }

    @Test
    public void shouldReturnAllNumbers_thenDefaultLastValue_caseSupplier() {
        // given
        MockDice dice = new MockDice();
        List<Integer> list = list(1, 2, 3, 4, 5);

        // when
        dice.then(max -> {
            assertEquals(Integer.valueOf(100), max);
            return next(list);
        });

        // then
        assertEquals(1, dice.next(100));
        assertEquals(2, dice.next(100));
        assertEquals(3, dice.next(100));
        assertEquals(4, dice.next(100));
        assertEquals(5, dice.next(100));
        assertEquals(0, dice.next(100));
        assertEquals(0, dice.next(100));
        assertEquals(0, dice.next(100));
        assertEquals(0, dice.next(100));
    }

    public static Integer next(List<Integer> list) {
        return !list.isEmpty() ? list.remove(0) : null;
    }

    public static List<Integer> list(Integer... numbers) {
        return new LinkedList<>(){{
            addAll(Arrays.asList(numbers));
        }};
    }

    @Test
    public void shouldReturnDefaultZero_whenNoSetup() {
        // given when
        MockDice dice = new MockDice();

        // then
        assertEquals(0, dice.next(100));
        assertEquals(0, dice.next(100));
        assertEquals(0, dice.next(100));
        assertEquals(0, dice.next(100));
        assertEquals(0, dice.next(100));
    }

    @Test
    public void shouldReturnAllNumbers_thenCustomSeries() {
        // given
        MockDice dice = new MockDice();

        // when
        dice.then(1, 2, 3, 4, 5, 6);
        dice.whenThen(101, 11, 12, 13, 14, 15);
        dice.whenThen(102, 21, 22, 23, 24);
        dice.whenThen(103, 31, 32, 33);

        // then
        assertEquals(1, dice.next(100));
        assertEquals(11, dice.next(101));
        assertEquals(21, dice.next(102));
        assertEquals(31, dice.next(103));

        assertEquals(2, dice.next(100));
        assertEquals(12, dice.next(101));
        assertEquals(22, dice.next(102));
        assertEquals(32, dice.next(103));

        assertEquals(3, dice.next(100));
        assertEquals(13, dice.next(101));
        assertEquals(23, dice.next(102));
        assertEquals(33, dice.next(103));

        assertEquals(4, dice.next(100));
        assertEquals(14, dice.next(101));
        assertEquals(24, dice.next(102));
        assertEquals(33, dice.next(103)); // default

        assertEquals(5, dice.next(100));
        assertEquals(15, dice.next(101));
        assertEquals(24, dice.next(102)); // default
        assertEquals(33, dice.next(103)); // default

        assertEquals(6, dice.next(100));
        assertEquals(15, dice.next(101)); // default
        assertEquals(24, dice.next(102)); // default
        assertEquals(33, dice.next(103)); // default

        assertEquals(6, dice.next(100));  // default
        assertEquals(15, dice.next(101)); // default
        assertEquals(24, dice.next(102)); // default
        assertEquals(33, dice.next(103)); // default

        assertEquals(6, dice.next(100));  // default
        assertEquals(15, dice.next(101)); // default
        assertEquals(24, dice.next(102)); // default
        assertEquals(33, dice.next(103)); // default

        assertEquals(6, dice.next(100));  // default
        assertEquals(15, dice.next(101)); // default
        assertEquals(24, dice.next(102)); // default
        assertEquals(33, dice.next(103)); // default
    }

    @Test
    public void shouldReturnAllNumbers_thenCustomSeries_caseSupplier() {
        // given
        MockDice dice = new MockDice();
        List<Integer> list1 = list(1, 2, 3, 4, 5, 6);
        List<Integer> list2 = list(11, 12, 13, 14, 15);
        List<Integer> list3 = list(21, 22, 23, 24);
        List<Integer> list4 = list(31, 32, 33);

        // when
        dice.then(max -> {
            assertEquals(Integer.valueOf(100), max);
            return next(list1);
        });
        dice.whenThen(101, max -> {
            assertEquals(Integer.valueOf(101), max);
            return next(list2);
        });
        dice.whenThen(102, max -> {
            assertEquals(Integer.valueOf(102), max);
            return next(list3);
        });
        dice.whenThen(103, max -> {
            assertEquals(Integer.valueOf(103), max);
            return next(list4);
        });

        // then
        assertEquals(1, dice.next(100));
        assertEquals(11, dice.next(101));
        assertEquals(21, dice.next(102));
        assertEquals(31, dice.next(103));

        assertEquals(2, dice.next(100));
        assertEquals(12, dice.next(101));
        assertEquals(22, dice.next(102));
        assertEquals(32, dice.next(103));

        assertEquals(3, dice.next(100));
        assertEquals(13, dice.next(101));
        assertEquals(23, dice.next(102));
        assertEquals(33, dice.next(103));

        assertEquals(4, dice.next(100));
        assertEquals(14, dice.next(101));
        assertEquals(24, dice.next(102));
        assertEquals(0, dice.next(103)); // default

        assertEquals(5, dice.next(100));
        assertEquals(15, dice.next(101));
        assertEquals(0, dice.next(102)); // default
        assertEquals(0, dice.next(103)); // default

        assertEquals(6, dice.next(100));
        assertEquals(0, dice.next(101)); // default
        assertEquals(0, dice.next(102)); // default
        assertEquals(0, dice.next(103)); // default

        assertEquals(0, dice.next(100)); // default
        assertEquals(0, dice.next(101)); // default
        assertEquals(0, dice.next(102)); // default
        assertEquals(0, dice.next(103)); // default

        assertEquals(0, dice.next(100)); // default
        assertEquals(0, dice.next(101)); // default
        assertEquals(0, dice.next(102)); // default
        assertEquals(0, dice.next(103)); // default

        assertEquals(0, dice.next(100)); // default
        assertEquals(0, dice.next(101)); // default
        assertEquals(0, dice.next(102)); // default
        assertEquals(0, dice.next(103)); // default
    }
}