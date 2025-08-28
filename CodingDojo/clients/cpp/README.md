This project represents a basic c++ websocket client for the codenjoy platform.
It allows you to easily and quickly join the game, developing your unique algorithm, having a configured infrastructure.

# What do you need to get started?
To get started, you should define the desired game and include an appropriate solver (e.g. `#include "games/<gamename>/YourSolver.h"`). \
The second important thing is the connection token to the server. After successful authorization on the site, you must copy your playerId and code
and enter a value in `main.yourName` (e.g. `const char* yourName = "0&code=000000000000"`). \
This is enough to connect and participate in the competition.

# How to run it?
To start a project from the console window, you must first perform build in your project directory.
The entry point for starting a project is `main(int argc, char** argv)` func in `src/main.cpp`.

# How does it work?
The elements on the map are defined in `games/<gamename>/Element.cpp`. They determine the meaning of a particular symbol.
The two important components of the game are the `games/<gamename>/Board.cpp` game board
and the `games/<gamename>/YourSolver.cpp` solver.

Every second the server sends a string representation of the current state of the board, which is parsed in an object of class `Board`.
Then the server expects a string representation of your bot's action that is computed by executing `YourSolver::get(String boardString)`.

Using the set of available methods of the `Board` class, you improve the algorithm of the bot's behavior.
You should develop this class, extending it with new methods that will be your tool in the fight.
For example, a bot can get information about an element in a specific coordinate by calling `AbstractBoard::getAt(int x, int y)`
or count the number of elements of a certain type near the coordinate by calling `AbstractBoard::countNear(int x, int y, CharElement* el)`, etc.

# Business logic testing
Writing tests will allow you to create conclusive evidence of the correctness of the existing code.
This is your faithful friend, who is always ready to answer the question: "Is everything working as I expect? The new code did not break my existing logic?". \
The `tests/games/<gamename>/BoardTest.cpp` file contains a set of tests that check board tools.
Implementation of new methods should be accompanied by writing new tests and checking the results of processing existing ones. \
Use `tests/games/<gamename>/YourSolverTest.cpp` to check the bot's behavior for a specific game scenario.