package com.codenjoy.dojo.games.a2048;

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

import static com.codenjoy.dojo.games.a2048.Element.NONE;
import static com.codenjoy.dojo.games.a2048.Element._x;
import static java.util.stream.Collectors.toList;

public class ElementUtils {

    public static Element[] valuesExcept(Element... excluded) {
        List<Element> list = Arrays.asList(excluded);
        return Arrays.stream(Element.values())
                .filter(el -> !list.contains(el))
                .collect(toList())
                .toArray(new Element[0]);
    }

    public static int number(Element element) {
        if (element == NONE) {
            return 0;
        }
        if (element == _x) {
            return -1;
        }
        return Integer.parseInt(element.name().substring(1));
    }
}