package com.codenjoy.dojo.sampletext.model;

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


import com.codenjoy.dojo.sampletext.services.GameSettings;
import com.codenjoy.dojo.services.EventListener;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.multiplayer.GamePlayer;
import com.codenjoy.dojo.services.questionanswer.Examiner;
import com.codenjoy.dojo.services.questionanswer.event.FailTestEvent;
import com.codenjoy.dojo.services.questionanswer.event.PassTestEvent;
import com.codenjoy.dojo.services.questionanswer.levels.LevelsPool;
import com.codenjoy.dojo.services.questionanswer.levels.LevelsPoolImpl;

import static com.codenjoy.dojo.sampletext.services.Event.LOSE;
import static com.codenjoy.dojo.sampletext.services.Event.WIN;

/**
 * Класс игрока. Тут кроме героя может подсчитываться очки.
 * Тут же ивенты передабтся лиснеру фреймворка.
 */
public class Player extends GamePlayer<Hero, Field> {

    private LevelsPool level;
    private Examiner examiner;

    public Player(EventListener listener, GameSettings settings) {
        super(listener, settings);
        this.level = new LevelsPoolImpl(settings.levels());
        examiner = new Examiner(level);
    }

    public void clearScore() {
        if (examiner != null) {
            examiner.clear(0);
        }
    }

    @Override
    public Hero createHero(Point pt) {
        return new Hero();
    }

    public void checkAnswer() {
        hero.tick();

        for (Object event : examiner.ask(hero)) {
            if (event instanceof FailTestEvent) {
                event(LOSE);
            } else if (event instanceof PassTestEvent) {
                event(WIN);
            }
        }
    }

    public LevelsPool levels() {
        return level;
    }

    public Examiner examiner() {
        return examiner;
    }
}