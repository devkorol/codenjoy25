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

#ifndef DIRECTION_H
#define DIRECTION_H

#include "utils.h"

class Direction
{
public:
    Direction(int value = -1);
    Direction(String name);

    bool operator==(const Direction& d) const;
    bool operator!=(const Direction& d) const;
    bool isNull() const;
    String toString() const;
    static String valueOf(int value);

    int changeX(int x) const;
    int changeY(int y) const;

    Direction inverted() const;
private:
    static DirectionMap initialise();
    static const DirectionMap Directions;

    DirectionItem dir;
};

#endif
