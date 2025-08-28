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

RSpec.describe Codenjoy::Client::Games::Tetris do
  before(:context) do
    @msg_from_server = File.open("spec/games/tetris/test_board.json", "r").read
    @board = Codenjoy::Client::Games::Tetris::Board.new
    @board.process(@msg_from_server)
    puts @board.to_s
  end

  let(:none)      { Codenjoy::Client::Games::Tetris::Board::ELEMENTS[:NONE] }
  let(:o_yellow)  { Codenjoy::Client::Games::Tetris::Board::ELEMENTS[:O_YELLOW] }
  let(:l_orange)  { Codenjoy::Client::Games::Tetris::Board::ELEMENTS[:L_ORANGE] }
  let(:i_blue)    { Codenjoy::Client::Games::Tetris::Board::ELEMENTS[:I_BLUE] }
  let(:s_green)   { Codenjoy::Client::Games::Tetris::Board::ELEMENTS[:S_GREEN] }
  let(:l_orange_none) { [l_orange, none] }

  it { expect(@board.current_figure_type).to eq "T" }
  it { expect(@board.current_figure_point.to_s).to eq "[1,2]" }
  it { expect(@board.future_figures).to eq ["I", "O", "L", "Z"] }

  context "test pt1" do
    before(:context) { @pt1 = Point.new(0, 0) }
    it { expect(@board.get_at(@pt1)).to eq none }
    it { expect(@board.is_at?(@pt1, o_yellow)).to eq false }
    it { expect(@board.is_at?(@pt1, none)).to eq true }
    it { expect(@board.is_at?(@pt1, l_orange_none)).to eq true }
    it { expect(@board.is_free?(@pt1)).to eq true }
  end

  context "test pt2" do
    before(:context) { @pt2 = Point.new(2, 0) }
    it { expect(@board.get_at(@pt2)).to eq o_yellow }
    it { expect(@board.is_at?(@pt2, o_yellow)).to eq true }
    it { expect(@board.is_at?(@pt2, none)).to eq false }
    it { expect(@board.is_at?(@pt2, l_orange_none)).to eq false }
    it { expect(@board.is_free?(@pt2)).to eq false }
  end

  context "test pt3" do
    before(:context) { @pt3 = Point.new(2, 2) }
    it { expect(@board.get_at(@pt3)).to eq s_green }
    it { expect(@board.is_at?(@pt3, o_yellow)).to eq false }
    it { expect(@board.is_at?(@pt3, none)).to eq false }
    it { expect(@board.is_at?(@pt3, l_orange_none)).to eq false }
    it { expect(@board.is_free?(@pt3)).to eq false }

    it { expect(@board.get_near(@pt3).to_s).to eq '["S", "S", ".", "O", ".", "O", "L", "L"]' }
    it { expect(@board.is_near?(@pt3, l_orange)).to eq true }
    it { expect(@board.is_near?(@pt3, i_blue)).to eq false }
    it { expect(@board.count_near(@pt3, l_orange)).to eq 2 }
    it { expect(@board.count_near(@pt3, s_green)).to eq 2 }
  end

  context "test pt4" do
    before(:context) { @pt4 = Point.new(3, 4) }
    it { expect(@board.get_at(@pt4)).to eq l_orange }
    it { expect(@board.is_at?(@pt4, o_yellow)).to eq false }
    it { expect(@board.is_at?(@pt4, none)).to eq false }
    it { expect(@board.is_at?(@pt4, l_orange_none)).to eq true }
    it { expect(@board.is_free?(@pt4)).to eq false }

    it { expect(@board.get_near(@pt4).to_s).to eq '[".", "L", ".", "L", ".", "I", ".", "."]' }
    it { expect(@board.is_near?(@pt4, Codenjoy::Client::Games::Tetris::Board::ELEMENTS[:T_PURPLE])).to eq false }
    it { expect(@board.is_near?(@pt4, i_blue)).to eq true }
    it { expect(@board.count_near(@pt4, i_blue)).to eq 1 }
    it { expect(@board.count_near(@pt4, l_orange)).to eq 2 }
  end

  it { expect(@board.get(s_green).map{ |e| e.to_s }).to eq ["[0,1]", "[1,1]", "[1,2]", "[2,2]"] }
  it { expect(@board.get(l_orange).map{ |e| e.to_s }).to eq ["[2,4]", "[3,2]", "[3,3]", "[3,4]"] }
  it { expect(@board.get([l_orange, s_green]).map{ |e| e.to_s }).to eq ["[0,1]", "[1,1]", "[1,2]", "[2,2]", "[2,4]", "[3,2]", "[3,3]", "[3,4]"] }

  it { expect(@board.get_figures.map{ |e| e.to_s }).to eq [
    "[0,1]", "[1,1]", "[1,2]", "[2,0]",
    "[2,1]", "[2,2]", "[2,4]", "[3,0]",
    "[3,1]", "[3,2]", "[3,3]", "[3,4]",
    "[4,0]", "[4,1]", "[4,2]", "[4,3]",
    "[5,0]", "[5,1]", "[6,0]", "[6,1]",
    "[6,2]", "[6,3]", "[6,4]", "[6,5]"
  ] }

  it { expect(@board.get_free_space.map{ |e| e.to_s }).to eq [
    "[0,0]", "[0,2]", "[0,3]", "[0,4]",
    "[0,5]", "[0,6]", "[1,0]", "[1,3]",
    "[1,4]", "[1,5]", "[1,6]", "[2,3]",
    "[2,5]", "[2,6]", "[3,5]", "[3,6]",
    "[4,4]", "[4,5]", "[4,6]", "[5,2]",
    "[5,3]", "[5,4]", "[5,5]", "[5,6]", "[6,6]"
  ] }
end
