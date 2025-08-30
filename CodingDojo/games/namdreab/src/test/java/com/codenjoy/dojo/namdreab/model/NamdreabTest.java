package com.codenjoy.dojo.namdreab.model;

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


import static com.codenjoy.dojo.services.round.RoundSettings.Keys.ROUNDS_ENABLED;

import com.codenjoy.dojo.namdreab.services.GameSettings;
import org.junit.Test;

public class NamdreabTest extends AbstractGameTest {

    @Override
    protected GameSettings setupSettings(GameSettings settings) {
        return super.setupSettings(settings)
                .bool(ROUNDS_ENABLED, false);
    }

    // спавниться подальше от других змеек
    @Test
    public void shouldSpawnFurtherAway() {
        // given
        givenFl("☼☼☼☼☼☼☼\n" +
            "☼#     ☼\n" +
            "☼     ☼\n" +
            "☼     ☼\n" +
            "☼     ☼\n" +
            "☼#×>  ☼\n" +
            "☼☼☼☼☼☼☼\n");

        givenPlayer();

        // then
        assertF("☼☼☼☼☼☼☼\n"
            + "×>     \n"
            + "☼☼     \n"
            + "☼☼     \n"
            + "☼☼     \n"
            + "☼☼#╘►  \n"
            + "☼☼☼☼☼☼☼\n");
    }


    // спавниться подальше от других змеек
    @Test
    public void shouldSpawnFurtherAway_NotOnTheSameSpot() {
        // given
        givenFl("☼☼☼☼☼☼☼\n" +
            "☼#     ☼\n" +
            "☼#    ☼\n" +
            "☼     ☼\n" +
            "☼     ☼\n" +
            "☼#×>  ☼\n" +
            "☼☼☼☼☼☼☼\n");

        givenPlayer();
        givenPlayer();

        // then
        assertF("☼☼☼☼☼☼☼\n"
            + "×>     \n"
            + "☼×>    \n"
            + "☼☼     \n"
            + "☼☼     \n"
            + "☼☼#╘►  \n"
            + "☼☼☼☼☼☼☼\n");
    }
}