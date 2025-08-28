package com.codenjoy.dojo.namdreab.services.ai;

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


import com.codenjoy.dojo.client.Solver;
import com.codenjoy.dojo.games.namdreab.Board;
import com.codenjoy.dojo.services.Dice;
import com.codenjoy.dojo.services.Direction;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.algs.DeikstraFindWay;

import java.util.List;

import static com.codenjoy.dojo.games.namdreab.Element.*;

public class AISolver implements Solver<Board> {

    private DeikstraFindWay way;
    private Dice dice;
    private Point head;
    private Point neck;

    public AISolver(Dice dice) {
        this.dice = dice;
        this.way = new DeikstraFindWay();
    }

    public DeikstraFindWay.Possible possible(Board board) {
        return new DeikstraFindWay.Possible() {
            @Override // TODO test me
            public boolean possible(Point from, Direction where) {
                Point to = where.change(from);

                if (neck != null && neck.equals(to)) {
                    return false;
                }

                int nx = to.getX();
                int ny = to.getY();

                // вероятность не есть желудь 3/4
                if (dice.next(4) != 0)
                    if (board.isAcornAt(nx, ny))
                        return false;

                // вероятность не врезаться в противника 9/10
                if (dice.next(10) != 0)
                    if (board.isAt(nx, ny,
                            ENEMY_HERO_DOWN,
                            ENEMY_HERO_LEFT,
                            ENEMY_HERO_RIGHT,
                            ENEMY_HERO_UP,
                            ENEMY_HERO_EVIL,
                            ENEMY_HERO_FLY,
                            ENEMY_HERO_TAIL_DOWN,
                            ENEMY_HERO_TAIL_LEFT,
                            ENEMY_HERO_TAIL_UP,
                            ENEMY_HERO_TAIL_RIGHT,
                            ENEMY_HERO_BEARD_HORIZONTAL,
                            ENEMY_HERO_BEARD_VERTICAL,
                            ENEMY_HERO_BEARD_LEFT_DOWN,
                            ENEMY_HERO_BEARD_LEFT_UP,
                            ENEMY_HERO_BEARD_RIGHT_DOWN,
                            ENEMY_HERO_BEARD_RIGHT_UP))
                        return false;

                //вероятность не есть себя 3/4
                if (dice.next(3) != 0)
                    if (board.isAt(nx, ny,
                            HERO_BEARD_HORIZONTAL,
                            HERO_BEARD_VERTICAL,
                            HERO_BEARD_LEFT_DOWN,
                            HERO_BEARD_LEFT_UP,
                            HERO_BEARD_RIGHT_DOWN,
                            HERO_BEARD_RIGHT_UP))
                        return false;

                return true;
            }

            @Override
            public boolean possible(Point point) {
                return !board.isBarrierAt(point.getX(), point.getY());
            }
        };
    }

    @Override
    public String get(final Board board) {
        if (board.isGameOver()) return "";
        head = board.getHeroHead();
        List<Direction> result = getDirections(board);
        neck = head;
        if (result.isEmpty()) return "";
        return result.get(0).toString() + getBombIfNeeded(board);
    }

    private String getBombIfNeeded(Board board) {
        if (head.getX() % 2 == 0 && head.getY() % 2 == 0) {
            return ", ACT";
        } else {
            return "";
        }
    }

    public List<Direction> getDirections(Board board) {
        int size = board.size();
        List<Point> to = board.get(FLY_AGARIC, DEATH_CAP, BLUEBERRY, STRAWBERRY);
        DeikstraFindWay.Possible map = possible(board);
        return way.getShortestWay(size, head, to, map);
    }
}
