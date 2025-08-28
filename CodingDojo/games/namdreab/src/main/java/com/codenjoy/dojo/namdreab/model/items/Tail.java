package com.codenjoy.dojo.namdreab.model.items;

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


import com.codenjoy.dojo.games.namdreab.Element;
import com.codenjoy.dojo.games.namdreab.ElementUtils;
import com.codenjoy.dojo.namdreab.model.Hero;
import com.codenjoy.dojo.namdreab.model.Player;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.PointImpl;
import com.codenjoy.dojo.services.printer.state.State;

import java.util.Arrays;
import java.util.List;

import static com.codenjoy.dojo.games.namdreab.Element.*;

public class Tail extends PointImpl implements State<Element, Player> {

    private Hero hero;

    public Tail(Point pt, Hero hero) {
        super(pt);
        this.hero = hero;
    }

    @Override
    public Element state(Player player, Object... alsoAtPoint) {
        Hero hero = player.getHero();
        Tail tail = higher(Arrays.asList(alsoAtPoint));
        Element element = tail.state();
        return tail.belongsTo(hero)
                ? element
                : ElementUtils.enemyHero(element);
    }

    private boolean belongsTo(Hero hero) {
        return this.hero.equals(hero);
    }

    private Element state() {
        if (isHead()) {
            if (!hero.isAlive()) {
                return HERO_DEAD;
            }

            if (!hero.isActive()) {
                return HERO_SLEEP;
            }

            if (hero.isFlying()) {
                return HERO_FLY;
            }

            if (hero.isFury()) {
                return HERO_EVIL;
            }

            return ElementUtils.head(hero.direction());
        }
        if (isTail()) {
            if (!hero.isActive()) {
                return HERO_TAIL_INACTIVE;
            }

            return ElementUtils.tail(hero.tailDirection());
        }
        return ElementUtils.beard(hero.bodyDirection(this));
    }

    private Tail higher(List<Object> elements) {
        return elements.stream()
                .filter(element -> element instanceof Tail)
                .map(element -> (Tail) element)
                .min((t1, t2) -> {
                    boolean isHead1 = t1.isHead();
                    boolean isHead2 = t2.isHead();
                    if (isHead1 && isHead2) {
                        return 0;
                    }

                    if (isHead1) {
                        return -1;
                    }
                    if (isHead2) {
                        return 1;
                    }
                    return Integer.compare(t2.bodyIndex(), t1.bodyIndex());
                })
                .orElse(this);
    }

    private int bodyIndex() {
        return hero.bodyIndex(this);
    }

    private boolean isHead() {
        return hero.itsMyHead(this);
    }

    private boolean isTail() {
        return hero.itsMyTail(this);
    }
}