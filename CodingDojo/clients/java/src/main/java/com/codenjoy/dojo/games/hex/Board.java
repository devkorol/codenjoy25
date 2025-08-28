package com.codenjoy.dojo.games.hex;

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

import java.util.Collection;
import java.util.List;

import static com.codenjoy.dojo.games.hex.Element.*;
import static com.codenjoy.dojo.services.PointImpl.pt;

/**
 * The class is a wrapper over the board string
 * coming from the server. Contains a number of
 * inherited methods {@link AbstractBoard},
 * but you can add any methods based on them here.
 */
public class Board extends AbstractBoard<Element> {

    @Override
    public Element[] elements() {
        return Element.values();
    }

    public List<Point> getBarriers() {
        return get(WALL,
                MY_HERO,
                HERO1,
                HERO2,
                HERO3,
                HERO4,
                HERO5,
                HERO6,
                HERO7,
                HERO8,
                HERO9,
                HERO10,
                HERO11);
    }

    public Collection<Point> getWalls() {
        return get(WALL);
    }

    public boolean isBarrierAt(int x, int y) {
        Point pt = pt(x, y);
        return getBarriers().contains(pt) || pt.isOutOf(size());
    }

    public boolean isGameOver() {
        return getFirst(MY_HERO) == null
                || getFirst(NONE) == null;
    }
}
