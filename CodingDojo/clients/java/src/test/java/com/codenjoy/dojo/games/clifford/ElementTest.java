package com.codenjoy.dojo.games.clifford;

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

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;
import static org.apache.commons.lang3.StringUtils.rightPad;
import static org.junit.Assert.assertEquals;

public class ElementTest {

    @Test
    public void testElementProperties() {
        assertEquals("BACKWAY[W]               =backWays\n" +
                    "BRICK[#]                 =walls, barriers\n" +
                    "BULLET[•]                =bullets, barriers\n" +
                    "CLOSED_DOOR_BRONZE[B]    =doors, barriers\n" +
                    "CLOSED_DOOR_GOLD[G]      =doors, barriers\n" +
                    "CLOSED_DOOR_SILVER[S]    =doors, barriers\n" +
                    "CLUE_GLOVE[&]            =clues\n" +
                    "CLUE_KNIFE[$]            =clues\n" +
                    "CLUE_RING[@]             =clues\n" +
                    "CRACK_PIT[*]             =pits\n" +
                    "ENEMY_HERO_DIE[L]        =enemyHeroes, barriers\n" +
                    "ENEMY_HERO_FALL[R]       =enemyHeroes, barriers\n" +
                    "ENEMY_HERO_LADDER[N]     =ladders, enemyHeroes, barriers\n" +
                    "ENEMY_HERO_LEFT[P]       =enemyHeroes, barriers\n" +
                    "ENEMY_HERO_MASK_DIE[l]   =enemyHeroes, barriers\n" +
                    "ENEMY_HERO_MASK_FALL[r]  =enemyHeroes, barriers\n" +
                    "ENEMY_HERO_MASK_LADDER[n]=ladders, enemyHeroes, barriers\n" +
                    "ENEMY_HERO_MASK_LEFT[p]  =enemyHeroes, barriers\n" +
                    "ENEMY_HERO_MASK_PIPE[t]  =enemyHeroes, pipes, barriers\n" +
                    "ENEMY_HERO_MASK_PIT[v]   =enemyHeroes, pits, barriers\n" +
                    "ENEMY_HERO_MASK_RIGHT[q] =enemyHeroes, barriers\n" +
                    "ENEMY_HERO_PIPE[T]       =enemyHeroes, pipes, barriers\n" +
                    "ENEMY_HERO_PIT[V]        =enemyHeroes, pits, barriers\n" +
                    "ENEMY_HERO_RIGHT[Q]      =enemyHeroes, barriers\n" +
                    "HERO_DIE[O]              =heroes, heroDie\n" +
                    "HERO_FALL[U]             =heroes\n" +
                    "HERO_LADDER[A]           =ladders, heroes\n" +
                    "HERO_LEFT[◄]             =heroes\n" +
                    "HERO_MASK_DIE[o]         =heroes, heroDie\n" +
                    "HERO_MASK_FALL[u]        =heroes\n" +
                    "HERO_MASK_LADDER[a]      =ladders, heroes\n" +
                    "HERO_MASK_LEFT[h]        =heroes\n" +
                    "HERO_MASK_PIPE[i]        =heroes, pipes\n" +
                    "HERO_MASK_PIT[e]         =heroes, pits\n" +
                    "HERO_MASK_RIGHT[w]       =heroes\n" +
                    "HERO_PIPE[I]             =heroes, pipes\n" +
                    "HERO_PIT[E]              =heroes, pits\n" +
                    "HERO_RIGHT[►]            =heroes\n" +
                    "KEY_BRONZE[!]            =keys\n" +
                    "KEY_GOLD[+]              =keys\n" +
                    "KEY_SILVER[-]            =keys\n" +
                    "LADDER[H]                =ladders\n" +
                    "MASK_POTION[m]           =maskPotions\n" +
                    "NONE[ ]                  =none\n" +
                    "OPENED_DOOR_BRONZE[b]    =doors\n" +
                    "OPENED_DOOR_GOLD[g]      =doors\n" +
                    "OPENED_DOOR_SILVER[s]    =doors\n" +
                    "OTHER_HERO_DIE[C]        =otherHeroes, barriers\n" +
                    "OTHER_HERO_FALL[F]       =otherHeroes, barriers\n" +
                    "OTHER_HERO_LADDER[D]     =ladders, otherHeroes, barriers\n" +
                    "OTHER_HERO_LEFT[«]       =otherHeroes, barriers\n" +
                    "OTHER_HERO_MASK_DIE[c]   =otherHeroes, barriers\n" +
                    "OTHER_HERO_MASK_FALL[f]  =otherHeroes, barriers\n" +
                    "OTHER_HERO_MASK_LADDER[d]=ladders, otherHeroes, barriers\n" +
                    "OTHER_HERO_MASK_LEFT[Z]  =otherHeroes, barriers\n" +
                    "OTHER_HERO_MASK_PIPE[j]  =otherHeroes, pipes, barriers\n" +
                    "OTHER_HERO_MASK_PIT[k]   =otherHeroes, pits, barriers\n" +
                    "OTHER_HERO_MASK_RIGHT[z] =otherHeroes, barriers\n" +
                    "OTHER_HERO_PIPE[J]       =otherHeroes, pipes, barriers\n" +
                    "OTHER_HERO_PIT[K]        =otherHeroes, pits, barriers\n" +
                    "OTHER_HERO_RIGHT[»]      =otherHeroes, barriers\n" +
                    "PIPE[~]                  =pipes\n" +
                    "PIT_FILL_1[1]            =pits\n" +
                    "PIT_FILL_2[2]            =pits\n" +
                    "PIT_FILL_3[3]            =pits\n" +
                    "PIT_FILL_4[4]            =pits\n" +
                    "ROBBER_FALL[x]           =robbers, barriers\n" +
                    "ROBBER_LADDER[X]         =ladders, robbers, barriers\n" +
                    "ROBBER_LEFT[)]           =robbers, barriers\n" +
                    "ROBBER_PIPE[Y]           =robbers, pipes, barriers\n" +
                    "ROBBER_PIT[y]            =robbers, pits, barriers\n" +
                    "ROBBER_RIGHT[(]          =robbers, barriers\n" +
                    "STONE[☼]                 =walls, barriers\n",
                Utils.elements(Element.values(), ElementUtils.class));
    }

    @Test
    public void testGetMask() {
        assertEquals("HERO_DIE            =HERO_MASK_DIE\n" +
                    "HERO_LADDER         =HERO_MASK_LADDER\n" +
                    "HERO_LEFT           =HERO_MASK_LEFT\n" +
                    "HERO_RIGHT          =HERO_MASK_RIGHT\n" +
                    "HERO_FALL           =HERO_MASK_FALL\n" +
                    "HERO_PIPE           =HERO_MASK_PIPE\n" +
                    "HERO_PIT            =HERO_MASK_PIT\n" +
                    "OTHER_HERO_DIE      =OTHER_HERO_MASK_DIE\n" +
                    "OTHER_HERO_LADDER   =OTHER_HERO_MASK_LADDER\n" +
                    "OTHER_HERO_LEFT     =OTHER_HERO_MASK_LEFT\n" +
                    "OTHER_HERO_RIGHT    =OTHER_HERO_MASK_RIGHT\n" +
                    "OTHER_HERO_FALL     =OTHER_HERO_MASK_FALL\n" +
                    "OTHER_HERO_PIPE     =OTHER_HERO_MASK_PIPE\n" +
                    "OTHER_HERO_PIT      =OTHER_HERO_MASK_PIT\n" +
                    "ENEMY_HERO_DIE      =ENEMY_HERO_MASK_DIE\n" +
                    "ENEMY_HERO_LADDER   =ENEMY_HERO_MASK_LADDER\n" +
                    "ENEMY_HERO_LEFT     =ENEMY_HERO_MASK_LEFT\n" +
                    "ENEMY_HERO_RIGHT    =ENEMY_HERO_MASK_RIGHT\n" +
                    "ENEMY_HERO_FALL     =ENEMY_HERO_MASK_FALL\n" +
                    "ENEMY_HERO_PIPE     =ENEMY_HERO_MASK_PIPE\n" +
                    "ENEMY_HERO_PIT      =ENEMY_HERO_MASK_PIT",
                toString(ElementUtils::mask));
    }

    @Test
    public void testGetOtherHero() {
        assertEquals("HERO_DIE            =OTHER_HERO_DIE\n" +
                    "HERO_LADDER         =OTHER_HERO_LADDER\n" +
                    "HERO_LEFT           =OTHER_HERO_LEFT\n" +
                    "HERO_RIGHT          =OTHER_HERO_RIGHT\n" +
                    "HERO_FALL           =OTHER_HERO_FALL\n" +
                    "HERO_PIPE           =OTHER_HERO_PIPE\n" +
                    "HERO_PIT            =OTHER_HERO_PIT\n" +
                    "HERO_MASK_DIE       =OTHER_HERO_MASK_DIE\n" +
                    "HERO_MASK_LADDER    =OTHER_HERO_MASK_LADDER\n" +
                    "HERO_MASK_LEFT      =OTHER_HERO_MASK_LEFT\n" +
                    "HERO_MASK_RIGHT     =OTHER_HERO_MASK_RIGHT\n" +
                    "HERO_MASK_FALL      =OTHER_HERO_MASK_FALL\n" +
                    "HERO_MASK_PIPE      =OTHER_HERO_MASK_PIPE\n" +
                    "HERO_MASK_PIT       =OTHER_HERO_MASK_PIT",
                toString(ElementUtils.TEAM_ELEMENT::otherHero));
    }

    @Test
    public void testGetEnemyHero() {
        assertEquals("HERO_DIE            =ENEMY_HERO_DIE\n" +
                        "HERO_LADDER         =ENEMY_HERO_LADDER\n" +
                        "HERO_LEFT           =ENEMY_HERO_LEFT\n" +
                        "HERO_RIGHT          =ENEMY_HERO_RIGHT\n" +
                        "HERO_FALL           =ENEMY_HERO_FALL\n" +
                        "HERO_PIPE           =ENEMY_HERO_PIPE\n" +
                        "HERO_PIT            =ENEMY_HERO_PIT\n" +
                        "HERO_MASK_DIE       =ENEMY_HERO_MASK_DIE\n" +
                        "HERO_MASK_LADDER    =ENEMY_HERO_MASK_LADDER\n" +
                        "HERO_MASK_LEFT      =ENEMY_HERO_MASK_LEFT\n" +
                        "HERO_MASK_RIGHT     =ENEMY_HERO_MASK_RIGHT\n" +
                        "HERO_MASK_FALL      =ENEMY_HERO_MASK_FALL\n" +
                        "HERO_MASK_PIPE      =ENEMY_HERO_MASK_PIPE\n" +
                        "HERO_MASK_PIT       =ENEMY_HERO_PIT",
                toString(ElementUtils.TEAM_ELEMENT::enemyHero));
    }

    private String toString(Function<Element, Element> transformer) {
        return allElements(transformer)
                .toString()
                .replace(", ", "\n")
                .replaceAll("[{}]", "");
    }

    private LinkedHashMap<String, String> allElements(Function<Element, Element> transformer) {
        return Arrays.stream(Element.values())
                .map(element -> {
                    try {
                        return new AbstractMap.SimpleEntry<>(
                                rightPad(element.name(), 20),
                                transformer.apply(element).name());
                    } catch (IllegalArgumentException exception) {
                        return null;
                    }
                })
                .filter(entry -> entry != null)
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (value1, value2) -> value2,
                        LinkedHashMap::new));
    }
}