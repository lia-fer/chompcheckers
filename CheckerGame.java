import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The CheckerGame class manages the main loop of the checkers game.
 * It takes player input for moves and updates the board accordingly.
 */
class CheckerGame {
    /**
     * Main method to run the Checkers game.
     * Initializes the game board, handles player turns, and processes moves.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        CheckersBoard board = new CheckersBoard();  // Initialize the checkers board
        Scanner scanner = new Scanner(System.in);   // Scanner to capture user input
        boolean isWhiteTurn = true;  // Flag to track whose turn it is

        while (true) {
            System.out.println("\nCurrent board state:");
            board.displayBoard();  // Display the current board

            // Determine and print whose turn it is
            String currentPlayer = isWhiteTurn ? "White" : "Black";
            System.out.println("\n" + currentPlayer + "'s turn");

            // Capture the player's move input with validation
            int from = getPosition(scanner, "Enter the starting position (0-63): ");
            int to = getPosition(scanner, "Enter the ending position (0-63): ");

            // Attempt to make the move
            boolean moveSuccessful = board.makeMove(from, to, isWhiteTurn);

            if (moveSuccessful) {
                System.out.println("Move successful!");
                isWhiteTurn = !isWhiteTurn;  // Switch turns if the move was valid
            } else {
                System.out.println("Invalid move. Please try again.");
            }

            // Ask the player if they want to continue playing, with validation
            if (!getContinuePlaying(scanner)) {
                break;  // Exit the game loop if the player chooses not to continue
            }
        }

        scanner.close();  // Close the scanner resource
        System.out.println("Game ended. Final board state:");
        board.displayBoard();  // Show the final board state
    }

    /**
     * Prompts the user to input a valid position (0-63) and validates it.
     *
     * @param scanner The scanner object to capture user input.
     * @param prompt  The message prompt to display to the user.
     * @return A valid position between 0 and 63.
     */
    private static int getPosition(Scanner scanner, String prompt) {
        int position = -1;
        while (true) {
            try {
                System.out.print(prompt);
                position = scanner.nextInt();
                if (position >= 0 && position <= 63) {
                    break;  // Valid input
                } else {
                    System.out.println("Invalid position. Please enter a number between 0 and 63.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.next();  // Clear invalid input
            }
        }
        return position;
    }

    /**
     * Prompts the user to continue playing by inputting 'y' or 'n' and validates the response.
     *
     * @param scanner The scanner object to capture user input.
     * @return True if the player wants to continue, false otherwise.
     */
    private static boolean getContinuePlaying(Scanner scanner) {
        String input;
        while (true) {
            System.out.print("Continue playing? (y/n): ");
            input = scanner.next().trim().toLowerCase();
            if (input.equals("y")) {
                return true;  // Continue playing
            } else if (input.equals("n")) {
                return false;  // Stop playing
            } else {
                System.out.println("Invalid input. Please enter 'y' or 'n'.");
            }
        }
    }
}
