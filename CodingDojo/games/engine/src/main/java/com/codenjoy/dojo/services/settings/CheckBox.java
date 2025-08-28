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


import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public class CheckBox<T> extends TypeUpdatable<T> implements Parameter<T> {

    public static final String TYPE = "checkbox";

    private T def;
    private String name;

    public CheckBox(String name) {
        this.name = name;
    }

    @Override
    public T getValue() {
        return getOrDefault();
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public CheckBox<T> update(Object value) {
        return update(value, super::set);
    }

    @Override
    public Parameter<T> justSet(Object value) {
        return update(value, super::setOnly);
    }

    private CheckBox<T> update(Object value, Function<T, Parameter<T>> process) {
        if (value == null) {
            return null;
        }
        Boolean b = parse(value);
        if (b == null) {
            process.apply(tryParse(value));
        } else {
            process.apply(code(b));
        }
        return this;
    }

    private T code(boolean value) {
        if (Integer.class.equals(type)) {
            return (T)((value) ? Integer.valueOf(1) : Integer.valueOf(0));
        }
        if (Boolean.class.equals(type)) {
            return (T) Boolean.valueOf(value);
        }
        if (String.class.equals(type)) {
            return (T) Boolean.valueOf(value).toString();
        }
        return tryParse(Boolean.valueOf(value));
    }

    @Override
    public <V> CheckBox<V> type(Class<V> type) {
        return (CheckBox<V>) super.type(type);
    }

    @Override
    public CheckBox<T> parser(Function<String, T> parser) {
        return (CheckBox<T>) super.parser(parser);
    }

    private Boolean parse(Object value) {
        if (value instanceof Boolean) {
            return (Boolean)value;
        }
        if (value instanceof String) {
            return ("true".equalsIgnoreCase((String) value)
                    || "1".equals(value));
        }
        if (value instanceof Integer){
            return ((Integer)value == 1);
        }
        return null;
    }

    @Override
    public CheckBox<T> def(T value) {
        this.def = value;
        return this;
    }

    @Override
    public void select(int index) {
        set((T) Boolean.valueOf(index == 1));
    }

    @Override
    public List<T> getOptions() {
        return new LinkedList<T>(){{
            add(def);
            T value = CheckBox.this.get();
            if (value != null && !this.contains(value)) {
                add(value);
            }
        }};
    }

    @Override
    public T getDefault() {
        return def;
    }

    @Override
    public void reset() {
        set(def);
        changesReacted();
    }

    @Override
    public Parameter<T> clone(String newName) {
        newName = (newName == null) ? name : newName;
        CheckBox<T> result = new CheckBox<>(newName);
        result.def = def;
        result.value = value;
        result.parser = parser;
        result.type = type;
        result.changed = changed;
        return result;
    }

    @Override
    public String toString() {
        return String.format("[%s:%s = def[%s] val[%s]]",
                name,
                type.getSimpleName(),
                def,
                get());
    }

    @Override
    protected T def() {
        return def;
    }
}