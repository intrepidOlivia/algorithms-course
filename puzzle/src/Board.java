import edu.princeton.cs.algs4.Stack;

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
        return false;
    }

    /**
     *
     * @return a board that is obtained by exchanging any pair of blocks
     */
    public Board twin() {
        return new Board();
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

    /**
     * @return a string representation of this board
     */
    public String toString() {
        return "";
    }

    public static void main(String[] args) {

    }
}