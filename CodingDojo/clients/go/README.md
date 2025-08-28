This project represents a basic go websocket client for the codenjoy platform.
It allows you to easily and quickly join the game, developing your unique algorithm, having a configured infrastructure.

# What do you need to get started?
To get started, you should define the desired game and enter a value in `main.GAME`. \
The second important thing is the connection token to the server. After successful authorization on the site, you must copy the url
and enter a value in `main.URL`. \
This is enough to connect and participate in the competition.

# How to run it?
To start a project from the console window, you must first perform build in your project directory `go build`.
The entry point for starting a project is `main()` func in `main.go`. \
You can pass the game type and token connection to the server as command-line arguments.
Game parameters passed by arguments at startup have a higher priority than those defined in the code.

The archive is run with the command `go run [<game>] [<url>]`

# How does it work?
The elements on the map are defined in `games/<gamename>/element.go`. They determine the meaning of a particular symbol.
The two important components of the game are the `games/<gamename>/board.go` game board 
and the `games/<gamename>/solver.go` solver.

Every second the server sends a string representation of the current state of the board, which is parsed in an object of class `Board`.
Then the server expects a string representation of your bot's action that is computed by executing `Solver.Get(rawBoard)`.

Using the set of available methods of the `Board` class, you improve the algorithm of the bot's behavior.
You should develop this class, extending it with new methods that will be your tool in the fight.
For example, a bot can get information about an element in a specific coordinate by calling `AbstractBoard.GetAt(point)`
or count the number of elements of a certain type near the coordinate by calling `AbstractBoard.CountNear(point, element)`, etc.

# Business logic testing
Writing tests will allow you to create conclusive evidence of the correctness of the existing code.
This is your faithful friend, who is always ready to answer the question: "Is everything working as I expect? The new code did not break my existing logic?". \
The `tests/board_test.go` file contains a set of tests that check board tools.
Implementation of new methods should be accompanied by writing new tests and checking the results of processing existing ones. \
Use `tests/games/<gamename>/solver_test.go` to check the bot's behavior for a specific game scenario.