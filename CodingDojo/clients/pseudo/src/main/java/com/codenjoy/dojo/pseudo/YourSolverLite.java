package com.codenjoy.dojo.pseudo;

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

import com.codenjoy.dojo.client.Solver;
import com.codenjoy.dojo.services.Dice;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class YourSolverLite implements Solver<RuleBoard> {

    private Processor processor;
    private ElementReader elements;

    public YourSolverLite(String rulesPlace, ElementReader elements, Dice dice) {
        this.elements = elements;
        this.processor = new Processor(rulesPlace, elements, dice, this::println);
    }

    private void println(Message message) {
        new PrintStream(System.out, true, StandardCharsets.UTF_8).println(message.toString());
    }

    @Override
    public String get(RuleBoard board) {
        return processor.next(board).toString();
    }

    public RuleBoard getBoard() {
        return new RuleBoard(elements);
    }
}
