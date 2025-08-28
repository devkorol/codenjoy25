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

        // Empty space - space where the hero can move.

    result[LL("NONE")] = LL(' ');

        // Impassable obstacle.

    result[LL("ROCK")] = LL('☼');

        // Respawn point from which the hero starts its movement.

    result[LL("START_SPOT")] = LL('#');

        // Blueberry.

    result[LL("BLUEBERRY")] = LL('○');

        // Acorn.

    result[LL("ACORN")] = LL('●');

        // Death cap. Brings the player into flight mode, which gives
        // him the ability to avoid obstacles.

    result[LL("DEATH_CAP")] = LL('©');

        // Fly agaric. Brings the player into a fury, which gives him
        // an advantage when clashing.

    result[LL("FLY_AGARIC")] = LL('®');

        // Strawberry.

    result[LL("STRAWBERRY")] = LL('$');

        // Your hero is pointing down.

    result[LL("HERO_DOWN")] = LL('▼');

        // Your hero is pointing left.

    result[LL("HERO_LEFT")] = LL('◄');

        // Your hero is pointing right.

    result[LL("HERO_RIGHT")] = LL('►');

        // Your hero is pointing up.

    result[LL("HERO_UP")] = LL('▲');

        // Your hero is dead.

    result[LL("HERO_DEAD")] = LL('☻');

        // Your hero is influenced by Fly agaric.

    result[LL("HERO_EVIL")] = LL('♥');

        // Your hero is influenced by Death cap.

    result[LL("HERO_FLY")] = LL('♠');

        // Your hero when inactive.

    result[LL("HERO_SLEEP")] = LL('&');

        // Your hero's beard is directed horizontally.

    result[LL("HERO_BEARD_HORIZONTAL")] = LL('═');

        // Your hero's beard is directed vertically.

    result[LL("HERO_BEARD_VERTICAL")] = LL('║');

        // Turning your hero's beard from left to down.

    result[LL("HERO_BEARD_LEFT_DOWN")] = LL('╗');

        // Turning your hero's beard from left to up.

    result[LL("HERO_BEARD_LEFT_UP")] = LL('╝');

        // Turning your hero's beard from right to down.

    result[LL("HERO_BEARD_RIGHT_DOWN")] = LL('╔');

        // Turning your hero's beard from left to up.

    result[LL("HERO_BEARD_RIGHT_UP")] = LL('╚');

        // Tail of your hero's beard that points to the down.

    result[LL("HERO_TAIL_DOWN")] = LL('╙');

        // Tail of your hero's beard that points to the left.

    result[LL("HERO_TAIL_LEFT")] = LL('╘');

        // Tail of your hero's beard that points to the up.

    result[LL("HERO_TAIL_UP")] = LL('╓');

        // Tail of your hero's beard that points to the right.

    result[LL("HERO_TAIL_RIGHT")] = LL('╕');

        // Tail of your hero's beard when inactive.

    result[LL("HERO_TAIL_INACTIVE")] = LL('~');

        // Enemy hero is pointing down.

    result[LL("ENEMY_HERO_DOWN")] = LL('˅');

        // Enemy hero is pointing left.

    result[LL("ENEMY_HERO_LEFT")] = LL('<');

        // Enemy hero is pointing right.

    result[LL("ENEMY_HERO_RIGHT")] = LL('>');

        // Enemy hero is pointing up.

    result[LL("ENEMY_HERO_UP")] = LL('˄');

        // Enemy hero is dead.

    result[LL("ENEMY_HERO_DEAD")] = LL('☺');

        // Enemy hero is influenced by Fly agaric.

    result[LL("ENEMY_HERO_EVIL")] = LL('♣');

        // Enemy hero is influenced by Death cap.

    result[LL("ENEMY_HERO_FLY")] = LL('♦');

        // Enemy hero when inactive.

    result[LL("ENEMY_HERO_SLEEP")] = LL('ø');

        // Enemy hero's beard is directed horizontally.

    result[LL("ENEMY_HERO_BEARD_HORIZONTAL")] = LL('─');

        // Enemy hero's beard is directed vertically.

    result[LL("ENEMY_HERO_BEARD_VERTICAL")] = LL('│');

        // Turning enemy hero's beard from left to down.

    result[LL("ENEMY_HERO_BEARD_LEFT_DOWN")] = LL('┐');

        // Turning enemy hero's beard from left to up.

    result[LL("ENEMY_HERO_BEARD_LEFT_UP")] = LL('┘');

        // Turning enemy hero's beard from right to down.

    result[LL("ENEMY_HERO_BEARD_RIGHT_DOWN")] = LL('┌');

        // Turning enemy hero's beard from left to up.

    result[LL("ENEMY_HERO_BEARD_RIGHT_UP")] = LL('└');

        // Tail of enemy hero's beard that points to the down.

    result[LL("ENEMY_HERO_TAIL_DOWN")] = LL('¤');

        // Tail of enemy hero's beard that points to the left.

    result[LL("ENEMY_HERO_TAIL_LEFT")] = LL('×');

        // Tail of enemy hero's beard that points to the up.

    result[LL("ENEMY_HERO_TAIL_UP")] = LL('æ');

        // Tail of enemy hero's beard that points to the right.

    result[LL("ENEMY_HERO_TAIL_RIGHT")] = LL('ö');

        // Tail of enemy hero's beard when inactive.

    result[LL("ENEMY_HERO_TAIL_INACTIVE")] = LL('*');

    return result;
};

const ElementMap Element::Elements = Element::initialiseElements();
