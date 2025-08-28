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

import com.codenjoy.dojo.services.settings.SettingsImpl;
import com.codenjoy.dojo.services.settings.SomeRoundSettings;
import com.codenjoy.dojo.services.settings.SomeSemifinalSettings;
import org.junit.Test;

import static com.codenjoy.dojo.client.Utils.split;
import static org.junit.Assert.assertEquals;

public class RoundSettingsTest {

    @Test
    public void testIf_caseNull() {
        assertEquals(false, RoundSettings.is(null));
    }

    @Test
    public void testIf_caseNotRoundInstance() {
        assertEquals(false, RoundSettings.is(new SettingsImpl()));
    }

    @Test
    public void testIf_caseRoundInstance() {
        assertEquals(true, RoundSettings.is(new RoundSettingsImpl()));
    }

    @Test
    public void testIf_caseAllPropertiesCopied() {
        // given when
        SettingsImpl settings = new SettingsImpl(){{
            new SomeRoundSettings().getRoundParams()
                    .forEach(param -> map.put(param.getName(), param));
        }};

        // then
        assertEquals(true, RoundSettings.is(settings));
    }

    @Test
    public void testIf_caseNotAllPropertiesCopied() {
        // given when
        SettingsImpl settings = new SettingsImpl(){{
            new SomeRoundSettings().getRoundParams()
                    .forEach(param -> map.put(param.getName(), param));
        }};

        settings.removeParameter(RoundSettings.Keys.ROUNDS_TIME.key());

        // then
        assertEquals(false, RoundSettings.is(settings));
    }

    @Test
    public void testGet_caseAllPropertiesCopied() {
        // given when
        SettingsImpl settings = givenAllPropertiesCopied();

        // then
        assertEquals("SettingsImpl(map={[Rounds] Enabled=[[Rounds] Enabled:Boolean = def[false] val[false]], \n" +
                        "[Rounds] Players per room=[[Rounds] Players per room:Integer = multiline[false] def[5] val[5]], \n" +
                        "[Rounds] Teams per room=[[Rounds] Teams per room:Integer = multiline[false] def[1] val[1]], \n" +
                        "[Rounds] Time per Round=[[Rounds] Time per Round:Integer = multiline[false] def[200] val[200]], \n" +
                        "[Rounds] Time for Winner=[[Rounds] Time for Winner:Integer = multiline[false] def[1] val[1]], \n" +
                        "[Rounds] Time before start Round=[[Rounds] Time before start Round:Integer = multiline[false] def[5] val[5]], \n" +
                        "[Rounds] Rounds per Match=[[Rounds] Rounds per Match:Integer = multiline[false] def[1] val[1]], \n" +
                        "[Rounds] Min ticks for win=[[Rounds] Min ticks for win:Integer = multiline[false] def[1] val[1]]})",
                split(RoundSettings.get(settings), ", \n[Rounds]"));
    }

    private SettingsImpl givenAllPropertiesCopied() {
        return new SettingsImpl(){{
            new SomeRoundSettings().getRoundParams()
                    .forEach(param -> map.put(param.getName(), param));
        }};
    }

    @Test
    public void testGet_caseRoundInstance() {
        // given when
        SettingsImpl settings = new SomeRoundSettings();

        // then
        assertEquals("Some[[Rounds] Enabled=false, \n" +
                        "[Rounds] Players per room=5, \n" +
                        "[Rounds] Teams per room=1, \n" +
                        "[Rounds] Time per Round=200, \n" +
                        "[Rounds] Time for Winner=1, \n" +
                        "[Rounds] Time before start Round=5, \n" +
                        "[Rounds] Rounds per Match=1, \n" +
                        "[Rounds] Min ticks for win=1, \n" +
                        "[Multiplayer] Mode=[MULTIPLE] One level chosen at random. Multi player (all together or in the rooms)., \n" +
                        "[Multiplayer] Room size=5, \n" +
                        "Parameter 1=15, \n" +
                        "Parameter 2=true, \n" +
                        "Parameter 3=0.5, \n" +
                        "Parameter 4=string]",
                split(RoundSettings.get(settings), ", \n"));
    }

    @Test
    public void testGet_caseNotRoundInstance() {
        // given when
        SettingsImpl settings = new SomeSemifinalSettings();

        // then
        assertEquals("SettingsImpl(map={})",
                RoundSettings.get(settings).toString());
    }

    @Test
    public void testGet_caseNull() {
        // given when
        SettingsImpl settings = null;

        // then
        assertEquals("SettingsImpl(map={})",
                RoundSettings.get(settings).toString());
    }

    @Test
    public void testGetters(){
        // given
        SomeRoundSettings settings = new SomeRoundSettings();

        // when
        settings.setRoundsEnabled(false)
                .setPlayersPerRoom(7)
                .setTeamsPerRoom(3)
                .setTimePerRound(150)
                .setTimeForWinner(30)
                .setTimeBeforeStart(6)
                .setRoundsPerMatch(4)
                .setMinTicksForWin(50);

        // when then
        assertEquals(false, settings.isRoundsEnabled());
        assertEquals(true, settings.isRoundsDisabled());
        assertEquals(7, settings.getPlayersPerRoom());
        assertEquals(3, settings.getTeamsPerRoom());
        assertEquals(150, settings.getTimePerRound());
        assertEquals(30, settings.getTimeForWinner());
        assertEquals(6, settings.getTimeBeforeStart());
        assertEquals(4, settings.getRoundsPerMatch());
        assertEquals(50, settings.getMinTicksForWin());
    }
}