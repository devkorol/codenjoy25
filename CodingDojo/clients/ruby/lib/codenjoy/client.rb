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

require "codenjoy/client/version"
require "faye/websocket"
require "eventmachine"

module Codenjoy
  module Client
    class Error < StandardError; end
    class Game
      def ws_url(url)
        res = url
        res["board/player/"] = "ws?user="
        res["?code="] = "&code="

        if res.include?("https")
          res["https"] = "wss"
        else
          res["http"] = "ws"
        end
        res
      end

      def play(url, log_level = nil)
        @log_level = log_level
        EM.run {
          ws = Faye::WebSocket::Client.new(ws_url(url))

          ws.on :open do |event|
            p [:open] if 'debug' == @log_level
          end

          ws.on :message do |event|
            yield ws, event.data
          end

          ws.on :close do |event|
            p [:close, event.code, event.reason]  if 'debug' == @log_level
            ws = nil
          end
        }
      end

      def source_path
        __dir__
      end
    end
  end
end
