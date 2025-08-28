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

import com.codenjoy.dojo.namdreab.TestGameSettings;
import com.codenjoy.dojo.namdreab.services.Event;
import com.codenjoy.dojo.namdreab.services.GameRunner;
import com.codenjoy.dojo.namdreab.services.GameSettings;
import com.codenjoy.dojo.services.GameType;
import com.codenjoy.dojo.utils.gametest.NewAbstractBaseGameTest;
import org.junit.After;
import org.junit.Before;

import java.util.function.Function;

public abstract class AbstractGameTest
        extends NewAbstractBaseGameTest<Player, Namdreab, GameSettings, Level, Hero> {

    @Before
    public void setup() {
        super.setup();
    }

    @After
    public void after() {
        super.after();
    }

    @Override
    protected GameType gameType() {
        return new GameRunner();
    }

    @Override
    protected GameSettings setupSettings(GameSettings settings) {
        return TestGameSettings.update(settings);
    }

    @Override
    protected Function<String, Level> createLevel() {
        return Level::new;
    }

    @Override
    protected Class<?> eventClass() {
        return Event.class;
    }

    @Override
    protected boolean manualHero() {
        // очень много тестов со сложным рисунком героев
        // это отдаляет игру от того, что будет на production,
        // но сделано для удобства тестирования
        return true;
    }

    @Override
    protected void afterCreatePlayer(Player player) {
        Hero hero = player.getHero();
        if (hero != null) {
            hero.setActive(true);
        }
    }
}