package com.codenjoy.dojo.sample.model.items;

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


import com.codenjoy.dojo.games.sample.Element;
import com.codenjoy.dojo.sample.model.Hero;
import com.codenjoy.dojo.sample.model.Player;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.PointImpl;
import com.codenjoy.dojo.services.printer.state.State;

/**
 * Артефакт - бомба на поле.
 *
 * Если артефакт пассивный, то он умеет только рисовать себя, но
 * встречаются и более активные артефакты, тогда тут может быть размещена их логика.
 * Яркий тому пример {@link com.codenjoy.dojo.sample.model.Hero}.
 */
public class Bomb extends PointImpl implements State<Element, Player> {

    private Hero owner;

    public Bomb(Point pt, Hero owner) {
        super(pt);
        this.owner = owner;
    }

    public Hero owner() {
        return owner;
    }

    @Override
    public Element state(Player player, Object... alsoAtPoint) {
        return Element.BOMB;
    }
}
