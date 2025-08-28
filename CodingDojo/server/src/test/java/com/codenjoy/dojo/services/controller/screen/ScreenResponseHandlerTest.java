package com.codenjoy.dojo.services.controller.screen;

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

import com.codenjoy.dojo.services.Player;
import com.codenjoy.dojo.services.hero.HeroDataImpl;
import com.codenjoy.dojo.services.playerdata.PlayerData;
import com.codenjoy.dojo.transport.ws.PlayerSocket;
import com.codenjoy.dojo.transport.ws.PlayerTransport;
import com.codenjoy.dojo.transport.ws.ResponseHandler;
import com.codenjoy.dojo.utils.JsonUtils;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.*;
import java.util.function.Function;

import static com.codenjoy.dojo.services.PointImpl.pt;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ScreenResponseHandlerTest {

    private PlayerTransport transport;
    private Player player;
    private ResponseHandler handler;
    private PlayerSocket socket;

    @Before
    public void setup() {
        transport = mock(PlayerTransport.class);
        player = new Player();
        handler = new ScreenResponseHandler(transport, player);
    }

    @Test
    public void performanceTest() { // about 20s
        // given
        handler.onResponse(socket,
                "{'name':getScreen, 'allPlayersScreen':true, " +
                        "'players':[], 'room':'room'}");
        Function function = verifySetFilterFor();
        Map<Player, PlayerData> map = getDummyPlayers(25);

        // when then
        for (int count = 0; count < 100000; count++) {
            String result = (String)function.apply(map);
        }
    }

    @Test
    public void shouldOnResponse_whenOnResponse_caseAllPlayersScreen_distinctByGroups() {
        // given

        // when
        handler.onResponse(socket,
                "{'name':getScreen, 'allPlayersScreen':true, " +
                        "'players':[], 'room':'room'}");

        // then
        Function function = verifySetFilterFor();

        Map<Player, PlayerData> map = getDummyPlayers();

        // when
        String result = (String)function.apply(map);

        // then
        assertEquals("{\n" +
                "  'player2':{\n" +
                "    'board':'some_board2',\n" +
                "    'boardSize':12,\n" +
                "    'coordinates':{\n" +
                "      'player2':{\n" +
                "        'additionalData':null,\n" +
                "        'coordinate':{\n" +
                "          'x':12,\n" +
                "          'y':7\n" +
                "        },\n" +
                "        'level':2,\n" +
                "        'multiplayer':true\n" +
                "      }\n" +
                "    },\n" +
                "    'game':'game',\n" +
                "    'group':[\n" +
                "      'player1',\n" +
                "      'player2'\n" +
                "    ],\n" +
                "    'info':'some_info2',\n" +
                "    'readableNames':{\n" +
                "      'player2':'Player2 Name2'\n" +
                "    },\n" +
                "    'score':546,\n" +
                "    'scores':{\n" +
                "      'player1':100,\n" +
                "      'player2':200\n" +
                "    },\n" +
                "    'teams':{\n" +
                "      'player1':1,\n" +
                "      'player2':2\n" +
                "    }\n" +
                "  },\n" +
                "  'player4':{\n" +
                "    'board':{\n" +
                "      'jsonBoard':'board4'\n" +
                "    },\n" +
                "    'boardSize':45,\n" +
                "    'coordinates':{\n" +
                "      'player4':{\n" +
                "        'additionalData':{\n" +
                "          'jsonData1':23,\n" +
                "          'jsonData2':true\n" +
                "        },\n" +
                "        'coordinate':{\n" +
                "          'x':14,\n" +
                "          'y':9\n" +
                "        },\n" +
                "        'level':4,\n" +
                "        'multiplayer':false\n" +
                "      }\n" +
                "    },\n" +
                "    'game':'game',\n" +
                "    'group':[\n" +
                "      'player4'\n" +
                "    ],\n" +
                "    'info':'some_info4',\n" +
                "    'readableNames':{\n" +
                "      'player4':'Player4 Name4'\n" +
                "    },\n" +
                "    'score':{\n" +
                "      'jsonScore':765\n" +
                "    },\n" +
                "    'scores':{\n" +
                "      'player4':400\n" +
                "    },\n" +
                "    'teams':{\n" +
                "      'player4':4\n" +
                "    }\n" +
                "  }\n" +
                "}", JsonUtils.prettyPrint(result));
    }

    @Test
    public void shouldOnResponse_whenOnResponse_caseAllPlayersScreen_noGroupsAllSelected() {
        // given

        // when
        handler.onResponse(socket,
                "{'name':getScreen, 'allPlayersScreen':true, " +
                        "'players':[], 'room':'room'}");

        // then
        Function function = verifySetFilterFor();

        Map<Player, PlayerData> map = getDummyPlayers();
        map.get(new Player("player1")).getGroup().remove(1);
        map.get(new Player("player1")).getScores().remove("player2");

        map.get(new Player("player2")).getGroup().remove(0);
        map.get(new Player("player2")).getScores().remove("player1");

        // when
        String result = (String)function.apply(map);

        // then
        assertEquals("{\n" +
                "  'player1':{\n" +
                "    'board':'some_board1',\n" +
                "    'boardSize':10,\n" +
                "    'coordinates':{\n" +
                "      'player1':{\n" +
                "        'additionalData':null,\n" +
                "        'coordinate':{\n" +
                "          'x':10,\n" +
                "          'y':5\n" +
                "        },\n" +
                "        'level':1,\n" +
                "        'multiplayer':true\n" +
                "      }\n" +
                "    },\n" +
                "    'game':'game',\n" +
                "    'group':[\n" +
                "      'player1'\n" +
                "    ],\n" +
                "    'info':'some_info1',\n" +
                "    'readableNames':{\n" +
                "      'player1':'Player1 Name1'\n" +
                "    },\n" +
                "    'score':134,\n" +
                "    'scores':{\n" +
                "      'player1':100\n" +
                "    },\n" +
                "    'teams':{\n" +
                "      'player1':1,\n" +
                "      'player2':2\n" +
                "    }\n" +
                "  },\n" +
                "  'player2':{\n" +
                "    'board':'some_board2',\n" +
                "    'boardSize':12,\n" +
                "    'coordinates':{\n" +
                "      'player2':{\n" +
                "        'additionalData':null,\n" +
                "        'coordinate':{\n" +
                "          'x':12,\n" +
                "          'y':7\n" +
                "        },\n" +
                "        'level':2,\n" +
                "        'multiplayer':true\n" +
                "      }\n" +
                "    },\n" +
                "    'game':'game',\n" +
                "    'group':[\n" +
                "      'player2'\n" +
                "    ],\n" +
                "    'info':'some_info2',\n" +
                "    'readableNames':{\n" +
                "      'player2':'Player2 Name2'\n" +
                "    },\n" +
                "    'score':546,\n" +
                "    'scores':{\n" +
                "      'player2':200\n" +
                "    },\n" +
                "    'teams':{\n" +
                "      'player1':1,\n" +
                "      'player2':2\n" +
                "    }\n" +
                "  },\n" +
                "  'player4':{\n" +
                "    'board':{\n" +
                "      'jsonBoard':'board4'\n" +
                "    },\n" +
                "    'boardSize':45,\n" +
                "    'coordinates':{\n" +
                "      'player4':{\n" +
                "        'additionalData':{\n" +
                "          'jsonData1':23,\n" +
                "          'jsonData2':true\n" +
                "        },\n" +
                "        'coordinate':{\n" +
                "          'x':14,\n" +
                "          'y':9\n" +
                "        },\n" +
                "        'level':4,\n" +
                "        'multiplayer':false\n" +
                "      }\n" +
                "    },\n" +
                "    'game':'game',\n" +
                "    'group':[\n" +
                "      'player4'\n" +
                "    ],\n" +
                "    'info':'some_info4',\n" +
                "    'readableNames':{\n" +
                "      'player4':'Player4 Name4'\n" +
                "    },\n" +
                "    'score':{\n" +
                "      'jsonScore':765\n" +
                "    },\n" +
                "    'scores':{\n" +
                "      'player4':400\n" +
                "    },\n" +
                "    'teams':{\n" +
                "      'player4':4\n" +
                "    }\n" +
                "  }\n" +
                "}", JsonUtils.prettyPrint(result));
    }

    @Test
    public void shouldOnResponse_whenOnResponse_caseSelectedPlayer_fromOtherGame() {
        // given

        // when
        handler.onResponse(socket,
                "{'name':getScreen, 'allPlayersScreen':false, " +
                        "'players':['player3'], 'room':'other_room'}");

        // then
        Function function = verifySetFilterFor();

        Map<Player, PlayerData> map = getDummyPlayers();

        // when
        String result = (String)function.apply(map);

        // then
        assertEquals("{\n" +
                "  'player3':{\n" +
                "    'board':'some_board3',\n" +
                "    'boardSize':14,\n" +
                "    'coordinates':{\n" +
                "      'player3':{\n" +
                "        'additionalData':null,\n" +
                "        'coordinate':{\n" +
                "          'x':13,\n" +
                "          'y':8\n" +
                "        },\n" +
                "        'level':3,\n" +
                "        'multiplayer':false\n" +
                "      }\n" +
                "    },\n" +
                "    'game':'other_game',\n" +
                "    'group':[\n" +
                "      'player3'\n" +
                "    ],\n" +
                "    'info':'some_info3',\n" +
                "    'readableNames':{\n" +
                "      'player3':'Player3 Name3'\n" +
                "    },\n" +
                "    'score':235,\n" +
                "    'scores':{\n" +
                "      'player3':300\n" +
                "    },\n" +
                "    'teams':{\n" +
                "      'player3':3\n" +
                "    }\n" +
                "  }\n" +
                "}", JsonUtils.prettyPrint(result));
    }

    @Test
    public void shouldOnResponse_whenOnResponse_caseSelectedPlayer() {
        // given

        // when
        handler.onResponse(socket,
                "{'name':getScreen, 'allPlayersScreen':false, " +
                        "'players':['player2'], 'room':'room'}");

        // then
        Function function = verifySetFilterFor();

        Map<Player, PlayerData> map = getDummyPlayers();

        // when
        String result = (String)function.apply(map);

        // then
        assertEquals("{\n" +
                "  'player2':{\n" +
                "    'board':'some_board2',\n" +
                "    'boardSize':12,\n" +
                "    'coordinates':{\n" +
                "      'player2':{\n" +
                "        'additionalData':null,\n" +
                "        'coordinate':{\n" +
                "          'x':12,\n" +
                "          'y':7\n" +
                "        },\n" +
                "        'level':2,\n" +
                "        'multiplayer':true\n" +
                "      }\n" +
                "    },\n" +
                "    'game':'game',\n" +
                "    'group':[\n" +
                "      'player1',\n" +
                "      'player2'\n" +
                "    ],\n" +
                "    'info':'some_info2',\n" +
                "    'readableNames':{\n" +
                "      'player2':'Player2 Name2'\n" +
                "    },\n" +
                "    'score':546,\n" +
                "    'scores':{\n" +
                "      'player1':100,\n" +
                "      'player2':200\n" +
                "    },\n" +
                "    'teams':{\n" +
                "      'player1':1,\n" +
                "      'player2':2\n" +
                "    }\n" +
                "  }\n" +
                "}", JsonUtils.prettyPrint(result));
    }

    private Map<Player, PlayerData> getDummyPlayers() {
        Map<Player, PlayerData> map = new HashMap<>();

        addDummyPlayers(map, "");

        return map;
    }

    private Map<Player, PlayerData> getDummyPlayers(int count) {
        Map<Player, PlayerData> map = new HashMap<>();

        for (int index = 0; index < count; index++) {
            addDummyPlayers(map, "preffix" + index + "");
        }

        return map;
    }

    private Map<Player, PlayerData> addDummyPlayers(Map<Player, PlayerData> map, String preffix) {
        Player player1 = new Player(preffix + "player1");
        player1.setGame("game");
        player1.setRoom("room");
        map.put(player1, new PlayerData(10, "some_board1", "game",
                134, "some_info1",
                new LinkedHashMap<>(){{ put(preffix + "player1", 100); put(preffix + "player2", 200); }},
                new LinkedHashMap<>(){{ put(preffix + "player1", 1); put(preffix + "player2", 2); }},
                new LinkedHashMap<>(){{ put(preffix + "player1", new HeroDataImpl(1, pt(10, 5), true)); }},
                new LinkedHashMap<>(){{ put(preffix + "player1", preffix + "Player1 Name1"); }},
                new LinkedList<>(){{ addAll(Arrays.asList(preffix + "player1", preffix + "player2")); }}));

        Player player2 = new Player(preffix + "player2");
        player2.setGame("game");
        player2.setRoom("room");
        map.put(player2, new PlayerData(12, "some_board2", "game",
                546, "some_info2",
                new LinkedHashMap<>(){{ put(preffix + "player1", 100); put(preffix + "player2", 200); }},
                new LinkedHashMap<>(){{ put(preffix + "player1", 1); put(preffix + "player2", 2); }},
                new LinkedHashMap<>(){{ put(preffix + "player2", new HeroDataImpl(2, pt(12, 7), true)); }},
                new LinkedHashMap<>(){{ put(preffix + "player2", preffix + "Player2 Name2"); }},
                new LinkedList<>(){{ addAll(Arrays.asList(preffix + "player1", preffix + "player2")); }}));

        Player player4 = new Player(preffix + "player4");
        player4.setGame("game");
        player4.setRoom("room");
        map.put(player4, new PlayerData(45, new JSONObject("{jsonBoard:\"board4\"}"), "game",
                new JSONObject("{jsonScore:765}"), "some_info4",
                new LinkedHashMap<>(){{ put(preffix + "player4", 400); }},
                new LinkedHashMap<>(){{ put(preffix + "player4", 4); }},
                new LinkedHashMap<>(){{ put(preffix + "player4", new HeroDataImpl(4, pt(14, 9), false){
                    @Override
                    public Object getAdditionalData() {
                        return new JSONObject("{jsonData1:23,jsonData2:true}");
                    }
                }); }},
                new LinkedHashMap<>(){{ put(preffix + "player4", preffix + "Player4 Name4"); }},
                new LinkedList<>(){{ addAll(Arrays.asList(preffix + "player4")); }}));

        Player player3 = new Player(preffix + "player3");
        player3.setGame("other_game");
        player3.setRoom("other_room");
        map.put(player3, new PlayerData(14, "some_board3", "other_game",
                235, "some_info3",
                new LinkedHashMap<>(){{ put(preffix + "player3", 300); }},
                new LinkedHashMap<>(){{ put(preffix + "player3", 3); }},
                new LinkedHashMap<>(){{ put(preffix + "player3", new HeroDataImpl(3, pt(13, 8), false)); }},
                new LinkedHashMap<>(){{ put(preffix + "player3", preffix + "Player3 Name3"); }},
                new LinkedList<>(){{ addAll(Arrays.asList(preffix + "player3")); }}));

        return map;
    }

    private Function verifySetFilterFor() {
        ArgumentCaptor<Function> result = ArgumentCaptor.forClass(Function.class);
        verify(transport).setFilterFor(eq(socket), result.capture());
        return result.getValue();
    }

}