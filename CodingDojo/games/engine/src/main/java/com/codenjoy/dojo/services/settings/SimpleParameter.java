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


import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class SimpleParameter<T> implements Parameter<T> {

    private String name;
    private T value;
    private BiConsumer<T, T> consumer;

    public SimpleParameter(T value) {
        this.value = value;
    }

    public SimpleParameter(String name, T value) {
        this.name = name;
        this.value = value;
    }

    public static Parameter<Integer> v(int value) {
        return new SimpleParameter<>(value);
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public String getType() {
        return "noui";
    }

    @Override
    public Class<?> getValueType() {
        return (value != null) ? value.getClass() : Object.class;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Parameter<T> update(Object value) {
        justSet(value);
        return this;
    }

    @Override
    public Parameter<T> justSet(Object value) {
        this.value = (T) value;
        return this;
    }

    @Override
    public Parameter def(Object value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Parameter<T> parser(Function<String, T> parser) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void select(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Parameter<T> onChange(BiConsumer<T, T> consumer) {
        this.consumer = consumer;
        return this;
    }

    @Override
    public boolean changed() {
        return false;
    }

    @Override
    public void changesReacted() {
        // do nothing
    }

    @Override
    public List<T> getOptions() {
        return Arrays.asList(value);
    }

    @Override
    public T getDefault() {
        return value;
    }

    @Override
    public void reset() {
        // do nothing
    }

    @Override
    public Parameter<T> clone(String newName) {
        newName = (newName == null) ? name : newName;
        return new SimpleParameter<>(newName, value);
    }

    @Override
    public Parameter type(Class type) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return String.format("[%s:%s = val[%s]]",
                name,
                (value == null) ? "Object" : value.getClass().getSimpleName(),
                value);
    }
}
