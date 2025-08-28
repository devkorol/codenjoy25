## Message Format

The player receives every second a question from the server to which he must give an answer.
If the answer to the question is correct, the `valid` field will be set to `true`.
If the answer to the question is wrong, the `valid` field will be set to `false`.
BoardData json includes a history of questions with player answers and a pointer 
to the next question.

## Example query

TODO