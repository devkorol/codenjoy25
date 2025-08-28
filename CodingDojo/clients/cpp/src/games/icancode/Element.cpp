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

        // Empty space (on layer 2) where player can go.

    result[LL("EMPTY")] = LL('-');

        // Empty space (on layer 1) where player can go.

    result[LL("FLOOR")] = LL('.');

        // Wall (on layer 1).

    result[LL("ANGLE_IN_LEFT")] = LL('╔');

        // Wall (on layer 1).

    result[LL("WALL_FRONT")] = LL('═');

        // Wall (on layer 1).

    result[LL("ANGLE_IN_RIGHT")] = LL('┐');

        // Wall (on layer 1).

    result[LL("WALL_RIGHT")] = LL('│');

        // Wall (on layer 1).

    result[LL("ANGLE_BACK_RIGHT")] = LL('┘');

        // Wall (on layer 1).

    result[LL("WALL_BACK")] = LL('─');

        // Wall (on layer 1).

    result[LL("ANGLE_BACK_LEFT")] = LL('└');

        // Wall (on layer 1).

    result[LL("WALL_LEFT")] = LL('║');

        // Wall (on layer 1).

    result[LL("WALL_BACK_ANGLE_LEFT")] = LL('┌');

        // Wall (on layer 1).

    result[LL("WALL_BACK_ANGLE_RIGHT")] = LL('╗');

        // Wall (on layer 1).

    result[LL("ANGLE_OUT_RIGHT")] = LL('╝');

        // Wall (on layer 1).

    result[LL("ANGLE_OUT_LEFT")] = LL('╚');

        // Wall (on layer 1).

    result[LL("SPACE")] = LL(' ');

        // Charging laser machine (on layer 1) pointing left.

    result[LL("LASER_MACHINE_CHARGING_LEFT")] = LL('˂');

        // Charging laser machine (on layer 1) pointing right.

    result[LL("LASER_MACHINE_CHARGING_RIGHT")] = LL('˃');

        // Charging laser machine (on layer 1) pointing up.

    result[LL("LASER_MACHINE_CHARGING_UP")] = LL('˄');

        // Charging laser machine (on layer 1) pointing down.

    result[LL("LASER_MACHINE_CHARGING_DOWN")] = LL('˅');

        // Charged laser machine (on layer 1) pointing left.

    result[LL("LASER_MACHINE_READY_LEFT")] = LL('◄');

        // Charged laser machine (on layer 1) pointing right.

    result[LL("LASER_MACHINE_READY_RIGHT")] = LL('►');

        // Charged laser machine (on layer 1) pointing up.

    result[LL("LASER_MACHINE_READY_UP")] = LL('▲');

        // Charged laser machine (on layer 1) pointing down.

    result[LL("LASER_MACHINE_READY_DOWN")] = LL('▼');

        // Level start spot (on layer 1).

    result[LL("START")] = LL('S');

        // Level exit spot (on layer 1).

    result[LL("EXIT")] = LL('E');

        // Hole (on layer 1).

    result[LL("HOLE")] = LL('O');

        // Box (on layer 2) that can be moved and jumped over.

    result[LL("BOX")] = LL('B');

        // Zombie start spot (on layer 1).

    result[LL("ZOMBIE_START")] = LL('Z');

        // Gold (on layer 1).

    result[LL("GOLD")] = LL('$');

        // Unstoppable laser perk (on layer 1).

    result[LL("UNSTOPPABLE_LASER_PERK")] = LL('l');

        // Death ray perk (on layer 1).

    result[LL("DEATH_RAY_PERK")] = LL('r');

        // Unlimited fire perk (on layer 1).

    result[LL("UNLIMITED_FIRE_PERK")] = LL('f');

        // Fire perk (on layer 1).

    result[LL("FIRE_PERK")] = LL('a');

        // Jump perk (on layer 1).

    result[LL("JUMP_PERK")] = LL('j');

        // Move boxes perk (on layer 1).

    result[LL("MOVE_BOXES_PERK")] = LL('m');

        // Your hero (on layer 2).

    result[LL("ROBO")] = LL('☺');

        // Your hero (on layer 2) falls into a hole.

    result[LL("ROBO_FALLING")] = LL('o');

        // Your hero (on layer 3) falling.

    result[LL("ROBO_FLYING")] = LL('*');

        // Your hero (on layer 2) died from a laser.

    result[LL("ROBO_LASER")] = LL('☻');

        // Other hero (on layer 2).

    result[LL("ROBO_OTHER")] = LL('X');

        // Other hero (on layer 2) falls into a hole.

    result[LL("ROBO_OTHER_FALLING")] = LL('x');

        // Other hero (on layer 3) falling.

    result[LL("ROBO_OTHER_FLYING")] = LL('^');

        // Other hero (on layer 2) died from a laser.

    result[LL("ROBO_OTHER_LASER")] = LL('&');

        // Laser (on layer 2) flies to the left.

    result[LL("LASER_LEFT")] = LL('←');

        // Laser (on layer 2) flies to the right.

    result[LL("LASER_RIGHT")] = LL('→');

        // Laser (on layer 2) flies to the up.

    result[LL("LASER_UP")] = LL('↑');

        // Laser (on layer 2) flies to the down.

    result[LL("LASER_DOWN")] = LL('↓');

        // Female zombie (on layer 2).

    result[LL("FEMALE_ZOMBIE")] = LL('♀');

        // Male zombie (on layer 2).

    result[LL("MALE_ZOMBIE")] = LL('♂');

        // Zombie dies (on layer 2).

    result[LL("ZOMBIE_DIE")] = LL('✝');

        // Fog of war system layer (on layer 1).

    result[LL("FOG")] = LL('F');

        // Background system layer (on layer 2).

    result[LL("BACKGROUND")] = LL('G');

    return result;
};

const ElementMap Element::Elements = Element::initialiseElements();
