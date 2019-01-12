# Five In a Row multiplayer game

## Programming Challenge: 5-in-a-Row  
5-in-a-Row, a variation of the famous Connect Four game, is a two-player connection game  
in which the players first choose a color and then take turns dropping colored discs from the  
top into a nine-column, six-row vertically suspended grid. The pieces fall straight down,  
occupying the next available space within the column. The objective of the game is to be the  
first to form a horizontal, vertical, or diagonal line of five of one's own discs.  
Using a client-server architecture, implement a plain text version of the game using any  
language (e.g. Java, Node.js, etc) where:

• The server application holds the state and business logic of the game, receiving the  
movements from the players and deciding whether a player has won, or the game is  
over. The state of the game, and who’s turn it is, will be returned to the client upon  
request. The communication between the clients and the server should be over HTTP.  

• The server, upon start, waits for the two players to connect. If one of the players  
disconnects, the game is over.  

• The client prompts the player to enter her name upon start, and displays whether it’s  
waiting for a 2nd player, or the game can start.  

• On each turn, the client displays the state of the board and prompts the corresponding  
player for input or displays that it’s waiting for the other player’s input (see example below).  

• The client receives the input from the player from the standard input (stdin).  

• The client displays when the game is over, and the name of the winner.  
The following is an example of what the client may display to the player, in this case  
John’s client is waiting for input to place his disc during his turn:

[  ][  ][  ][  ][  ][  ][  ][  ][  ]  
[  ][  ][  ][  ][  ][  ][  ][  ][  ]  
[  ][  ][  ][  ][  ][  ][  ][  ][  ]  
[  ][  ][  ][o][  ][o][  ][  ][  ]  
[ ][ ][x][x][ ][x][ ][o][ ]  
[ ][x][o][o][o][x][x][o][x]  
It’s your turn John, please enter column (1-9):

