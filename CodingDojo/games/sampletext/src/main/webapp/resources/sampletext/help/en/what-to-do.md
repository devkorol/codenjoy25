## How do you play?

The game is turn-based, every second the server sends your client
the state of the updated field for the current moment and waits for a response
command to the hero. In the next second, the player has to give
command to the hero. If he doesn't, the hero stands still.

Your goal is to make the hero move according to your algorithm.
The hero on the field must be able to score as many points as he can.
The main goal of the game is to beat all opponents by points.

## Commands

There are several commands:

TODO

## Settings

The settings will change[(?)](#ask) as the game progresses.

## Cases

## Hints

The first task is to write a websocket client that connects to the server. Then get the hero on the field to obey commands.
This will prepare the player for the main game.
The main goal is to play a meaningful game and win.