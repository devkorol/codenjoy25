package com.codenjoy.dojo.games.vacuum;

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


import com.codenjoy.dojo.client.AbstractBoard;
import com.codenjoy.dojo.services.Point;

import java.util.List;

import static com.codenjoy.dojo.games.vacuum.Element.*;

/**
 * Класс, обрабатывающий строковое представление доски.
 * Содержит ряд унаследованных методов {@link AbstractBoard},
 * но ты можешь добавить сюда любые свои методы на их основе.
 */
public class Board extends AbstractBoard<Element> {

    @Override
    public Element[] elements() {
        return Element.values();
    }

    @Override
    protected int inversionY(int y) {
        return size() - 1 - y;
    }

    public Element getAt(int x, int y) {
        if (isOutOf(x, y)) {
            return BARRIER;
        }
        return super.getAt(x, y);
    }

    public boolean isBarrierAt(int x, int y) {
        return isAt(x, y, BARRIER);
    }

    public boolean isBarrierAt(Point pt) {
        return isAt(pt, BARRIER);
    }

    @Override
    public String toString() {
        return String.format("%s\n" +
                        "Vacuum cleaner at: %s\n" +
                        "Start point at: %s\n" +
                        "Barriers at: %s\n" +
                        "Dust at: %s\n" +
                        "None: %s\n" +
                        "Roundabouts: %s\n" +
                        "Limiters: %s\n" +
                        "Switches: %s",
                boardAsString(),
                getHero(),
                getStartPoint(),
                getBarriers(),
                getDust(),
                getNone(),
                getRoundabouts(),
                getLimiters(),
                getSwitches());
    }

    public Point getHero() {
        return getFirst(VACUUM);
    }

    public Point getStartPoint() {
        Point start = getFirst(START);
        if (start != null) {
            return start;
        }
        return getHero();
    }

    public List<Point> getBarriers() {
        return get(BARRIER);
    }

    public List<Point> getDust() {
        return get(DUST);
    }

    public List<Point> getNone() {
        return get(NONE);
    }

    public List<Point> getRoundabouts() {
        return get(ROUNDABOUT_LEFT_UP,
                ROUNDABOUT_UP_RIGHT,
                ROUNDABOUT_RIGHT_DOWN,
                ROUNDABOUT_DOWN_LEFT);
    }

    public List<Point> getLimiters() {
        return get(LIMITER_LEFT,
                LIMITER_RIGHT,
                LIMITER_UP,
                LIMITER_DOWN,
                LIMITER_VERTICAL,
                LIMITER_HORIZONTAL);
    }

    public List<Point> getSwitches() {
        return get(SWITCH_LEFT,
                SWITCH_RIGHT,
                SWITCH_DOWN,
                SWITCH_UP);
    }

}
