**[Important] This works only for OS Windows**

Unzip [this archive](https://epa.ms/DME34) into folder `d:\pseudo-client\` (or use other drive). Please make sure there is no space character ` ` in the folder name - use `-` for word separator, or just use one word folder `d:\client\`.

inside the folder you can see these stuff:
    
    ```
    /jdk8/
    /maven/
    /rules/
    /sources/
    /0-settings.bat
    /1-build-engine.bat
    /2-build-client.bat
    /3-run-client.bat
    /README.md
    ```
    
Follow the instructions below to understand how to use it. 


## Registration

1. Go to https://codenjoy.com/ or https://dojorena.io

2. Press `SignUp`

3. Fill in registration form

4. Select `MollyMage` game

5. Then press `Submit` button

## Setup your client

1. Please copy `board url` from your browser,  it will look like 

    ```
    https://codenjoy.com/codenjoy-contest/board/player/playerId?code=12345678901234567890
    ```

2. Edit `0-settings.bat` file as text

3. Set `mollymage` game at first line *(it is set by default, but please check)*

    ```
    set GAME_TO_RUN=mollymage
    ```

4. Set `board url` at second line 

    ```
    set SERVER_URL=https://codenjoy.com/codenjoy-contest/board/player/playerId?code=12345678901234567890
    ```

5. Save `0-settings.bat` file and close

## Run your client

1. Run `3-run-client.bat` script

2. Wait till board will come. It will be your command post.

    ```
    Board:
    ☼☼☼☼☼☼☼
    ☼ &  #☼
    ☼ ☼#☼ ☼
    ☼     ☼
    ☼☺☼#☼ ☼
    ☼     ☼
    ☼☼☼☼☼☼☼
    Answer: UP
    ```
   
## Write your bot logic
	
1. Open `\rules\main.rules` file as text

2. Now you can write your script to control the hero on the map. How? It's pretty simple. 

3. If you change anything, just save the file and your hero will follow the new directions. Look carefully at this file, this is your field for pseudo coding. Your control panel.

4. You can make a mistake during "programming" your hero. Don’t worry, we will let you know exactly where and why it happened - check command post.

    ```
    [ERROR] Pattern is not valid: '???wrong?☺????' at C:\pseudo-client-portable\rules\main.rule:91
    ``` 

##	How to write your rules?

1. There are **SEVERAL TYPES** of command which you can use, check `*.rules` file.

2. Indicate what the space around the hero should look like, and then indicate the direction of movement of the hero. 

    * Your hero is indicated by the symbol `☺` 

    * The space (mask/pattern/frame) around the hero should be **SQUARE** forms of any size, it does not matter where the hero is located (in the center of the mask or on its border)

      In this example we are interested in what is in 1 cell around the hero

       ```
        ☼  
        ☼☺☼
        ☼  
       ```

      In this example we are interested in what is in 2 cells around the hero

        ```
        ☼☼ ☼#
        ☼☼   
        ☼☼☺☼#
        ☼☼   
        ☼☼☼☼☼
        ```

      In this example we are interested in what is in the upper left corner of the hero

        ```
         ☼#
       
        ☺☼#
        ```

    * The next line after the mask indicate the direction of hero's movement - one of the commands: `LEFT`, `RIGHT`, `UP`, `DOWN` or the `ACT` command - put the potion

      If the picture around the hero looks like this mask, he will run up

      ```
      ☼  
      ☼☺☼
      ☼  
      UP
      ```

      And in this case (if nothing prevents the hero) he will go down

      ```
      ☼ ☼
       ☺ 
      ☼ ☼
      DOWN
      ```
   
3. Left potion will explode after 5 ticks (1 tick - 1 second) and the blast wave will destroy everyone who touches. 

4. You can also specify not 1 action, but several separated by commas. This will mean that when a given mask is triggered, the specified tick-by-tick commands will be processed. For example, in the example below, if the hero sees a potion to his left, then he first goes to the right, and then hides up from the blast wave.

    ```
    ☼☼☼☼
    ☼ ☼ 
    ☼5☺ 
    ☼ ☼ 
    ☼☼☼☼
    RIGHT,UP
    ```

5. It often happens that you don’t know what symbol will be at this particular point, and you want to generalize. To do this, you have a symbol that means any possible symbol `?` at this point. The past example will be more universal, if described as follows.

    ```
    ????
    ??? 
    ?5☺ 
    ????
    ????
    RIGHT,UP

    ????
    ????
    ?5☺ 
    ??? 
    ????
    RIGHT,DOWN

    ????
    ?? ?
    ?5☺?
    ????
    ????
    UP

    ????
    ????
    ?5☺?
    ?? ?
    ????
    DOWN
    ```

6. It is **IMPORTANT** to understand that the first matched mask will work out in the list of commands and further verification will not be carried out.

7. The legend of possible symbols

    ```
    /// the potions
    POTION_TIMER_5('5'),    // After Molly set the potion, the timer starts (5 ticks).
    POTION_TIMER_4('4'),    // This will blow up after 4 ticks.
    POTION_TIMER_3('3'),    // This after 3...
    POTION_TIMER_2('2'),    // Two..
    POTION_TIMER_1('1'),    // One.
    BLAST('҉'),             // Boom! this is what is potion does,
                            // everything that is destroyable got destroyed.

    /// walls
    WALL('☼'),              // Indestructible wall - it will not fall from potion.
    TREASURE_BOX('#'),      // this is a treasure box, it opens with an explosion.
    TREASURE_BOX_OPENING('H'), // this is like a treasure box opens looks
                            // like, it will disappear on next move.
                            // if it's you did it - you'll get score
                            // points. Perhaps a prize will appear.

    /// soulless creatures
    GHOST('&'),             // This guys runs over the board randomly
                            // and gets in the way all the time.
                            // If it will touch Molly - she will die.
                            // You'd better kill this piece of ... soul,
                            // you'll get score points for it.

    GHOST_DEAD('x'),        // This is ghost corpse.

    /// perks
    POTION_BLAST_RADIUS_INCREASE('+'), // Potion blast radius increase.
                                       // Applicable only to new potions.
                                       // The perk is temporary.

    POTION_COUNT_INCREASE('c'), // Increase available potions count.
                                // Number of extra potions can be set 
                                // in settings. Temporary.

    POTION_REMOTE_CONTROL('r'), // Potion blast not by timer but by second act.
                                // Number of RC triggers is limited and c
                                // an be set in settings.

    POTION_IMMUNE('i'),         // Do not die after potion blast
                                // (own potion and others as well). Temporary.

    POISON_THROWER('T'),        // Hero can shoot by poison cloud
                                // Using: Direction,ACT(1). Example: RIGHT,ACT(1)
                                // Temporary.
                                    
    POTION_EXPLODER('A'),       // Hero can explode all potions on the field
                                // Using: ACT(2).
                                // Temporary.
                                    

    /// a void
    NONE(' '),              // This is the only place where you can move your Molly.

    /// your Molly
    HERO('☺'),              // This is what she usually looks like.
    HERO_POTION('☻'),       // This is if she is sitting on own potion.
    HERO_DEAD('Ѡ'),         // Oops, your Molly is dead (don't worry,
                            // she will appear somewhere in next move).
                            // You're getting penalty points for each death.

    /// other players heroes
    OTHER_HERO('♥'),        // This is what other heroes looks like.
    OTHER_HERO_POTION('♠'), // This is if player is sitting on own potion.
    OTHER_HERO_DEAD('♣');   // Enemy corpse (it will disappear shortly,
                            // right on the next move).
                            // If you've done it you'll get score points.
    ```
   
8. There are occasions when it is necessary to combine several characters into one group. To do this, use the `LET A=QWERTYUIOP`, where `A `is the character that can be used in the mask after the `LET` command, and `QWERTYUIOP` are the characters that will be substituted for `A`. For example, we can generalize our past example with saving from the potion so that the hero does not run away only from a potion with a timer of `5`, but also from `4`, `3`, `2` and `1`.

    ```
    LET B=54321

    ????
    ??? 
    ?B☺ 
    ????
    ????
    RIGHT,UP

    ????
    ????
    ?B☺ 
    ??? 
    ????
    RIGHT,DOWN

    ????
    ?? ?
    ?B☺?
    ????
    ????
    UP

    ????
    ????
    ?B☺?
    ?? ?
    ????
    DOWN
    ```
   
9. You can invert characters by using `LET A!=QWERTYUIOP`, where `A `is the character that can be used in the mask after the `LET` command, and `QWERTYUIOP` are the characters that will be inverted, then result will be substituted for `A`. For example, we can use any bot bumb elements with this: 

    ```
    LET X!=54321

    ???
    ?☺?
    ?X?
    DOWN

    ???
    ?☺X
    ???
    RIGHT

    ?X?
    ?☺?
    ???
    UP

    ???
    X☺?
    ???
    LEFT
    ```
    
10. In some cases, you may need to place part of the behavior script in a separate file. For example, if there is no potion, we do one action, but if the potion appears, we run away. To do this, instead of a specific command `LEFT`, `RIGHT`, `UP`, `DOWN` or `ACT`, you should specify the RULE directive:

    ```
    &☺
    ??
    RULE runAway.rule

    ☺&
    ??
    RULE runAway.rule

    ??
    ☺&
    RULE runAway.rule

    ??
    &☺
    RULE runAway.rule
    ```
   
    In this script, we said that if a hunter appears around our hero, we must run. And how to run is described in the `runAway.rule` file. If you need to, create it! Inside the new script, you can write everything the same as in the main `main.rule` script.

11. **DO NOT FORGET** that each mask should be square (2x2, 3x3, 4x4, 5x5). 

12. Also, **WATCH CAREFULLY** for the symbols ` `  - this is an empty space on the field that is not occupied by anything. The space character ` `  is easy to skip (or add some redundant), so check back carefully.

13. The order of the commands depends on the order of their execution. Possible overlap - one command / rule overlaps another.

## Other stuff

1. All participants play on the same field. The winner is the one who for the given time will earn the most points.

2. For the destruction of the destructible wall you will get `+10`

3. If you manage to catch the hunter you will receive `+100`

4. If you manage to catch another hero, you will get `+1000`

## Codenjoy!