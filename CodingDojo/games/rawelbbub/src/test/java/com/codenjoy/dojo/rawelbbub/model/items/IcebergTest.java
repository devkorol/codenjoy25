package com.codenjoy.dojo.rawelbbub.model.items;

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


import com.codenjoy.dojo.rawelbbub.model.items.Iceberg;
import com.codenjoy.dojo.rawelbbub.model.items.Torpedo;
import com.codenjoy.dojo.services.Direction;
import org.junit.Test;

import static com.codenjoy.dojo.services.Direction.*;
import static com.codenjoy.dojo.services.PointImpl.pt;
import static org.junit.Assert.assertEquals;

public class IcebergTest {

    @Test
    public void shouldDestroyOnce() {
        assertDestroyFrom('╠', LEFT);
        assertDestroyFrom('╣', RIGHT);
        assertDestroyFrom('╦', UP);
        assertDestroyFrom('╩', DOWN);
    }

    @Test
    public void shouldDestroyTwice() {
        assertDestroyFrom('╞', LEFT, LEFT);
        assertDestroyFrom('╡', RIGHT, RIGHT);
        assertDestroyFrom('╥', UP, UP);
        assertDestroyFrom('╨', DOWN, DOWN);
    }

    @Test
    public void shouldDestroyFromOneSideThreeTimes() {
        assertDestroyFrom(' ', LEFT, LEFT, LEFT);
        assertDestroyFrom(' ', RIGHT, RIGHT, RIGHT);
        assertDestroyFrom(' ', UP, UP, UP);
        assertDestroyFrom(' ', DOWN, DOWN, DOWN);
    }

    @Test
    public void shouldDestroyOnceAndFromOtherSideAnother() {
        assertDestroyFrom('┌', UP, LEFT);
        assertDestroyFrom('┐', UP, RIGHT);
        assertDestroyFrom('─', UP, DOWN);

        assertDestroyFrom('└', DOWN, LEFT);
        assertDestroyFrom('┘', DOWN, RIGHT);
        assertDestroyFrom('─', DOWN, UP);

        assertDestroyFrom('│', LEFT, RIGHT);
        assertDestroyFrom('┌', LEFT, UP);
        assertDestroyFrom('└', LEFT, DOWN);

        assertDestroyFrom('│', RIGHT, LEFT);
        assertDestroyFrom('┐', RIGHT, UP);
        assertDestroyFrom('┘', RIGHT, DOWN);
    }

    @Test
    public void shouldDestroyTwiceFromSomeSideAndFormOtherSideLast() {
        assertDestroyAll(LEFT);
        assertDestroyAll(RIGHT);
        assertDestroyAll(UP);
        assertDestroyAll(DOWN);
    }

    private void assertDestroyAll(Direction direction) {
        assertDestroyAll(LEFT, direction);
        assertDestroyAll(RIGHT, direction);
        assertDestroyAll(UP, direction);
        assertDestroyAll(DOWN, direction);
    }

    private void assertDestroyAll(Direction direction1, Direction direction2) {
        assertDestroyFrom(' ', LEFT, direction1, direction2);
        assertDestroyFrom(' ', RIGHT, direction1, direction2);
        assertDestroyFrom(' ', UP, direction1, direction2);
        assertDestroyFrom(' ', DOWN, direction1, direction2);
    }

    private void assertDestroyFrom(char expected, Direction... directions) {
        Iceberg iceberg = new Iceberg(pt(0, 0));
        for (Direction direction : directions) {
            iceberg.affect(torpedo(direction));
        }
        assertEquals(String.valueOf(expected),
                String.valueOf(iceberg.state(null).ch()));
    }

    private Torpedo torpedo(Direction direction) {
        return new Torpedo(null, direction.inverted(), pt(0, 0), null);
    }
}