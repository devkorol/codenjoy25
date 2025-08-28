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

RSpec.describe Codenjoy::Client::Games::Knibert::Board do
  before(:context) do
    @formated_data = File.open("spec/games/knibert/test_board.txt", "r").read
    @board = Codenjoy::Client::Games::Knibert::Board.new
    data = @formated_data.split("\n").join('')
    @board.process(data)
    @elements = Codenjoy::Client::Games::Knibert::Board::ELEMENTS
  end

  let(:barriers) { [
    [0.0, 14.0], [1.0, 14.0], [2.0, 14.0], [3.0, 14.0], [4.0, 14.0], [5.0, 14.0],
    [6.0, 14.0], [7.0, 14.0], [8.0, 14.0], [9.0, 14.0], [10.0, 14.0], [11.0, 14.0],
    [12.0, 14.0], [13.0, 14.0], [14.0, 14.0], [0.0, 13.0], [14.0, 13.0], [0.0, 12.0],
    [14.0, 12.0], [0.0, 11.0], [14.0, 11.0], [0.0, 10.0], [14.0, 10.0], [0.0, 9.0],
    [14.0, 9.0], [0.0, 8.0], [14.0, 8.0], [0.0, 7.0], [14.0, 7.0], [0.0, 6.0],
    [14.0, 6.0], [0.0, 5.0], [14.0, 5.0], [0.0, 4.0], [14.0, 4.0], [0.0, 3.0],
    [14.0, 3.0], [0.0, 2.0], [14.0, 2.0], [0.0, 1.0], [14.0, 1.0], [0.0, 0.0],
    [1.0, 0.0], [2.0, 0.0], [3.0, 0.0], [4.0, 0.0], [5.0, 0.0], [6.0, 0.0],
    [7.0, 0.0], [8.0, 0.0], [9.0, 0.0], [10.0, 0.0], [11.0, 0.0], [12.0, 0.0],
    [13.0, 0.0], [14.0, 0.0], [9.0, 13.0]
  ] }
  let(:my_body) { [
    [7.0, 11.0], [4.0, 7.0], [5.0, 11.0], [6.0, 11.0], [5.0, 10.0], [3.0, 8.0],
    [3.0, 7.0], [6.0, 9.0], [6.0, 10.0], [5.0, 9.0], [4.0, 8.0], [6.0, 8.0],
    [4.0, 11.0], [4.0, 9.0], [2.0, 8.0], [4.0, 10.0], [5.0, 8.0], [2.0, 7.0]
  ] }
  let(:tail_left_up) { @elements[:TAIL_LEFT_UP] }
  let(:bad_apple) { @elements[:BAD_APPLE] }
  let(:good_apple) { @elements[:GOOD_APPLE] }
  let(:head_down) { @elements[:HEAD_DOWN] }

  it { expect(@board.size).to eq 15 }
  it { expect(@board.get_stone).to eq [[9.0, 13.0]] }
  it { expect(@board.get_apple).to eq [[13.0, 12.0]] }
  it { expect(@board.get_barriers).to eq barriers }
  it { expect(@board.get_my_body).to eq my_body }
  it { expect(@board.get_my_head).to eq [[7.0, 11.0]] }

  it { expect(@board.at?(9, 13, bad_apple)).to eq true }
  it { expect(@board.at?(9, 13, good_apple)).to eq false }
  it { expect(@board.at?(6, 8, tail_left_up)).to eq true }
  it { expect(@board.at?(3, -1, tail_left_up)).to eq false }
  it { expect(@board.at?(3, @board.size(), @elements[:BREAK])).to eq false }
  it { expect(@board.at?(3, @board.size() - 1, @elements[:BREAK])).to eq true }

  it { expect(@board.get_at(9, 13)).to eq bad_apple }
  it { expect(@board.get_at(6, 8)).to eq tail_left_up }
  it { expect(@board.get_at(3, 0)).to eq @elements[:BREAK] }

  it { expect(@board.board_to_s + "\n").to eq @formated_data }

  it { expect(@board.find_by_list([tail_left_up])).to eq [[4.0, 8.0], [6.0, 8.0]] }
  it { expect(@board.find_by_list([@elements[:TAIL_HORIZONTAL]])).to eq [[5.0, 11.0], [6.0, 11.0], [5.0, 10.0], [3.0, 8.0], [3.0, 7.0]] }
  it { expect(@board.find_by_list([bad_apple])).to eq [[9.0, 13.0]] }
  it { expect(@board.find_by_list([good_apple])).to eq [[13.0, 12.0]] }
  it { expect(@board.find_by_list([@elements[:HEAD_RIGHT]])).to eq [[7.0, 11.0]] }
  it { expect(@board.find_by_list([head_down])).to eq [] }

  it { expect(@board.any_of_at?(9, 13, [head_down, @elements[:HEAD_UP], @elements[:HEAD_RIGHT], @elements[:HEAD_LEFT]])).to eq false }
  it { expect(@board.any_of_at?(9, 13, [@elements[:HEAD_DOWN]])).to eq false }
  it { expect(@board.any_of_at?(3, -1, [@elements[:HEAD_DOWN]])).to eq false }

  it { expect(@board.near?(9, 14,  @elements[:BAD_APPLE])).to eq true }
  it { expect(@board.near?(8, 13,  @elements[:BAD_APPLE])).to eq true }
  it { expect(@board.near?(10, 13, @elements[:BAD_APPLE])).to eq true }
  it { expect(@board.near?(8, 12,  @elements[:BAD_APPLE])).to eq false }
  it { expect(@board.near?(3, -1,  @elements[:BAD_APPLE])).to eq false }

  it { expect(@board.barrier_at?(9, 13)).to eq true }
  it { expect(@board.barrier_at?(0, 0)).to eq true }
  it { expect(@board.barrier_at?(3, -1)).to eq false }
  it { expect(@board.barrier_at?(3, 3)).to eq false }

  it { expect(@board.count_near(9, 12, @elements[:BAD_APPLE])).to eq 1 }
  it { expect(@board.count_near(0, 0, @elements[:BREAK])).to eq 2 }
  it { expect(@board.count_near(3, -1, @elements[:GOOD_APPLE])).to eq 0 }
end
