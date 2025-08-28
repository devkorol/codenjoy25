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
#include "engine/utils.h"
#include "engine/LengthToXY.h"
#include "engine/Point.h"

TEST(getLengthTest, LengthToXYTest) {
    StringStream ss;
    ss << "...";
    ss << "...";
    ss << "...";

    LengthToXY convertor(3);

    EXPECT_EQ(1, convertor.getLength(1, 2));
    EXPECT_EQ(3, convertor.getLength(0, 1));
    EXPECT_EQ(8, convertor.getLength(2, 0));
}

TEST(getXYTest, LengthToXYTest) {
    StringStream ss;
    ss << "...";
    ss << "...";
    ss << "...";

    LengthToXY convertor(3);

    EXPECT_EQ(Point(1, 2), convertor.getXY(1));
    EXPECT_EQ(Point(0, 1), convertor.getXY(3));
    EXPECT_EQ(Point(2, 0), convertor.getXY(8));
}