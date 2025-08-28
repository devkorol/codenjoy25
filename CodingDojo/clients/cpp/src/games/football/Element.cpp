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

        // Пустое место, куда можно перейти игроку.

    result[LL("NONE")] = LL(' ');

        // Внешняя разметка поля, через которую нельзя пройти.

    result[LL("WALL")] = LL('☼');

        // Твой игроку.

    result[LL("HERO")] = LL('☺');

        // Игрок с мячом.

    result[LL("HERO_W_BALL")] = LL('☻');

        // Мяч в движении.

    result[LL("BALL")] = LL('*');

        // Мяч остановился.

    result[LL("STOPPED_BALL")] = LL('∙');

        // Верхние ворота.

    result[LL("TOP_GOAL")] = LL('┴');

        // Нижние ворота.

    result[LL("BOTTOM_GOAL")] = LL('┬');

        // Твои ворота.

    result[LL("MY_GOAL")] = LL('=');

        // Чужие ворота.

    result[LL("ENEMY_GOAL")] = LL('⌂');

        // Гол в ворота.

    result[LL("HITED_GOAL")] = LL('x');

        // Гол в твои ворота.

    result[LL("HITED_MY_GOAL")] = LL('#');

        // Игрок твоей команды.

    result[LL("TEAM_MEMBER")] = LL('♦');

        // Игрок твоей команды с мячем.

    result[LL("TEAM_MEMBER_W_BALL")] = LL('♥');

        // Игрок команды противников.

    result[LL("ENEMY")] = LL('♣');

        // Игрок команды противников с мячем.

    result[LL("ENEMY_W_BALL")] = LL('♠');

    return result;
};

const ElementMap Element::Elements = Element::initialiseElements();
