# chompcheckers
Project Overview: 
This project implements a checkers game using bitboards for efficient state representation and manipulation. By using bitwise operations, the game is optimized to perform quick move calculations, captures, and game state updates. The project is written in Java and is structured around a utility class (BitUtils) that handles various bitwise functions, and a CheckersBoard class that uses these functions to manage the game logic.

Bitboard Representation: The checkers board is represented using 64-bit integers, where each bit corresponds to a square on the 8x8 board. Separate bitboards track black pieces, white pieces, and kings.

Efficient Move Calculation: Bitwise operations are used to quickly calculate valid moves and captures for both regular and king pieces.

Game Logic: The game is fully interactive through the terminal, allowing two players to alternate turns, make moves, and view the board in real time.

Bitwise Utility Class: BitUtils provides core bitwise operations such as setting, clearing, toggling, and retrieving bits, as well as binary arithmetic functions.


Running the Game:

- Clone the repository and compile the code.

- Run the CheckerGame class to start the game.

- Players will take turns inputting their moves by specifying the starting and ending positions (0-63).

- The game will validate moves, update the board, and display the current state in both human-readable and bitboard formats.
