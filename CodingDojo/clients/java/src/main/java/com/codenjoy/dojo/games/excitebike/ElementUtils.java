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

import java.util.Arrays;
import java.util.LinkedList;

import static com.codenjoy.dojo.games.excitebike.Element.*;

public class ElementUtils {

    public static final Element[] stuff = new Element[]{
            NONE,
            FENCE,
            ACCELERATOR,
            INHIBITOR,
            OBSTACLE,
            LINE_CHANGER_UP,
            LINE_CHANGER_DOWN,
    };

    public static final Element[] springboard = new Element[]{
            SPRINGBOARD_LEFT_UP,
            SPRINGBOARD_LEFT,
            SPRINGBOARD_LEFT_DOWN,
            SPRINGBOARD_TOP,
            SPRINGBOARD_RIGHT_UP,
            SPRINGBOARD_RIGHT,
            SPRINGBOARD_RIGHT_DOWN,
    };

    public static final Element[] bikes = new Element[]{
            BIKE,
            BIKE_AT_ACCELERATOR,
            BIKE_AT_INHIBITOR,
            BIKE_AT_LINE_CHANGER_UP,
            BIKE_AT_LINE_CHANGER_DOWN,
            BIKE_AT_KILLED_BIKE,
            BIKE_AT_SPRINGBOARD_LEFT,
            BIKE_AT_SPRINGBOARD_LEFT_DOWN,
            BIKE_AT_SPRINGBOARD_RIGHT,
            BIKE_AT_SPRINGBOARD_RIGHT_DOWN,
            BIKE_IN_FLIGHT_FROM_SPRINGBOARD,
            BIKE_FALLEN,
            BIKE_FALLEN_AT_ACCELERATOR,
            BIKE_FALLEN_AT_INHIBITOR,
            BIKE_FALLEN_AT_LINE_CHANGER_UP,
            BIKE_FALLEN_AT_LINE_CHANGER_DOWN,
            BIKE_FALLEN_AT_FENCE,
            BIKE_FALLEN_AT_OBSTACLE,
    };

    public static final Element[] otherBikes = new Element[]{
            OTHER_BIKE,
            OTHER_BIKE_AT_ACCELERATOR,
            OTHER_BIKE_AT_INHIBITOR,
            OTHER_BIKE_AT_LINE_CHANGER_UP,
            OTHER_BIKE_AT_LINE_CHANGER_DOWN,
            OTHER_BIKE_AT_KILLED_BIKE,
            OTHER_BIKE_AT_SPRINGBOARD_LEFT,
            OTHER_BIKE_AT_SPRINGBOARD_LEFT_DOWN,
            OTHER_BIKE_AT_SPRINGBOARD_RIGHT,
            OTHER_BIKE_AT_SPRINGBOARD_RIGHT_DOWN,
            OTHER_BIKE_IN_FLIGHT_FROM_SPRINGBOARD,
            OTHER_BIKE_FALLEN,
            OTHER_BIKE_FALLEN_AT_ACCELERATOR,
            OTHER_BIKE_FALLEN_AT_INHIBITOR,
            OTHER_BIKE_FALLEN_AT_LINE_CHANGER_UP,
            OTHER_BIKE_FALLEN_AT_LINE_CHANGER_DOWN,
            OTHER_BIKE_FALLEN_AT_FENCE,
            OTHER_BIKE_FALLEN_AT_OBSTACLE,
    };

    public static final Element[] allBikes = new LinkedList<Element>(){{
        addAll(Arrays.asList(bikes));
        addAll(Arrays.asList(otherBikes));
    }}.toArray(Element[]::new);
    
    public static Element valueOf(char ch) {
        for (Element el : Element.values()) {
            if (el.ch() == ch) {
                return el;
            }
        }
        throw new IllegalArgumentException("No such element for " + ch);
    }
}
