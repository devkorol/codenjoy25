package com.codenjoy.dojo.services.printer;

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

import java.util.function.Consumer;

/**
 * Абстракция над доской для Printer.
 */
public interface BoardReader<P> {

    int size();

    /**
     * @param player Игрок от имени которого рисуем.
     * @param processor этому методу стоит скормить по очереди все коллекции
     * представляющие живность на поле.
     * Порядок предлагаемых игрой элементов имеет значение -
     * вначале размещай элементы типа Hero, тогда при отрисовке
     * элементов в одной и той же клетке в его реализацию
     * интерфейса State прилетят расположенные ниже по
     * этому списку объекты в параметре alsoAtPoint.
     */
    void addAll(P player, Consumer<Iterable<? extends Point>> processor);
}
