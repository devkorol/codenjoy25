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

        // Фишка 1. Герой ее может переместить на свое место.

    result[LL("A")] = LL('a');

        // Фишка 2.

    result[LL("B")] = LL('b');

        // Фишка 3.

    result[LL("C")] = LL('c');

        // Фишка 4.

    result[LL("D")] = LL('d');

        // Фишка 5.

    result[LL("E")] = LL('e');

        // Фишка 6.

    result[LL("F")] = LL('f');

        // Фишка 7.

    result[LL("G")] = LL('g');

        // Фишка 8.

    result[LL("H")] = LL('h');

        // Фишка 9.

    result[LL("I")] = LL('i');

        // Фишка 10.

    result[LL("J")] = LL('j');

        // Фишка 11.

    result[LL("K")] = LL('k');

        // Фишка 12.

    result[LL("L")] = LL('l');

        // Фишка 13.

    result[LL("M")] = LL('m');

        // Фишка 14.

    result[LL("N")] = LL('n');

        // Фишка 15.

    result[LL("O")] = LL('o');

        // Твой герой. Герой перемещает фишки меняясь с ними местами.

    result[LL("HERO")] = LL('+');

        // Стена. Препятствие для перемещения фишек.

    result[LL("WALL")] = LL('*');

    return result;
};

const ElementMap Element::Elements = Element::initialiseElements();
