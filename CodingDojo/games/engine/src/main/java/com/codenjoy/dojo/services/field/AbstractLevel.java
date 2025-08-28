package com.codenjoy.dojo.services.field;

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

import com.codenjoy.dojo.services.BoardMap;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.multiplayer.PlayerHero;
import com.codenjoy.dojo.services.printer.BoardReader;
import com.codenjoy.dojo.services.printer.CharElement;
import com.codenjoy.dojo.utils.LevelUtils;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

public abstract class AbstractLevel implements Level {

    // TODO make all final & private after migrating all games
    protected BoardMap map;

    public AbstractLevel(String map) {
        this.map = new BoardMap(LevelUtils.clear(map));
    }

    @Override
    public int size() {
        return map.size();
    }

    protected Character getAt(Point pt) {
        return map.getAt(pt.getX(), pt.getY());
    }

    protected <T, E extends CharElement> List<T> find(
             BiFunction<Point, E, T> objects,
             E... elements)
    {
        List<T> result = new LinkedList<>();
        for (E el : elements) {
            for (int index = 0; index < map.length(); index++) {
                if (map.map().charAt(index) == el.ch()) {
                    Point pt = map.xy().point(index);
                    result.add(objects.apply(pt, el));
                }
            }
        }
        return result;
    }

    protected <T, E extends CharElement> List<T> find(
             Function<Point, T> objects,
             E... elements)
    {
        return find((pt, el) -> objects.apply(pt), elements);
    }

    protected <T, E extends CharElement> List<T> find(
            Map<E, Function<Point, T>> conversions)
    {
        if (!(conversions instanceof LinkedHashMap)) {
            throw new IllegalArgumentException("Expected LinkedHashMap because of HashMap will affect tests");
        }
        return conversions.entrySet().stream()
                .flatMap(entry -> find(entry.getValue(), entry.getKey()).stream())
                    .collect(toList());
    }

    // TODO remove it after migrating all games
    @Override
    public BoardReader<Object> reader() {
        return new BoardReader<>() {
            @Override
            public int size() {
                return AbstractLevel.this.size();
            }

            @Override
            public void addAll(Object player, Consumer<Iterable<? extends Point>> processor) {
               AbstractLevel.this.addAll(processor);
            }
        };
    }

    // TODO remove it after migrating all games
    protected void addAll(Consumer<Iterable<? extends Point>> processor) {
        // just override if needed
    }

    @Override
    public void saveTo(PointField field) {
        field.size(size());

        fill(field);
    }

    protected void fill(PointField field) {
        // just override if needed
    }

    public String map() {
        return map.map();
    }

    public void resize(int size) {
        map.resize(size);
    }

    public List<? extends PlayerHero> heroes() {
        return Arrays.asList();
    }
}
