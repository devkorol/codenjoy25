package com.codenjoy.dojo.pseudo;

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

import com.codenjoy.dojo.services.printer.CharElement;
import com.google.common.base.Function;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class HeroElements {

    private static Map<String, List<CharElement>> map = new HashMap<>(){{
        // TODO что делать с другими играми, которые не графические?
        // TODO что делать с другими играми, у которых на поле несколько юнитов одновременно?

        // TODO потестить как работает pseudo c несколькими леерами icancode
        put("icancode",
                getAll(com.codenjoy.dojo.games.icancode.Element::valueOf,
                        // TODO вот тут сильно не удобно, что захардкоджены строчки
                        "ROBO",
                        "ROBO_FALLING",
                        "ROBO_FLYING",
                        "ROBO_LASER"));

        put("verland",
                getAll(com.codenjoy.dojo.games.verland.Element::valueOf,
                        "HERO",
                        "HERO_DEAD"));

        put("sample",
                getAll(com.codenjoy.dojo.games.sample.Element::valueOf,
                        "HERO",
                        "HERO_DEAD"));

        put("puzzlebox",
                getAll(com.codenjoy.dojo.games.puzzlebox.Element::valueOf,
                        "HERO"));

        put("pong",
                getAll(com.codenjoy.dojo.games.pong.Element::valueOf,
                        "BALL"));

        put("xonix",
                getAll(com.codenjoy.dojo.games.xonix.Element::valueOf,
                        "HERO"));

        put("sokoban",
                getAll(com.codenjoy.dojo.games.sokoban.Element::valueOf,
                        "HERO"));

        put("vacuum",
                getAll(com.codenjoy.dojo.games.vacuum.Element::valueOf,
                        "VACUUM"));

        put("spacerace",
                getAll(com.codenjoy.dojo.games.spacerace.Element::valueOf,
                        "HERO",
                        "DEAD_HERO"));

        put("startandjump",
                getAll(com.codenjoy.dojo.games.startandjump.Element::valueOf,
                        "HERO",
                        "BLACK_HERO"));

        put("mollymage",
                getAll(com.codenjoy.dojo.games.mollymage.Element::valueOf,
                        "HERO",
                        "HERO_POTION",
                        "HERO_DEAD"));

        put("clifford",
                getAll(com.codenjoy.dojo.games.clifford.Element::valueOf,
                        "HERO_DIE",
                        "HERO_LADDER",
                        "HERO_LEFT",
                        "HERO_RIGHT",
                        "HERO_FALL",
                        "HERO_PIPE",
                        "HERO_PIT",

                        "HERO_MASK_DIE",
                        "HERO_MASK_LADDER",
                        "HERO_MASK_LEFT",
                        "HERO_MASK_RIGHT",
                        "HERO_MASK_FALL",
                        "HERO_MASK_PIPE",
                        "HERO_MASK_PIT"));

        put("rawelbbub",
                getAll(com.codenjoy.dojo.games.rawelbbub.Element::valueOf,
                        "TANK_UP",
                        "TANK_RIGHT",
                        "TANK_DOWN",
                        "TANK_LEFT"));

        put("knibert",
                getAll(com.codenjoy.dojo.games.knibert.Element::valueOf,
                        "HEAD_DOWN",
                        "HEAD_LEFT",
                        "HEAD_RIGHT",
                        "HEAD_UP"));

        put("namdreab",
                getAll(com.codenjoy.dojo.games.namdreab.Element::valueOf,
                        "HEAD_DOWN",
                        "HEAD_LEFT",
                        "HEAD_RIGHT",
                        "HEAD_UP",
                        "HEAD_DEAD",
                        "HEAD_EVIL",
                        "HEAD_FLY",
                        "HEAD_SLEEP"));
    }};

    private static List<CharElement> getAll(Function<String, CharElement> get, String... names) {
        return Arrays.stream(names)
                .map(get::apply)
                .collect(toList());
    }

    public static List<CharElement> get(String game) {
        // TODO do not use map.containsKey just check that map.get() != null
        if (!map.containsKey(game)) {
            throw new UnsupportedOperationException(
                    "Game not implemented in pseudo: " + game);
        }
        return map.get(game);
    }
}
