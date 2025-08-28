package com.codenjoy.dojo.games.tetris;

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
import java.util.List;

import static java.util.stream.Collectors.toList;

public class ElementUtils {

    public static int index(Element element) {
        switch (element) {
            case BLUE:   return 2;
            case CYAN:   return 3;
            case ORANGE: return 4;
            case YELLOW: return 1;
            case GREEN:  return 5;
            case PURPLE: return 7;
            case RED:    return 6;
            case NONE:   return 0;
        }
        throw new IllegalArgumentException("Bad element: " + element.name());
    }

    public static Element[] valuesExcept(Element... excluded) {
        List<Element> list = Arrays.asList(excluded);
        return Arrays.stream(Element.values())
                .filter(el -> !list.contains(el))
                .collect(toList())
                .toArray(new Element[0]);
    }
}
