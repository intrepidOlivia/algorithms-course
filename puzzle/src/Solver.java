import edu.princeton.cs.algs4.Stack;

public class Solver {

    Stack<Board> solutionBoards;

    /**
     * finds a solution to the initial board (using the A* algorithm)
     * @param initial the initial game board
     */
    public Solver(Board initial) {

    }

    /**
     * determines whether the initial board is solvable
     * @return true if solvable
     */
    public boolean isSolvable() {
        return false;
    }

    /**
     * @return min number of moves to solve initial board; -1 if unsolvable
     */
    public int moves() {
        return 0;
    }

    /**
     * @return sequence of boards in a shortest solution; null if unsolvable
     */
    public Iterable<Board> solution() {
        return solutionBoards;
    }

    public static void main(String[] args) {

    }
}
