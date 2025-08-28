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

require "codenjoy/utils"
require "codenjoy/base_board"

module Codenjoy
  module Client
    module Games
      module Rawelbbub
      end
    end
  end
end

class Codenjoy::Client::Games::Rawelbbub::Board < BaseBoard

  ELEMENTS = {
    NONE: ' ',
    BATTLE_WALL: '☼',
    BANG: 'Ѡ',

    CONSTRUCTION: '╬',

    CONSTRUCTION_DESTROYED_DOWN: '╩',
    CONSTRUCTION_DESTROYED_UP: '╦',
    CONSTRUCTION_DESTROYED_LEFT: '╠',
    CONSTRUCTION_DESTROYED_RIGHT: '╣',

    CONSTRUCTION_DESTROYED_DOWN_TWICE: '╨',
    CONSTRUCTION_DESTROYED_UP_TWICE: '╥',
    CONSTRUCTION_DESTROYED_LEFT_TWICE: '╞',
    CONSTRUCTION_DESTROYED_RIGHT_TWICE: '╡',

    CONSTRUCTION_DESTROYED_LEFT_RIGHT: '│',
    CONSTRUCTION_DESTROYED_UP_DOWN: '─',

    CONSTRUCTION_DESTROYED_UP_LEFT: '┌',
    CONSTRUCTION_DESTROYED_RIGHT_UP: '┐',
    CONSTRUCTION_DESTROYED_DOWN_LEFT: '└',
    CONSTRUCTION_DESTROYED_DOWN_RIGHT: '┘',

    CONSTRUCTION_DESTROYED: ' ',

    BULLET: '•',

    TANK_UP: '▲',
    TANK_RIGHT: '►',
    TANK_DOWN: '▼',
    TANK_LEFT: '◄',

    OTHER_TANK_UP: '˄',
    OTHER_TANK_RIGHT: '˃',
    OTHER_TANK_DOWN: '˅',
    OTHER_TANK_LEFT: '˂',

    AI_TANK_UP: '?',
    AI_TANK_RIGHT: '»',
    AI_TANK_DOWN: '¿',
    AI_TANK_LEFT: '«'
  }

  ENEMIES = [
    ELEMENTS[:AI_TANK_UP],
    ELEMENTS[:AI_TANK_DOWN],
    ELEMENTS[:AI_TANK_LEFT],
    ELEMENTS[:AI_TANK_RIGHT],
    ELEMENTS[:OTHER_TANK_UP],
    ELEMENTS[:OTHER_TANK_DOWN],
    ELEMENTS[:OTHER_TANK_LEFT],
    ELEMENTS[:OTHER_TANK_RIGHT]
  ]

  TANK = [
    ELEMENTS[:TANK_UP],
    ELEMENTS[:TANK_DOWN],
    ELEMENTS[:TANK_LEFT],
    ELEMENTS[:TANK_RIGHT]
  ]

  BARRIERS = [
    ELEMENTS[:BATTLE_WALL],
    ELEMENTS[:CONSTRUCTION],
    ELEMENTS[:CONSTRUCTION_DESTROYED_DOWN],
    ELEMENTS[:CONSTRUCTION_DESTROYED_UP],
    ELEMENTS[:CONSTRUCTION_DESTROYED_LEFT],
    ELEMENTS[:CONSTRUCTION_DESTROYED_RIGHT],
    ELEMENTS[:CONSTRUCTION_DESTROYED_DOWN_TWICE],
    ELEMENTS[:CONSTRUCTION_DESTROYED_UP_TWICE],
    ELEMENTS[:CONSTRUCTION_DESTROYED_LEFT_TWICE],
    ELEMENTS[:CONSTRUCTION_DESTROYED_RIGHT_TWICE],
    ELEMENTS[:CONSTRUCTION_DESTROYED_LEFT_RIGHT],
    ELEMENTS[:CONSTRUCTION_DESTROYED_UP_DOWN],
    ELEMENTS[:CONSTRUCTION_DESTROYED_UP_LEFT],
    ELEMENTS[:CONSTRUCTION_DESTROYED_RIGHT_UP],
    ELEMENTS[:CONSTRUCTION_DESTROYED_DOWN_LEFT],
    ELEMENTS[:CONSTRUCTION_DESTROYED_DOWN_RIGHT]
  ]

  def process(data)
    @raw = data
  end

  def xyl
    @xyl ||= LengthToXY.new(size);
  end

  def get_me
    me = find_by_list(TANK)
    return nil if me.nil?
    find_by_list(TANK).flatten
  end

  def find_by_list(list)
    result = list.map{ |e| find_all(e) }.flatten.map{ |e| [e.x, e.y] }
    return nil if (result.length == 0)
    result
  end

  def get_enemies
    find_by_list(ENEMIES)
  end

  def get_bullets
    find_by_list([ELEMENTS[:BULLET]])
  end

  def get_near(x, y)
    return false if Point.new(x, y).out_of?(size)
    result = []
    (-1..1).each do |dx|
      (-1..1).each do |dy|
          next if (dx == 0 && dy == 0)
          result.push(get_at(x + dx, y + dy))
      end
    end
    result;
  end

  def barrier_at?(x, y)
    return false if Point.new(x, y).out_of?(size)
    get_barriers.include?([x.to_f, y.to_f]);
  end

  def count_near(x, y, element)
    get_near(x, y).select{ |e| e == element}.size
  end

  def near?(x, y, element)
    n = get_near(x, y)
    return false if !n
    n.include?(element);
  end

  def bullet_at?(x, y)
    return false if Point.new(x, y).out_of?(size)
    get_at(x, y) == ELEMENTS[:BULLET]
  end

  def any_of_at?(x, y, elements = [])
    return false if Point.new(x, y).out_of?(size)
    elements.each do |e|
      return true if at?(x, y, e)
    end
    false;
  end

  def game_over?
    get_me.nil?;
  end

  def get_barriers
    find_by_list(BARRIERS)
  end

  def to_s
    [
      "Board:\n#{board_to_s}",
      "My tank at: #{get_me}",
      "Enemies at: #{get_enemies}",
      "Bullets at: #{get_bullets}"
    ].join("\n")
  end
end
