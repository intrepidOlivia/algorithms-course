import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;

public class Board {

    int n;
    int[][] board;
    Stack<Board> iterableBoards;

    /**
     * constructs a board from an n-by-n array of blocks (where blocks[i][j] = block in row i, column j)
     * @param blocks
     */
    public Board(int[][] blocks) {
        n = blocks.length;
        board = blocks;

        System.out.println("New board:");
        for (int i = 0; i < blocks.length; i++) {
            for (int col : blocks[i]) {
                System.out.print(" " + col + " ");
            }
            System.out.println();
        }
    }

    /**
     * @return the dimension n of the board
     */
    public int dimension() {
        return n;
    }

    /**
     *
     * @return number of blocks out of place
     */
    public int hamming() {
        return 0;
    }

    /**
     *
     * @return sum of Manhattan distances between blocks and goal
     */
    public int manhattan() {
        return 0;
    }

    /**
     * is this board the goal board?
     * @return true if board is the goal board
     */
    public boolean isGoal() {
        int checkValue = 1;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] != checkValue++) {
                    return checkValue == (n * n) + 1;
                }
            }
        }

        return false;
    }

    /**
     *
     * @return a board that is obtained by exchanging any pair of blocks (not the empty block)
     */
    public Board twin() {
        int sourceVal, targetVal;
        int[] sourceCoords, targetCoords;

        // select random source (not zero)
        sourceCoords = getRandomCoord();
        sourceVal = board[sourceCoords[0]][sourceCoords[1]];

        // select random target (not zero)
        targetCoords = getRandomCoord();
        targetVal = board[targetCoords[0]][targetCoords[1]];

        if (sourceVal == targetVal) {
            return twin();
        }

        // use the swap method on them
        return new Board(swap(board, sourceCoords, targetCoords));
    }

    /**
     * @return a random coordinate on the board that is not 0
     */
    int[] getRandomCoord() {
        int i, j;
        i = StdRandom.uniform(0, n);
        j = StdRandom.uniform(0, n);
        if (board[i][j] == 0) {
            return getRandomCoord();
        }
        return new int[]{i, j};
    }

    /**
     * does this board equal y?
     * @param y
     * @return true if board is equal to y
     */
    public boolean equals(Object y) {
        return false;
    }

    /**
     * @return all neighboring boards
     */
    public Iterable<Board> neighbors() {
        return iterableBoards;
    }

    private Stack<Board> getNextBoards() {
        Stack<Board> nextBoards = new Stack<>();

        // for each location, find adjacents
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                int[][] adjacent = adjacentSites(i, j);

                for (int a = 0; a < adjacent.length; a++) {
                    // each entry in adjacents at a is an array with two elements, [0] = i, [1] = j
                    int iCoord = adjacent[a][0];
                    int jCoord = adjacent[a][1];

                    if (board[iCoord][jCoord] == 0) {
                        System.out.println("0 was found at these coordinates: " + iCoord + ", " + jCoord);
                        nextBoards.push(new Board(swap(board, new int[]{i, j}, new int[]{iCoord, jCoord})));
                    }
                }
            }
        }

        return nextBoards;
    }

    /**
     *
     * @param board The blocks on which this swap is taking place
     * @param swapSource The block to swap with target, [row, col]
     * @param swapTarget The block to be swapped with source, [row, col]
     * @return a copy of board with the specified elements swapped
     */
    int[][] swap(int[][] board, int[] swapSource, int[] swapTarget) {
        int[][] swapped = new int[board.length][board.length];

        int sourceRow = swapSource[0];
        int sourceCol = swapSource[1];

        int targetRow = swapTarget[0];
        int targetCol = swapTarget[1];

        int sourceValue = board[sourceRow][sourceCol];
        int targetValue = board[targetRow][targetCol];  // usually 0

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                swapped[i][j] = board[i][j];
            }
        }

        swapped[sourceRow][sourceCol] = targetValue;
        swapped[targetRow][targetCol] = sourceValue;

        return swapped;
    }

    // NOTE: This method originally came from Permutation.java and is not written well (although it works)
    // If you use it again, ideally rewrite it to be more intuitive.
    private int[][] adjacentSites(int row, int col) {
        int[][] adjacent;
        boolean[] validCoordinates = new boolean[] {true, true, true, true};
        int adjLength = 4;

        int[][] abstractAdjacents = new int[4][2];

        abstractAdjacents[0] = new int[] {row - 1, col}; // previous row
        abstractAdjacents[1] = new int[] {row + 1, col}; // next row
        abstractAdjacents[2] = new int[] {row, col - 1}; // previous column
        abstractAdjacents[3] = new int[] {row, col + 1}; // next column

        // check whether each coordinate value is within the board
        for (int i = 0; i < abstractAdjacents.length; i++) {
            int rowValue = abstractAdjacents[i][0];
            if (!within0Bounds(rowValue)) {
                adjLength--;
                validCoordinates[i] = false;
            }

            int colValue = abstractAdjacents[i][1];
            if (!within0Bounds(colValue)) {
                adjLength--;
                validCoordinates[i] = false;
            }
        }

        adjacent = new int[adjLength][2];
        int aIndex = 0;
        for (int i = 0; i < abstractAdjacents.length; i++) {
            if (validCoordinates[i]) {
                adjacent[aIndex] = abstractAdjacents[i];
                aIndex++;
            }
        }

        return adjacent;
    }

    /**
     * @param c A row or column value (from 0 to n-1)
     * @return true if the provided integer is within the bounds of N (from o to N - 1)
     */
    private boolean within0Bounds(int c) {
        if (c < 0 || c >= board.length) {
            return false;
        }
        return true;
    }

    /**
     * @return a string representation of this board
     */
    public String toString() {
        return "";
    }

    public static void main(String[] args) {
        // read in the board specified in the filename
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }
        Board b = new Board(tiles);
//        b.getNextBoards();
        System.out.println("Is board goal? " + b.isGoal());
        System.out.println("Get a twin!");
        Board t = b.twin();
    }
}