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

        // Море, где живут морские враги. Море нужно делать сушей.

    result[LL("SEA")] = LL('.');

        // Ничейная суша, по которой можно передвигаться героям и
        // наземным врагам.

    result[LL("LAND")] = LL('X');

        // Твой герой.

    result[LL("HERO")] = LL('O');

        // Захваченная тобой суша.

    result[LL("HERO_LAND")] = LL('#');

        // След, который оставляет герой двигаясь по морю или по сушам
        // противника. Уязвим для морских врагов. После выхода героя на
        // сушу, море (и/или суша другого противника), очерченное
        // следом, превращается в сушу.

    result[LL("HERO_TRACE")] = LL('o');

        // Герой противника.

    result[LL("HOSTILE")] = LL('A');

        // Захваченные противниками суша.

    result[LL("HOSTILE_LAND")] = LL('@');

        // След, оставляемые противником во время захвата суши.

    result[LL("HOSTILE_TRACE")] = LL('a');

        // Морской враг.

    result[LL("MARINE_ENEMY")] = LL('M');

        // Сухопутный враг.

    result[LL("LAND_ENEMY")] = LL('L');

    return result;
};

const ElementMap Element::Elements = Element::initialiseElements();
