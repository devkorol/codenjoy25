package com.codenjoy.dojo.services.settings;

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


import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Изменяемый параметр. Фишка его в том, чтобы на админке
 * можно было в runtime менять параметры игры,
 * которые иначе тебе пришлось бы тебе прошить в твоей игре.
 *
 * @see Settings
 */
public interface Parameter<T> extends Cloneable {

    /**
     * @return Значение параметра.
     */
    T getValue();

    /**
     * @return Тип параметра.
     */
    String getType();

    /**
     * @return Тип значения.
     */
    Class<?> getValueType();

    String getName();

    /**
     * Установка нового значения и триггер onChange обработчика.
     * @param value Новое значение.
     * @return this parameter.
     */
    Parameter<T> update(Object value);

    /**
     * Установка нового значения без триггера onChange обработчика.
     * @param value Новое значение.
     * @return this parameter.
     */
    Parameter<T> justSet(Object value);

    /**
     * Так ты указываешь значение по умолчанию.
     * Обычно этого достаточно для ввода значения.
     *
     * @param value Значение.
     * @return this parameter.
     */
    Parameter<T> def(T value);

    <V> Parameter<V> type(Class<V> type);

    Parameter<T> parser(Function<String, T> parser);

    /**
     * Установка значения по индексу (актуально для SelectBox и CheckBox).
     * @param index
     */
    void select(int index);

    Parameter<T> onChange(BiConsumer<T, T> consumer);

    boolean changed();

    void changesReacted();

    List<T> getOptions();

    T getDefault();

    void reset();

    Parameter<T> clone(String newName);

    static void copy(Parameter<?> source, Parameter<?> dest) {
        if (source != null && dest != null) {
            dest.update(source.getValue());
        }
    }
}
