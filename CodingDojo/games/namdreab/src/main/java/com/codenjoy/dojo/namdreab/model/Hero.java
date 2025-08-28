package com.codenjoy.dojo.namdreab.model;

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


import com.codenjoy.dojo.games.namdreab.BodyDirection;
import com.codenjoy.dojo.games.namdreab.TailDirection;
import com.codenjoy.dojo.namdreab.model.items.Tail;
import com.codenjoy.dojo.services.Direction;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.joystick.Act;
import com.codenjoy.dojo.services.joystick.RoundsDirectionActJoystick;
import com.codenjoy.dojo.services.printer.state.State;
import com.codenjoy.dojo.services.round.RoundPlayerHero;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static com.codenjoy.dojo.games.namdreab.BodyDirection.*;
import static com.codenjoy.dojo.games.namdreab.TailDirection.*;
import static com.codenjoy.dojo.namdreab.services.Event.Type.DIE;
import static com.codenjoy.dojo.namdreab.services.GameSettings.Keys.*;
import static com.codenjoy.dojo.services.Direction.LEFT;
import static com.codenjoy.dojo.services.Direction.RIGHT;

public class Hero extends RoundPlayerHero<Field>
        implements RoundsDirectionActJoystick,
                   State<LinkedList<Tail>, Player> {

    private static final int MINIMUM_LENGTH = 2;

    public static final boolean NOW = true;
    public static final boolean NEXT_TICK = !NOW;

    private static final int ACT_SUICIDE = 0;

    private LinkedList<Tail> body;
    private Direction direction;
    private Direction newDirection;
    private int growBy;
    private int acornsCount;
    private int flyingCount;
    private int furyCount;
    private boolean leaveBlueberry;
    private Point lastTailPosition;
    private int score;

    public Hero(Point pt) {
        this(RIGHT);
        body.add(new Tail(LEFT.change(pt), this));
        body.add(new Tail(pt, this));
    }

    public Hero(Direction direction) {
        this.direction = direction;
        body = new LinkedList<>();
        clearScores();
    }

    public void clearScores() {
        growBy = 0;
        score = 0;
        leaveBlueberry = false;
        newDirection = null;
        acornsCount = 0;
        flyingCount = 0;
        furyCount = 0;
    }

    public void addScore(int added) {
        score = Math.max(0, score + added);
    }

    @Override
    public int scores() {
        return score;
    }

    public List<Tail> body() {
        return body;
    }

    public List<Tail> reversedBody() {
        return new LinkedList<>(body){{
            Collections.reverse(this);
        }};
    }

    public Point tail() {
        return body.getFirst();
    }

    @Override
    public int getX() {
        return head().getX();
    }

    @Override
    public int getY() {
        return head().getY();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null) {
            return false;
        }

        if (!(o instanceof Hero)) {
            throw new IllegalArgumentException("Must be Hero!");
        }

        return o == this;
    }

    public int size() {
        return body == null ? 0 : body.size();
    }

    public Point head() {
        if (body.isEmpty()) {
            return pt(-1, -1);
        }
        return body.getLast();
    }

    public Point neck() {
        if (body.size() <= 1) {
            return pt(-1, -1);
        }
        int last = body.size() - 1;
        return body.get(last - 1);
    }

    @Override
    public void change(Direction direction) {
        newDirection = direction;
    }

    @Override
    public void act(Act act) {
        // TODO test что только если змейка жива, если она в загоне нельзя давать эту команду выполнять
        if (act.is(ACT_SUICIDE)) {
            die();
            leaveBlueberry = true;
            return;
        }

        if (act.is()) {
            if (acornsCount <= 0) {
                return;
            }
            Point to = tail();
            if (field.addAcorn(to)) {
                acornsCount--;
            }
            return;
        }
    }

    @Override
    public void die() {
        die(DIE);
    }

    public Direction direction() {
        return direction;
    }

    @Override
    public void tickHero() {
        applyNewDirection();

        reduceIfShould();
        tickPills();

        Point next = direction.change(head());
        if (isMe(next) && !isFlying())
            selfReduce(next);

        go(next);
    }

    private void applyNewDirection() {
        if (newDirection != null && !newDirection.equals(direction.inverted())) {
            direction = newDirection;
            newDirection = null;
        }
    }

    // Этот метод должен вызываться отдельно от tick,
    // уже после обработки столкновений с другими змейками
    public void eat() {
        if (!isActiveAndAlive()) {
            return;
        }

        Point head = head();
        if (field.isBlueberry(head)) {
            growBy(1);
            // если не сделать этого здесь, при съедании черники и одновременной
            // потере части бороды будет зачтена лишь на следующий тик,
            // что неправильно
            grow();
        }
        if (field.isAcorn(head) && !isFlying()) {
            acornsCount++;
            if (!isFury()) {
                reduce(settings().integer(ACORN_REDUCED), NOW);
            }
        }
        if (field.isDeathCap(head)) {
            eatDeathCap();
        }
        if (field.isFlyAgaric(head)) {
            eatFlyAgaric();
        }
        if (field.isBarrier(head)) {
            die();
        }
    }

    public void eatDeathCap() {
        // TODO запусти test shouldCase13 там идет загрузка из текста борды,
        //      еще до создания field когда создаются heroes идет запрос на
        //      settings() и он возвращает null, поэтому тут надо проверять
        flyingCount += (settings() != null)
                ? settings().integer(DEATH_CAP_EFFECT_TIMEOUT)
                : 10;
    }

    public void eatFlyAgaric() {
        // TODO запусти test shouldCase13 там идет загрузка из текста борды,
        //      еще до создания field когда создаются heroes идет запрос на
        //      settings() и он возвращает null, поэтому тут надо проверять
        furyCount += (settings() != null)
                ? settings().integer(FLY_AGARIC_EFFECT_TIMEOUT)
                : 10;
    }

    public void tickPills() {
        if (isFlying()) {
            flyingCount--;
        }
        if (isFury()) {
            furyCount--;
        }
    }

    private void reduceIfShould() {
        if (growBy < 0) {
            if (growBy < -body.size()) {
                die();
            } else {
                body = new LinkedList<>(body.subList(-growBy, body.size()));
                // TODO тут тоже надо по идее lastTailPosition = getTailPoint();
            }
            growBy = 0;
        }
    }

    private void selfReduce(Point from) {
        if (from.equals(tail()))
            return;
        body = new LinkedList<>(body.subList(body.indexOf(from), body.size()));
        // TODO тут тоже надо по идее lastTailPosition = getTailPoint();
    }

    public int reduceFrom(Point from) {
        int was = size();
        lastTailPosition = from;
        body = new LinkedList<>(body.subList(body.indexOf(from) + 1, body.size()));
        if (size() < MINIMUM_LENGTH) {
            die();
            return was; // TODO я не нашел случая когда это может случиться
        } else {
            return was - size();
        }
    }

    public int reduce(int len, boolean now) {
        int was = size();
        if (was < len + MINIMUM_LENGTH) {
            die();
            return was;
        } else {
            if (now) {
                body = new LinkedList<>(body.subList(len, body.size()));
                // TODO тут тоже надо по идее lastTailPosition = getTailPoint();
            } else {
                growBy = -len;
            }
            return len;
        }
    }

    private void grow() {
        growBy--;
        body.addFirst(new Tail(lastTailPosition, this));
    }

    private void go(Point newLocation) {
        lastTailPosition = tail();
        body.add(new Tail(newLocation, this));
        body.removeFirst();
    }

    public boolean isHeadIntersect(Hero enemy) {
        return enemy.head().equals(head()) ||
                enemy.neck().equals(head()) ||
                neck().equals(enemy.head());
    }

    @Override
    public LinkedList<Tail> state(Player player, Object... alsoAtPoint) {
        return body;
    }

    public BodyDirection bodyDirection(Tail curr) {
        int currIndex = bodyIndex(curr);
        Point prev = body.get(currIndex - 1);
        Point next = body.get(currIndex + 1);

        BodyDirection nextPrev = orientation(next, prev);
        if (nextPrev != null) {
            return nextPrev;
        }

        if (orientation(prev, curr) == HORIZONTAL) {
            boolean clockwise = curr.getY() < next.getY() ^ curr.getX() > prev.getX();
            if (curr.getY() < next.getY()) {
                return (clockwise) ? TURNED_RIGHT_UP : TURNED_LEFT_UP;
            } else {
                return (clockwise) ? TURNED_LEFT_DOWN : TURNED_RIGHT_DOWN;
            }
        } else {
            boolean clockwise = curr.getX() < next.getX() ^ curr.getY() < prev.getY();
            if (curr.getX() < next.getX()) {
                return (clockwise) ? TURNED_RIGHT_DOWN : TURNED_RIGHT_UP;
            } else {
                return (clockwise) ? TURNED_LEFT_UP : TURNED_LEFT_DOWN;
            }
        }
    }

    private BodyDirection orientation(Point curr, Point next) {
        if (curr.getX() == next.getX()) {
            return VERTICAL;
        } else if (curr.getY() == next.getY()) {
            return HORIZONTAL;
        } else {
            return null;
        }
    }

    public TailDirection tailDirection() {
        Point body = this.body.get(1);
        Point tail = tail();

        if (body.getX() == tail.getX()) {
            return (body.getY() < tail.getY()) ? VERTICAL_UP : VERTICAL_DOWN;
        } else {
            return (body.getX() < tail.getX()) ? HORIZONTAL_RIGHT : HORIZONTAL_LEFT;
        }
    }

    public boolean itsMyHead(Point point) {
        return head() == point;
    }

    public boolean isMe(Point next) {
        return body.contains(next);
    }

    public boolean itsMyTail(Point point) {
        return tail() == point;
    }

    public void growBy(int val) {
        growBy += val;
    }

    public void clear() {
        List<Point> points = new LinkedList<>(body);
        body = new LinkedList<>();
        if (leaveBlueberry) {
            points.forEach(e -> field.addBlueberry(e));
            leaveBlueberry = false;
        }
        growBy = 0;
    }

    public int acornsCount() {
        return acornsCount;
    }

    public int flyingCount() {
        return flyingCount;
    }

    public int furyCount() {
        return furyCount;
    }

    public void removeFury() {
        furyCount = 0;
    }

    public boolean isFlying() {
        return flyingCount > 0;
    }

    public void removeFlying() {
        flyingCount = 0;
    }

    public boolean isFury() {
        return furyCount > 0;
    }

    public void addTail(Point part) {
        body.addFirst(new Tail(part, this));
    }

    public int bodyIndex(Point pt) {
        // возможны наложения элементов по pt, а потому надо вначале искать по ==
        for (int index = 0; index < body.size(); index++) {
            if (body.get(index) == pt) {
                return index;
            }
        }
        return body.indexOf(pt);
    }

    @Override
    public String toString() {
        return String.format("[%s,%s]", head().getX(), head().getY());
    }
}