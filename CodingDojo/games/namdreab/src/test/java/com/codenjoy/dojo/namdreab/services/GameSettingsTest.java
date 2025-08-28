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

import com.codenjoy.dojo.utils.TestUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GameSettingsTest {

    @Test
    public void shouldGetAllKeys() {
        assertEquals("DEATH_CAP_EFFECT_TIMEOUT  =[Game] Death cap (flying) effect timeout\n" +
                    "FLY_AGARIC_EFFECT_TIMEOUT =[Game] Fly agarics (fury) effect timeout\n" +
                    "DEATH_CAPS_COUNT          =[Game] Death caps count\n" +
                    "FLY_AGARICS_COUNT         =[Game] Fly agarics count\n" +
                    "STRAWBERRIES_COUNT        =[Game] Strawberries count\n" +
                    "ACORNS_COUNT              =[Game] Acorns count\n" +
                    "BLUEBERRIES_COUNT         =[Game] Blueberries count\n" +
                    "ACORN_REDUCED             =[Game] Acorn reduced value\n" +
                    "WIN_SCORE                 =[Score] Win score\n" +
                    "BLUEBERRY_SCORE           =[Score] Blueberry score\n" +
                    "STRAWBERRY_SCORE          =[Score] Strawberry score\n" +
                    "DIE_PENALTY               =[Score] Die penalty\n" +
                    "ACORN_SCORE               =[Score] Acorn score\n" +
                    "EAT_SCORE                 =[Score] Eat enemy score\n" +
                    "SCORE_COUNTING_TYPE       =[Score] Counting score mode",
                TestUtils.toString(new GameSettings().allKeys()));
    }
}