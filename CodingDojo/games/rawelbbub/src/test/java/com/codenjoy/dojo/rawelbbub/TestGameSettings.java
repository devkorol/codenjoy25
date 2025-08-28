package com.codenjoy.dojo.rawelbbub;

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


import com.codenjoy.dojo.rawelbbub.services.GameSettings;

import static com.codenjoy.dojo.rawelbbub.services.GameSettings.Keys.*;
import static com.codenjoy.dojo.services.round.RoundSettings.Keys.ROUNDS_ENABLED;

public class TestGameSettings extends GameSettings {

    public static final int CHANCE = 20;
    public static final int DICE_IMMORTALITY = 0*CHANCE;
    public static final int DICE_BREAKING_BAD = 1*CHANCE;
    public static final int DICE_WALKING_ON_FISHNET = 2*CHANCE;
    public static final int DICE_VISIBILITY = 3*CHANCE;
    public static final int DICE_NO_SLIDING = 4*CHANCE;

    /**
     * Here you can override the settings for all tests.
     */
    public TestGameSettings() {
        bool(ROUNDS_ENABLED, false);
        integer(TURN_MODE, MODE_ALL_DIRECTIONS);
        integer(AI_PRIZE_PROBABILITY, 4);
        integer(AI_PRIZE_SURVIVABILITY, 3);
        integer(AI_TICKS_PER_SHOOT, 10);
        integer(HERO_TICKS_PER_SHOOT, 1);
        integer(OIL_SLIPPERINESS, 3);
        bool(SHOW_MY_HERO_UNDER_SEAWEED, false);
        integer(PRIZE_AVAILABLE_TIMEOUT, 3);
        integer(PRIZE_EFFECT_TIMEOUT, 10);
        integer(PRIZES_COUNT, 10);
        integer(CHANCE_IMMORTALITY, CHANCE);
        integer(CHANCE_BREAKING_BAD, CHANCE);
        integer(CHANCE_WALKING_ON_FISHNET, CHANCE);
        integer(CHANCE_VISIBILITY, CHANCE);
        integer(CHANCE_NO_SLIDING, CHANCE);
        integer(ICEBERG_REGENERATE_TIME, 30);
        integer(HERO_DIED_PENALTY, -10);
    }
}