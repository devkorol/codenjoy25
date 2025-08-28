<?php

###
# #%L
# Codenjoy - it's a dojo-like platform from developers to developers.
# %%
# Copyright (C) 2012 - 2022 Codenjoy
# %%
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as
# published by the Free Software Foundation, either version 3 of the
# License, or (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public
# License along with this program.  If not, see
# <http://www.gnu.org/licenses/gpl-3.0.html>.
# #L%
###

namespace Sample;

abstract class Element
{
    static array $elements = array(

            # Пустое место, куда можно перейти герою.

        "NONE" => ' ',

            # стенка, через которую проходить нельзя.

        "WALL" => '☼',

            # Твой герой.

        "HERO" => '☺',

            # Твой герой погиб. Его тело в следующем тике пропадет.

        "HERO_DEAD" => 'X',

            # Герои других игроков.

        "OTHER_HERO" => '☻',

            # Мертвые герои других игроков.

        "OTHER_HERO_DEAD" => 'Y',

            # Герои игроков в других командах.

        "ENEMY_HERO" => '♥',

            # Герои игроков в других командах, которые погибли.

        "ENEMY_HERO_DEAD" => 'Z',

            # Золото. Его можно поднять.

        "GOLD" => '$',

            # Бомба установленная героем. Ты можешь на ней подорваться.

        "BOMB" => 'x'
    );
}
