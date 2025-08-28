package com.codenjoy.dojo.services.settings;

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

import com.codenjoy.dojo.services.round.RoundSettings;
import com.codenjoy.dojo.services.semifinal.SemifinalSettings;

import java.util.Arrays;
import java.util.List;

import static com.codenjoy.dojo.services.round.RoundSettings.Keys.*;
import static com.codenjoy.dojo.services.round.RoundSettings.Keys.ROUNDS_MIN_TICKS_FOR_WIN;
import static com.codenjoy.dojo.services.settings.SomeGameSettings.Keys.*;

public class SomeSemifinalRoundSettings extends SettingsImpl
        implements SemifinalSettings<SomeSemifinalRoundSettings>,
                RoundSettings<SomeSemifinalRoundSettings> {

    public enum Keys implements Key {

        PARAMETER1("Parameter 1"),
        PARAMETER2("Parameter 2"),
        PARAMETER3("Parameter 3"),
        PARAMETER4("Parameter 4");

        private String key;

        Keys(String key) {
            this.key = key;
        }

        @Override
        public String key() {
            return key;
        }
    }

    @Override
    public List<Key> allKeys() {
        return Arrays.asList(Keys.values());
    }

    public SomeSemifinalRoundSettings() {
        initRound();
        initSemifinal();

        integer(PARAMETER1, 12);
        integerValue(PARAMETER1).update(15);
        bool(PARAMETER2, true);
        real(PARAMETER3, 0.5);
        string(PARAMETER4, "string");
    }

    @Override
    public void initRound() {
        bool(ROUNDS_ENABLED, true);
        integer(ROUNDS_PLAYERS_PER_ROOM, 5);
        integer(ROUNDS_TEAMS_PER_ROOM, 1);
        integer(ROUNDS_TIME, 200);
        integer(ROUNDS_TIME_FOR_WINNER, 1);
        integer(ROUNDS_TIME_BEFORE_START, 5);
        integer(ROUNDS_PER_MATCH, 1);
        integer(ROUNDS_MIN_TICKS_FOR_WIN, 1);
    }

    @Override
    public String toString() {
        return "Some" + super.toStringShort();
    }
}
