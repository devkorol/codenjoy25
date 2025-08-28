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
require 'json'

module Codenjoy
  module Client
    module Games
      module Expansion
      end
    end
  end
end

class Codenjoy::Client::Games::Expansion::Board < BaseBoard

  ELEMENTS = {
    # LAYER1
    # empty space where player can go
    FLOOR: '.',

    # walls
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

    # other stuff
    EXIT: 'E',
    HOLE: 'O',
    BREAK: 'B',
    GOLD: '$',

    # bases stuff
    BASE1: '1',
    BASE2: '2',
    BASE3: '3',
    BASE4: '4',

    # LAYER2
    EMPTY: '-',
    # forces stuff
    FORCE1: '♥',
    FORCE2: '♦',
    FORCE3: '♣',
    FORCE4: '♠',

    # system elements, don't touch it
    BACKGROUND: 'G'
  }


  def process(data)
    @data = JSON.parse(data)
    @raw = @data["layers"][0]
    @l1 = Layer.new(@data["layers"][0])
    @l2 = Layer.new(@data["layers"][1])
  end

  def get_my_forces
    "0"
  end

  def get_gold
    @l1.find_by_list([ELEMENTS[:GOLD]])
  end

  def get_bases
    @l1.find_by_list([:BASE1, :BASE2, :BASE3, :BASE4].map{ |e| ELEMENTS[e] })
  end

  def get_exits
    @l1.find_by_list([ELEMENTS[:EXIT]])
  end

  def get_breaks
    @l1.find_by_list([ELEMENTS[:BREAK]])
  end

  def get_holes
    @l1.find_by_list([ELEMENTS[:HOLE]])
  end

  def get_round
    @data["round"]
  end

  def infoline(n)
    res = [
      " My Forces: #{get_my_forces}",
      " Enemy Forces: ",
      " Gold: #{get_gold}",
      " Bases: #{get_bases}",
      " Exits: #{get_exits}",
      " Breaks: #{get_breaks}",
      " Holes: #{get_holes}"
    ]
    d = 1
    return res[n - d] if d <= n && n < res.size + d
    ""
  end

  def nline(i)
    return " 0" if 0 == i
    format("%02.0d", i)
  end

  def headlayers
    spaces = ([' '] * (size - 6)).join()
    @data["layers"].each_with_index.map{ |e, n| " Layers#{n + 1}#{spaces}" }.join('')
  end

  def headfoot
    Array.new(3).map{ |i| "  " + numberinline(size) }.join
  end

  def numberinline(length)
    Array.new(length) { |i| (i % 10).to_s }.join
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
