package com.codenjoy.dojo.games.mollymage;

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

import com.codenjoy.dojo.services.Direction;

import java.util.function.Function;

import static com.codenjoy.dojo.services.Direction.*;

public class Command {

    public static final String NONE = "";

    public static final Function<Direction, String> MOVE = Direction::toString;
    public static final String MOVE_LEFT = MOVE.apply(LEFT);
    public static final String MOVE_RIGHT = MOVE.apply(RIGHT);
    public static final String MOVE_UP = MOVE.apply(UP);
    public static final String MOVE_DOWN = MOVE.apply(DOWN);

    public static final String DROP_POTION = ACT.toString();

    public static final Function<Direction, String> DROP_POTION_THEN_MOVE = d -> d.ACT(true);
    public static final Function<Direction, String> MOVE_THEN_DROP_POTION = d -> d.ACT(false);

    public static final Function<Direction, String> THROW_POTION_AT =  d -> d.ACT(false, 1);

    public static final Function<Direction, String> EXPLODE_POTIONS_THEN_MOVE = d -> d.ACT(true, 2);
    public static final Function<Direction, String> MOVE_THEN_EXPLODE_POTIONS = d -> d.ACT(false, 2);
}
