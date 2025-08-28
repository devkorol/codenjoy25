package com.codenjoy.dojo.client;

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
import com.codenjoy.dojo.services.printer.CharElement;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractBoard<E extends CharElement> extends AbstractLayeredBoard<E> {

    public ClientBoard forString(String boardString) {
        return super.forString(boardString);
    }

    @Override
    public AbstractBoard forString(String... layers) {
        return (AbstractBoard) super.forString(layers);
    }

    /**
     * @param elements List of elements that we try to find.
     * @return All positions of element specified.
     */
    public List<Point> get(E... elements) {
        List<Point> result = new LinkedList<>();
        for (int layer = 0; layer < countLayers(); ++layer) {
            result.addAll(layer(layer).get(elements));
        }
        return result;
    }

    /**
     * @param elements List of elements that we try to find.
     * @return First found position of element specified.
     */
    public Point getFirst(E... elements) {
        for (int layer = 0; layer < countLayers(); ++layer) {
            Point pt = layer(layer).getFirst(elements);
            if (pt != null) {
                return pt;
            }
        }
        return null;
    }

    // TODO сделать более защищенным метод добавив проверку isOutOfField
    public E getAt(int x, int y) {
        List<E> at = getAllAt(x, y);
        if (at.isEmpty()) {
            return null;
        }
        return at.get(0);
    }

    public E getAt(Point pt) {
        return getAt(pt.getX(), pt.getY());
    }

    public List<E> getAllAt(int x, int y) {
        List<E> result = new LinkedList<>();
        for (int layer = 0; layer < countLayers(); ++layer) {
            result.add(layer(layer).getAt(x, y));
        }
        return result;
    }

    protected List<Character> field(int x, int y) {
        List<Character> result = new LinkedList<>();
        for (int layer = 0; layer < countLayers(); ++layer) {
            result.add(layer(layer).field(x, y));
        }
        return result;
    }

    public List<E> getAllAt(Point pt) {
        return getAllAt(pt.getX(), pt.getY());
    }

    public String boardAsString() {
        StringBuilder result = new StringBuilder();
        for (int layer = 0; layer < countLayers(); ++layer) {
            if (layer > 0) {
                result.append('\n');
            }
            result.append(layer(layer).boardAsString());
        }
        return result.toString();
    }

    /**
     * Says if at given position (X, Y) at given layer has given elements.
     * @param x X coordinate.
     * @param y Y coordinate.
     * @param elements List of elements that we try to detect on this point.
     * @return true is any of this elements was found.
     */
    public boolean isAt(int x, int y, E... elements) {
        if (isOutOf(x, y)) {
            // TODO за пределами поля должны быть барьеры
            return false;
        }

        for (int layer = 0; layer < countLayers(); ++layer) {
            E found = layer(layer).getAt(x, y);
            for (E element : elements) {
                if (isEquals(found, element)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isAt(Point pt, E... elements) {
        return isAt(pt.getX(), pt.getY(), elements);
    }

    /**
     * Says if near (at left, at right, at up, at down) given position (X, Y) at given layer exists given element.
     * @param x X coordinate.
     * @param y Y coordinate.
     * @param element Element that we try to detect on near point.
     * @return true is element was found.
     */
    public boolean isNear(int x, int y, E element) {
        for (int layer = 0; layer < countLayers(); ++layer) {
            if (layer(layer).isNear(x, y, element)) {
                return true;
            }
        }
        return false;
    }

    public boolean isNear(Point pt, E element) {
        return isNear(pt.getX(), pt.getY(), element);
    }

    /**
     * @param x X coordinate.
     * @param y Y coordinate.
     * @param element Element that we try to detect on near point.
     * @return Returns count of elements with type specified near
     * (at left, right, down, up, left-down, left-up,
     *     right-down, right-up) {x,y} point.
     */
    public int countNear(int x, int y, E element) {
        int result = 0;

        for (int layer = 0; layer < countLayers(); ++layer) {
            result += layer(layer).countNear(x, y, element);
        }

        return result;
    }

    public int countNear(Point pt, E element){
        return countNear(pt.getX(), pt.getY(), element);
    }

    /**
     * @param x X coordinate.
     * @param y Y coordinate.
     * @return All elements around (at left, right, down, up,
     *     left-down, left-up, right-down, right-up) position.
     */
    public List<E> getNear(int x, int y) {
        List<E> result = new LinkedList<>();

        for (int layer = 0; layer < countLayers(); ++layer) {
            result.addAll(layer(layer).getNear(x, y));
        }

        return result;
    }

    public List<E> getNear(Point pt) {
        return getNear(pt.getX(), pt.getY());
    }

    public void set(int x, int y, char ch) {
        layer(0).set(x, y, ch);
    }

    // TODO используется только в 2048.AISolver попробовать убрать оттуда и инкапсулировать поле
    public char[][] field() {
        return layer(0).field();
    }
}