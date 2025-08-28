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
      module Knibert
      end
    end
  end
end

class Codenjoy::Client::Games::Knibert::Board < BaseBoard
  ELEMENTS = {
    BAD_APPLE: '☻',
    GOOD_APPLE: '☺',

    BREAK: '☼',

    HEAD_DOWN: '▼',
    HEAD_LEFT: '◄',
    HEAD_RIGHT: '►',
    HEAD_UP: '▲',

    TAIL_END_DOWN: '╙',
    TAIL_END_LEFT: '╘',
    TAIL_END_UP: '╓',
    TAIL_END_RIGHT: '╕',
    TAIL_HORIZONTAL: '═',
    TAIL_VERTICAL: '║',
    TAIL_LEFT_DOWN: '╗',
    TAIL_LEFT_UP: '╝',
    TAIL_RIGHT_DOWN: '╔',
    TAIL_RIGHT_UP: '╚',

    NONE: ' '
  }

  def process(str)
    puts "-------------------------------------------------------------------------------------------"
    puts str
    @raw = str
  end

  def get_my_head
    find_by_list([:HEAD_DOWN, :HEAD_UP, :HEAD_RIGHT, :HEAD_LEFT].map{ |e| ELEMENTS[e] })
  end

  def get_my_body
    find_by_list([
      :HEAD_DOWN, :HEAD_LEFT, :HEAD_RIGHT, :HEAD_UP,
      :TAIL_END_DOWN, :TAIL_END_LEFT, :TAIL_END_UP,
      :TAIL_END_RIGHT, :TAIL_HORIZONTAL, :TAIL_VERTICAL,
      :TAIL_LEFT_DOWN, :TAIL_LEFT_UP, :TAIL_RIGHT_DOWN, :TAIL_RIGHT_UP
    ].map{ |e| ELEMENTS[e] })
  end

  def get_apple
    find_by_list([ELEMENTS[:GOOD_APPLE]])
  end

  def get_stone
    find_by_list([ELEMENTS[:BAD_APPLE]])
  end

  def get_walls
    find_by_list([ELEMENTS[:BREAK]])
  end

  def get_barriers
    find_by_list([ELEMENTS[:BREAK], ELEMENTS[:BAD_APPLE]])
  end

  def barrier_at?(x, y)
    return false if Point.new(x, y).out_of?(size)
    get_barriers.include?([x.to_f, y.to_f]);
  end

  def to_s
    [
      "Board:",
      board_to_s,
      "My head at: #{get_my_head}",
      "My body at: #{get_my_body}",
      "Apple at: #{get_apple}",
      "Stone at: #{get_stone}"
    ].join("\n")
  end
end
