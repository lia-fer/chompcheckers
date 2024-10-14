/**
 * Enhanced Checkers board implementation using advanced bitboard operations.
 * This class uses bitboards to represent the state of a checkers game, making
 * it more efficient to calculate moves and update the game state.
 */
public class CheckersBoard {
    private long blackPieces; // Bitboard for black pieces
    private long whitePieces; // Bitboard for white pieces
    private long kings; // Bitboard for king pieces

    private static final int BOARD_SIZE = 8; // Board size is 8x8

    // Starting positions for black and white pieces using bitboard representation.
    private static final long BLACK_STARTING_POSITION = 0xAA55AA0000000000L; // Black at rows 5-7
    private static final long WHITE_STARTING_POSITION = 0x000000000055AA55L; // White at rows 0-2

    // Masks for restricting movement directions to prevent wrapping around the board.
    private static final long NORTHWEST_MASK = 0xfefefefefefefe00L; // Excludes the rightmost column
    private static final long NORTHEAST_MASK = 0x7f7f7f7f7f7f7f00L; // Excludes the leftmost column
    private static final long SOUTHWEST_MASK = 0x00fefefefefefefeL; // Excludes the rightmost column
    private static final long SOUTHEAST_MASK = 0x007f7f7f7f7f7f7fL; // Excludes the leftmost column

    // Valid squares for play (only dark squares are used in checkers).
    private static final long VALID_SQUARES = 0xAA55AA55AA55AA55L;
    private static final long TOP_ROW = 0xFF00000000000000L; // Top row for white promotion
    private static final long BOTTOM_ROW = 0x00000000000000FFL; // Bottom row for black promotion

    /**
     * Constructor: Initializes the board to the starting state.
     */
    public CheckersBoard() {
        reset();
    }

    /**
     * Resets the board to the initial starting positions.
     */
    public void reset() {
        blackPieces = BLACK_STARTING_POSITION;
        whitePieces = WHITE_STARTING_POSITION;
        kings = 0L; // No kings at the start of the game.
    }

    /**
     * Calculates all possible moves for a given player (white or black).
     * @param isWhite True if calculating for white pieces, false for black.
     * @return A bitboard representing all possible destination squares.
     */
    public long calculateAllMoves(boolean isWhite) {
        long moves = 0L;
        long pieces = isWhite ? whitePieces : blackPieces;
        long opponents = isWhite ? blackPieces : whitePieces;
        long emptySquares = ~(whitePieces | blackPieces) & VALID_SQUARES;

        // Regular moves
        long regularMoves = calculateRegularMoves(pieces, emptySquares, isWhite);
        moves |= regularMoves;

        // Capture moves
        long captureMoves = calculateCaptureMoves(pieces, opponents, emptySquares, isWhite);
        moves |= captureMoves;

        // Calculate moves for king pieces.
        long kingPieces = pieces & kings;
        if (kingPieces != 0) {
            long kingMoves = calculateKingMoves(kingPieces, emptySquares);
            moves |= kingMoves;

            long kingCaptureMoves = calculateKingCaptures(kingPieces, opponents, emptySquares);
            moves |= kingCaptureMoves;
        }

        return moves;
    }

    /**
     * Calculates regular (non-capture) moves for a given set of pieces.
     * @param pieces The bitboard representing the player's pieces.
     * @param emptySquares Bitboard of empty squares.
     * @param isWhite True if calculating for white, false for black.
     * @return A bitboard of possible regular move destinations.
     */
    public long calculateRegularMoves(long pieces, long emptySquares, boolean isWhite) {
        long moves = 0L;
        long forward = isWhite ? (pieces << 7) | (pieces << 9) : (pieces >> 7) | (pieces >> 9);
        moves = forward & emptySquares;
        return moves;
    }

    /**
     * Calculates capture moves for regular pieces.
     * @param pieces The bitboard representing the player's pieces.
     * @param opponents Bitboard of opponent's pieces.
     * @param emptySquares Bitboard of empty squares.
     * @param isWhite True if calculating for white, false for black.
     * @return A bitboard of possible capture destinations.
     */
    public long calculateCaptureMoves(long pieces, long opponents, long emptySquares, boolean isWhite) {
        long captures = 0L;
        long forward = isWhite ? (pieces << 7) | (pieces << 9) : (pieces >> 7) | (pieces >> 9);
        long jumpLeft = isWhite ? (pieces << 14) | (pieces << 18) : (pieces >> 14) | (pieces >> 18);
        long jumpRight = isWhite ? (pieces << 18) | (pieces << 14) : (pieces >> 18) | (pieces >> 14);

        captures |= (forward & opponents) & emptySquares; // Check if you can jump over an opponent
        captures |= (jumpLeft & emptySquares);
        captures |= (jumpRight & emptySquares);

        return captures;
    }

    /**
     * Calculates possible moves for king pieces in all four directions.
     * @param kingPieces Bitboard of king pieces.
     * @param emptySquares Bitboard of empty squares.
     * @return A bitboard of possible king move destinations.
     */
    private long calculateKingMoves(long kingPieces, long emptySquares) {
        long moves = 0L;

        // Kings can move in all four directions.
        moves |= (kingPieces >> 7) & emptySquares;
        moves |= (kingPieces >> 9) & emptySquares;
        moves |= (kingPieces << 7) & emptySquares;
        moves |= (kingPieces << 9) & emptySquares;

        return moves & VALID_SQUARES;
    }

    /**
     * Calculates capture moves for king pieces.
     * @param kingPieces Bitboard of king pieces.
     * @param opponents Bitboard of opponent's pieces.
     * @param emptySquares Bitboard of empty squares.
     * @return A bitboard of possible king capture destinations.
     */
    private long calculateKingCaptures(long kingPieces, long opponents, long emptySquares) {
        long moves = 0L;

        // Kings can capture in all four directions.
        moves |= ((kingPieces & (opponents >> 7)) >> 7) & emptySquares;
        moves |= ((kingPieces & (opponents >> 9)) >> 9) & emptySquares;
        moves |= ((kingPieces & (opponents << 7)) << 7) & emptySquares;
        moves |= ((kingPieces & (opponents << 9)) << 9) & emptySquares;

        return moves & VALID_SQUARES;
    }

    /**
     * Makes a move from one position to another.
     * @param from The starting position of the move.
     * @param to The destination position of the move.
     * @param isWhite True if the moving piece is white, false for black.
     * @return True if the move was successful, false otherwise.
     */
    public boolean makeMove(int from, int to, boolean isWhite) {
        // Verify that the move is legal.
        long possibleMoves = calculateAllMoves(isWhite);

        if ((possibleMoves & (1L << to)) == 0) {
            System.out.println((isWhite ? "White" : "Black") + " move is not valid.");
            return false;
        }

        // Update piece positions.
        long fromBit = 1L << from;
        long toBit = 1L << to;

        if (isWhite) {
            whitePieces = BitUtils.clearBit(whitePieces, from);
            whitePieces = BitUtils.setBit(whitePieces, to);
        } else {
            blackPieces = BitUtils.clearBit(blackPieces, from);
            blackPieces = BitUtils.setBit(blackPieces, to);
        }

        // Handle captures.
        if (Math.abs(to - from) > 9) {
            int capturedPosition = (from + to) / 2;
            long capturedBit = 1L << capturedPosition;

            if (isWhite) {
                blackPieces &= ~capturedBit;
            } else {
                whitePieces &= ~capturedBit;
            }

            // Remove from kings if necessary.
            kings &= ~capturedBit;
        }

        // Handle promotions to king.
        if ((isWhite && (toBit & TOP_ROW) != 0) || (!isWhite && (toBit & BOTTOM_ROW) != 0)) {
            kings |= toBit;
        }

        return true;
    }

    /**
     * Evaluates the current board state to determine the advantage.
     * @return A positive number for white's advantage, a negative number for black's advantage.
     */
    public int evaluatePosition() {
        int whiteScore = BitUtils.countBits(whitePieces) + 2 * BitUtils.countBits(whitePieces & kings);
        int blackScore = BitUtils.countBits(blackPieces) + 2 * BitUtils.countBits(blackPieces & kings);
        return whiteScore - blackScore;
    }

    public void displayBoard() {
        System.out.println("  Checkers Board:");
        System.out.println("   0 1 2 3 4 5 6 7");
        for (int row = 0; row < BOARD_SIZE; row++) {
            System.out.print(row + " |");
            for (int col = 0; col < BOARD_SIZE; col++) {
                int position = row * 8 + col;
                char piece = ' ';
                if (BitUtils.getBit(blackPieces, position) == 1) {
                    piece = BitUtils.getBit(kings, position) == 1 ? 'B' : 'b';
                } else if (BitUtils.getBit(whitePieces, position) == 1) {
                    piece = BitUtils.getBit(kings, position) == 1 ? 'W' : 'w';
                }
                System.out.print(piece + "|");
            }
            System.out.println();
        }
        
        // Display bitboard representations
        System.out.println("\nBitboard Representations:");
        System.out.println("Black pieces: " + BitUtils.toBinaryString(blackPieces));
        System.out.println("Black pieces (hex): 0x" + BitUtils.toHexString(blackPieces).toUpperCase());
        System.out.println("White pieces: " + BitUtils.toBinaryString(whitePieces));
        System.out.println("White pieces (hex): 0x" + BitUtils.toHexString(whitePieces).toUpperCase());
        System.out.println("Kings: " + BitUtils.toBinaryString(kings));
        System.out.println("Kings (hex): 0x" + BitUtils.toHexString(kings).toUpperCase());
    }
}

