###
# #%L
# Codenjoy - it's a dojo-like platform from developers to developers.
# %%
# Copyright (C) 2012 - 2022 Codenjoy
# %%
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as
# published by the Free Software Foundation, either version 3 of the
# License, or (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public
# License along with this program.  If not, see
# <http://www.gnu.org/licenses/gpl-3.0.html>.
# #L%
###

require "codenjoy/utils"
require "codenjoy/base_board"

module Codenjoy
  module Client
    module Games
      module Clifford
      end
    end
  end
end

class Codenjoy::Client::Games::Clifford::Board < BaseBoard

  ELEMENTS = {
  
    NONE: ' ',                    // пустое место – по которому может двигаться детектив

    BRICK: '#',                   // стена в которой можно прострелить дырочку слева или справа от детектива
                                   // (в зависимости от того, куда он сейчас смотрит)

    PIT_FILL_1: '1',              // стена со временем зарастает. Когда процес начинается - мы видим таймер
    PIT_FILL_2: '2',
    PIT_FILL_3: '3',
    PIT_FILL_4: '4',

    STONE: '☼',                   // неразрушаемая стена - в ней ничего прострелить не получится

    CRACK_PIT: '*',               // в момент выстрела мы видим процесс так

    // улики
    CLUE_KNIFE: '$',              // нож
    CLUE_GLOVE: '&',              // перчатка
    CLUE_RING: '@',               // кольцо

    // твой детектив
    HERO_DIE: 'Ѡ',                // переживает процесс умирания
    HERO_LADDER: 'Y',             // находится на лестнице
    HERO_LEFT: '◄',               // бежит влево
    HERO_RIGHT: '►',              // бежит вправо
    HERO_FALL: ']',               // падает
    HERO_PIPE: '{',               // ползёт по трубе
    HERO_PIT: '⍃',                // в яме 

    // твой детектив под маскировкой
    HERO_MASK_DIE: 'x',           // переживает процесс умирания
    HERO_MASK_LADDER: '⍬',        // находится на лестнице
    HERO_MASK_LEFT: '⊲',          // бежит влево
    HERO_MASK_RIGHT: '⊳',         // бежит вправо
    HERO_MASK_FALL: '⊅',          // падает
    HERO_MASK_PIPE: '⋜',          // ползёт по трубе
    HERO_MASK_PIT: 'ᐊ',           // в яме

    // детективы других игроков
    OTHER_HERO_DIE: 'Z',          // переживает процесс умирания
    OTHER_HERO_LADDER: 'U',       // находится на лестнице
    OTHER_HERO_LEFT: ')',         // бежит влево
    OTHER_HERO_RIGHT: '(',        // бежит вправо
    OTHER_HERO_FALL: '⊐',         // падает
    OTHER_HERO_PIPE: 'Э',         // ползёт по трубе
    OTHER_HERO_PIT: 'ᗉ',          // в яме

    // детективы других игроков под маскировкой
    OTHER_HERO_MASK_DIE: '⋈',         // переживает процесс умирания
    OTHER_HERO_MASK_LADDER: '⋕',        // находится на лестнице
    OTHER_HERO_MASK_LEFT: '⋊',       // бежит влево
    OTHER_HERO_MASK_RIGHT: '⋉',      // бежит вправо
    OTHER_HERO_MASK_FALL: '⋣',       // падает
    OTHER_HERO_MASK_PIPE: '⊣',        // ползёт по трубе
    OTHER_HERO_MASK_PIT: 'ᗏ',          // в яме

    // вражеские детективы других игроков
    ENEMY_HERO_DIE: 'Ž',          // переживает процесс умирания
    ENEMY_HERO_LADDER: 'Ǔ',       // находится на лестнице
    ENEMY_HERO_LEFT: '❫',         // бежит влево
    ENEMY_HERO_RIGHT: '❪',        // бежит вправо
    ENEMY_HERO_FALL: '⋥',         // падает
    ENEMY_HERO_PIPE: 'Ǯ',         // ползёт по трубе
    ENEMY_HERO_PIT: '⇇',          // в яме

    // вражеские детективы других игроков под маскировкой
    ENEMY_HERO_MASK_DIE: '⧓',         // переживает процесс умирания
    ENEMY_HERO_MASK_LADDER: '≠',        // находится на лестнице
    ENEMY_HERO_MASK_LEFT: '⧒',       // бежит влево
    ENEMY_HERO_MASK_RIGHT: '⧑',      // бежит вправо
    ENEMY_HERO_MASK_FALL: '⌫',        // падает
    ENEMY_HERO_MASK_PIPE: '❵',        // ползёт по трубе
    ENEMY_HERO_MASK_PIT: '⬱',         // в яме

    // боты-воры
    ROBBER_LADDER: 'Q',        // находится на лестнице
    ROBBER_LEFT: '«',          // бежит влево
    ROBBER_RIGHT: '»',         // бежит вправо
    ROBBER_FALL: '‹',          // падает
    ROBBER_PIPE: '<',          // ползёт по трубе
    ROBBER_PIT: '⍇',           // в яме

    // ворота/ключи
    OPENED_DOOR_GOLD: '⍙',     // золотые, открытые
    OPENED_DOOR_SILVER: '⍚',   // серебряные, открытые
    OPENED_DOOR_BRONZE: '⍜',   // бронзовые, открытые

    CLOSED_DOOR_GOLD: '⍍',     // золотые, закрытые
    CLOSED_DOOR_SILVER: '⌺',   // серебряные, закрытые
    CLOSED_DOOR_BRONZE: '⌼',   // бронзовые, закрытые

    KEY_GOLD: '✦',             // золотой ключ
    KEY_SILVER: '✼',           // серебряный ключ
    KEY_BRONZE: '⍟',           // бронзовый ключ

    BULLET: '•',               // пуля
    LADDER: 'H',               // Лестница - по ней можно перемещаться по уровню
    PIPE: '~',                 // Труба - по ней так же можно перемещаться по уровню, но только горизонтально
    BACKWAY: '⊛',              // Черный ход - позволяет скрыто перемещаться в иное место на карте
    MASK_POTION: 'S'           // Маскировочное зелье - наделяют детектива дополнительными способностями
  };

  HERO = [
    :HERO_DIE,
    :HERO_LADDER,
    :HERO_LEFT,
    :HERO_RIGHT,
    :HERO_FALL,
    :HERO_PIPE,
    :HERO_PIT,
    
    :HERO_MASK_DIE,
    :HERO_MASK_LADDER,
    :HERO_MASK_LEFT,
    :HERO_MASK_RIGHT,
    :HERO_MASK_FALL,
    :HERO_MASK_PIPE,
    :HERO_MASK_PIT
  ]

  OTHER_HERO = [
    :OTHER_HERO_DIE,
    :OTHER_HERO_LADDER,
    :OTHER_HERO_LEFT,
    :OTHER_HERO_RIGHT,
    :OTHER_HERO_FALL,
    :OTHER_HERO_PIPE,
    :OTHER_HERO_PIT,
    
    :OTHER_HERO_MASK_DIE,
    :OTHER_HERO_MASK_LADDER,
    :OTHER_HERO_MASK_LEFT,
    :OTHER_HERO_MASK_RIGHT,
    :OTHER_HERO_MASK_FALL,
    :OTHER_HERO_MASK_PIPE,
    :OTHER_HERO_MASK_PIT
  ]
  
  ENEMY_HERO = [
    :ENEMY_HERO_DIE,
    :ENEMY_HERO_LADDER,
    :ENEMY_HERO_LEFT,
    :ENEMY_HERO_RIGHT,
    :ENEMY_HERO_FALL,
    :ENEMY_HERO_PIPE,
    :ENEMY_HERO_PIT,
    
    :ENEMY_HERO_MASK_DIE,
    :ENEMY_HERO_MASK_LADDER,
    :ENEMY_HERO_MASK_LEFT,
    :ENEMY_HERO_MASK_RIGHT,
    :ENEMY_HERO_MASK_FALL,
    :ENEMY_HERO_MASK_PIPE,
    :ENEMY_HERO_MASK_PIT
  ]

  ROBBER = [
    :ROBBER_LADDER,
    :ROBBER_LEFT,
    :ROBBER_RIGHT,
    :ROBBER_FALL,
    :ROBBER_PIPE,
    :ROBBER_PIT
  ]

  PIPE = [
    :PIPE, 
    :HERO_PIPE,
    :HERO_MASK_PIPE,
    :OTHER_HERO_PIPE,
    :OTHER_HERO_MASK_PIPE,
    :ENEMY_HERO_PIPE,
    :ENEMY_HERO_MASK_PIPE
  ]

  LADDER = [
      :LADDER,
      :HERO_LADDER,
      :HERO_MASK_LADDER,
      :OTHER_HERO_LADDER,
      :OTHER_HERO_MASK_LADDER,
      :ENEMY_HERO_LADDER,
      :ENEMY_HERO_MASK_LADDER
  ]

  CLUE = [
    :CLUE_KNIFE,
    :CLUE_GLOVE,
    :CLUE_RING
  ]

  def process(data)
    @raw = data
  end

  def to_s
    [
      "Board:",
      board_to_s,
      "Me at: #{get_me}",
      "Other heroes at: #{get_other_heroes}",
      "Robbers at: #{get_robbers}",
      "Clues at: #{get_clues}"
    ].join("\n")
  end

  def get_me
    find_by_list(HERO.map{ |e| ELEMENTS[e] })
  end

  def get_other_heroes
    find_by_list(OTHER_HERO.map{ |e| ELEMENTS[e] })
  end

  def get_enemy_heroes
    find_by_list(ENEMY_HERO.map{ |e| ELEMENTS[e] })
  end

  def get_robbers
    find_by_list(ROBBER.map{ |e| ELEMENTS[e] })
  end

  def get_clues
    find_by_list(CLUE.map{ |e| ELEMENTS[e] })
  end

  def get_walls
    find_by_list([ELEMENTS[:BRICK], ELEMENTS[:STONE]])
  end

  def get_ladders
    find_by_list(LADDER.map{ |e| ELEMENTS[e] })
  end

  def get_pipes
    find_by_list(PIPE.map{ |e| ELEMENTS[e] })
  end

  def game_over?
    !@raw.index(ELEMENTS[:HERO_DIE]).nil?
  end

  def get_barriers
    [get_enemies, get_other_heroes, get_walls].flatten(1)
  end

  def barrier_at?(x, y)
    return false if Point.new(x, y).out_of?(size)
    !get_barriers.index([x, y]).nil?
  end

  def robber_at?(x, y)
    return false if Point.new(x, y).out_of?(size)
    any_of_at?(x, y, ROBBER.map{ |e| ELEMENTS[e] })
  end

  def other_hero_at?(x, y)
    return false if Point.new(x, y).out_of?(size)
    any_of_at?(x, y, OTHER_HERO.map{ |e| ELEMENTS[e] })
  end

  def enemy_hero_at?(x, y)
    return false if Point.new(x, y).out_of?(size)
    any_of_at?(x, y, ENEMY_HERO.map{ |e| ELEMENTS[e] })
  end

  def wall_at?(x, y)
    return false if Point.new(x, y).out_of?(size)
    any_of_at?(x, y, [ELEMENTS[:BRICK], ELEMENTS[:STONE]])
  end

  def ladder_at?(x, y)
    return false if Point.new(x, y).out_of?(size)
    any_of_at?(x, y, LADDER.map{ |e| ELEMENTS[e] })
  end

  def clue_at?(x, y)
    return false if Point.new(x, y).out_of?(size)
    any_of_at?(x, y, CLUE.map{ |e| ELEMENTS[e] })
  end

  def pipe_at?(x, y)
    return false if Point.new(x, y).out_of?(size)
    any_of_at?(x, y, PIPE.map{ |e| ELEMENTS[e] })
  end
end
