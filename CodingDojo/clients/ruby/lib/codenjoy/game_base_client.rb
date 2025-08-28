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

require "codenjoy/client"
require "codenjoy/games/rawelbbub/board"
require "codenjoy/games/tetris/board"

class YourSolver
  # UP:   'up',                // you can move
  # DOWN: 'down',
  # LEFT: 'left',
  # RIGHT:'right',
  # ACT:  'act',               // fire
  # STOP: ''                   // stay
  def get_answer(board)

    #######################################################################
    #
    #                     YOUR ALGORITHM HERE
    #
    #######################################################################

    return 'down'
  end

end

game = Codenjoy::Client::Game.new
# board = Codenjoy::Client::Games::Rawelbbub::Board.new
board = Codenjoy::Client::Games::Tetris::Board.new
solver = YourSolver.new
url = "http://127.0.0.1:8080/codenjoy-contest/board/player/0?code=000000000000"
count = 0

game.play(url) do |ws, msg|
  json = msg[6..-1].force_encoding('UTF-8')
  board.process(json)
  puts board.to_s

  ws.send solver.get_answer(board)
  p count
  count += 1
end
