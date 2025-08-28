package com.codenjoy.dojo.services.multiplayer.types;

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

import com.codenjoy.dojo.services.Progressive;
import com.codenjoy.dojo.services.multiplayer.LevelProgress;
import com.codenjoy.dojo.services.multiplayer.MultiplayerType;
import com.codenjoy.dojo.services.multiplayer.Single;
import org.json.JSONArray;
import org.json.JSONObject;

public class LevelsType extends MultiplayerType {

    public static final String LAYERS = "layers";

    public LevelsType(int roomSize, int levelsCount, boolean disposable, boolean shouldReloadAlone) {
        super(roomSize, levelsCount, disposable, shouldReloadAlone);
    }

    @Override
    public Object postProcessBoard(Object board, Single single) {
        LevelProgress progress = single.getProgress();

        if (board instanceof JSONObject) {
            JSONObject json = (JSONObject) board;
            progress.saveTo(json);
            return json;
        }

        JSONObject json = new JSONObject();
        progress.saveTo(json);
        json.put(LAYERS, new JSONArray(){{
            put(board);
        }});
        return json;
    }

    @Override
    public JSONObject postProcessSave(JSONObject save, Single single) {
        LevelProgress progress = single.getProgress();

        JSONObject result = new JSONObject();
        result.put("field", save);
        progress.saveTo(result);
        return result;
    }

    @Override
    public LevelProgress progress() {
        return new LevelProgress(){{
            total = getLevelsCount();
        }};
    }

    @Override
    public int loadProgress(Progressive game, JSONObject save) {
        if (!save.has("levelProgress")) {
            return super.loadProgress(game, save);
        }

        LevelProgress progress = new LevelProgress(save);
        int roomSize = getRoomSize(progress);
        game.setProgress(progress);
        return roomSize;
    }

    @Override
    public String getRoom(String room, int levelNumber) {
        // TODO #7D4 это будет иметь нежелательные последствия, надо придумать иной подход
        return room + "[" + levelNumber + "]";
    }
}
