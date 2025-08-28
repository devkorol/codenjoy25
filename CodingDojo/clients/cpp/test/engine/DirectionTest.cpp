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

#include "pch.h"
#include "engine/Direction.h"

TEST(invalidValueTest, DirectionTest) {
    try {
        Direction(-1);
        Direction(1);
        Direction(2);
        Direction(3);
        Direction(4);
        Direction(5);
        Direction(6);
        FAIL() << "Expected std::invalid_argument";
    }
    catch (std::invalid_argument const& err) {
        EXPECT_EQ(err.what(), std::string("Direction::valueOf(int value): No direction for 6"));
    }
    catch (...) {
        FAIL() << "Expected std::invalid_argument";
    }
}

TEST(invalidNameTest, DirectionTest) {
    try {
        Direction(LL("NULL"));
        Direction(LL("LEFT"));
        Direction(LL("RIGHT"));
        Direction(LL("UP"));
        Direction(LL("DOWN"));
        Direction(LL("ACT"));
        Direction(LL("STOP"));
        Direction(LL("invalid"));
        FAIL() << "Expected std::invalid_argument";
    }
    catch (std::invalid_argument const& err) {
        EXPECT_EQ(err.what(), std::string("Direction::Direction(String name): No direction for such name"));
    }
    catch (...) {
        FAIL() << "Expected std::invalid_argument";
    }
}

TEST(equalsTest, DirectionTest) {
    EXPECT_EQ(true, Direction(LL("LEFT")) == Direction(LL("LEFT")));
    EXPECT_EQ(true, Direction(LL("LEFT")) != Direction(LL("RIGHT")));
}

TEST(isNullTest, DirectionTest) {
    EXPECT_EQ(true, Direction(LL("NULL")).isNull());
    EXPECT_EQ(false, Direction(LL("LEFT")).isNull());
}

TEST(changeTest, DirectionTest) {
    EXPECT_EQ(1, Direction(LL("DOWN")).changeX(1));
    EXPECT_EQ(0, Direction(LL("DOWN")).changeY(1));
}

TEST(invertedTest, DirectionTest) {
    EXPECT_EQ(Direction(LL("LEFT")), Direction(LL("RIGHT")).inverted());
    EXPECT_EQ(Direction(LL("RIGHT")), Direction(LL("RIGHT")).inverted());
    EXPECT_EQ(Direction(LL("UP")), Direction(LL("DOWN")).inverted());
    EXPECT_EQ(Direction(LL("DOWN")), Direction(LL("UP")).inverted());
    EXPECT_EQ(Direction(LL("ACT")), Direction(LL("STOP")).inverted());
    EXPECT_EQ(Direction(LL("STOP")), Direction(LL("STOP")).inverted());
    EXPECT_EQ(Direction(LL("NULL")), Direction(LL("STOP")).inverted());
}