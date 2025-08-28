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
    for (Point pt : list) {
        ss << pt.toString() << " ";
    }
    return ss.str();
}

String Board::toString() const {
    StringStream ss;
    ss  << LL("Board:\n") << AbstractBoard::boardAsString() << LL("\n")
        << LL("Hero at: ") << getHero().toString() << LL("\n")
        << LL("Other heroes at: ") << getOtherHeroes().toString() << LL("\n")
        << LL("Enemy heroes at: ") << getEnemyHeroes().toString() << LL("\n")
        << LL("Other stuff at: ") << getOtherStuff().toString() << LL("\n");
    return ss.str();
}

CharElement* Board::valueOf(Char ch) const {
    return new Element(ch);
}

int Board::inversionY(int y) const {
    return size - 1 - y;
}

Point Board::getHero() const {
    PointList result;
    result.splice(result.end(), findAll(new Element(LL("HERO_DEAD"))));
    result.splice(result.end(), findAll(new Element(LL("HERO"))));
    result.splice(result.end(), findAll(new Element(LL("HERO_CURE"))));
    result.splice(result.end(), findAll(new Element(LL("HERO_HEALING"))));
    return result.front();
}

PointList Board::getOtherHeroes() const {
    PointList result;
    result.splice(result.end(), findAll(new Element(LL("OTHER_HERO_DEAD"))));
    result.splice(result.end(), findAll(new Element(LL("OTHER_HERO"))));
    result.splice(result.end(), findAll(new Element(LL("OTHER_HERO_CURE"))));
    result.splice(result.end(), findAll(new Element(LL("OTHER_HERO_HEALING"))));
    return result;
}

PointList Board::getEnemyHeroes() const {
    PointList result;
    result.splice(result.end(), findAll(new Element(LL("ENEMY_HERO_DEAD"))));
    result.splice(result.end(), findAll(new Element(LL("ENEMY_HERO"))));
    result.splice(result.end(), findAll(new Element(LL("ENEMY_HERO_HEALING"))));
    return result;
}

PointList Board::getOtherStuff() const {
    PointList result;
    result.splice(result.end(), findAll(new Element(LL("INFECTION"))));
    result.splice(result.end(), findAll(new Element(LL("HIDDEN"))));
    result.splice(result.end(), findAll(new Element(LL("PATHLESS"))));
    return result;
}

PointList Board::getWalls() const {
    PointList result;
    result.splice(result.end(), findAll(new Element(LL("PATHLESS"))));
    return result;
}

bool Board::isGameOver() const {
    return board.find(Element(LL("DEAD_HERO")).getChar()) != String::npos;
}

bool Board::isWin() const {
    return isGameOver() || board.find(Element(LL("HERO_HEALING")).getChar()) == String::npos;
}

int inversionY(int y) {
     return size - 1 - y;
}
