package ToggleGame.backend;
import ToggleGame.frontend.ToggleGameInteraction;
import java.util.Collections;
import java.util.ArrayList;

/**
 * This class ToggleGameEngine runs the game which is a 3x3 grid of squares that are
 * either black or white. Clicking on any square will change the color of that square
 * and its 4 neighbours (north, south, east and west) to their opposite color,
 * i.e., black becomes white and vice versa.
 * It provides methods to initialize the game to a board with all white squares,
 * communicate the minimum number of moves to solve the game from the current board,
 * and generate a list of the necessary order of buttons to click to solve the game
 * in the minimum number of moves.
 *
 * Buttons have the following order / placement on the screen:
 *
 *  0 1 2
 *  3 4 5
 *  6 7 8
 *
 * For the Colors: BLACK is 0 and WHITE = 1
 *
 */
public class ToggleGameEngine implements ToggleGameInteraction {
    private static final int BOARD_STATES = 512; // Number of possible board states (9^2)
    private static final int[] MASK = {416, 464, 200, 308, 186, 89, 38, 23, 11}; // Binary masks for each button.
    private final Graph G; // Graph containing all possible board states as individual vertices.
    private BreadthFirstPaths S; // For breadth first search.

    /**
     * Constructor that initializes the graph containing all possible
     * board states as individual vertices and adds appropriate edges.
     */
    public ToggleGameEngine() {
        G = new Graph(BOARD_STATES);
        for (int i = 0; i < BOARD_STATES ; i++) {
            for (int mask : MASK) {
                G.addEdge(i, i ^ mask);
            }
        }
    }

    /**
     * Initialize and return the game board for a ToggleGame (9 x "1").
     *
     * @return the String "111111111" to start a game with all white squares.
     */
    @Override
    public String initializeGame() { return "111111111"; }

    /**
     * Update the game board for the given button that was clicked.
     * Squares marked as 0 are black and 1 is white.
     *
     * @param button the game board square button that was clicked (between 0 and 8).
     *
     * @return the updated game board as a String giving the button colors in order
     *         with "0" for black and "1" for white.
     *
     * @throws IllegalArgumentException when button is outside 0-8.
     */
    @Override
    public String buttonClicked(String current, int button) {
        if (button < 0 || button > 8) throw new IllegalArgumentException("Button must be between 0-8");
        int newBoard = GameHelper.stringToBinary(current) ^ MASK[button];
        return GameHelper.binaryToString(newBoard);
    }

    /**
     * Return a sequence of moves that leads in the minimum number of moves
     * from the current board state to the target state.
     *
     * @param current the current board state given as a String of 1's (white square)
     *                and 0's (black square).
     * @param target the target board state given as a String of 1's (white square)
     *               and 0's (black square).
     * @return The sequence of moves to advance the board from current to target.
     *         Each move is the number associated with a button on the board. If no moves are
     *         required to advance the currentBoard to the target an empty array is returned.
     */
    @Override
    public int[] movesToSolve(String current, String target) {
        S = new BreadthFirstPaths(G, GameHelper.stringToBinary(current));
        int[] buttonSequence; // Array to be returned containing the most efficient sequence of moves.
        int[] shortestPath; // Array containing the vertices of the shortest path to the target.
        ArrayList<Integer> adjacencyList;  // ArrayList to hold the adjacency list for desired vertex.

        buttonSequence = new int[minNumberOfMoves(current, target)];
        shortestPath = S.getPath(GameHelper.stringToBinary(target));

        for (int i = 0; i < buttonSequence.length; i++) {
            adjacencyList = G.getAdjacencyList(shortestPath[i]);
            Collections.reverse(adjacencyList);
            buttonSequence[i] = adjacencyList.indexOf(shortestPath[i+1]);
        }
        return buttonSequence;
    }

    /**
     * Return the minimum required number of required moves (button clicks)
     * to advance the current board to the target board.
     *
     * @param current the current board state given as a String of 1's (white square)
     *                and 0's (black square).
     * @param target the target board state given as a String of 1's (white square)
     *               and 0's (black square).
     * @return the minimum number of moves to advance the current board
     * to the target.
     */
    @Override
    public int minNumberOfMoves(String current, String target) {
        S = new BreadthFirstPaths(G, GameHelper.stringToBinary(current));
        return S.distTo(GameHelper.stringToBinary(target));
    }
}
