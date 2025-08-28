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

#include "AbstractBoard.h"

AbstractBoard::AbstractBoard(String boardString) {
    for (auto i_str = boardString.find(LL('\n')); i_str != String::npos; i_str = boardString.find(LL('\n'))) {
        boardString.replace(i_str, 1, LL(""));
    }
    board = boardString;
    size = boardSize();
    xyl = LengthToXY(size);
}

CharElement* AbstractBoard::getAt(int x, int y) const {
    int index = xyl.getLength(x, y);
    return valueOf(board.at(index));
}

bool AbstractBoard::isAt(int x, int y, CharElement* el) const {
    if (Point(x, y).isBad(size)) return false;
    return getAt(x, y)->ch() == el->ch();
}

bool AbstractBoard::isAt(int x, int y, std::list<CharElement*> els) const {
    for (auto i : els) {
        if (isAt(x, y, i)) return true;
    }
    return false;
}

PointList AbstractBoard::findAll(CharElement* el) const {
    PointList rslt;
    for (auto i = 0; i < size * size; ++i) {
        Point pt = xyl.getXY(i);
        if (isAt(pt.getX(), pt.getY(), el)) {
            rslt.push_back(pt);
        }
    }
    return rslt;
}

int AbstractBoard::boardSize() const {
    return std::sqrt(board.length());
}

String AbstractBoard::boardAsString() const {
    StringStream ss;
    for (auto i = 0; i < board.length(); ++i) {
        ss << board.at(i);
        if ((i + 1) % size == 0) {
            ss << LL('\n');
        }
    }
    return ss.str();
}

bool AbstractBoard::isNear(int x, int y, CharElement* el) const {
    if (Point(x, y).isBad(size)) return false;
    PointList nears = Point(x, y).getSurrounds(size);
    bool res = false;
    for (auto pt : nears) {
        res = res || isAt(pt.getX(), pt.getY(), el);
    }
    return res;
}

int AbstractBoard::countNear(int x, int y, CharElement* el) const {
    PointList nearp = Point(x, y).getSurrounds(size);
    int count = 0;
    for (auto p : nearp) {
        if (isAt(p.getX(), p.getY(), el)) {
            ++count;
        }
    }
    return count;
}
