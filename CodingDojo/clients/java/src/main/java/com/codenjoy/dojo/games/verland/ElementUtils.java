package com.codenjoy.dojo.games.verland;

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

import java.util.LinkedHashMap;
import java.util.Map;

import static com.codenjoy.dojo.games.verland.Element.*;

public class ElementUtils {

    private static final Map<Element, Integer> contagionsCount = new LinkedHashMap<>();

    static {
        contagionsCount.put(CLEAR, 0);
        contagionsCount.put(CONTAGION_ONE, 1);
        contagionsCount.put(CONTAGION_TWO, 2);
        contagionsCount.put(CONTAGION_THREE, 3);
        contagionsCount.put(CONTAGION_FOUR, 4);
        contagionsCount.put(CONTAGION_FIVE, 5);
        contagionsCount.put(CONTAGION_SIX, 6);
        contagionsCount.put(CONTAGION_SEVEN, 7);
        contagionsCount.put(CONTAGION_EIGHT, 8);
    }

    public static int contagions(Element element) {
        Integer count = contagionsCount.get(element);
        return count == null ? 0 : count;
    }

    public static final Element[] heroes = new Element[]{
            HERO_DEAD,
            HERO,
    };

    public static final Element[] otherHeroes = new Element[]{
            OTHER_HERO_DEAD,
            OTHER_HERO,
    };

    public static final Element[] enemyHeroes = new Element[]{
            ENEMY_HERO_DEAD,
            ENEMY_HERO,
    };

    public static final Element[] pathless = new Element[]{
            PATHLESS,
    };

    public static final Element[] infections = new Element[]{
            INFECTION,
    };

    public static final Element[] hidden = new Element[]{
            HIDDEN,
    };

    public static final Element[] clear = new Element[]{
            CLEAR,
    };

    public static final Element[] contagions = new Element[]{
            CONTAGION_ONE,
            CONTAGION_TWO,
            CONTAGION_THREE,
            CONTAGION_FOUR,
            CONTAGION_FIVE,
            CONTAGION_SIX,
            CONTAGION_SEVEN,
            CONTAGION_EIGHT,
    };

    public static final Element[] healing = new Element[]{
            HERO_HEALING,
            OTHER_HERO_HEALING,
            ENEMY_HERO_HEALING,
    };

    public static final Element[] cure = new Element[]{
            HERO_CURE,
            OTHER_HERO_CURE,
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
