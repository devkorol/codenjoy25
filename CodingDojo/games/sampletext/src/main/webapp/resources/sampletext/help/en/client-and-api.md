## Client and API

The organizers provide the players with prepared clients in source code in several languages. Each of these clients already knows how to contact the server, receive and parse messages from the server (usually called board) and send commands to the server.

The client code doesn't give too much help to the players because this code still needs to be understood, but there is some logic behind communicating with the server + some high-level API to work with the board (which is already nice).

All languages in one way or another have a similar set of methods:

* `Solver`.
  An empty class with one method - you have to fill it with clever logic.
* `Board`.
  Contains logic for easy search and manipulation of elements on the board.
* and so on...
