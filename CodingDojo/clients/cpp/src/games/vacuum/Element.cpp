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

        // Чистая клетка локации. Проезд облагается штрафом.
        // Эффективный пылесос должен меньше гулять по чистым местам и
        // больше убираться.

    result[LL("NONE")] = LL(' ');

        // Сам робот-пылесос.

    result[LL("VACUUM")] = LL('O');

        // Стартовая точка робота-пылесоса на уровне.

    result[LL("START")] = LL('S');

        // Барьер, через который нельзя проехать.

    result[LL("BARRIER")] = LL('#');

        // Грязь/пыль, которую нужно очистить для того, чтобы пройти
        // уровень.

    result[LL("DUST")] = LL('*');

        // Переключатель движения влево. Заехать можно с любой стороны.
        // Автоматически поворачивает робота в сторону, в которую
        // указывает.

    result[LL("SWITCH_LEFT")] = LL('←');

        // Переключатель движения вправо. Заехать можно с любой
        // стороны. Автоматически поворачивает робота в сторону, в
        // которую указывает.

    result[LL("SWITCH_RIGHT")] = LL('→');

        // Переключатель движения вверх. Заехать можно с любой стороны.
        // Автоматически поворачивает робота в сторону, в которую
        // указывает.

    result[LL("SWITCH_UP")] = LL('↑');

        // Переключатель движения вниз. Заехать можно с любой стороны.
        // Автоматически поворачивает робота в сторону, в которую
        // указывает.

    result[LL("SWITCH_DOWN")] = LL('↓');

        // Ограничитель въезда. Заехать можно только слева. Выезжать с
        // ограничителя можно в любом направлении.

    result[LL("LIMITER_LEFT")] = LL('╡');

        // Ограничитель въезда. Заехать можно только справа. Выезжать с
        // ограничителя можно в любом направлении.

    result[LL("LIMITER_RIGHT")] = LL('╞');

        // Ограничитель въезда. Заехать можно только сверху. Выезжать с
        // ограничителя можно в любом направлении.

    result[LL("LIMITER_UP")] = LL('╨');

        // Ограничитель въезда. Заехать можно только снизу. Выезжать с
        // ограничителя можно в любом направлении.

    result[LL("LIMITER_DOWN")] = LL('╥');

        // Ограничитель въезда. Заехать можно как сверху, так и снизу.
        // Выезжать с ограничителя можно в любом направлении.

    result[LL("LIMITER_VERTICAL")] = LL('║');

        // Ограничитель въезда. Заехать можно как слева, так и справа.
        // Выезжать с ограничителя можно в любом направлении.

    result[LL("LIMITER_HORIZONTAL")] = LL('═');

        // Карусель. Работает одновременно и как переключатель движения
        // и как ограничитель въезда. Заехать можно слева или сверху,
        // повернет робота наверх или налево соответственно. После
        // проезда карусель поворачиваются по часовой стрелке.

    result[LL("ROUNDABOUT_LEFT_UP")] = LL('┘');

        // Карусель. Работает одновременно и как переключатель движения
        // и как ограничитель въезда. Заехать можно сверху или справа,
        // повернет робота направо или наверх соответственно. После
        // проезда карусель поворачиваются по часовой стрелке.

    result[LL("ROUNDABOUT_UP_RIGHT")] = LL('└');

        // Карусель. Работает одновременно и как переключатель движения
        // и как ограничитель въезда. Заехать можно справа или снизу,
        // повернет робота вниз или направо соответственно. После
        // проезда карусель поворачиваются по часовой стрелке.

    result[LL("ROUNDABOUT_RIGHT_DOWN")] = LL('┌');

        // Карусель. Работает одновременно и как переключатель движения
        // и как ограничитель въезда. Заехать можно снизу или слева,
        // повернет робота налево или вниз соответственно. После
        // проезда карусель поворачиваются по часовой стрелке.

    result[LL("ROUNDABOUT_DOWN_LEFT")] = LL('┐');

    return result;
};

const ElementMap Element::Elements = Element::initialiseElements();
