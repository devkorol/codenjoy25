package com.codenjoy.dojo.namdreab.services;

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

import com.codenjoy.dojo.services.event.Calculator;
import com.codenjoy.dojo.services.settings.AllSettings;
import com.codenjoy.dojo.services.settings.PropertiesKey;
import com.codenjoy.dojo.services.settings.SettingsImpl;

import java.util.Arrays;
import java.util.List;

import static com.codenjoy.dojo.namdreab.services.GameRunner.GAME_NAME;
import static com.codenjoy.dojo.namdreab.services.GameSettings.Keys.*;

public class GameSettings extends SettingsImpl implements AllSettings<GameSettings> {

    public enum Keys implements PropertiesKey {

        DEATH_CAP_EFFECT_TIMEOUT,
        FLY_AGARIC_EFFECT_TIMEOUT,
        DEATH_CAPS_COUNT,
        FLY_AGARICS_COUNT,
        STRAWBERRIES_COUNT,
        ACORNS_COUNT,
        BLUEBERRIES_COUNT,
        ACORN_REDUCED,
        WIN_SCORE,
        BLUEBERRY_SCORE,
        STRAWBERRY_SCORE,
        DIE_PENALTY,
        ACORN_SCORE,
        EAT_SCORE,
        SCORE_COUNTING_TYPE;

        private String key;

        Keys() {
            this.key = key(GAME_NAME);
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
    
    public GameSettings() {
        initAll();

        // сколько тиков на 1 раунд
        setTimePerRound(300);
        // сколько раундов (с тем же составом героев) на 1 матч
        setRoundsPerMatch(3);
        // // сколько тиков должно пройти от начала раунда, чтобы засчитать победу
        setMinTicksForWin(40);

        integer(DEATH_CAP_EFFECT_TIMEOUT, 10);
        integer(FLY_AGARIC_EFFECT_TIMEOUT, 10);

        integer(DEATH_CAPS_COUNT, 2);
        integer(FLY_AGARICS_COUNT, 2);
        integer(STRAWBERRIES_COUNT, 5);
        integer(ACORNS_COUNT, 10);
        integer(BLUEBERRIES_COUNT, 30);

        integer(ACORN_REDUCED, 3);

        integer(WIN_SCORE, 50);
        integer(BLUEBERRY_SCORE, 1);
        integer(STRAWBERRY_SCORE, 10);
        integer(DIE_PENALTY, -0);
        integer(ACORN_SCORE, 5);
        integer(EAT_SCORE, 10);

        Levels.setup(this);
    }

    public Calculator<Integer> calculator() {
        return new Calculator<>(new Scores(this));
    }
}