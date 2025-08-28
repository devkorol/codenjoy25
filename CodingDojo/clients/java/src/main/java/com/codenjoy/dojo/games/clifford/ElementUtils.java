package com.codenjoy.dojo.games.clifford;

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

import com.codenjoy.dojo.services.printer.TeamElement;

import static com.codenjoy.dojo.games.clifford.Element.*;

public class ElementUtils {

    public static final Element[] none = new Element[]{
            NONE,
    };

    public static final Element[] clues = new Element[]{
            CLUE_KNIFE,
            CLUE_GLOVE,
            CLUE_RING,
    };

    public static final Element[] backWays = new Element[]{
            BACKWAY,
    };

    public static final Element[] maskPotions = new Element[]{
            MASK_POTION,
    };

    public static final Element[] ladders = new Element[]{
            LADDER,

            HERO_LADDER,
            OTHER_HERO_LADDER,
            ROBBER_LADDER,
            ENEMY_HERO_LADDER,

            HERO_MASK_LADDER,
            OTHER_HERO_MASK_LADDER,
            ENEMY_HERO_MASK_LADDER,
    };

    public static final Element[] walls = new Element[]{
            STONE,
            BRICK,
    };

    public static final Element[] heroes = new Element[]{
            HERO_LEFT,
            HERO_RIGHT,
            HERO_LADDER,
            HERO_PIPE,
            HERO_FALL,
            HERO_PIT,
            HERO_DIE,

            HERO_MASK_LEFT,
            HERO_MASK_RIGHT,
            HERO_MASK_LADDER,
            HERO_MASK_PIPE,
            HERO_MASK_FALL,
            HERO_MASK_PIT,
            HERO_MASK_DIE,
    };

    public static final Element[] heroDie = new Element[]{
            HERO_DIE,
            HERO_MASK_DIE,
    };

    public static final Element[] robbers = new Element[]{
            ROBBER_LEFT,
            ROBBER_RIGHT,
            ROBBER_LADDER,
            ROBBER_PIPE,
            ROBBER_FALL,
            ROBBER_PIT,
    };

    public static final Element[] otherHeroes = new Element[]{
            OTHER_HERO_LEFT,
            OTHER_HERO_RIGHT,
            OTHER_HERO_LADDER,
            OTHER_HERO_PIPE,
            OTHER_HERO_FALL,
            OTHER_HERO_PIT,
            OTHER_HERO_DIE,

            OTHER_HERO_MASK_LEFT,
            OTHER_HERO_MASK_RIGHT,
            OTHER_HERO_MASK_LADDER,
            OTHER_HERO_MASK_PIPE,
            OTHER_HERO_MASK_FALL,
            OTHER_HERO_MASK_PIT,
            OTHER_HERO_MASK_DIE,
    };

    public static final Element[] enemyHeroes =  new Element[]{
            ENEMY_HERO_LEFT,
            ENEMY_HERO_RIGHT,
            ENEMY_HERO_LADDER,
            ENEMY_HERO_PIPE,
            ENEMY_HERO_FALL,
            ENEMY_HERO_PIT,
            ENEMY_HERO_DIE,

            ENEMY_HERO_MASK_LEFT,
            ENEMY_HERO_MASK_RIGHT,
            ENEMY_HERO_MASK_LADDER,
            ENEMY_HERO_MASK_PIPE,
            ENEMY_HERO_MASK_FALL,
            ENEMY_HERO_MASK_PIT,
            ENEMY_HERO_MASK_DIE,
    };

    public static final Element[] pipes = new Element[]{
            PIPE,

            HERO_PIPE,
            OTHER_HERO_PIPE,
            ROBBER_PIPE,
            ENEMY_HERO_PIPE,

            HERO_MASK_PIPE,
            OTHER_HERO_MASK_PIPE,
            ENEMY_HERO_MASK_PIPE,
    };

    public static final Element[] pits = new Element[]{
            CRACK_PIT,
            PIT_FILL_1,
            PIT_FILL_2,
            PIT_FILL_3,
            PIT_FILL_4,

            HERO_PIT,
            OTHER_HERO_PIT,
            ROBBER_PIT,
            ENEMY_HERO_PIT,

            HERO_MASK_PIT,
            OTHER_HERO_MASK_PIT,
            ENEMY_HERO_MASK_PIT,
    };

    public static final Element[] bullets = new Element[]{
            BULLET,
    };

    public static final Element[] doors = new Element[]{
            CLOSED_DOOR_GOLD,
            CLOSED_DOOR_SILVER,
            CLOSED_DOOR_BRONZE,

            OPENED_DOOR_GOLD,
            OPENED_DOOR_SILVER,
            OPENED_DOOR_BRONZE,
    };

    public static final Element[] keys = new Element[]{
            KEY_GOLD,
            KEY_SILVER,
            KEY_BRONZE,
    };

    public static final Element[] barriers = new Element[]{
            STONE,
            BRICK,
            CLOSED_DOOR_GOLD,
            CLOSED_DOOR_SILVER,
            CLOSED_DOOR_BRONZE,
            BULLET,

            ROBBER_LEFT,
            ROBBER_RIGHT,
            ROBBER_LADDER,
            ROBBER_PIPE,
            ROBBER_FALL,
            ROBBER_PIT,

            OTHER_HERO_LEFT,
            OTHER_HERO_RIGHT,
            OTHER_HERO_LADDER,
            OTHER_HERO_PIPE,
            OTHER_HERO_FALL,
            OTHER_HERO_PIT,
            OTHER_HERO_DIE,

            ENEMY_HERO_LEFT,
            ENEMY_HERO_RIGHT,
            ENEMY_HERO_LADDER,
            ENEMY_HERO_PIPE,
            ENEMY_HERO_FALL,
            ENEMY_HERO_PIT,
            ENEMY_HERO_DIE,

            OTHER_HERO_MASK_LEFT,
            OTHER_HERO_MASK_RIGHT,
            OTHER_HERO_MASK_LADDER,
            OTHER_HERO_MASK_PIPE,
            OTHER_HERO_MASK_FALL,
            OTHER_HERO_MASK_PIT,
            OTHER_HERO_MASK_DIE,

            ENEMY_HERO_MASK_LEFT,
            ENEMY_HERO_MASK_RIGHT,
            ENEMY_HERO_MASK_LADDER,
            ENEMY_HERO_MASK_PIPE,
            ENEMY_HERO_MASK_FALL,
            ENEMY_HERO_MASK_PIT,
            ENEMY_HERO_MASK_DIE,
    };

    public static Element mask(Element element) {
        switch (element) {
            case HERO_DIE:          return HERO_MASK_DIE;
            case HERO_LADDER:       return HERO_MASK_LADDER;
            case HERO_LEFT:         return HERO_MASK_LEFT;
            case HERO_RIGHT:        return HERO_MASK_RIGHT;
            case HERO_FALL:         return HERO_MASK_FALL;
            case HERO_PIPE:         return HERO_MASK_PIPE;
            case HERO_PIT:          return HERO_MASK_PIT;

            case OTHER_HERO_DIE:    return OTHER_HERO_MASK_DIE;
            case OTHER_HERO_LADDER: return OTHER_HERO_MASK_LADDER;
            case OTHER_HERO_LEFT:   return OTHER_HERO_MASK_LEFT;
            case OTHER_HERO_RIGHT:  return OTHER_HERO_MASK_RIGHT;
            case OTHER_HERO_FALL:   return OTHER_HERO_MASK_FALL;
            case OTHER_HERO_PIPE:   return OTHER_HERO_MASK_PIPE;
            case OTHER_HERO_PIT:    return OTHER_HERO_MASK_PIT;

            case ENEMY_HERO_DIE:    return ENEMY_HERO_MASK_DIE;
            case ENEMY_HERO_LADDER: return ENEMY_HERO_MASK_LADDER;
            case ENEMY_HERO_LEFT:   return ENEMY_HERO_MASK_LEFT;
            case ENEMY_HERO_RIGHT:  return ENEMY_HERO_MASK_RIGHT;
            case ENEMY_HERO_FALL:   return ENEMY_HERO_MASK_FALL;
            case ENEMY_HERO_PIPE:   return ENEMY_HERO_MASK_PIPE;
            case ENEMY_HERO_PIT:    return ENEMY_HERO_MASK_PIT;
        }
        throw new IllegalArgumentException("Bad hero state: " + element);
    }

    public static final TeamElement<Element> TEAM_ELEMENT = new TeamElement<>() {
        @Override
        public Element otherHero(Element element) {
            switch (element) {
                case HERO_DIE:         return OTHER_HERO_DIE;
                case HERO_LADDER:      return OTHER_HERO_LADDER;
                case HERO_LEFT:        return OTHER_HERO_LEFT;
                case HERO_RIGHT:       return OTHER_HERO_RIGHT;
                case HERO_FALL:        return OTHER_HERO_FALL;
                case HERO_PIPE:        return OTHER_HERO_PIPE;
                case HERO_PIT:         return OTHER_HERO_PIT;

                case HERO_MASK_DIE:    return OTHER_HERO_MASK_DIE;
                case HERO_MASK_LADDER: return OTHER_HERO_MASK_LADDER;
                case HERO_MASK_LEFT:   return OTHER_HERO_MASK_LEFT;
                case HERO_MASK_RIGHT:  return OTHER_HERO_MASK_RIGHT;
                case HERO_MASK_FALL:   return OTHER_HERO_MASK_FALL;
                case HERO_MASK_PIPE:   return OTHER_HERO_MASK_PIPE;
                case HERO_MASK_PIT:    return OTHER_HERO_MASK_PIT;
            }
            throw new IllegalArgumentException("Bad hero state: " + element);
        }

        @Override
        public Element enemyHero(Element element) {
            switch (element) {
                case HERO_DIE:         return ENEMY_HERO_DIE;
                case HERO_LADDER:      return ENEMY_HERO_LADDER;
                case HERO_LEFT:        return ENEMY_HERO_LEFT;
                case HERO_RIGHT:       return ENEMY_HERO_RIGHT;
                case HERO_FALL:        return ENEMY_HERO_FALL;
                case HERO_PIPE:        return ENEMY_HERO_PIPE;
                case HERO_PIT:         return ENEMY_HERO_PIT;

                case HERO_MASK_DIE:    return ENEMY_HERO_MASK_DIE;
                case HERO_MASK_LADDER: return ENEMY_HERO_MASK_LADDER;
                case HERO_MASK_LEFT:   return ENEMY_HERO_MASK_LEFT;
                case HERO_MASK_RIGHT:  return ENEMY_HERO_MASK_RIGHT;
                case HERO_MASK_FALL:   return ENEMY_HERO_MASK_FALL;
                case HERO_MASK_PIPE:   return ENEMY_HERO_MASK_PIPE;
                case HERO_MASK_PIT:    return ENEMY_HERO_PIT;
            }
            throw new IllegalArgumentException("Bad hero state: " + element);
        }
    };
}
