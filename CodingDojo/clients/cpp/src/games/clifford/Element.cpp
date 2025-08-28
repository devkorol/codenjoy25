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

        // Empty space - where the hero can move.

    result[LL("NONE")] = LL(' ');

        // A wall where you can shoot a hole.

    result[LL("BRICK")] = LL('#');

        // The wall is restored over time. When the process begins, we
        // see a timer.

    result[LL("PIT_FILL_1")] = LL('1');

        // The wall is restored over time. When the process begins, we
        // see a timer.

    result[LL("PIT_FILL_2")] = LL('2');

        // The wall is restored over time. When the process begins, we
        // see a timer.

    result[LL("PIT_FILL_3")] = LL('3');

        // The wall is restored over time. When the process begins, we
        // see a timer.

    result[LL("PIT_FILL_4")] = LL('4');

        // Indestructible wall - It cannot be destroyed with a shot.

    result[LL("STONE")] = LL('☼');

        // At the moment of the shot, we see the wall like this.

    result[LL("CRACK_PIT")] = LL('*');

        // Clue knife. Collect a series of clues to get the maximum
        // points.

    result[LL("CLUE_KNIFE")] = LL('$');

        // Clue glove. Collect a series of clues to get the maximum
        // points.

    result[LL("CLUE_GLOVE")] = LL('&');

        // Clue ring. Collect a series of clues to get the maximum
        // points.

    result[LL("CLUE_RING")] = LL('@');

        // Your hero is dead. In the next tick, it will disappear and
        // appear in a new location.

    result[LL("HERO_DIE")] = LL('O');

        // Your hero is climbing the ladder.

    result[LL("HERO_LADDER")] = LL('A');

        // Your hero runs to the left.

    result[LL("HERO_LEFT")] = LL('◄');

        // Your hero runs to the right.

    result[LL("HERO_RIGHT")] = LL('►');

        // Your hero is falling.

    result[LL("HERO_FALL")] = LL('U');

        // Your hero is crawling along the pipe.

    result[LL("HERO_PIPE")] = LL('I');

        // Your hero in the pit.

    result[LL("HERO_PIT")] = LL('E');

        // Your shadow-hero is dead. In the next tick, it will
        // disappear and appear in a new location.

    result[LL("HERO_MASK_DIE")] = LL('o');

        // Your shadow-hero is climbing the ladder.

    result[LL("HERO_MASK_LADDER")] = LL('a');

        // Your shadow-hero runs to the left.

    result[LL("HERO_MASK_LEFT")] = LL('h');

        // Your shadow-hero runs to the right.

    result[LL("HERO_MASK_RIGHT")] = LL('w');

        // Your shadow-hero is falling.

    result[LL("HERO_MASK_FALL")] = LL('u');

        // Your shadow-hero is crawling along the pipe.

    result[LL("HERO_MASK_PIPE")] = LL('i');

        // Your shadow-hero in the pit.

    result[LL("HERO_MASK_PIT")] = LL('e');

        // Other hero is dead. In the next tick, it will disappear and
        // appear in a new location.

    result[LL("OTHER_HERO_DIE")] = LL('C');

        // Other hero is climbing the ladder.

    result[LL("OTHER_HERO_LADDER")] = LL('D');

        // Other hero runs to the left.

    result[LL("OTHER_HERO_LEFT")] = LL('«');

        // Other hero runs to the right.

    result[LL("OTHER_HERO_RIGHT")] = LL('»');

        // Other hero is falling.

    result[LL("OTHER_HERO_FALL")] = LL('F');

        // Other hero is crawling along the pipe.

    result[LL("OTHER_HERO_PIPE")] = LL('J');

        // Other hero in the pit.

    result[LL("OTHER_HERO_PIT")] = LL('K');

        // Other shadow-hero is dead. In the next tick, it will
        // disappear and appear in a new location.

    result[LL("OTHER_HERO_MASK_DIE")] = LL('c');

        // Other shadow-hero is climbing the ladder.

    result[LL("OTHER_HERO_MASK_LADDER")] = LL('d');

        // Other shadow-hero runs to the left.

    result[LL("OTHER_HERO_MASK_LEFT")] = LL('Z');

        // Other shadow-hero runs to the right.

    result[LL("OTHER_HERO_MASK_RIGHT")] = LL('z');

        // Other shadow-hero is falling.

    result[LL("OTHER_HERO_MASK_FALL")] = LL('f');

        // Other shadow-hero is crawling along the pipe.

    result[LL("OTHER_HERO_MASK_PIPE")] = LL('j');

        // Other shadow-hero in the pit.

    result[LL("OTHER_HERO_MASK_PIT")] = LL('k');

        // Enemy hero is dead. In the next tick, it will disappear and
        // appear in a new location.

    result[LL("ENEMY_HERO_DIE")] = LL('L');

        // Enemy hero is climbing the ladder.

    result[LL("ENEMY_HERO_LADDER")] = LL('N');

        // Enemy hero runs to the left.

    result[LL("ENEMY_HERO_LEFT")] = LL('P');

        // Enemy hero runs to the right.

    result[LL("ENEMY_HERO_RIGHT")] = LL('Q');

        // Enemy hero is falling.

    result[LL("ENEMY_HERO_FALL")] = LL('R');

        // Enemy hero is crawling along the pipe.

    result[LL("ENEMY_HERO_PIPE")] = LL('T');

        // Enemy hero in the pit.

    result[LL("ENEMY_HERO_PIT")] = LL('V');

        // Enemy shadow-hero is dead. In the next tick, it will
        // disappear and appear in a new location.

    result[LL("ENEMY_HERO_MASK_DIE")] = LL('l');

        // Enemy shadow-hero is climbing the ladder.

    result[LL("ENEMY_HERO_MASK_LADDER")] = LL('n');

        // Enemy shadow-hero runs to the left.

    result[LL("ENEMY_HERO_MASK_LEFT")] = LL('p');

        // Enemy shadow-hero runs to the right.

    result[LL("ENEMY_HERO_MASK_RIGHT")] = LL('q');

        // Enemy shadow-hero is falling.

    result[LL("ENEMY_HERO_MASK_FALL")] = LL('r');

        // Enemy shadow-hero is crawling along the pipe.

    result[LL("ENEMY_HERO_MASK_PIPE")] = LL('t');

        // Enemy shadow-hero in the pit.

    result[LL("ENEMY_HERO_MASK_PIT")] = LL('v');

        // Robber is climbing the ladder.

    result[LL("ROBBER_LADDER")] = LL('X');

        // Robber runs to the left. Robber picks up the nearest prey
        // and hunts for it until it overtakes it.

    result[LL("ROBBER_LEFT")] = LL(')');

        // Robber runs to the right. Robber picks up the nearest prey
        // and hunts for it until it overtakes it.

    result[LL("ROBBER_RIGHT")] = LL('(');

        // Robber is falling.

    result[LL("ROBBER_FALL")] = LL('x');

        // Robber is crawling along the pipe.

    result[LL("ROBBER_PIPE")] = LL('Y');

        // Robber in the pit.

    result[LL("ROBBER_PIT")] = LL('y');

        // Opened golden gates. Can only be locked with a golden key.

    result[LL("OPENED_DOOR_GOLD")] = LL('g');

        // Opened silver gates. Can only be locked with a silver key.

    result[LL("OPENED_DOOR_SILVER")] = LL('s');

        // Opened bronze gates. Can only be locked with a bronze key.

    result[LL("OPENED_DOOR_BRONZE")] = LL('b');

        // Closed golden gates. Can only be opened with a golden key.

    result[LL("CLOSED_DOOR_GOLD")] = LL('G');

        // Closed silver gates. Can only be opened with a silver key.

    result[LL("CLOSED_DOOR_SILVER")] = LL('S');

        // Closed bronze gates. Can only be opened with a bronze key.

    result[LL("CLOSED_DOOR_BRONZE")] = LL('B');

        // Golden key. Helps open/close golden gates. The key can only
        // be used once.

    result[LL("KEY_GOLD")] = LL('+');

        // Silver key. Helps open/close silver gates. The key can only
        // be used once.

    result[LL("KEY_SILVER")] = LL('-');

        // Bronze key. Helps open/close bronze gates. The key can only
        // be used once.

    result[LL("KEY_BRONZE")] = LL('!');

        // Bullet. After the shot by the hero, the bullet flies until
        // it meets an obstacle. The bullet kills the hero. It
        // ricochets from the indestructible wall (no more than 1
        // time). The bullet destroys the destructible wall.

    result[LL("BULLET")] = LL('•');

        // Ladder - the hero can move along the level along it.

    result[LL("LADDER")] = LL('H');

        // Pipe - the hero can also move along the level along it, but
        // only horizontally.

    result[LL("PIPE")] = LL('~');

        // Back door - allows the hero to secretly move to another
        // random place on the map.

    result[LL("BACKWAY")] = LL('W');

        // Disguise potion - endow the hero with additional abilities.
        // The hero goes into shadow mode.

    result[LL("MASK_POTION")] = LL('m');

        // Ammo clip - additional ammo for hero's gun.

    result[LL("AMMO_CLIP")] = LL('M');

    return result;
};

const ElementMap Element::Elements = Element::initialiseElements();
