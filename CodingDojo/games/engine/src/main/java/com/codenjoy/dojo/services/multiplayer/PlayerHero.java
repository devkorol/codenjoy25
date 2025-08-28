package com.codenjoy.dojo.services.multiplayer;

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


import com.codenjoy.dojo.services.Joystick;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.PointImpl;
import com.codenjoy.dojo.services.Tickable;
import com.codenjoy.dojo.services.field.Fieldable;
import com.codenjoy.dojo.services.joystick.NoMessageJoystick;
import com.codenjoy.dojo.services.settings.SettingsReader;
import org.json.JSONObject;

/**
 * Герой на поле, которым может управлять игрок должен быть представлен наследником этого класса.
 * Благодаря {@link PointImpl} игрок имеет свою координату и может двигаться.
 * Благодаря {@link Joystick} игрок может управлять героем.
 * Благодаря {@link Tickable} часть логики GameField.tick() которая по SRP больше к герою относится может быть перенесена сюда.
 * @param <F> Поле {@link GameField} по которому "бегает" герой.
 *           Герой пользуется этим полем, чтобы уточнить что-то про игру во время своих телодвижений.
 *           Связь циклическая (герой знает про поле, а поле про героя) но так и задумано
 */
public abstract class PlayerHero<F extends GameField> extends PointImpl implements Fieldable<F>, Joystick, NoMessageJoystick, Tickable {

    protected F field;

    /**
     * Этим флагом мы указываем, что герой уже создан нами и
     * не надо искать ему место на поле.
     */
    private boolean manual = false;

    public PlayerHero(int x, int y) {
        super(x, y);
    }

    // TODO используется для безкординатных героев, подумать над этим
    public PlayerHero() {
        super(-1, -1);
    }

    public PlayerHero(Point pt) {
        super(pt);
    }

    public PlayerHero(JSONObject json) {
        super(json);
    }

    @Override
    public void init(F field) {
        this.field = field;
    }

    public F field() {
        return field;
    }

    public SettingsReader settings() {
        return (field != null) ? field.settings() : null;
    }

    public abstract boolean isAlive();

    public boolean manual() {
        return manual;
    }

    public void manual(boolean manual) {
        this.manual = manual;
    }

    @Override
    public void move(int x, int y) {
        if (isValidateOnMove()
                && Point.isOutOf(x, y, field.size()))
        {
            return;
        }
        super.move(x, y);
    }

    // TODO убрать когда пофикшу в играх несоответсвия
    protected boolean isValidateOnMove() {
        return true;
    }
}
