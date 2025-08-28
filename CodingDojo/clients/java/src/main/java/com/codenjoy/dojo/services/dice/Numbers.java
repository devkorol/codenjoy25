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

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public class Numbers implements Function<Integer, Integer> {

    private List<Integer> numbers;

    public Numbers(Integer... next) {
        this.numbers = toList(next);
    }

    private static List<Integer> toList(Integer[] array) {
        return new LinkedList<>(Arrays.asList(array));
    }

    @Override
    public Integer apply(Integer max) {
        return numbers.isEmpty()
                ? null
                : numbers.remove(0);
    }
}