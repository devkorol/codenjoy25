package com.codenjoy.dojo.games.mollymage;

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

import static com.codenjoy.dojo.games.mollymage.Element.*;

public class ElementUtils {

    public static final Element[] none = new Element[]{
            NONE,
    };

    public static final Element[] heroes = new Element[]{
            HERO,
            HERO_POTION,
            HERO_DEAD,
    };

    public static final Element[] enemyHeroes = new Element[]{
            ENEMY_HERO,
            ENEMY_HERO_POTION,
            ENEMY_HERO_DEAD,
    };

    public static final Element[] otherHeroes = new Element[]{
            OTHER_HERO,
            OTHER_HERO_POTION,
            OTHER_HERO_DEAD,
    };

    public static final Element[] potions = new Element[]{
            POTION_TIMER_1,
            POTION_TIMER_2,
            POTION_TIMER_3,
            POTION_TIMER_4,
            POTION_TIMER_5,
            HERO_POTION,
            OTHER_HERO_POTION,
            ENEMY_HERO_POTION,
    };

    public static final Element[] perks = new Element[]{
            POTION_BLAST_RADIUS_INCREASE,
            POTION_COUNT_INCREASE,
            POTION_IMMUNE,
            POTION_REMOTE_CONTROL,
            POISON_THROWER,
            POTION_EXPLODER,
    };

    public static final Element[] ghosts = new Element[]{
            GHOST,
            GHOST_DEAD,
    };

    public static final Element[] walls = new Element[]{
            WALL,
    };

    public static final Element[] treasureBoxes = new Element[]{
            TREASURE_BOX,
            TREASURE_BOX_OPENING,
    };

    public static final Element[] blasts = new Element[]{
            BLAST,
    };

    public static final Element[] barriers = new Element[]{
            GHOST,
            //GHOST_DEAD,
            WALL,
            POTION_TIMER_1,
            POTION_TIMER_2,
            POTION_TIMER_3,
            POTION_TIMER_4,
            POTION_TIMER_5,
            HERO_POTION,
            OTHER_HERO_POTION,
            ENEMY_HERO_POTION,
            TREASURE_BOX,
            //TREASURE_BOX_OPENING,
            OTHER_HERO,
            OTHER_HERO_POTION,
            OTHER_HERO_DEAD,
            ENEMY_HERO,
            ENEMY_HERO_POTION,
            ENEMY_HERO_DEAD,
    };

    public static final TeamElement<Element> TEAM_ELEMENT = new TeamElement<>() {
        @Override
        public Element otherHero(Element element) {
            switch (element) {
                case HERO:        return OTHER_HERO;
                case HERO_DEAD:   return OTHER_HERO_DEAD;
                case HERO_POTION: return OTHER_HERO_POTION;
            }
            throw new IllegalArgumentException("Bad hero state: " + element);
        }

        @Override
        public Element enemyHero(Element element) {
            switch (element) {
                case HERO:        return ENEMY_HERO;
                case HERO_DEAD:   return ENEMY_HERO_DEAD;
                case HERO_POTION: return ENEMY_HERO_POTION;
            }
            throw new IllegalArgumentException("Bad hero state: " + element);
        }
    };
}
