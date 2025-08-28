package com.codenjoy.dojo.games.moebius;

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

import com.codenjoy.dojo.services.Dice;
import com.codenjoy.dojo.services.Point;

import java.util.HashMap;
import java.util.Map;

import static com.codenjoy.dojo.games.moebius.Element.*;
import static com.codenjoy.dojo.services.PointImpl.pt;

public class ElementUtils {

    public static final Map<Element, Point> from = new HashMap<>();
    public static final Map<Element, Point> to = new HashMap<>();

    static {
        add(LEFT_UP,    pt(-1, 0), pt(0, 1));
        add(UP_RIGHT,   pt(0, 1), pt(1, 0));
        add(RIGHT_DOWN, pt(1, 0), pt(0, -1));
        add(DOWN_LEFT,  pt(0, -1), pt(-1, 0));
        add(LEFT_RIGHT, pt(-1, 0),  pt(1, 0));
        add(UP_DOWN,    pt(0, 1),  pt(0, -1));
        add(CROSS,      null, null);
        add(EMPTY,      null, null);
    }

    private static void add(Element element, Point ptFrom, Point ptTo) {
        from.put(element, ptFrom);
        to.put(element, ptTo);
    }

    public static Element random(Dice dice) {
        return Element.values()[dice.next(Element.values().length - 1)];
    }
}
