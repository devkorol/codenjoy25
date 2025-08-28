package com.codenjoy.dojo.sample.model;

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


import org.junit.Test;

public class MultiplayerTest extends AbstractGameTest {

    @Test
    public void severalHeroesCanAppearOnTheMap() {
        // given
        givenFl("☼☼☼☼☼☼\n" +
                "☼☺ ☺$☼\n" +
                "☼    ☼\n" +
                "☼ ☺  ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n");

        // when then
        assertF("☼☼☼☼☼☼\n" +
                "☼☺ ☻$☼\n" +
                "☼    ☼\n" +
                "☼ ☻  ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼\n" +
                "☼☻ ☺$☼\n" +
                "☼    ☼\n" +
                "☼ ☻  ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n", 1);

        assertF("☼☼☼☼☼☼\n" +
                "☼☻ ☻$☼\n" +
                "☼    ☼\n" +
                "☼ ☺  ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n", 2);

        assertScores("");
    }

    @Test
    public void eachHeroCanBeControlledIndependentlyInOneTickOfTheGame() {
        // given
        givenFl("☼☼☼☼☼☼\n" +
                "☼☺ ☺ ☼\n" +
                "☼    ☼\n" +
                "☼ ☺  ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n");

        // when
        hero(0).bomb();
        hero(0).down();

        hero(1).down();

        hero(2).right();

        tick();

        // then
        assertF("☼☼☼☼☼☼\n" +
                "☼x   ☼\n" +
                "☼☺ ☻ ☼\n" +
                "☼  ☻ ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n", 0);

        assertScores("");
    }

    @Test
    public void heroesCanBeRemovedFromTheGame() {
        // given
        givenFl("☼☼☼☼☼☼\n" +
                "☼☺ ☺ ☼\n" +
                "☼    ☼\n" +
                "☼ ☺  ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n");

        // when
        game(1).close();

        tick();

        // then
        assertF("☼☼☼☼☼☼\n" +
                "☼☺   ☼\n" +
                "☼    ☼\n" +
                "☼ ☻  ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n", 0);

        verifyAllEvents("listener(1) => [HERO_DIED]\n");

        assertScores("");
    }

    @Test
    public void heroesCanBeRestartedInTheGame() {
        // given
        givenFl("☼☼☼☼☼☼\n" +
                "☼☺ ☺ ☼\n" +
                "☼    ☼\n" +
                "☼ ☺  ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n");

        // when
        dice(4, 1);
        game(1).newGame();
        tick();

        // then
        assertF("☼☼☼☼☼☼\n" +
                "☼☺   ☼\n" +
                "☼    ☼\n" +
                "☼ ☻  ☼\n" +
                "☼   ☻☼\n" +
                "☼☼☼☼☼☼\n", 0);

        assertScores("");
    }

    @Test
    public void anyOfTheHeroesCanExplodeOnABomb_caseSameTeam() {
        // given
        givenFl("☼☼☼☼☼☼\n" +
                "☼☺ ☺ ☼\n" +
                "☼    ☼\n" +
                "☼ ☺  ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n");

        player(0).inTeam(0);
        player(1).inTeam(0);
        player(2).inTeam(0);

        hero(0).down();
        hero(0).bomb();

        hero(1).left();

        tick();

        assertF("☼☼☼☼☼☼\n" +
                "☼x☻  ☼\n" +
                "☼☺   ☼\n" +
                "☼ ☻  ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼\n" +
                "☼x☺  ☼\n" +
                "☼☻   ☼\n" +
                "☼ ☻  ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n", 1);

        assertF("☼☼☼☼☼☼\n" +
                "☼x☻  ☼\n" +
                "☼☻   ☼\n" +
                "☼ ☺  ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n", 2);

        // when
        hero(1).left();
        tick();

        // then
        assertF("☼☼☼☼☼☼\n" +
                "☼Y   ☼\n" +
                "☼☺   ☼\n" +
                "☼ ☻  ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼\n" +
                "☼X   ☼\n" +
                "☼☻   ☼\n" +
                "☼ ☻  ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n", 1);

        assertF("☼☼☼☼☼☼\n" +
                "☼Y   ☼\n" +
                "☼☻   ☼\n" +
                "☼ ☺  ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n", 2);

        verifyAllEvents(
                "listener(0) => [KILL_OTHER_HERO]\n" +
                "listener(1) => [HERO_DIED]\n");

        assertScores("hero(0)=5");

        assertHeroStatus(
                "active:\n" +
                "hero(0)=true\n" +
                "hero(1)=true\n" +
                "hero(2)=true\n" +
                "alive\n" +
                "hero(0)=true\n" +
                "hero(1)=false\n" +
                "hero(2)=true");

        dice(4, 1);
        tick(); // new game for hero1

        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼☺   ☼\n" +
                "☼ ☻  ☼\n" +
                "☼   ☻☼\n" +
                "☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼☻   ☼\n" +
                "☼ ☻  ☼\n" +
                "☼   ☺☼\n" +
                "☼☼☼☼☼☼\n", 1);

        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼☻   ☼\n" +
                "☼ ☺  ☼\n" +
                "☼   ☻☼\n" +
                "☼☼☼☼☼☼\n", 2);

        assertScores("hero(0)=5");
    }

    @Test
    public void anyOfTheHeroesCanExplodeOnABomb_caseOtherTeam() {
        // given
        givenFl("☼☼☼☼☼☼\n" +
                "☼☺ ☺ ☼\n" +
                "☼    ☼\n" +
                "☼ ☺  ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n");
        
        player(0).inTeam(0);
        player(1).inTeam(1);
        player(2).inTeam(0);

        hero(0).down();
        hero(0).bomb();

        hero(1).left();

        tick();

        assertF("☼☼☼☼☼☼\n" +
                "☼x♥  ☼\n" +
                "☼☺   ☼\n" +
                "☼ ☻  ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼\n" +
                "☼x☺  ☼\n" +
                "☼♥   ☼\n" +
                "☼ ♥  ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n", 1);

        assertF("☼☼☼☼☼☼\n" +
                "☼x♥  ☼\n" +
                "☼☻   ☼\n" +
                "☼ ☺  ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n", 2);

        // when
        hero(1).left();
        tick();

        // then
        assertF("☼☼☼☼☼☼\n" +
                "☼Z   ☼\n" +
                "☼☺   ☼\n" +
                "☼ ☻  ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼\n" +
                "☼X   ☼\n" +
                "☼♥   ☼\n" +
                "☼ ♥  ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n", 1);

        assertF("☼☼☼☼☼☼\n" +
                "☼Z   ☼\n" +
                "☼☻   ☼\n" +
                "☼ ☺  ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n", 2);

        verifyAllEvents(
                "listener(0) => [KILL_ENEMY_HERO]\n" +
                "listener(1) => [HERO_DIED]\n");

        assertScores("hero(0)=10");

        assertHeroStatus(
                "active:\n" +
                        "hero(0)=true\n" +
                        "hero(1)=true\n" +
                        "hero(2)=true\n" +
                        "alive\n" +
                        "hero(0)=true\n" +
                        "hero(1)=false\n" +
                        "hero(2)=true");

        dice(4, 1);
        tick();

        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼☺   ☼\n" +
                "☼ ☻  ☼\n" +
                "☼   ♥☼\n" +
                "☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼♥   ☼\n" +
                "☼ ♥  ☼\n" +
                "☼   ☺☼\n" +
                "☼☼☼☼☼☼\n", 1);

        assertF("☼☼☼☼☼☼\n" +
                "☼    ☼\n" +
                "☼☻   ☼\n" +
                "☼ ☺  ☼\n" +
                "☼   ♥☼\n" +
                "☼☼☼☼☼☼\n", 2);

        assertScores("hero(0)=10");
    }

    @Test
    public void anyOfTheHeroesCanPickUpGold() {
        // given
        givenFl("☼☼☼☼☼☼\n" +
                "☼☺ ☺$☼\n" +
                "☼    ☼\n" +
                "☼ ☺  ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n");

        // when
        hero(1).right();
        dice(1, 2);
        tick();

        // then
        assertF("☼☼☼☼☼☼\n" +
                "☼☺  ☻☼\n" +
                "☼    ☼\n" +
                "☼$☻  ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n", 0);

        verifyAllEvents(
                "listener(1) => [GET_GOLD]\n");

        assertScores("hero(1)=30");
    }

    @Test
    public void heroesCannotWalkThroughOneAnother() {
        // given
        givenFl("☼☼☼☼☼☼\n" +
                "☼☺ ☺ ☼\n" +
                "☼    ☼\n" +
                "☼ ☺  ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n");

        // when
        hero(0).right();
        hero(1).left();

        tick();

        // then
        assertF("☼☼☼☼☼☼\n" +
                "☼ ☺☻ ☼\n" +
                "☼    ☼\n" +
                "☼ ☻  ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n", 0);

        assertScores("");
    }

    @Test
    public void playersCanBeDividedIntoTeams_scoresForKillingEnemy() {
        // given
        givenFl("☼☼☼☼☼☼\n" +
                "☼☺ ☺ ☼\n" +
                "☼    ☼\n" +
                "☼☺ ☺ ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n");

        // hunters and preys in different teams
        player(0).inTeam(1); // hunter1
        player(1).inTeam(1); // prey2
        player(2).inTeam(2); // prey1
        player(3).inTeam(2); // hunter2

        hero(0).down();
        hero(3).up();
        tick();

        hero(0).bomb();
        hero(0).up();

        hero(3).bomb();
        hero(3).down();
        tick();

        assertF("☼☼☼☼☼☼\n" +
                "☼☺ ☻ ☼\n" +
                "☼x x ☼\n" +
                "☼♥ ♥ ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼\n" +
                "☼☻ ☺ ☼\n" +
                "☼x x ☼\n" +
                "☼♥ ♥ ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n", 1);

        assertF("☼☼☼☼☼☼\n" +
                "☼♥ ♥ ☼\n" +
                "☼x x ☼\n" +
                "☼☺ ☻ ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n", 2);

        assertF("☼☼☼☼☼☼\n" +
                "☼♥ ♥ ☼\n" +
                "☼x x ☼\n" +
                "☼☻ ☺ ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n", 3);

        // when
        hero(1).down();
        hero(2).up();
        tick();

        assertF("☼☼☼☼☼☼\n" +
                "☼☺   ☼\n" +
                "☼Z Y ☼\n" +
                "☼  ♥ ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼\n" +
                "☼☻   ☼\n" +
                "☼Z X ☼\n" +
                "☼  ♥ ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n", 1);

        assertF("☼☼☼☼☼☼\n" +
                "☼♥   ☼\n" +
                "☼X Z ☼\n" +
                "☼  ☻ ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n", 2);

        assertF("☼☼☼☼☼☼\n" +
                "☼♥   ☼\n" +
                "☼Y Z ☼\n" +
                "☼  ☺ ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n", 3);

        verifyAllEvents(
                "listener(0) => [KILL_ENEMY_HERO]\n" +
                "listener(1) => [HERO_DIED]\n" +
                "listener(2) => [HERO_DIED]\n" +
                "listener(3) => [KILL_ENEMY_HERO]\n");

        assertScores(
                "hero(0)=10\n" +
                "hero(3)=10");
    }

    @Test
    public void playersCanBeDividedIntoTeams_scoresForKillingOtherHero() {
        // given
        givenFl("☼☼☼☼☼☼\n" +
                "☼☺ ☺ ☼\n" +
                "☼    ☼\n" +
                "☼☺ ☺ ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n");

        // hunters and preys in the same team
        player(0).inTeam(1); // hunter1
        player(1).inTeam(2); // prey2
        player(2).inTeam(1); // prey1
        player(3).inTeam(2); // hunter2

        hero(0).down();
        hero(3).up();
        tick();

        hero(0).bomb();
        hero(0).up();

        hero(3).bomb();
        hero(3).down();
        tick();

        assertF("☼☼☼☼☼☼\n" +
                "☼☺ ♥ ☼\n" +
                "☼x x ☼\n" +
                "☼☻ ♥ ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼\n" +
                "☼♥ ☺ ☼\n" +
                "☼x x ☼\n" +
                "☼♥ ☻ ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n", 1);

        assertF("☼☼☼☼☼☼\n" +
                "☼☻ ♥ ☼\n" +
                "☼x x ☼\n" +
                "☼☺ ♥ ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n", 2);

        assertF("☼☼☼☼☼☼\n" +
                "☼♥ ☻ ☼\n" +
                "☼x x ☼\n" +
                "☼♥ ☺ ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n", 3);

        // when
        hero(1).down();
        hero(2).up();
        tick();

        assertF("☼☼☼☼☼☼\n" +
                "☼☺   ☼\n" +
                "☼Y Z ☼\n" +
                "☼  ♥ ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼☼\n" +
                "☼♥   ☼\n" +
                "☼Z X ☼\n" +
                "☼  ☻ ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n", 1);

        assertF("☼☼☼☼☼☼\n" +
                "☼☻   ☼\n" +
                "☼X Z ☼\n" +
                "☼  ♥ ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n", 2);

        assertF("☼☼☼☼☼☼\n" +
                "☼♥   ☼\n" +
                "☼Z Y ☼\n" +
                "☼  ☺ ☼\n" +
                "☼    ☼\n" +
                "☼☼☼☼☼☼\n", 3);

        verifyAllEvents(
                "listener(0) => [KILL_OTHER_HERO]\n" +
                "listener(1) => [HERO_DIED]\n" +
                "listener(2) => [HERO_DIED]\n" +
                "listener(3) => [KILL_OTHER_HERO]\n");

        assertScores(
                "hero(0)=5\n" +
                "hero(3)=5");
    }
}