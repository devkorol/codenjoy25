package com.codenjoy.dojo.games.sample;

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

import static com.codenjoy.dojo.games.sample.Element.*;

public class ElementUtils {

    public static final Element[] none = new Element[]{
            NONE,
    };

    public static final Element[] heroes = new Element[]{
            HERO,
            HERO_DEAD,
    };

    public static final Element[] otherHeroes = new Element[]{
            OTHER_HERO,
            OTHER_HERO_DEAD,
    };

    public static final Element[] enemyHeroes = new Element[]{
            ENEMY_HERO,
            ENEMY_HERO_DEAD,
    };

    public static final Element[] walls = new Element[]{
            WALL,
    };

    public static final Element[] gold = new Element[]{
            GOLD,
    };

    public static final Element[] bombs = new Element[]{
            BOMB,
    };

    public static final Element[] barriers = new Element[]{
            WALL,
            BOMB,
            OTHER_HERO,
            OTHER_HERO_DEAD,
    };

    public static final TeamElement<Element> TEAM_ELEMENT = new TeamElement<>() {
        @Override
        public Element otherHero(Element element) {
            switch (element) {
                case HERO:      return OTHER_HERO;
                case HERO_DEAD: return OTHER_HERO_DEAD;
            }
            throw new IllegalArgumentException("Bad hero state: " + element);
        }

        @Override
        public Element enemyHero(Element element) {
            switch (element) {
                case HERO:      return ENEMY_HERO;
                case HERO_DEAD: return ENEMY_HERO_DEAD;
            }
            throw new IllegalArgumentException("Bad hero state: " + element);
        }
    };
}
