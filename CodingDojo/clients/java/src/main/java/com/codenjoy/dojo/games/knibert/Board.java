package com.codenjoy.dojo.games.knibert;

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
import com.codenjoy.dojo.services.Direction;
import com.codenjoy.dojo.services.Point;

import java.util.Arrays;
import java.util.List;

import static com.codenjoy.dojo.games.knibert.Element.*;
import static com.codenjoy.dojo.services.Direction.*;

public class Board extends AbstractBoard<Element> {

    @Override
    public Element[] elements() {
        return Element.values();
    }

    public List<Point> getApples() {
        return get(GOOD_APPLE);
    }

    @Override
    protected int inversionY(int y) {
        return size - 1 - y;
    }

    public Direction getHeroDirection() {
        Point head = getHead();
        if (head == null) {
            return null;
        }
        if (isAt(head.getX(), head.getY(), HEAD_LEFT)) {
            return LEFT;
        } else if (isAt(head.getX(), head.getY(), HEAD_RIGHT)) {
            return RIGHT;
        } else if (isAt(head.getX(), head.getY(), HEAD_UP)) {
            return UP;
        } else {
            return DOWN;
        }
    }

    public Point getHead() {
        List<Point> result = get(
                HEAD_UP,
                HEAD_DOWN,
                HEAD_LEFT,
                HEAD_RIGHT);
        if (result.isEmpty()) {
            return null;
        } else {
            return result.get(0);
        }
    }

    public List<Point> getBarriers() {
        List<Point> result = getHero();
        result.addAll(getStones());
        result.addAll(getWalls());
        return result;
    }

    public List<Point> getHero() {
        Point head = getHead();
        if (head == null) {
            return Arrays.asList();
        }
        List<Point> result = get(
                TAIL_END_DOWN,
                TAIL_END_LEFT,
                TAIL_END_UP,
                TAIL_END_RIGHT,
                TAIL_HORIZONTAL,
                TAIL_VERTICAL,
                TAIL_LEFT_DOWN,
                TAIL_LEFT_UP,
                TAIL_RIGHT_DOWN,
                TAIL_RIGHT_UP);
        result.add(0, head);
        return result;
    }

    public boolean isGameOver() {
        return getHead() == null;
    }

    @Override
    public String toString() {
        return String.format("Board:\n%s\n" +
            "Apple at: %s\n" +
            "Stones at: %s\n" +
            "Head at: %s\n" +
            "Hero at: %s\n" +
            "Current direction: %s",
                boardAsString(),
                getApples(),
                getStones(),
                getHead(),
                getHero(),
                getHeroDirection());
    }

    public List<Point> getStones() {
        return get(Element.BAD_APPLE);
    }

    public List<Point> getWalls() {
        return get(Element.BREAK);
    }
}
