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
#include "engine/Point.h"

TEST(equalsTest, PointTest) {
    EXPECT_EQ(true, Point(1, 2).equals(Point(1, 2)));
    EXPECT_EQ(true, Point(1, 2) == Point(1, 2));
}

TEST(setNullTest, PointTest) {
    Point p = Point(1, 2);

    EXPECT_EQ(false, p.isNull());

    p.setNull(true);
    EXPECT_EQ(true, p.isNull());
}

TEST(isBadTest, PointTest) {
    EXPECT_EQ(true, Point(-1, 10).isBad(10));
    EXPECT_EQ(true, Point(10, -1).isBad(10));
    EXPECT_EQ(true, Point(15, 10).isBad(10));
    EXPECT_EQ(true, Point(10, 15).isBad(10));
    EXPECT_EQ(false, Point(10, 10).isBad(10));
}

TEST(getSurroundsTest, PointTest) {
    StringStream ss;
    ss << "...";
    ss << ".X.";
    ss << "...";

    Point p = Point(1, 1);

    String expected = L"[0,1], [1,0], [1,2], [2,1]";
    StringStream actual;
    for (Point i : p.getSurrounds(3)) {
        ss << i.toString() << " ";
    }
    EXPECT_EQ(expected, actual.str());
}