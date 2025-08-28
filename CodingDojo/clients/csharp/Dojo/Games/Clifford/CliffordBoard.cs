﻿/*-
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

using Dojo.Infrastructure;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Dojo.Games.Clifford
{
    public class CliffordBoard : AbstractBoard<CliffordElement>
    {
        public CliffordBoard(string boardString)
            : base(boardString)
        {
        }

        public List<Point> GetBarriers()
        {
            var result = GetRelativeElements(GetWallsElements());
            return result;
        }

        public Point GetHero()
        {
            var result = GetRelativeElements(GetHeroesElements());

            if (result.Count == 0)
            {
                throw new NullReferenceException("Hero element has not been found.");
            }

            return result[0];
        }

        public List<Point> GetOtherHeroes()
        {
            var result = GetRelativeElements(GetOtherHeroesElements());
            return result;
        }

        public List<Point> GetRobbers()
        {
            var result = GetRelativeElements(GetRobbersElements());
            return result;
        }

        public List<Point> GetEnemyHeroes()
        {
            var result = GetRelativeElements(GetEnemyHeroElements());
            return result;
        }

        internal List<Point> GetLadders()
        {
            var result = GetRelativeElements(GetLaddersElements());
            return result;
        }

        public List<Point> GetPipes()
        {
            var result = GetRelativeElements(GetPipesElements());
            return result;
        }

        public List<Point> GetPits()
        {
            var result = GetRelativeElements(GetPitsElements());
            return result;
        }

        public List<Point> GetClues()
        {
            var result = GetRelativeElements(GetCluesElements());
            return result;
        }

        public List<Point> GetBackways()
        {
            var result = GetRelativeElements(GetBackwaysElements());
            return result;
        }

        public List<Point> GetPotions()
        {
            var result = GetRelativeElements(GetPotionsElements());
            return result;
        }

        public List<Point> GetDoors()
        {
            var result = GetRelativeElements(GetDoorsElements());
            return result;
        }

        public List<Point> GetOpenDoors()
        {
            var result = GetRelativeElements(GetOpenDoorsElements());
            return result;
        }

        public List<Point> GetCloseDoors()
        {
            var result = GetRelativeElements(GetCloseDoorsElements());
            return result;
        }

        public List<Point> GetKeys()
        {
            var result = GetRelativeElements(GetKeysElements());
            return result;
        }


        public bool IsGameOver
        {
            get
            {
                return BoardString.Contains((char)CliffordElement.HERO_DIE)
                    || BoardString.Contains((char)CliffordElement.HERO_MASK_DIE);
            }
        }

        internal bool IsBarrierAt(Point point)
        {
            return IsAnyOfAt(point, GetWallsElements().ToArray());
        }

        internal bool IsRobberAt(Point point)
        {
            return IsAnyOfAt(point, GetRobbersElements().ToArray());
        }

        internal bool IsEnemyAt(Point point)
        {
            return IsAnyOfAt(point, GetEnemyHeroesElements().ToArray());
        }

        internal bool IsOtherHeroAt(Point point)
        {
            return IsAnyOfAt(point, GetOtherHeroesElements().ToArray());
        }

        internal bool IsLadderAt(Point point)
        {
            return IsAnyOfAt(point, GetLaddersElements().ToArray());
        }

        internal bool IsPipeAt(Point point)
        {
            return IsAnyOfAt(point, GetPipesElements().ToArray());
        }

        internal static CliffordElement MapHeroToEnemy(CliffordElement source)
        {
            switch (source)
            {
                case CliffordElement.HERO_DIE:
                    return CliffordElement.ENEMY_HERO_DIE;
                case CliffordElement.HERO_LADDER:
                    return CliffordElement.ENEMY_HERO_LADDER;
                case CliffordElement.HERO_LEFT:
                    return CliffordElement.ENEMY_HERO_LEFT;
                case CliffordElement.HERO_RIGHT:
                    return CliffordElement.ENEMY_HERO_RIGHT;
                case CliffordElement.HERO_FALL:
                    return CliffordElement.ENEMY_HERO_FALL;
                case CliffordElement.HERO_PIPE:
                    return CliffordElement.ENEMY_HERO_PIPE;
                case CliffordElement.HERO_PIT:
                    return CliffordElement.ENEMY_HERO_PIT;
                case CliffordElement.HERO_MASK_DIE:
                    return CliffordElement.ENEMY_HERO_MASK_DIE;
                case CliffordElement.HERO_MASK_LADDER:
                    return CliffordElement.ENEMY_HERO_MASK_LADDER;
                case CliffordElement.HERO_MASK_LEFT:
                    return CliffordElement.ENEMY_HERO_MASK_LEFT;
                case CliffordElement.HERO_MASK_RIGHT:
                    return CliffordElement.ENEMY_HERO_MASK_RIGHT;
                case CliffordElement.HERO_MASK_FALL:
                    return CliffordElement.ENEMY_HERO_MASK_FALL;
                case CliffordElement.HERO_MASK_PIPE:
                    return CliffordElement.ENEMY_HERO_MASK_PIPE;
                case CliffordElement.HERO_MASK_PIT:
                    return CliffordElement.ENEMY_HERO_PIT;
            }

            throw new ArgumentException("Unknown state for work with it.");
        }

        internal static CliffordElement MapHeroToOtherHero(CliffordElement source)
        {
            switch (source)
            {
                case CliffordElement.HERO_DIE:
                    return CliffordElement.OTHER_HERO_DIE;
                case CliffordElement.HERO_LADDER:
                    return CliffordElement.OTHER_HERO_LADDER;
                case CliffordElement.HERO_LEFT:
                    return CliffordElement.OTHER_HERO_LEFT;
                case CliffordElement.HERO_RIGHT:
                    return CliffordElement.OTHER_HERO_RIGHT;
                case CliffordElement.HERO_FALL:
                    return CliffordElement.OTHER_HERO_FALL;
                case CliffordElement.HERO_PIPE:
                    return CliffordElement.OTHER_HERO_PIPE;
                case CliffordElement.HERO_PIT:
                    return CliffordElement.OTHER_HERO_PIT;
                case CliffordElement.HERO_MASK_DIE:
                    return CliffordElement.OTHER_HERO_MASK_DIE;
                case CliffordElement.HERO_MASK_LADDER:
                    return CliffordElement.OTHER_HERO_MASK_LADDER;
                case CliffordElement.HERO_MASK_LEFT:
                    return CliffordElement.OTHER_HERO_MASK_LEFT;
                case CliffordElement.HERO_MASK_RIGHT:
                    return CliffordElement.OTHER_HERO_MASK_RIGHT;
                case CliffordElement.HERO_MASK_FALL:
                    return CliffordElement.OTHER_HERO_MASK_FALL;
                case CliffordElement.HERO_MASK_PIPE:
                    return CliffordElement.OTHER_HERO_MASK_PIPE;
                case CliffordElement.HERO_MASK_PIT:
                    return CliffordElement.OTHER_HERO_MASK_PIT;
            }

            throw new ArgumentException("Unknown state for work with it.");
        }

        internal static CliffordElement Mask(CliffordElement source)
        {
            switch (source)
            {
                case CliffordElement.HERO_DIE: 
                    return CliffordElement.HERO_MASK_DIE;
                case CliffordElement.HERO_LADDER: 
                    return CliffordElement.HERO_MASK_LADDER;
                case CliffordElement.HERO_LEFT: 
                    return CliffordElement.HERO_MASK_LEFT;
                case CliffordElement.HERO_RIGHT: 
                    return CliffordElement.HERO_MASK_RIGHT;
                case CliffordElement.HERO_FALL: 
                    return CliffordElement.HERO_MASK_FALL;
                case CliffordElement.HERO_PIPE: 
                    return CliffordElement.HERO_MASK_PIPE;
                case CliffordElement.HERO_PIT: 
                    return CliffordElement.HERO_MASK_PIT;

                case CliffordElement.OTHER_HERO_DIE: 
                    return CliffordElement.OTHER_HERO_MASK_DIE;
                case CliffordElement.OTHER_HERO_LADDER: 
                    return CliffordElement.OTHER_HERO_MASK_LADDER;
                case CliffordElement.OTHER_HERO_LEFT: 
                    return CliffordElement.OTHER_HERO_MASK_LEFT;
                case CliffordElement.OTHER_HERO_RIGHT: 
                    return CliffordElement.OTHER_HERO_MASK_RIGHT;
                case CliffordElement.OTHER_HERO_FALL: 
                    return CliffordElement.OTHER_HERO_MASK_FALL;
                case CliffordElement.OTHER_HERO_PIPE: 
                    return CliffordElement.OTHER_HERO_MASK_PIPE;
                case CliffordElement.OTHER_HERO_PIT: 
                    return CliffordElement.OTHER_HERO_MASK_PIT;

                case CliffordElement.ENEMY_HERO_DIE: 
                    return CliffordElement.ENEMY_HERO_MASK_DIE;
                case CliffordElement.ENEMY_HERO_LADDER: 
                    return CliffordElement.ENEMY_HERO_MASK_LADDER;
                case CliffordElement.ENEMY_HERO_LEFT: 
                    return CliffordElement.ENEMY_HERO_MASK_LEFT;
                case CliffordElement.ENEMY_HERO_RIGHT: 
                    return CliffordElement.ENEMY_HERO_MASK_RIGHT;
                case CliffordElement.ENEMY_HERO_FALL: 
                    return CliffordElement.ENEMY_HERO_MASK_FALL;
                case CliffordElement.ENEMY_HERO_PIPE: 
                    return CliffordElement.ENEMY_HERO_MASK_PIPE;
                case CliffordElement.ENEMY_HERO_PIT: 
                    return CliffordElement.ENEMY_HERO_MASK_PIT;
            }

            throw new ArgumentException("Unknown state for masked it.");
        }

        internal static List<CliffordElement> GetEnemyHeroElements()
        {
            return new List<CliffordElement>
            {
                CliffordElement.ENEMY_HERO_DIE,
                CliffordElement.ENEMY_HERO_LADDER,
                CliffordElement.ENEMY_HERO_LEFT,
                CliffordElement.ENEMY_HERO_RIGHT,
                CliffordElement.ENEMY_HERO_FALL,
                CliffordElement.ENEMY_HERO_PIPE,
                CliffordElement.ENEMY_HERO_PIT,
                CliffordElement.ENEMY_HERO_MASK_DIE,
                CliffordElement.ENEMY_HERO_MASK_LADDER,
                CliffordElement.ENEMY_HERO_MASK_LEFT,
                CliffordElement.ENEMY_HERO_MASK_RIGHT,
                CliffordElement.ENEMY_HERO_MASK_FALL,
                CliffordElement.ENEMY_HERO_MASK_PIPE,
                CliffordElement.ENEMY_HERO_MASK_PIT,
            };
        }

        internal static List<CliffordElement> GetPipesElements()
        {
            return new List<CliffordElement>
            {
                CliffordElement.PIPE,
                CliffordElement.HERO_PIPE,
                CliffordElement.HERO_MASK_PIPE,
                CliffordElement.OTHER_HERO_PIPE,
                CliffordElement.OTHER_HERO_MASK_PIPE,
                CliffordElement.ENEMY_HERO_PIPE,
                CliffordElement.ENEMY_HERO_MASK_PIPE,
            };
        }

        internal static List<CliffordElement> GetBackwaysElements()
        {
            return new List<CliffordElement>
            {
                CliffordElement.BACKWAY
            };
        }

        internal static List<CliffordElement> GetPotionsElements()
        {
            return new List<CliffordElement>
            {
                CliffordElement.MASK_POTION
            };
        }

        internal static List<CliffordElement> GetDoorsElements()
        {
            return new List<CliffordElement>
            {
                CliffordElement.OPENED_DOOR_GOLD,
                CliffordElement.OPENED_DOOR_SILVER,
                CliffordElement.OPENED_DOOR_BRONZE,
                CliffordElement.CLOSED_DOOR_GOLD,
                CliffordElement.CLOSED_DOOR_SILVER,
                CliffordElement.CLOSED_DOOR_BRONZE
            };
        }

        internal static List<CliffordElement> GetOpenDoorsElements()
        {
            return new List<CliffordElement>
            {
                CliffordElement.OPENED_DOOR_GOLD,
                CliffordElement.OPENED_DOOR_SILVER,
                CliffordElement.OPENED_DOOR_BRONZE
            };
        }

        internal static List<CliffordElement> GetCloseDoorsElements()
        {
            return new List<CliffordElement>
            {
                CliffordElement.CLOSED_DOOR_GOLD,
                CliffordElement.CLOSED_DOOR_SILVER,
                CliffordElement.CLOSED_DOOR_BRONZE
            };
        }

        internal static List<CliffordElement> GetKeysElements()
        {
            return new List<CliffordElement>
            {
                CliffordElement.KEY_GOLD,
                CliffordElement.KEY_SILVER,
                CliffordElement.KEY_BRONZE
            };
        }

        internal static List<CliffordElement> GetPitsElements()
        {
            return new List<CliffordElement>
            {
                CliffordElement.CRACK_PIT,
                CliffordElement.PIT_FILL_1,
                CliffordElement.PIT_FILL_2,
                CliffordElement.PIT_FILL_3,
                CliffordElement.PIT_FILL_4
            };
        }

        internal static List<CliffordElement> GetCluesElements()
        {
            return new List<CliffordElement>
            {
                CliffordElement.CLUE_KNIFE,
                CliffordElement.CLUE_GLOVE,
                CliffordElement.CLUE_RING
            };
        }

        internal static List<CliffordElement> GetLaddersElements()
        {
            return new List<CliffordElement>
            {
                CliffordElement.LADDER,
                CliffordElement.HERO_LADDER,
                CliffordElement.HERO_MASK_LADDER,
                CliffordElement.OTHER_HERO_LADDER,
                CliffordElement.OTHER_HERO_MASK_LADDER,
                CliffordElement.ROBBER_LADDER
            };
        }

        internal static List<CliffordElement> GetWallsElements()
        {
            return new List<CliffordElement>
            {
                CliffordElement.BRICK,
                CliffordElement.STONE
            };
        }

        internal static List<CliffordElement> GetHeroesElements()
        {
            return new List<CliffordElement>
            {
                CliffordElement.HERO_DIE,
                CliffordElement.HERO_LADDER,
                CliffordElement.HERO_LEFT,
                CliffordElement.HERO_RIGHT,
                CliffordElement.HERO_FALL,
                CliffordElement.HERO_PIPE,
                CliffordElement.HERO_PIT,
                CliffordElement.HERO_MASK_DIE,
                CliffordElement.HERO_MASK_LADDER,
                CliffordElement.HERO_MASK_LEFT,
                CliffordElement.HERO_MASK_RIGHT,
                CliffordElement.HERO_MASK_FALL,
                CliffordElement.HERO_MASK_PIPE,
                CliffordElement.HERO_MASK_PIT
            };
        }

        internal static List<CliffordElement> GetEnemyHeroesElements()
        {
            return new List<CliffordElement>
            {
                CliffordElement.ENEMY_HERO_DIE,
                CliffordElement.ENEMY_HERO_LADDER,
                CliffordElement.ENEMY_HERO_LEFT,
                CliffordElement.ENEMY_HERO_RIGHT,
                CliffordElement.ENEMY_HERO_FALL,
                CliffordElement.ENEMY_HERO_PIPE,
                CliffordElement.ENEMY_HERO_PIT,
                CliffordElement.ENEMY_HERO_MASK_DIE,
                CliffordElement.ENEMY_HERO_MASK_LADDER,
                CliffordElement.ENEMY_HERO_MASK_LEFT,
                CliffordElement.ENEMY_HERO_MASK_RIGHT,
                CliffordElement.ENEMY_HERO_MASK_FALL,
                CliffordElement.ENEMY_HERO_MASK_PIPE,
                CliffordElement.ENEMY_HERO_MASK_PIT
            };
        }

        internal static List<CliffordElement> GetOtherHeroesElements()
        {
            return new List<CliffordElement>
            {
                CliffordElement.OTHER_HERO_DIE,
                CliffordElement.OTHER_HERO_LADDER,
                CliffordElement.OTHER_HERO_LEFT,
                CliffordElement.OTHER_HERO_RIGHT,
                CliffordElement.OTHER_HERO_FALL,
                CliffordElement.OTHER_HERO_PIPE,
                CliffordElement.OTHER_HERO_PIT,
                CliffordElement.OTHER_HERO_MASK_DIE,
                CliffordElement.OTHER_HERO_MASK_LADDER,
                CliffordElement.OTHER_HERO_MASK_LEFT,
                CliffordElement.OTHER_HERO_MASK_RIGHT,
                CliffordElement.OTHER_HERO_MASK_FALL,
                CliffordElement.OTHER_HERO_MASK_PIPE,
                CliffordElement.OTHER_HERO_MASK_PIT
            };
        }

        internal static List<CliffordElement> GetRobbersElements()
        {
            return new List<CliffordElement>
            {
                CliffordElement.ROBBER_LADDER,
                CliffordElement.ROBBER_LEFT,
                CliffordElement.ROBBER_RIGHT,
                CliffordElement.ROBBER_FALL,
                CliffordElement.ROBBER_PIPE,
                CliffordElement.ROBBER_PIT
            };
        }

        public override string ToString()
        {
            return string.Format("{0}\n" +
                     "Hero at: {1}\n" +
                     "Other heroes at: {2}\n" +
                     "Enemy heroes at: {3}\n" +
                     "Robbers at: {4}\n" +
                     "Mask potions at: {5}\n" +
                     "Keys at: {6}",
                     BoardAsString(),
                     GetHero(),
                     GetOtherHeroes().ListAsString(),
                     GetEnemyHeroes().ListAsString(),
                     GetRobbers().ListAsString(),
                     GetPotions().ListAsString(),
                     GetKeys().ListAsString());
        }
    }
}
