package com.codenjoy.dojo.namdreab;

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

import com.codenjoy.dojo.namdreab.services.GameSettings;

import static com.codenjoy.dojo.namdreab.services.GameSettings.Keys.*;
import static com.codenjoy.dojo.services.round.RoundSettings.Keys.*;

public class TestGameSettings extends GameSettings {

    public TestGameSettings() {
        update(this);
    }

    /**
     * Here you can override the settings for all tests.
     */
    public static GameSettings update(GameSettings settings) {
        return settings
                .bool(ROUNDS_ENABLED, true)
                .integer(ROUNDS_PER_MATCH, 5)
                .integer(ROUNDS_MIN_TICKS_FOR_WIN, 1)
                .integer(ROUNDS_TIME_BEFORE_START, 0)
                .integer(ROUNDS_TIME, 300)
                .integer(ROUNDS_TIME_FOR_WINNER, 1)
                .integer(DEATH_CAP_EFFECT_TIMEOUT, 10)
                .integer(FLY_AGARIC_EFFECT_TIMEOUT, 10)
                .integer(ACORN_REDUCED, 3);
    }
}
