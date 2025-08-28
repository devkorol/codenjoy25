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

class BaseBoard
  def xyl
    @xyl ||= LengthToXY.new(size);
  end

  def size
    @size ||= Math.sqrt(@raw.length);
  end

  def board_to_s
    Array.new(size).each_with_index.map{ |e, n| @raw[(n * size)..((n + 1) * size - 1)]}.join("\n")
  end

  def get_at(x, y)
    return false if Point.new(x, y).out_of?(size)
    @raw[xyl.getLength(x, y)]
  end

  def any_of_at?(x, y, elements = [])
    return false if Point.new(x, y).out_of?(size)
    elements.each do |e|
      return true if at?(x, y, e)
    end
    false;
  end

  def near?(x, y, element)
    return false if Point.new(x, y).out_of?(size)
    at?(x + 1, y, element) || at?(x - 1, y, element) || at?(x, y + 1, element) || at?(x, y - 1, element)
  end

  def at?(x, y, element)
    return false if Point.new(x, y).out_of?(size)
    get_at(x, y) == element;
  end

  def find_all(element)
    result = []
    @raw.length.times do |i|
      point = xyl.getXY(i);
      result.push(point) if at?(point.x, point.y, element)
    end
    result;
  end

  def find_by_list(list)
    list.map{ |e| find_all(e) }.flatten.map{ |e| [e.x, e.y] }
  end

  def count_near(x, y, element)
    return 0 if Point.new(x, y).out_of?(size)
    count = 0
    count += 1 if at?(x - 1, y    , element)
    count += 1 if at?(x + 1, y    , element)
    count += 1 if at?(x    , y - 1, element)
    count += 1 if at?(x    , y + 1, element)
    count
  end
end

class Layer < BaseBoard
  def initialize(data)
    @raw = data
  end
end
