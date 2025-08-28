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

#include "Board.h"

String pointListToString(PointList list) {
    StringStream ss;
    for (Point i : list) {
        ss << i.toString() << " ";
    }
    return ss.str();
}

String Board::toString() const {
    StringStream ss;
    ss << LL("Board:\n") << AbstractBoard::boardAsString() << LL("\n")
        << LL("Hero at: ") << getHero().toString() << LL("\n")
        << LL("Bombs at: ") << pointListToString(getBombs()) << LL("\n");
    return ss.str();
}

CharElement* Board::valueOf(Char ch) const {
    return new Element(ch);
}

Point Board::getHero() const {
    PointList result;
    result.splice(result.end(), findAll(new Element(LL("HERO"))));
    result.splice(result.end(), findAll(new Element(LL("HERO_DEAD"))));
    return result.front();
}

PointList Board::getOtherHeroes() const {
    PointList result;
    result.splice(result.end(), findAll(new Element(LL("OTHER_HERO"))));
    result.splice(result.end(), findAll(new Element(LL("OTHER_HERO_DEAD"))));
    return result;
}

bool Board::isGameOver() const {
    return board.find(Element(LL("HERO_DEAD")).getChar()) != String::npos;
}

PointList Board::getBarriers() const {
    PointList result = getGhosts();
    result.splice(result.end(), getWalls());
    result.splice(result.end(), getBombs());
    result.splice(result.end(), getOtherHeroes());
    return removeDuplicates(result);
}

PointList Board::getWalls() const {
    return findAll(new Element(LL("WALL")));
}

PointList Board::getBombs() const {
    return findAll(new Element(LL("BOMB")));
}

PointList Board::removeDuplicates(PointList list) const {
    PointList result;
    for (auto pt : list) {
        if (std::find(result.begin(), result.end(), pt) == result.end()) {
            result.push_back(pt);
        }
    }
    return result;
}

bool Board::isBarrierAt(int x, int y) const {
    Point pt(x, y);
    if (pt.isBad(size)) return false;

    PointList barriers = getBarriers();
    for (auto barrier : barriers) {
        if (barrier == pt) return true;
    }
    return false;
}

bool Board::isBombAt(int x, int y) const {
    Point pt(x, y);
    if (pt.isBad(size)) return false;

    PointList bombs = getBombs();
    for (auto bomb : bombs) {
        if (bomb == pt) return true;
    }
    return false;
}

