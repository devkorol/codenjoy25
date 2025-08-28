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

import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.Tickable;
import com.codenjoy.dojo.services.annotations.PerformanceOptimized;
import com.codenjoy.dojo.services.multiplayer.GameField;
import com.codenjoy.dojo.services.multiplayer.GamePlayer;
import com.codenjoy.dojo.services.printer.BoardReader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Квадратное поле заданного размера. В каждой ячейке которого содержится
 * список из нескольких корзинок для объектов заданного типа (определяется по классу
 * объекта-наследника PointImpl). Так же существует отдельный список корзинок
 * для всех объектов независимо от их координат - сделано для оптимизации производительности
 * при работе со всеми данными. Важно держать этих два контейнера в консистентном состоянии.
 * Класс умеет реагировать на изменениие координаты объекта за пределами хранилища
 * - он разместит объект в новом месте, предварительно удалив его из старого
 * (я очень надеюсь на это). Пожелания принимаются, если получится сделать более
 * производительную реализацию этого контракта - буду признателен.
 */
public class PointField {

    private MultimapMatrix<Class<? extends Point>, Point> field;
    private Multimap<Class<? extends Point>, Point> all;

    public PointField size(int size) {
        field = new MultimapMatrix<>(size);
        all = new Multimap<>();
        return this;
    }

    /**
     * @param classes Порядок отрисовки типов элементов.
     * @return BoardReader для отрисовки элементов на поле в заданном порядке.
     */
    public <P extends GamePlayer> BoardReader<P> reader(Class<? extends Point>... classes) {
        return reader((P player) -> classes);
    }

    /**
     * Метод позволяет грузить разные списки в зависимости от состояния player.
     * @param function функция преобразования плеера в массив классов упорядоченных
     *                 по порядку отрисовки.
     * @return BoardReader для отрисовки элементов на поле в заданном порядке.
     */
    public <P extends GamePlayer> BoardReader<P> reader(Function<P, Class<? extends Point>[]> function) {
        return new BoardReader<>() {

            @Override
            public int size() {
                return PointField.this.size();
            }

            @Override
            public void addAll(P player, Consumer<Iterable<? extends Point>> processor) {
                for (Class clazz : function.apply(player)) {
                    processor.accept(PointField.this.of(clazz).all());
                }
            }
        };
    }

    /**
     * @return Размер квадратного поля.
     */
    public int size() {
        return field.size();
    }

    /**
     * Добавляет все элементы из списка каждый в корзинку своего типа
     * (тип будет извлечен из класса передаваемого объекта).
     * @param elements Элементы к добавлению.
     */
    public void addAll(List<? extends Point> elements) {
        elements.forEach(this::add);
    }

    /**
     * Метод помогает работать в связке с двумя контейнерами данных.
     * Если выполнение операции на первом вернул false, со вторым (из за
     * опатимизаций производительности) операция не будет осуществляться.
     * @param pt Координата с которой работаем.
     * @param function Осуществляемая операция.
     * @return Результат выполнения операции на первом контейнере.
     */
    @PerformanceOptimized
    private boolean with(Point pt, Function<Multimap<Class<? extends Point>, Point>, Boolean> function) {
        boolean result = function.apply(get(pt));
        if (result) {
            function.apply(all);
        }
        return result;
    }

    /**
     * Добавляет текущий элемент в кординку его типа (тип будет извлечен из
     * класса передаваемого объекта).
     * @param pt Добавляемые элемент.
     */
    @PerformanceOptimized
    public void add(Point pt) {
        with(pt, map -> map.get(pt.getClass()).add(pt));
        pt.beforeChange(new OnBeforeChange(this, pt));
        pt.onChange(new OnChange(this, pt));
    }

    public static class OnBeforeChange implements Consumer<Point> {

        private PointField field;
        private Point key;

        public OnBeforeChange(PointField field, Point key) {
            this.field = field;
            this.key = key;
        }

        @Override
        public void accept(Point from) {
            field.with(from, map -> map.removeAllExact(key.getClass(), from));
        }
    }

    public static class OnChange implements BiConsumer<Point, Point> {

        private PointField field;
        private Point key;

        public OnChange(PointField field, Point key) {
            this.field = field;
            this.key = key;
        }

        @Override
        public void accept(Point from, Point to) {
            field.with(key, map -> map.get(key.getClass()).add(to));
        }
    }

    public Multimap<Class<? extends Point>, Point> get(Point pt) {
        return field.get(pt.getX(), pt.getY());
    }

    /**
     * Возвращает все координаты поля, элменты в ячейках которых соответствуют
     * критерию.
     * @param filter Критерий отбора.
     * @return Список координат ячеек.
     */
    public List<Point> pointsMatch(Predicate<List<Point>> filter) {
        return field.pointsMatch(filter);
    }

    /**
     * @param pt Ячейка которой интересуемся.
     * @return Класс с группой утилитных методов, отвечающих на вопрос -
     *         что содержится в данной ячейке.
     */
    public Contains at(Point pt) {
        return new Contains(get(pt).keys());
    }

    /**
     * @param filter Класс объекта коллекцию которых в этой клетке хотим получить.
     * @param <E> Тип объекта наследуемого от PointImpl.
     * @return Удобный интерфейс для работы с коллекцией выбранных элементов.
     */
    public <E extends Point> Accessor<E> of(Class<E> filter) {
        return new Accessor<>() {

            @Override
            @PerformanceOptimized
            public Iterator<E> iterator() {
                return (Iterator) points().iterator();
            }

            @Override
            @PerformanceOptimized
            public boolean contains(Point pt) {
                return get(pt).contains(filter);
            }

            @Override
            @PerformanceOptimized
            public boolean removeExact(E element) {
                return with(element, map -> map.removeAllExact(filter, element));
            }

            @Override
            @PerformanceOptimized
            public boolean removeAt(Point pt) {
                return with(pt, map -> map.remove(filter, pt));
            }

            @Override
            @PerformanceOptimized
            public List<E> all() {
                return (List) Collections.unmodifiableList(points());
            }

            @Override
            public Stream<E> stream() {
                return (Stream) points().stream();
            }

            private List<Point> points() {
                return all.get(filter);
            }

            @Override
            public void removeNotSame(List<E> elements) {
                List<E> toRemove = stream()
                        .filter(it -> elements.stream()
                                .noneMatch(el -> el == it))
                        .collect(toList());
                toRemove.forEach(this::removeExact);
            }

            @Override
            @PerformanceOptimized
            public void add(E element) {
                PointField.this.add(element);
            }

            @Override
            public int size() {
                return all().size();
            }

            @Override
            @PerformanceOptimized
            public void clear() {
                field.clear(filter);
                all.clear(filter);
            }

            @Override
            public void removeIn(List<? extends Point> points) {
                points.forEach(this::removeAt);
            }

            @Override
            public void removeIf(Predicate<E> predicate) {
                field.remove(filter, (Predicate) predicate);
                all.remove(filter, (Predicate) predicate);
            }

            @Override
            public void addAll(List<E> elements) {
                elements.forEach(this::add);
            }

            @Override
            public List<E> getAt(Point pt) {
                return (List) new ArrayList<>(get(pt).get(filter));
            }

            @Override
            public E getFirstAt(Point pt) {
                List<Point> list = get(pt).get(filter);
                if (list.isEmpty()) {
                    return null;
                }
                return (E) list.get(0);
            }

            @Override
            public boolean hasAt(Point pt, Consumer<E> onExists) {
                List<E> list = getAt(pt);
                list.forEach(onExists);
                return !list.isEmpty();
            }

            @Override
            @PerformanceOptimized
            public List<E> copy() {
                return new ArrayList(points());
            }

            @Override
            @PerformanceOptimized
            public void tick() {
                for (Point element : copy()) {
                    if (element instanceof Tickable) {
                        ((Tickable)element).tick();
                    }
                }
            }

            @Override
            public void remove(int from, int to) {
                List<Point> list = points();
                if (list.isEmpty()) {
                    return;
                }
                if (from < 0 || to > list.size() || from > to) {
                    throw new IndexOutOfBoundsException();
                }
                for (int index = to - 1; index >= from; index--) {
                    removeExact((E) list.get(index));
                }
            }

            public List<E> filter(Predicate<E> filter) {
                return stream()
                        .filter(filter)
                        .collect(toList());
            }

            @Override
            public String toString() {
                return all().toString();
            }
        };
    }

    public String toString() {
        return String.format("[map=%s]\n\n[field=%s]",
                all.toString(), field.toString());
    }

    public <T extends GameField> void init(T field) { // TODO test me
        all.forEach(it -> {
            if (it instanceof Fieldable) {
                ((Fieldable<T>) it).init(field);
            }
        });
    }

}