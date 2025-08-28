package com.codenjoy.dojo.services.round;

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

import com.codenjoy.dojo.services.settings.Parameter;
import com.codenjoy.dojo.services.settings.Settings;
import com.codenjoy.dojo.services.settings.SettingsReader;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static com.codenjoy.dojo.services.round.RoundSettings.Keys.*;
import static com.codenjoy.dojo.services.settings.Parameter.copy;

public interface RoundSettings<T extends SettingsReader> extends SettingsReader<T> {

    String ROUNDS = "[Rounds]";

    public enum Keys implements SettingsReader.Key {

        ROUNDS_ENABLED(ROUNDS + " Enabled"),
        ROUNDS_PLAYERS_PER_ROOM(ROUNDS + " Players per room"),
        ROUNDS_TEAMS_PER_ROOM(ROUNDS + " Teams per room"),
        ROUNDS_TIME(ROUNDS + " Time per Round"),
        ROUNDS_TIME_FOR_WINNER(ROUNDS + " Time for Winner"),
        ROUNDS_TIME_BEFORE_START(ROUNDS + " Time before start Round"),
        ROUNDS_PER_MATCH(ROUNDS + " Rounds per Match"),
        ROUNDS_MIN_TICKS_FOR_WIN(ROUNDS + " Min ticks for win");

        private String key;

        Keys(String key) {
            this.key = key;
        }

        @Override
        public String key() {
            return key;
        }
    }

    static boolean is(Settings settings) {
        if (settings == null) return false;

        return settings instanceof RoundSettings
                || allRoundsKeys().stream()
                        .map(Key::key)
                        .allMatch(settings::hasParameter);
    }

    static RoundSettingsImpl get(Settings settings) {
        if (RoundSettings.is(settings)) {
            return new RoundSettingsImpl(settings);
        }

        return new RoundSettingsImpl(null);
    }

    static List<SettingsReader.Key> allRoundsKeys() {
        return Arrays.asList(Keys.values());
    }

    default void initRound() {
        // включен ли режим раундов
        bool(ROUNDS_ENABLED, false);

        // сколько участников в комнате
        integer(ROUNDS_PLAYERS_PER_ROOM, 5);

        // количество команд среди игроков в комнате
        integer(ROUNDS_TEAMS_PER_ROOM, 1);

        // сколько тиков на 1 раунд
        integer(ROUNDS_TIME, 200);

        // сколько тиков победитель будет сам оставаться после всех побежденных
        integer(ROUNDS_TIME_FOR_WINNER, 1);

        // обратный отсчет перед началом раунда
        integer(ROUNDS_TIME_BEFORE_START, 5);

        // сколько раундов (с тем же составом героев) на 1 матч
        integer(ROUNDS_PER_MATCH, 1);

        // сколько тиков должно пройти от начала раунда, чтобы засчитать победу
        integer(ROUNDS_MIN_TICKS_FOR_WIN, 1);
    }

    // parameters getters

    default Parameter<Boolean> roundsEnabled() {
        return boolValue(Keys.ROUNDS_ENABLED);
    }

    default Parameter<Integer> playersPerRoom() {
        return integerValue(Keys.ROUNDS_PLAYERS_PER_ROOM);
    }

    default Parameter<Integer> teamsPerRoom() {
        return integerValue(ROUNDS_TEAMS_PER_ROOM);
    }

    default Parameter<Integer> timePerRound() {
        return integerValue(ROUNDS_TIME);
    }

    default Parameter<Integer> timeForWinner() {
        return integerValue(Keys.ROUNDS_TIME_FOR_WINNER);
    }

    default Parameter<Integer> timeBeforeStart() {
        return integerValue(Keys.ROUNDS_TIME_BEFORE_START);
    }

    default Parameter<Integer> roundsPerMatch() {
        return integerValue(Keys.ROUNDS_PER_MATCH);
    }

    default Parameter<Integer> minTicksForWin() {
        return integerValue(Keys.ROUNDS_MIN_TICKS_FOR_WIN);
    }

    // update methods

    // TODO test me
    default List<Parameter> getRoundParams() {
        if (getParameters().isEmpty()) {
            return Arrays.asList();
        }
        return new LinkedList<>(){{
            add(roundsEnabled());
            add(playersPerRoom());
            add(teamsPerRoom());
            add(timePerRound());
            add(timeForWinner());
            add(timeBeforeStart());
            add(roundsPerMatch());
            add(minTicksForWin());
        }};
    }

    default T update(RoundSettings input) {
        setRoundsEnabled(input.isRoundsEnabled());
        setPlayersPerRoom(input.getPlayersPerRoom());
        setTeamsPerRoom(input.getTeamsPerRoom());
        setTimePerRound(input.getTimePerRound());
        setTimeForWinner(input.getTimeForWinner());
        setTimeBeforeStart(input.getTimeBeforeStart());
        setRoundsPerMatch(input.getRoundsPerMatch());
        setMinTicksForWin(input.getMinTicksForWin());
        return (T) this;
    }

    default T updateRound(Settings input) {
        if (input != null) {
            allRoundsKeys().stream()
                    .map(Key::key)
                    .forEach(key -> copy(
                            input.getParameter(key, () -> null),
                            getParameter(key, () -> null)));
        }
        return (T) this;
    }

    // getters

    default boolean isRoundsEnabled() {
        return roundsEnabled().getValue();
    }

    default boolean isRoundsDisabled() {
        return !isRoundsEnabled();
    }

    default int getPlayersPerRoom() {
        return playersPerRoom().getValue();
    }

    default int getTeamsPerRoom(){
        return teamsPerRoom().getValue();
    }

    default int getTimePerRound() {
        return timePerRound().getValue();
    }

    default int getTimeForWinner() {
        return timeForWinner().getValue();
    }

    default int getTimeBeforeStart() {
        return timeBeforeStart().getValue();
    }

    default int getRoundsPerMatch() {
        return roundsPerMatch().getValue();
    }

    default int getMinTicksForWin() {
        return minTicksForWin().getValue();
    }

    // setters

    default T setRoundsEnabled(boolean input) {
        roundsEnabled().update(input);
        return (T) this;
    }

    default T setPlayersPerRoom(int input) {
        playersPerRoom().update(input);
        return (T) this;
    }

    default T setTeamsPerRoom(int input) {
        teamsPerRoom().update(input);
        return (T) this;
    }

    default T setTimePerRound(int input) {
        timePerRound().update(input);
        return (T) this;
    }

    default T setTimeForWinner(int input) {
        timeForWinner().update(input);
        return (T) this;
    }

    default T setTimeBeforeStart(int input) {
        timeBeforeStart().update(input);
        return (T) this;
    }

    default T setRoundsPerMatch(int input) {
        roundsPerMatch().update(input);
        return (T) this;
    }

    default T setMinTicksForWin(int input) {
        minTicksForWin().update(input);
        return (T) this;
    }
}