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


import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LengthToXYTest {

    @Test
    public void test() {
        LengthToXY xy = new LengthToXY(5);

        assertEquals(5*5 - 5, xy.length(0, 0));
        assertEquals(0, xy.length(0, 4));

        assertEquals(5*5 - 1, xy.length(4, 0));
        assertEquals(4, xy.length(4, 4));

        assertEquals("[0,0]", xy.point(xy.length(0, 0)).toString());
        assertEquals("[0,4]", xy.point(xy.length(0, 4)).toString());

        assertEquals("[4,0]", xy.point(xy.length(4, 0)).toString());
        assertEquals("[4,4]", xy.point(xy.length(4, 4)).toString());
    }
}
