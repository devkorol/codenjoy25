package com.codenjoy.dojo.games.sokoban;

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

import static com.codenjoy.dojo.games.sokoban.Element.HERO;
import static com.codenjoy.dojo.games.sokoban.Element.WALL;

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

    public boolean isBarrierAt(int x, int y) {
        return isAt(x, y, WALL);
    }

    public Point getHero() {
        return getFirst(HERO);
    }

    /**
     * @return bollean game over == true if all boxes are impossible to push by hero and not in mark fields
     */
    public boolean isGameOver() {
        return false;  // TODO realize condition of GameOver
    }

}