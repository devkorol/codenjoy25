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

        // Сапер взорвался на бомбе.

    result[LL("BANG")] = LL('Ѡ');

        // На секунду после смерти героя поле открывается и можно
        // увидеть где были бомбы.

    result[LL("HERE_IS_BOMB")] = LL('☻');

        // ...а так же какие бомбы удалось нейтрализовать.

    result[LL("DESTROYED_BOMB")] = LL('x');

        // Сапер.

    result[LL("DETECTOR")] = LL('☺');

        // Флажок сапера, указывающий что тут вероятно бомба. Если он
        // был выставлен на мину - она тут нейтрализуется и цифры
        // вокруг обновятся. Если нет - штраф.

    result[LL("FLAG")] = LL('‼');

        // Туман - место где еще не бывал сапер. Там может быть бомба.

    result[LL("HIDDEN")] = LL('*');

        // Граница поля или препятствие для перемещения.

    result[LL("BORDER")] = LL('☼');

        // Вокруг этой клеточки нет бомб.

    result[LL("NONE")] = LL(' ');

        // Вокруг этой клеточки одна бомба.

    result[LL("ONE_MINE")] = LL('1');

        // Вокруг этой клеточки две бомбы.

    result[LL("TWO_MINES")] = LL('2');

        // Вокруг этой клеточки три бомбы.

    result[LL("THREE_MINES")] = LL('3');

        // Вокруг этой клеточки четыре бомбы.

    result[LL("FOUR_MINES")] = LL('4');

        // Вокруг этой клеточки пять бомб.

    result[LL("FIVE_MINES")] = LL('5');

        // Вокруг этой клеточки шесть бомб.

    result[LL("SIX_MINES")] = LL('6');

        // Вокруг этой клеточки семь бомб.

    result[LL("SEVEN_MINES")] = LL('7');

        // Вокруг этой клеточки восемь бомб.

    result[LL("EIGHT_MINES")] = LL('8');

    return result;
};

const ElementMap Element::Elements = Element::initialiseElements();
