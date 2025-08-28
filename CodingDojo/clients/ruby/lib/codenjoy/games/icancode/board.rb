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
      module Icancode
      end
    end
  end
end

class Codenjoy::Client::Games::Icancode::Board

  ELEMENT = {
    EMPTY: '-',
    FLOOR: '.',

    ANGLE_IN_LEFT: '╔',
    WALL_FRONT: '═',
    ANGLE_IN_RIGHT: '┐',
    WALL_RIGHT: '│',
    ANGLE_BACK_RIGHT: '┘',
    WALL_BACK: '─',
    ANGLE_BACK_LEFT: '└',
    WALL_LEFT: '║',
    WALL_BACK_ANGLE_LEFT: '┌',
    WALL_BACK_ANGLE_RIGHT: '╗',
    ANGLE_OUT_RIGHT: '╝',
    ANGLE_OUT_LEFT: '╚',
    SPACE: ' ',

    LASER_MACHINE_CHARGING_LEFT: '˂',
    LASER_MACHINE_CHARGING_RIGHT: '˃',
    LASER_MACHINE_CHARGING_UP: '˄',
    LASER_MACHINE_CHARGING_DOWN: '˅',

    LASER_MACHINE_READY_LEFT: '◄',
    LASER_MACHINE_READY_RIGHT: '►',
    LASER_MACHINE_READY_UP: '▲',
    LASER_MACHINE_READY_DOWN: '▼',

    START: 'S',
    EXIT: 'E',
    HOLE: 'O',
    BOX: 'B',
    ZOMBIE_START: 'Z',
    GOLD: '$',

    ROBOT: '☺',
    ROBOT_FALLING: 'o',
    ROBOT_FLYING: '*',
    ROBOT_LASER: '☻',

    ROBOT_OTHER: 'X',
    ROBOT_OTHER_FALLING: 'x',
    ROBOT_OTHER_FLYING: '^',
    ROBOT_OTHER_LASER: '&',

    LASER_LEFT: '←',
    LASER_RIGHT: '→',
    LASER_UP: '↑',
    LASER_DOWN: '↓',

    FEMALE_ZOMBIE: '♀',
    MALE_ZOMBIE: '♂',
    ZOMBIE_DIE: '✝'
  }

  WALLS = [
    :ANGLE_IN_LEFT, :WALL_FRONT, :ANGLE_IN_RIGHT, :WALL_RIGHT, :ANGLE_BACK_RIGHT,
    :WALL_BACK, :ANGLE_BACK_LEFT, :WALL_LEFT, :WALL_BACK_ANGLE_LEFT, :WALL_BACK_ANGLE_RIGHT,
    :ANGLE_OUT_RIGHT, :ANGLE_OUT_LEFT, :SPACE
  ]

  attr_accessor :l1, :l2, :l3 # short names for layers

  def command
    # 'up'                // you can move
    # 'down'
    # 'left'
    # 'right'
    # 'act(1)'            // jump
    # 'act(2)'            // pull box
    # 'act(3)'            // fire
    # 'act(0)'            // die
  end

  def process(data)
    @data = JSON.parse(data)
    @raw = @data["layers"][0]
    @l1 = Layer.new(@data["layers"][0])
    @l2 = Layer.new(@data["layers"][1])
    @l3 = Layer.new(@data["layers"][2])
  end

  def nline(i)
    return " 0" if 0 == i
    format("%02.0d", i)
  end

  def numberinline(length)
    Array.new(length) { |i| (i % 10).to_s }.join
  end

  def headfoot
    Array.new(3).map{ |i| "  " + numberinline(size) }.join
  end

  def headlayers
    spaces = ([' '] * (size - 6)).join()
    @data["layers"].each_with_index.map{ |e, n| " Layers#{n + 1}#{spaces}" }.join('')
  end

  def get_me
    [@data['heroPosition']['x'], @data['heroPosition']['y']]
  end

  def get_other_heroes
    l2.find_by_list([
      :ROBOT_OTHER, :ROBOT_OTHER_FALLING, :ROBOT_OTHER_LASER
    ].map{ |e| ELEMENT[e] }) + l3.find_by_list([ELEMENT[:ROBOT_OTHER_FLYING]])
  end

  def get_gold
    l1.find_by_list([ELEMENT[:GOLD]])
  end

  def get_starts
    l1.find_by_list([ELEMENT[:START]])
  end

  def get_exits
    l1.find_by_list([ELEMENT[:EXIT]])
  end

  def get_boxes
    l2.find_by_list([ELEMENT[:BOX]])
  end

  def get_holes
    l1.find_by_list([ELEMENT[:HOLE]])
  end

  def get_laser_machines
    l1.find_by_list([:LASER_MACHINE_CHARGING_LEFT, :LASER_MACHINE_CHARGING_RIGHT,
    :LASER_MACHINE_CHARGING_UP, :LASER_MACHINE_CHARGING_DOWN,
    :LASER_MACHINE_READY_LEFT, :LASER_MACHINE_READY_RIGHT,
    :LASER_MACHINE_READY_UP, :LASER_MACHINE_READY_DOWN].map{ |e| ELEMENT[e] })
  end

  def get_lasers
    l2.find_by_list([:LASER_LEFT, :LASER_RIGHT, :LASER_UP, :LASER_DOWN].map{ |e| ELEMENT[e] })
  end

  def get_zombies
    l2.find_by_list([:FEMALE_ZOMBIE, :MALE_ZOMBIE, :ZOMBIE_DIE].map{ |e| ELEMENT[e] })
  end

  def get_walls
    l1.find_by_list(WALLS.map{ |e| ELEMENT[e] })
  end

  def wall_at?(x, y)
    WALLS.map{ |e| ELEMENT[e] }.include?(l1.get_at(x,y))
  end

  def infoline(n)
    res = [
      " Robots: #{get_me}, #{get_other_heroes}",
      " Gold: #{get_gold}",
      " Starts: #{get_starts}",
      " Exits: #{get_exits}",
      " Boxes: #{get_boxes}",
      " Holes: #{get_holes}",
      " LaserMachine: #{get_laser_machines}",
      " Lasers: #{get_lasers}",
      " Zombies: #{get_zombies}"
    ]
    d = 1
    return res[n - d] if d <= n && n < res.size + d
    ""
  end

  def board_to_s
    ([headlayers, headfoot] +
    Array.new(size).each_with_index.map do |e, n|
      @data["layers"].map{ |l| nline(size - n - 1) + l[(n * size)..((n + 1) * size - 1)] }.join + infoline(n)
    end + [headfoot]).join("\n")
  end

  def to_s
    board_to_s
  end
end
