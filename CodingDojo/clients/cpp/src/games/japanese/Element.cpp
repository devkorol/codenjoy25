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

        // Игрок утверждает, что пиксель белый.

    result[LL("WHITE")] = LL('-');

        // Игрок утверждает, что пиксель черный.

    result[LL("BLACK")] = LL('*');

        // Игрок пока не определился, какого цвета этот пиксель.

    result[LL("UNSET")] = LL(' ');

        // Пустое место в полое для цифр.

    result[LL("NAN")] = LL('.');

        // Блок отсутствует.

    result[LL("_0")] = LL('0');

        // Блок длинной в 1 пиксель.

    result[LL("_1")] = LL('1');

        // Блок длинной в 2 пикселя.

    result[LL("_2")] = LL('2');

        // Блок длинной в 3 пикселя.

    result[LL("_3")] = LL('3');

        // Блок длинной в 4 пикселя.

    result[LL("_4")] = LL('4');

        // Блок длинной в 5 пикселей.

    result[LL("_5")] = LL('5');

        // Блок длинной в 6 пикселей.

    result[LL("_6")] = LL('6');

        // Блок длинной в 7 пикселей.

    result[LL("_7")] = LL('7');

        // Блок длинной в 8 пикселей.

    result[LL("_8")] = LL('8');

        // Блок длинной в 9 пикселей.

    result[LL("_9")] = LL('9');

        // Блок длинной в 10 пикселей.

    result[LL("_10")] = LL('a');

        // Блок длинной в 11 пикселей.

    result[LL("_11")] = LL('b');

        // Блок длинной в 12 пикселей.

    result[LL("_12")] = LL('c');

        // Блок длинной в 13 пикселей.

    result[LL("_13")] = LL('d');

        // Блок длинной в 14 пикселей.

    result[LL("_14")] = LL('e');

        // Блок длинной в 15 пикселей.

    result[LL("_15")] = LL('f');

        // Блок длинной в 16 пикселей.

    result[LL("_16")] = LL('g');

        // Блок длинной в 17 пикселей.

    result[LL("_17")] = LL('h');

        // Блок длинной в 18 пикселей.

    result[LL("_18")] = LL('i');

        // Блок длинной в 19 пикселей.

    result[LL("_19")] = LL('j');

        // Блок длинной в 20 пикселей.

    result[LL("_20")] = LL('k');

        // Блок длинной в 21 пиксель.

    result[LL("_21")] = LL('l');

        // Блок длинной в 22 пикселя.

    result[LL("_22")] = LL('m');

        // Блок длинной в 23 пикселя.

    result[LL("_23")] = LL('n');

        // Блок длинной в 24 пикселя.

    result[LL("_24")] = LL('o');

        // Блок длинной в 25 пикселей.

    result[LL("_25")] = LL('p');

        // Блок длинной в 26 пикселей.

    result[LL("_26")] = LL('q');

        // Блок длинной в 27 пикселей.

    result[LL("_27")] = LL('r');

        // Блок длинной в 28 пикселей.

    result[LL("_28")] = LL('s');

        // Блок длинной в 29 пикселей.

    result[LL("_29")] = LL('t');

        // Блок длинной в 30 пикселей.

    result[LL("_30")] = LL('u');

        // Блок длинной в 31 пиксель.

    result[LL("_31")] = LL('v');

        // Блок длинной в 32 пикселя.

    result[LL("_32")] = LL('w');

        // Блок длинной в 33 пикселя.

    result[LL("_33")] = LL('x');

        // Блок длинной в 34 пикселя.

    result[LL("_34")] = LL('y');

        // Блок длинной в 35 пикселей.

    result[LL("_35")] = LL('z');

        // Блок длинной в 36 пикселей.

    result[LL("_36")] = LL('A');

        // Блок длинной в 37 пикселей.

    result[LL("_37")] = LL('B');

        // Блок длинной в 38 пикселей.

    result[LL("_38")] = LL('C');

        // Блок длинной в 39 пикселей.

    result[LL("_39")] = LL('D');

        // Блок длинной в 40 пикселей.

    result[LL("_40")] = LL('E');

        // Блок длинной в 41 пиксель.

    result[LL("_41")] = LL('F');

        // Блок длинной в 42 пикселя.

    result[LL("_42")] = LL('G');

        // Блок длинной в 43 пикселя.

    result[LL("_43")] = LL('H');

        // Блок длинной в 44 пикселя.

    result[LL("_44")] = LL('I');

        // Блок длинной в 45 пикселей.

    result[LL("_45")] = LL('J');

        // Блок длинной в 46 пикселей.

    result[LL("_46")] = LL('K');

        // Блок длинной в 47 пикселей.

    result[LL("_47")] = LL('L');

        // Блок длинной в 48 пикселей.

    result[LL("_48")] = LL('M');

        // Блок длинной в 49 пикселей.

    result[LL("_49")] = LL('N');

        // Блок длинной в 50 пикселей.

    result[LL("_50")] = LL('O');

        // Блок длинной в 51 пиксель.

    result[LL("_51")] = LL('P');

        // Блок длинной в 52 пикселя.

    result[LL("_52")] = LL('Q');

        // Блок длинной в 53 пикселя.

    result[LL("_53")] = LL('R');

        // Блок длинной в 54 пикселя.

    result[LL("_54")] = LL('S');

        // Блок длинной в 55 пикселей.

    result[LL("_55")] = LL('T');

        // Блок длинной в 56 пикселей.

    result[LL("_56")] = LL('U');

        // Блок длинной в 57 пикселей.

    result[LL("_57")] = LL('V');

        // Блок длинной в 58 пикселей.

    result[LL("_58")] = LL('W');

        // Блок длинной в 59 пикселей.

    result[LL("_59")] = LL('X');

        // Блок длинной в 60 пикселей.

    result[LL("_60")] = LL('Y');

        // Блок длинной в 61 пиксель.

    result[LL("_61")] = LL('Z');

    return result;
};

const ElementMap Element::Elements = Element::initialiseElements();
