package com.codenjoy.dojo.games.mollymage;

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

import com.codenjoy.dojo.client.Utils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ElementTest {

    @Test
    public void testElementProperties() {
        assertEquals("BLAST[҉]                 =blasts\n" +
                    "ENEMY_HERO[ö]            =enemyHeroes, barriers\n" +
                    "ENEMY_HERO_DEAD[ø]       =enemyHeroes, barriers\n" +
                    "ENEMY_HERO_POTION[Ö]     =enemyHeroes, potions, barriers, barriers\n" +
                    "GHOST[&]                 =ghosts, barriers\n" +
                    "GHOST_DEAD[x]            =ghosts\n" +
                    "HERO[☺]                  =heroes\n" +
                    "HERO_DEAD[Ѡ]             =heroes\n" +
                    "HERO_POTION[☻]           =heroes, potions, barriers\n" +
                    "NONE[ ]                  =none\n" +
                    "OTHER_HERO[♥]            =otherHeroes, barriers\n" +
                    "OTHER_HERO_DEAD[♣]       =otherHeroes, barriers\n" +
                    "OTHER_HERO_POTION[♠]     =otherHeroes, potions, barriers, barriers\n" +
                    "POISON_THROWER[T]        =perks\n" +
                    "POTION_BLAST_RADIUS_INCREASE[+]=perks\n" +
                    "POTION_COUNT_INCREASE[c] =perks\n" +
                    "POTION_EXPLODER[A]       =perks\n" +
                    "POTION_IMMUNE[i]         =perks\n" +
                    "POTION_REMOTE_CONTROL[r] =perks\n" +
                    "POTION_TIMER_1[1]        =potions, barriers\n" +
                    "POTION_TIMER_2[2]        =potions, barriers\n" +
                    "POTION_TIMER_3[3]        =potions, barriers\n" +
                    "POTION_TIMER_4[4]        =potions, barriers\n" +
                    "POTION_TIMER_5[5]        =potions, barriers\n" +
                    "TREASURE_BOX[#]          =treasureBoxes, barriers\n" +
                    "TREASURE_BOX_OPENING[H]  =treasureBoxes\n" +
                    "WALL[☼]                  =walls, barriers\n",
                Utils.elements(Element.values(), ElementUtils.class));
    }
}