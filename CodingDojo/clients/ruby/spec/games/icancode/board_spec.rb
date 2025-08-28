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

RSpec.describe Codenjoy::Client::Games::Icancode do
  before(:context) do
    @msg_from_server = File.open("spec/games/icancode/test_board.json", "r").read
    @board = Codenjoy::Client::Games::Icancode::Board.new
    @board.process(@msg_from_server)

    p @board.get_walls[4]
  end

  it { expect(@board.get_other_heroes).to eq [[1.0, 6.0], [1.0, 5.0], [6.0, 6.0], [2.0, 2.0], [6.0, 4.0]] }
  it { expect(@board.get_gold).to eq [[3.0, 7.0], [5.0, 6.0], [2.0, 5.0]] }
  it { expect(@board.get_starts).to eq [[1.0, 7.0]] }
  it { expect(@board.get_exits).to eq [[3.0, 5.0]] }
  it { expect(@board.get_boxes).to eq [[5.0, 5.0], [3.0, 4.0], [6.0, 4.0], [5.0, 3.0]] }
  it { expect(@board.get_holes).to eq [[7.0, 7.0], [6.0, 6.0], [4.0, 4.0], [2.0, 3.0]] }
  it { expect(@board.get_laser_machines).to eq [[4.0, 1.0], [1.0, 4.0], [3.0, 2.0], [3.0, 1.0], [4.0, 7.0], [6.0, 1.0], [7.0, 1.0], [5.0, 1.0]] }
  it { expect(@board.get_lasers).to eq [[6.0, 2.0], [2.0, 4.0], [5.0, 2.0], [5.0, 4.0]] }
  it { expect(@board.get_zombies).to eq [[3.0, 3.0], [1.0, 3.0], [2.0, 3.0], [4.0, 3.0]] }
  it { expect(@board.get_walls.size).to eq 32 }
  it { expect(@board.wall_at?(4, 8)).to eq true }
  it { expect(@board.wall_at?(5, 9)).to eq false }
end
