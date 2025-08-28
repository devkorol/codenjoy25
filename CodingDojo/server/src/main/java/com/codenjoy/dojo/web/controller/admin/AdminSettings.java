package com.codenjoy.dojo.web.controller.admin;

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


import com.codenjoy.dojo.services.PlayerInfo;
import com.codenjoy.dojo.services.StatisticService;
import com.codenjoy.dojo.services.incativity.InactivitySettingsImpl;
import com.codenjoy.dojo.services.level.LevelsSettingsImpl;
import com.codenjoy.dojo.services.room.GamesRooms;
import com.codenjoy.dojo.services.round.RoundSettingsImpl;
import com.codenjoy.dojo.services.semifinal.SemifinalSettingsImpl;
import com.codenjoy.dojo.services.settings.Parameter;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class AdminSettings {

    private List<PlayerInfo> players;
    private List<Object> activeGames;

    // used to get data from jsp
    private String action; // submit button value
    
    private List<Object> otherValues;
    private List<Object> levelsValues;
    private List<Object> levelsKeys;
    private List<Object> levelsNewKeys;

    private String loggersLevels;
    private String player;
    private String game;
    private String room;
    private String newRoom;
    private String generateNameMask;
    private Integer generateCount;
    private Integer timerPeriod;
    private String progress;
    private Integer semifinalTick;
    private String gameVersion;
    private Boolean active;
    private Boolean roomOpened;
    private Boolean recording;
    private Boolean autoSave;
    private Boolean debugLog;
    private Boolean opened;
    private GamesRooms gamesRooms;
    private Map<String, Integer> playersCount;

    // used to send data to jsp
    private SemifinalSettingsImpl semifinal;
    private RoundSettingsImpl rounds;
    private InactivitySettingsImpl inactivity;
    private LevelsSettingsImpl levels;
    private List<Parameter> other;
    private StatisticService statistic;
    private AdminPostActions actions;

}
