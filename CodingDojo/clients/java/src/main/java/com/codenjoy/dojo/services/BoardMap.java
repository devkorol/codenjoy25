package com.codenjoy.dojo.services;

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

import static org.apache.commons.lang3.StringUtils.repeat;

public class BoardMap { // TODO test me

    public LengthToXY xy; // TODO make private
    private String map;
    private int size;

    public BoardMap(int size) {
        this.size = size;
        xy = new LengthToXY(size);
        map = repeat(' ', size * size);
    }

    public BoardMap(String map) {
        size = (int) Math.sqrt(map.length());
        xy = new LengthToXY(size);
        this.map = map;
    }

    public int size() {
        return size;
    }

    // TODO используется только в japanese - порефачить там
    public void resize(int newSize) {
        BoardMap boardMap = new BoardMap(newSize);
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                boardMap.setAt(x, y, this.getAt(x, y));
            }
        }
        this.size = boardMap.size();
        this.map = boardMap.map();
        this.xy = boardMap.xy();
    }

    public char getAt(int x, int y) {
        return map.charAt(xy.length(x, y));
    }

    public char setAt(int x, int y, char ch) {
        int length = xy.length(x, y);
        char old = map.charAt(length);
        map = map.substring(0, length)
                + ch
                + ((length + 1 < length())
                       ? map.substring(length + 1)
                       : "");
        return old;
    }

    public boolean isOutOf(int x, int y) {
        return Point.isOutOf(x, y, size);
    }

    public String map() {
        return map;
    }

    public int length() {
        return map.length();
    }

    public LengthToXY xy() {
        return xy;
    }
}
