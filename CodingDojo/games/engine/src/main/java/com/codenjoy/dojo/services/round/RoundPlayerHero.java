package com.codenjoy.dojo.services.round;

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

import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.multiplayer.PlayerHero;

public abstract class RoundPlayerHero<F extends RoundGameField> extends PlayerHero<F> {

    private RoundGamePlayer player;

    private boolean alive;
    private boolean active;

    public RoundPlayerHero() {
        this(pt(-1, -1));
    }

    public RoundPlayerHero(Point pt) {
        super(pt);

        active = false;
        alive = true;
    }

    public boolean isActiveAndAlive() {
        return isActive() && isAlive();
    }

    @Override
    public boolean isAlive() {
        return alive;
    }

    public void die(Object loseEvent) {
        if (alive) {
            alive = false;
            field.oneMoreDead(player, loseEvent);
        }
    }

    @Override
    public void tick() {
        if (!isActiveAndAlive()) return;

        tickHero();
    }

    protected abstract void tickHero();

    public abstract void die();

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setPlayer(RoundGamePlayer player) {
        this.player = player;
    }

    public RoundGamePlayer getPlayer() {
        return player;
    }

    public void event(Object event) {
        if (!hasPlayer()) {
            return;
        }

        player.event(event);
    }

    public abstract int scores();

    public boolean hasPlayer() {
        return player != null;  // TODO а почему player может быть у героя null ?
    }

    public int getTeamId() {
        if (!hasPlayer()) {
            return -1;
        }
        return getPlayer().getTeamId();
    }

    public boolean isMyTeam(RoundPlayerHero hero) {
        return getTeamId() == hero.getTeamId();
    }
}