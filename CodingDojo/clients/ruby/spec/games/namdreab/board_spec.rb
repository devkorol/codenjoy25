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

RSpec.describe Codenjoy::Client::Games::Namdreab::Board do
  before(:context) do
    @formated_data = File.open("spec/games/namdreab/test_board.txt", "r").read
    @board = Codenjoy::Client::Games::Namdreab::Board.new
    data = @formated_data.split("\n").join('')
    @board.process(data)
    @elements = Codenjoy::Client::Games::Namdreab::Board::ELEMENTS
  end

  it { expect(@board.get_walls.size).to eq 169 }
  it { expect(@board.get_enemy.size).to eq 9 }
  it { expect(@board.get_apple.size).to eq 17 }
  it { expect(@board.get_my_head).to eq [[11.0, 27.0]] }
  it { expect(@board.get_my_body).to eq [[11.0, 27.0], [10.0, 27.0]] }
  it { expect(@board.get_stone).to eq [[13.0, 24.0], [26.0, 19.0], [6.0, 16.0], [18.0, 11.0]] }
end
