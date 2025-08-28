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

RSpec.describe Codenjoy::Client::Games::Expansion::Board do
  before(:context) do
    @formated_data = File.open("spec/games/expansion/test_board.json", "r").read
    @board = Codenjoy::Client::Games::Expansion::Board.new
    data = @formated_data.split("\n").join('')
    @board.process(data)
    @elements = Codenjoy::Client::Games::Expansion::Board::ELEMENTS
    p @board.get_holes
  end

  it { expect(@board.get_gold).to eq [[9.0, 10.0], [10.0, 10.0], [9.0, 9.0], [10.0, 9.0]] }
  it { expect(@board.get_bases).to eq [[2.0, 17.0], [17.0, 17.0], [17.0, 2.0], [2.0, 2.0]] }
  it { expect(@board.get_exits).to eq [] }
  it { expect(@board.get_breaks.size).to eq 16 }
  it { expect(@board.get_holes).to eq 16 }

end
