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

//include "Element.h"

Element::Element(Char el) {
    elem.first = valueOf(el);
    elem.second = el;
}

Element::Element(String name) {
    elem.second = Elements.at(name);
    elem.first = name;
}

Char Element::ch() {
    return elem.second;
}

String Element::name() {
    return elem.first;
}

Char Element::getChar() const {
    return elem.second;
}

String Element::valueOf(Char ch) const {
    for (auto i : Elements) {
        if (i.second == ch) return i.first;
    }
    throw std::invalid_argument("Element::valueOf(Char ch): No such Element for " + ch);
}

bool Element::operator==(const Element& el) const {
    return elem == el.elem;
}

ElementMap Element::initialiseElements() {
    ElementMap result;

    result[LL("NONE")] = LL(' ');

    result[LL("FENCE")] = LL('■');

    result[LL("ACCELERATOR")] = LL('>');

    result[LL("INHIBITOR")] = LL('<');

    result[LL("OBSTACLE")] = LL('|');

    result[LL("LINE_CHANGER_UP")] = LL('▲');

    result[LL("LINE_CHANGER_DOWN")] = LL('▼');

    result[LL("SPRINGBOARD_LEFT_UP")] = LL('╔');

    result[LL("SPRINGBOARD_LEFT")] = LL('ˊ');

    result[LL("SPRINGBOARD_LEFT_DOWN")] = LL('╚');

    result[LL("SPRINGBOARD_TOP")] = LL('═');

    result[LL("SPRINGBOARD_RIGHT_UP")] = LL('╗');

    result[LL("SPRINGBOARD_RIGHT")] = LL('ˋ');

    result[LL("SPRINGBOARD_RIGHT_DOWN")] = LL('╝');

    result[LL("BIKE")] = LL('B');

    result[LL("BIKE_AT_ACCELERATOR")] = LL('A');

    result[LL("BIKE_AT_INHIBITOR")] = LL('I');

    result[LL("BIKE_AT_LINE_CHANGER_UP")] = LL('U');

    result[LL("BIKE_AT_LINE_CHANGER_DOWN")] = LL('D');

    result[LL("BIKE_AT_KILLED_BIKE")] = LL('K');

    result[LL("BIKE_AT_SPRINGBOARD_LEFT")] = LL('L');

    result[LL("BIKE_AT_SPRINGBOARD_LEFT_DOWN")] = LL('M');

    result[LL("BIKE_AT_SPRINGBOARD_RIGHT")] = LL('R');

    result[LL("BIKE_AT_SPRINGBOARD_RIGHT_DOWN")] = LL('S');

    result[LL("BIKE_IN_FLIGHT_FROM_SPRINGBOARD")] = LL('F');

    result[LL("BIKE_FALLEN")] = LL('b');

    result[LL("BIKE_FALLEN_AT_ACCELERATOR")] = LL('a');

    result[LL("BIKE_FALLEN_AT_INHIBITOR")] = LL('i');

    result[LL("BIKE_FALLEN_AT_LINE_CHANGER_UP")] = LL('u');

    result[LL("BIKE_FALLEN_AT_LINE_CHANGER_DOWN")] = LL('d');

    result[LL("BIKE_FALLEN_AT_FENCE")] = LL('f');

    result[LL("BIKE_FALLEN_AT_OBSTACLE")] = LL('o');

    result[LL("OTHER_BIKE")] = LL('Ḃ');

    result[LL("OTHER_BIKE_AT_ACCELERATOR")] = LL('Ā');

    result[LL("OTHER_BIKE_AT_INHIBITOR")] = LL('Ī');

    result[LL("OTHER_BIKE_AT_LINE_CHANGER_UP")] = LL('Ū');

    result[LL("OTHER_BIKE_AT_LINE_CHANGER_DOWN")] = LL('Ď');

    result[LL("OTHER_BIKE_AT_KILLED_BIKE")] = LL('Ḱ');

    result[LL("OTHER_BIKE_AT_SPRINGBOARD_LEFT")] = LL('Ĺ');

    result[LL("OTHER_BIKE_AT_SPRINGBOARD_LEFT_DOWN")] = LL('Ṁ');

    result[LL("OTHER_BIKE_AT_SPRINGBOARD_RIGHT")] = LL('Ř');

    result[LL("OTHER_BIKE_AT_SPRINGBOARD_RIGHT_DOWN")] = LL('Ŝ');

    result[LL("OTHER_BIKE_IN_FLIGHT_FROM_SPRINGBOARD")] = LL('Ḟ');

    result[LL("OTHER_BIKE_FALLEN")] = LL('ḃ');

    result[LL("OTHER_BIKE_FALLEN_AT_ACCELERATOR")] = LL('ā');

    result[LL("OTHER_BIKE_FALLEN_AT_INHIBITOR")] = LL('ī');

    result[LL("OTHER_BIKE_FALLEN_AT_LINE_CHANGER_UP")] = LL('ū');

    result[LL("OTHER_BIKE_FALLEN_AT_LINE_CHANGER_DOWN")] = LL('ď');

    result[LL("OTHER_BIKE_FALLEN_AT_FENCE")] = LL('ḟ');

    result[LL("OTHER_BIKE_FALLEN_AT_OBSTACLE")] = LL('ō');

    return result;
};

const ElementMap Element::Elements = Element::initialiseElements();
