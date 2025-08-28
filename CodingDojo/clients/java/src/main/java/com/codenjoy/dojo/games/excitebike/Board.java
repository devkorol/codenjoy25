package com.codenjoy.dojo.games.excitebike;

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
import com.codenjoy.dojo.services.annotations.PerformanceOptimized;
import com.codenjoy.dojo.services.printer.CharElement;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The class is a wrapper over the board string
 * coming from the server. Contains a number of
 * inherited methods {@link AbstractBoard},
 * but you can add any methods based on them here.
 */
public class Board extends AbstractBoard<CharElement> {

    private static final String OTHER_BIKE_PREFIX = "OTHER";
    private static final String BIKE_PREFIX = "BIKE";
    private static final String FALLEN_BIKE_SUFFIX = "FALLEN";

    @PerformanceOptimized
    public static final Element[] BIKE_TYPES = Arrays.stream(Element.values())
            .filter(v -> v.name().contains(BIKE_PREFIX))
            .filter(v -> !v.name().contains(OTHER_BIKE_PREFIX))
            .collect(Collectors.toList())
            .toArray(new Element[]{});

    @Override
    @PerformanceOptimized
    public CharElement[] elements() {
        return Element.values();
    }

    public Point getHero() {
        return getFirst(BIKE_TYPES);
    }

    public boolean isGameOver() {
        Point me = getHero();
        return me == null || Arrays.stream(Element.values())
                .filter(v -> !v.name().contains(OTHER_BIKE_PREFIX)
                        && v.name().contains(FALLEN_BIKE_SUFFIX))
                .anyMatch(v -> isAt(me, v));
    }

    public boolean checkNearMe(List<Direction> directions, CharElement... elements) {
        Point point = getHero();
        if (point == null) {
            return false;
        }
        for (Direction direction : directions) {
            point = direction.change(point);
        }
        return isAt(point.getX(), point.getY(), elements);
    }

    public boolean checkNearMe(Direction direction, CharElement... elements) {
        Point hero = getHero();
        if (hero == null) {
            return false;
        }
        Point atDirection = direction.change(hero);
        return isAt(atDirection.getX(), atDirection.getY(), elements);
    }

    public boolean checkAtMe(CharElement... elements) {
        Point hero = getHero();
        return hero != null && isAt(hero, elements);
    }

    public boolean isOutOfFieldRelativeToMe(Direction direction) {
        Point hero = getHero();
        if (hero == null) {
            return false;
        }
        Point atDirection = direction.change(hero);
        return isOutOf(atDirection.getX(), atDirection.getY());
    }

    @Override
    public String toString() {
        String superToString = super.toString();
        int indexOfFirstFence = superToString.indexOf(Element.FENCE.ch());
        int nextLineStatementLength = 2;
        return superToString.substring(indexOfFirstFence >= nextLineStatementLength
                ? indexOfFirstFence - nextLineStatementLength
                : indexOfFirstFence);
    }
}
