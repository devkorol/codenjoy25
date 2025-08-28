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

#ifndef YOURDIRECTIONSOLVER_H
#define YOURDIRECTIONSOLVER_H

#include "engine/utils.h"

#include "Element.h"
#include "Board.h"

#include "engine/DirectionSolver.h"
#include "engine/Direction.h"
#include "engine/Dice.h"
#include "engine/Point.h"

class YourSolver : public DirectionSolver
{
public:
    YourSolver(Dice* d) : dice(d) {}

    // From DirectionSolver
    virtual String get(String boardString);

private:
    Dice* dice;
};

#endif
