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

        // Пустое место – место которое заполнится фишкой в следующий
        // тик.

    result[LL("NONE")] = LL(' ');

        // Граница поля, в игре не участвует (не перемещается).

    result[LL("BORDER")] = LL('☼');

        // Перемещаемая фишка 1.

    result[LL("ONE")] = LL('1');

        // Перемещаемая фишка 2.

    result[LL("TWO")] = LL('2');

        // Перемещаемая фишка 3.

    result[LL("THREE")] = LL('3');

        // Перемещаемая фишка 4.

    result[LL("FOUR")] = LL('4');

        // Перемещаемая фишка 5.

    result[LL("FIVE")] = LL('5');

        // Перемещаемая фишка 6.

    result[LL("SIX")] = LL('6');

        // Перемещаемая фишка 7.

    result[LL("SEVEN")] = LL('7');

        // Перемещаемая фишка 8.

    result[LL("EIGHT")] = LL('8');

        // Перемещаемая фишка 9.

    result[LL("NINE")] = LL('9');

    return result;
};

const ElementMap Element::Elements = Element::initialiseElements();
