package com.codenjoy.dojo

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2012 - 2022 Codenjoy
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import com.codenjoy.dojo.client.WebSocketRunner
import com.codenjoy.dojo.client.runner.ReflectLoader.{loadScalaBoard, loadScalaSolver}

object ScalaRunner {

  // Select your game
  var GAME = "mollymage"

  // Paste here board page url from browser after registration,
  // or put it as command line parameter.
  var URL = "http://127.0.0.1:8080/codenjoy-contest/board/player/0?code=000000000000"

  def main(args: Array[String]): Unit = {
    if (args != null && args.length == 2) {
      GAME = args(0)
      URL = args(1)
    }
    WebSocketRunner.runClient(URL, loadScalaSolver(GAME), loadScalaBoard(GAME))
  }
}