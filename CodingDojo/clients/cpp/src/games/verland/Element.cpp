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

        // Мой герой заразился инфекцией.

    result[LL("HERO_DEAD")] = LL('X');

        // Мой герой.

    result[LL("HERO")] = LL('♥');

        // Попытка моим героем зачистить инфекцию. Если инфекция была
        // устранена ситуация вокруг обновится. Если герой ошибся и
        // зона была не инфицирована - штраф.

    result[LL("HERO_CURE")] = LL('!');

        // На секунду после окончания игры поле открывается и можно
        // увидеть какую инфекцию удалось обеззаразить герою.

    result[LL("HERO_HEALING")] = LL('x');

        // Герой из моей команды заразился инфекцией.

    result[LL("OTHER_HERO_DEAD")] = LL('Y');

        // Герой из моей команды в работе.

    result[LL("OTHER_HERO")] = LL('♠');

        // Попытка героем из моей команды зачистить инфекцию. Если
        // инфекция была устранена ситуация вокруг обновится.  Если
        // герой ошибся и зона была не инфицирована - штраф.

    result[LL("OTHER_HERO_CURE")] = LL('+');

        // На секунду после окончания игры поле открывается и можно
        // увидеть какую инфекцию удалось обеззаразить герою из моей
        // команды.

    result[LL("OTHER_HERO_HEALING")] = LL('y');

        // Вражеский герой заразился инфекцией.

    result[LL("ENEMY_HERO_DEAD")] = LL('Z');

        // Вражеский герой в работе.

    result[LL("ENEMY_HERO")] = LL('♣');

        // На секунду после окончания игры поле открывается и можно
        // увидеть какую инфекцию удалось обеззаразить вражескому
        // герою.

    result[LL("ENEMY_HERO_HEALING")] = LL('z');

        // На секунду после смерти героя поле открывается и можно
        // увидеть где была инфекция.

    result[LL("INFECTION")] = LL('o');

        // Туман - место где еще не бывал герой. Возможно эта зона
        // инфицирована.

    result[LL("HIDDEN")] = LL('*');

        // Непроходимые территории - обычно граница поля, но может быть
        // и простое на пути героя.

    result[LL("PATHLESS")] = LL('☼');

        // Вокруг этой зоны нет заражений.

    result[LL("CLEAR")] = LL(' ');

        // Вокруг этой зоны было зафиксировано одно заражение.

    result[LL("CONTAGION_ONE")] = LL('1');

        // Вокруг этой зоны было зафиксировано два заражения.

    result[LL("CONTAGION_TWO")] = LL('2');

        // Вокруг этой зоны было зафиксировано три заражения.

    result[LL("CONTAGION_THREE")] = LL('3');

        // Вокруг этой зоны было зафиксировано четыре заражения.

    result[LL("CONTAGION_FOUR")] = LL('4');

        // Вокруг этой зоны было зафиксировано пять заражений.

    result[LL("CONTAGION_FIVE")] = LL('5');

        // Вокруг этой зоны было зафиксировано шесть заражений.

    result[LL("CONTAGION_SIX")] = LL('6');

        // Вокруг этой зоны было зафиксировано семь заражений.

    result[LL("CONTAGION_SEVEN")] = LL('7');

        // Вокруг этой зоны было зафиксировано восемь заражений.

    result[LL("CONTAGION_EIGHT")] = LL('8');

    return result;
};

const ElementMap Element::Elements = Element::initialiseElements();
