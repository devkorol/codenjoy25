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
    ss << LL("Board:\n") << AbstractBoard::boardAsString() << LL("\n")
        << LL("Hero at: ") << getHero().toString() << LL("\n")
        << LL("Other Heroes at: ") << pointListToString(getOtherHeroes()) << LL("\n")
        << LL("Enemy Heroes at: ") << pointListToString(getEnemyHeroes()) << LL("\n")
        << LL("Robbers at: ") << pointListToString(getRobbers()) << LL("\n")
        << LL("Mask potions at: ") << pointListToString(getPotions()) << LL("\n");
        << LL("Keys at: ") << pointListToString(getKeys()) << LL("\n");
    return ss.str();
}

CharElement* Board::valueOf(Char ch) const {
    return new Element(ch);
}

Point Board::getHero() const {
    PointList result;
    result.splice(result.end(), findAll(new Element(LL("HERO_DIE"))));
    result.splice(result.end(), findAll(new Element(LL("HERO_LADDER"))));
    result.splice(result.end(), findAll(new Element(LL("HERO_LEFT"))));
    result.splice(result.end(), findAll(new Element(LL("HERO_RIGHT"))));
    result.splice(result.end(), findAll(new Element(LL("HERO_FALL"))));
    result.splice(result.end(), findAll(new Element(LL("HERO_PIPE"))));
    result.splice(result.end(), findAll(new Element(LL("HERO_PIT"))));

    result.splice(result.end(), findAll(new Element(LL("HERO_MASK_DIE"))));
    result.splice(result.end(), findAll(new Element(LL("HERO_MASK_LADDER"))));
    result.splice(result.end(), findAll(new Element(LL("HERO_MASK_LEFT"))));
    result.splice(result.end(), findAll(new Element(LL("HERO_MASK_RIGHT"))));
    result.splice(result.end(), findAll(new Element(LL("HERO_MASK_FALL"))));
    result.splice(result.end(), findAll(new Element(LL("HERO_MASK_PIPE"))));
    result.splice(result.end(), findAll(new Element(LL("HERO_MASK_PIT"))));
    return result.front();
}

PointList Board::getOtherHeroes() const {
    PointList result;
    result.splice(result.end(), findAll(new Element(LL("OTHER_HERO_DIE"))));
    result.splice(result.end(), findAll(new Element(LL("OTHER_HERO_LADDER"))));
    result.splice(result.end(), findAll(new Element(LL("OTHER_HERO_LEFT"))));
    result.splice(result.end(), findAll(new Element(LL("OTHER_HERO_RIGHT"))));
    result.splice(result.end(), findAll(new Element(LL("OTHER_HERO_FALL"))));
    result.splice(result.end(), findAll(new Element(LL("OTHER_HERO_PIPE"))));
    result.splice(result.end(), findAll(new Element(LL("OTHER_HERO_PIT"))));
    
    result.splice(result.end(), findAll(new Element(LL("OTHER_HERO_MASK_DIE"))));    
    result.splice(result.end(), findAll(new Element(LL("OTHER_HERO_MASK_LADDER"))));
    result.splice(result.end(), findAll(new Element(LL("OTHER_HERO_MASK_LEFT"))));
    result.splice(result.end(), findAll(new Element(LL("OTHER_HERO_MASK_RIGHT"))));
    result.splice(result.end(), findAll(new Element(LL("OTHER_HERO_MASK_FALL"))));
    result.splice(result.end(), findAll(new Element(LL("OTHER_HERO_MASK_PIPE"))));
    result.splice(result.end(), findAll(new Element(LL("OTHER_HERO_MASK_PIT"))));
    return result;
}

PointList Board::getEnemyHeroes() const {
    PointList result;
    result.splice(result.end(), findAll(new Element(LL("ENEMY_HERO_DIE"))));
    result.splice(result.end(), findAll(new Element(LL("ENEMY_HERO_LADDER"))));
    result.splice(result.end(), findAll(new Element(LL("ENEMY_HERO_LEFT"))));
    result.splice(result.end(), findAll(new Element(LL("ENEMY_HERO_RIGHT"))));
    result.splice(result.end(), findAll(new Element(LL("ENEMY_HERO_FALL"))));
    result.splice(result.end(), findAll(new Element(LL("ENEMY_HERO_PIPE"))));
    result.splice(result.end(), findAll(new Element(LL("ENEMY_HERO_PIT"))));

    result.splice(result.end(), findAll(new Element(LL("ENEMY_HERO_MASK_DIE"))));
    result.splice(result.end(), findAll(new Element(LL("ENEMY_HERO_MASK_LADDER"))));
    result.splice(result.end(), findAll(new Element(LL("ENEMY_HERO_MASK_LEFT"))));
    result.splice(result.end(), findAll(new Element(LL("ENEMY_HERO_MASK_RIGHT"))));
    result.splice(result.end(), findAll(new Element(LL("ENEMY_HERO_MASK_FALL"))));
    result.splice(result.end(), findAll(new Element(LL("ENEMY_HERO_MASK_PIPE"))));
    result.splice(result.end(), findAll(new Element(LL("ENEMY_HERO_MASK_PIT"))));
    return result;
}

PointList Board::getRobbers() const {
    PointList result;
    result.splice(result.end(), findAll(new Element(LL("ROBBER_LADDER"))));
    result.splice(result.end(), findAll(new Element(LL("ROBBER_LEFT"))));
    result.splice(result.end(), findAll(new Element(LL("ROBBER_RIGHT"))));
    result.splice(result.end(), findAll(new Element(LL("ROBBER_FALL"))));
    result.splice(result.end(), findAll(new Element(LL("ROBBER_PIPE"))));
    result.splice(result.end(), findAll(new Element(LL("ROBBER_PIT"))));
    return result;
}

bool Board::isGameOver() const {
    return board.find(Element(LL("HERO_DIE")).getChar()) != String::npos &&
           board.find(Element(LL("HERO_MASK_DIE")).getChar()) != String::npos;
}

PointList Board::getBarriers() const {
    PointList result;
    result.splice(result.end(), findAll(new Element(LL("BRICK"))));
    result.splice(result.end(), findAll(new Element(LL("STONE"))));
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

PointList Board::getPits() const {
    PointList result;
    result.splice(result.end(), findAll(new Element(LL("CRACK_PIT"))));
    result.splice(result.end(), findAll(new Element(LL("PIT_FILL_1"))));
    result.splice(result.end(), findAll(new Element(LL("PIT_FILL_2"))));
    result.splice(result.end(), findAll(new Element(LL("PIT_FILL_3"))));
    result.splice(result.end(), findAll(new Element(LL("PIT_FILL_4"))));
    return result;
}

PointList Board::getClues() const {
    PointList result;
    result.splice(result.end(), findAll(new Element(LL("CLUE_KNIFE"))));
    result.splice(result.end(), findAll(new Element(LL("CLUE_GLOVE"))));
    result.splice(result.end(), findAll(new Element(LL("CLUE_RING"))));
    return result;
}

PointList Board::getBackways() const {
    PointList result;
    result.splice(result.end(), findAll(new Element(LL("BACKWAY"))));
    return result;
}

PointList Board::getPotions() const {
    PointList result;
    result.splice(result.end(), findAll(new Element(LL("MASK_POTION"))));
    return result;
}

PointList Board::getDoors() const {
    PointList result;
    result.splice(result.end(), findAll(new Element(LL("OPENED_DOOR_GOLD"))));
    result.splice(result.end(), findAll(new Element(LL("OPENED_DOOR_SILVER"))));
    result.splice(result.end(), findAll(new Element(LL("OPENED_DOOR_BRONZE"))));
    result.splice(result.end(), findAll(new Element(LL("CLOSED_DOOR_GOLD"))));
    result.splice(result.end(), findAll(new Element(LL("CLOSED_DOOR_SILVER"))));
    result.splice(result.end(), findAll(new Element(LL("CLOSED_DOOR_BRONZE"))));
    return result;
}

PointList Board::getKeys() const {
    PointList result;
    result.splice(result.end(), findAll(new Element(LL("KEY_GOLD"))));
    result.splice(result.end(), findAll(new Element(LL("KEY_SILVER"))));
    result.splice(result.end(), findAll(new Element(LL("KEY_BRONZE"))));
    return result;
}