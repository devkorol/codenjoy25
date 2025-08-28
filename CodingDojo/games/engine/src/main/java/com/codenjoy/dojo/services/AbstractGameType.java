package com.codenjoy.dojo.services;

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


import com.codenjoy.dojo.services.dice.RandomDice;
import com.codenjoy.dojo.services.multiplayer.MultiplayerType;
import com.codenjoy.dojo.services.printer.PrinterFactory;
import com.codenjoy.dojo.services.printer.PrinterFactoryImpl;
import com.codenjoy.dojo.services.settings.Settings;

/**
 * Класс позволяет не фиксить все игры, если будет добьавлен интерфейсный метод в GameType
 * Так же содержит наиболее общий код, актуальный для всех игр
 */
public abstract class AbstractGameType<T extends Settings> implements GameType<T> {

    @Override
    public MultiplayerType getMultiplayerType(T settings) {
        return MultiplayerType.SINGLE;
    }

    @Override
    public String getVersion() {
        return VersionReader.version(name()).toString();
    }

    /**
     * Этот метод будет дергаться перед всеми тиками игрушек
     */
    @Override
    public void tick() {
        // do nothing
    }

    @Override
    public Dice getDice() {
        return new RandomDice();
    }

    @Override
    public PrinterFactory getPrinterFactory() {
        return new PrinterFactoryImpl();
    }

    @Override
    public String toString() {
        return String.format("GameType[%s]", name());
    }
}
