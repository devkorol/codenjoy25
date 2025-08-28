package com.codenjoy.dojo.games.chess;

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

import static com.codenjoy.dojo.games.chess.Element.BARRIER;
import static com.codenjoy.dojo.games.chess.Element.SQUARE;
import static java.util.stream.Collectors.toList;

public class ElementUtils {

    public static List<Element> elements(Color color) {
        return Arrays.stream(Element.values())
                .filter(element -> color.equals(color(element)))
                .collect(toList());
    }

    public static Element[] pieces() {
        return Arrays.stream(Element.values())
                .filter(e -> e != SQUARE)
                .filter(e -> e != BARRIER)
                .toArray(Element[]::new);
    }

    public static Color color(Element element) {
        switch (element.name().split("_")[0]) {
            case "RED" : return Color.RED;
            case "BLACK" : return Color.BLACK;
            case "WHITE" : return Color.WHITE;
            case "BLUE" : return Color.BLUE;
            default: return null;
        }
    }
}