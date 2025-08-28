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


/**
 * В модельке игры класс отвечающий за подсчет очков, должен реализовать этот интерфейс.
 */
public interface PlayerScores extends EventListener {

    /**
     * @return Текущее значение очков, что успел набрать игрок.
     */
    Object getScore();

    /**
     * @return очистка очков с возвращением последнего значения.
     * // TODO почти ни в одной реализации это не так + ко всему оно игнорится
     */
    int clear();

    /**
     * В крайних случаях можно обновить количество очков напрямую.
     * @param score Новое значения очков.
     */
    void update(Object score);
}
