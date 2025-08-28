package com.codenjoy.dojo.games.icancode;

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

import static com.codenjoy.dojo.client.AbstractLayeredBoard.Layers.*;
import static com.codenjoy.dojo.games.icancode.Element.*;

public class ElementUtils {

    public static final Element[] perks = new Element[]{
            UNSTOPPABLE_LASER_PERK,
            DEATH_RAY_PERK,
            UNLIMITED_FIRE_PERK,
            FIRE_PERK,
            JUMP_PERK,
            MOVE_BOXES_PERK,
    };

    public static final Element[] walls = new Element[]{
            ANGLE_IN_LEFT,
            WALL_FRONT,
            ANGLE_IN_RIGHT,
            WALL_RIGHT,
            ANGLE_BACK_RIGHT,
            WALL_BACK,
            ANGLE_BACK_LEFT,
            WALL_LEFT,
            WALL_BACK_ANGLE_LEFT,
            WALL_BACK_ANGLE_RIGHT,
            ANGLE_OUT_RIGHT,
            ANGLE_OUT_LEFT,
            SPACE,
    };

    public static final Element[] layer1 = new Element[]{
            FLOOR,
            ANGLE_IN_LEFT,
            WALL_FRONT,
            ANGLE_IN_RIGHT,
            WALL_RIGHT,
            ANGLE_BACK_RIGHT,
            WALL_BACK,
            ANGLE_BACK_LEFT,
            WALL_LEFT,
            WALL_BACK_ANGLE_LEFT,
            WALL_BACK_ANGLE_RIGHT,
            ANGLE_OUT_RIGHT,
            ANGLE_OUT_LEFT,
            SPACE,
            LASER_MACHINE_CHARGING_LEFT,
            LASER_MACHINE_CHARGING_RIGHT,
            LASER_MACHINE_CHARGING_UP,
            LASER_MACHINE_CHARGING_DOWN,
            LASER_MACHINE_READY_LEFT,
            LASER_MACHINE_READY_RIGHT,
            LASER_MACHINE_READY_UP,
            LASER_MACHINE_READY_DOWN,
            START,
            EXIT,
            HOLE,
            ZOMBIE_START,
            GOLD,
            UNSTOPPABLE_LASER_PERK,
            DEATH_RAY_PERK,
            UNLIMITED_FIRE_PERK,
            FIRE_PERK,
            JUMP_PERK,
            MOVE_BOXES_PERK,
            FOG,
    };

    public static final Element[] layer2 = new Element[]{
            EMPTY,
            BOX,
            ROBO,
            ROBO_FALLING,
            ROBO_LASER,
            ROBO_OTHER,
            ROBO_OTHER_FALLING,
            ROBO_OTHER_LASER,
            LASER_LEFT,
            LASER_RIGHT,
            LASER_UP,
            LASER_DOWN,
            FEMALE_ZOMBIE,
            MALE_ZOMBIE,
            ZOMBIE_DIE,
            BACKGROUND,
    };

    public static final Element[] layer3 = new Element[]{
            ROBO_FLYING,
            ROBO_OTHER_FLYING,
    };

    public static Element other(Element element) {
        switch (element) {
            case ROBO : return ROBO_OTHER;
            case ROBO_FALLING : return ROBO_OTHER_FALLING;
            case ROBO_FLYING : return ROBO_OTHER_FLYING;
            case ROBO_LASER : return ROBO_OTHER_LASER;

            case ROBO_OTHER : return ROBO;
            case ROBO_OTHER_FALLING : return ROBO_FALLING;
            case ROBO_OTHER_FLYING : return ROBO_FLYING;
            case ROBO_OTHER_LASER : return ROBO_LASER;
        }
        throw new IllegalArgumentException("Bad hero state: " + element);
    }

    public static boolean isWall(Element element) {
        return element.is(walls);
    }

    public static boolean isPerk(Element element) {
        return element.is(perks);
    }

    public static int layer(Element element) {
        for (Element el : layer3) {
            if (el == element) {
                return LAYER3;
            }
        }
        for (Element el : layer2) {
            if (el == element) {
                return LAYER2;
            }
        }
        return LAYER1;
    }
}
