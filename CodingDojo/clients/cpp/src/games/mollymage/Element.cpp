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

        // After Molly set the potion, the timer starts (5 ticks).

    result[LL("POTION_TIMER_5")] = LL('5');

        // This potion will blow up after 4 ticks.

    result[LL("POTION_TIMER_4")] = LL('4');

        // This after 3...

    result[LL("POTION_TIMER_3")] = LL('3');

        // Two..

    result[LL("POTION_TIMER_2")] = LL('2');

        // One.

    result[LL("POTION_TIMER_1")] = LL('1');

        // Boom! this is what is potion does, everything that is
        // destroyable got destroyed.

    result[LL("BLAST")] = LL('҉');

        // Indestructible wall - it will not fall from potion.

    result[LL("WALL")] = LL('☼');

        // This is a treasure box, it opens with an explosion.

    result[LL("TREASURE_BOX")] = LL('#');

        // This is like a treasure box opens looks like, it will
        // disappear on next move. If it's you did it - you'll get
        // score points. Perhaps a prize will appear.

    result[LL("TREASURE_BOX_OPENING")] = LL('H');

        // This guys runs over the board randomly and gets in the way
        // all the time. If it will touch Molly - she will die. You'd
        // better kill this piece of ... soul, you'll get score points
        // for it.

    result[LL("GHOST")] = LL('&');

        // This is ghost corpse.

    result[LL("GHOST_DEAD")] = LL('x');

        // Temporarily increase potion radius blast. Applicable only to
        // new potions.

    result[LL("POTION_BLAST_RADIUS_INCREASE")] = LL('+');

        // Temporarily increase available potions count. Number of
        // extra potions can be set in settings*.

    result[LL("POTION_COUNT_INCREASE")] = LL('c');

        // Next several potions would be with remote control.
        // Activating by command ACT. Number of RC triggers is limited
        // and can be set in settings*.

    result[LL("POTION_REMOTE_CONTROL")] = LL('r');

        // Temporarily gives you immunity from potion blasts (own
        // potion and others as well).

    result[LL("POTION_IMMUNE")] = LL('i');

        // Hero can shoot by poison cloud. Using: ACT(1)+Direction.
        // Temporary.

    result[LL("POISON_THROWER")] = LL('T');

        // Hero can explode all potions on the field. Using: ACT(2).
        // Temporary.

    result[LL("POTION_EXPLODER")] = LL('A');

        // A void. This is the only place where you can move your
        // Molly.

    result[LL("NONE")] = LL(' ');

        // This is what your Molly usually looks like.

    result[LL("HERO")] = LL('☺');

        // This is if your Molly is sitting on own potion.

    result[LL("HERO_POTION")] = LL('☻');

        // Oops, your Molly is dead (don't worry, she will appear
        // somewhere in next move). You're getting penalty points for
        // each death.

    result[LL("HERO_DEAD")] = LL('Ѡ');

        // This is what other heroes looks like.

    result[LL("OTHER_HERO")] = LL('♥');

        // This is if other hero is sitting on own potion.

    result[LL("OTHER_HERO_POTION")] = LL('♠');

        // Other hero corpse (it will disappear shortly, right on the
        // next move). If you've done it you'll get score points.

    result[LL("OTHER_HERO_DEAD")] = LL('♣');

        // This is what enemy heroes looks like.

    result[LL("ENEMY_HERO")] = LL('ö');

        // This is if enemy hero is sitting on own potion.

    result[LL("ENEMY_HERO_POTION")] = LL('Ö');

        // Enemy hero corpse (it will disappear shortly, right on the
        // next move). If you've done it you'll get score points.

    result[LL("ENEMY_HERO_DEAD")] = LL('ø');

    return result;
};

const ElementMap Element::Elements = Element::initialiseElements();
