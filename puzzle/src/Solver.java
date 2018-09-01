import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Solver {

    private SearchNode goalBoard;
    private Stack<Board> solutionBoards;
//    private final MinPQ<SearchNode> nodes = new MinPQ<>(new SearchNodeCompare());
    private boolean solutionConcluded = false;
    private boolean solutionPossible = true;
    private final Board initialTwin;


    /**
     * finds a solution to the initial board (using the A* algorithm)
     * @param initial the initial game board
     */
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }

        // take the initial board and its twin, put them into search nodes.
        initialTwin = initial.twin();
        MinPQ<SearchNode> nodes = new MinPQ<>(new SearchNodeCompare());
        MinPQ<SearchNode> twinNodes = new MinPQ<>(new SearchNodeCompare());
        nodes.insert(new SearchNode(initial, 0, null));
        twinNodes.insert(new SearchNode(initialTwin, 0, null));

        while (!solutionConcluded) {
            makeNextMove(nodes, twinNodes);
        }

        if (solutionPossible) {
            solutionBoards = solutionTrace();
        }
    }

    private Stack<Board> solutionTrace() {
        if (goalBoard == null) {
            return null;
        }

        Stack<Board> trace = new Stack<>();
        SearchNode current = goalBoard;
        trace.push(current.board);

        while (current.predecessor != null) {
            trace.push(current.predecessor.board);
            current = current.predecessor;
        }

        return trace;
    }

    private void makeNextMove(MinPQ<SearchNode> nodes, MinPQ<SearchNode> twinNodes) {

        // remove minimum priority board and add it to Stack
        SearchNode next = nodes.delMin();
        SearchNode nextTwin = twinNodes.delMin();

        if (nextTwin.board.isGoal()) {
            solutionConcluded = true;
            solutionPossible = false;
            return;
        }

        // check it for goal status
        if (next.board.isGoal()) {
            solutionConcluded = true;
            goalBoard = next;
            return;
        }

        Iterable<Board> neighbors = next.board.neighbors();
        for (Board b : neighbors) {
            if (next.predecessor == null || !b.equals(next.predecessor.board)) {
                nodes.insert(new SearchNode(b, next.movesPrior + 1, next));
            }
        }

        Iterable<Board> twinNeighbors = nextTwin.board.neighbors();
        for (Board b : twinNeighbors) {
            if (nextTwin.predecessor == null || !b.equals(nextTwin.predecessor.board)) {
                twinNodes.insert(new SearchNode(b, nextTwin.movesPrior + 1, nextTwin));
            }
        }
    }

    /**
     * determines whether the initial board is solvable
     * @return true if solvable
     */
    public boolean isSolvable() {
        return goalBoard != null && solutionPossible;
    }

    /**
     * @return min number of moves to solve initial board; -1 if unsolvable
     */
    public int moves() {
        if (!isSolvable()) {
            return -1;
        }

        return solutionBoards.size() - 1;
    }

    /**
     * @return sequence of boards in a shortest solution; null if unsolvable
     */
    public Iterable<Board> solution() {
        if (!this.isSolvable()) {
            return null;
        }

        return solutionBoards;
    }

    private class SearchNode {
        Board board;
        int movesPrior;
        SearchNode predecessor;
        int score;

        SearchNode(Board b, int moves, SearchNode predecessor) {
            this.board = b;
            this.movesPrior = moves;
            this.predecessor = predecessor;
            this.score = b.manhattan() + this.movesPrior;
        }

        public int hamming() {
            return this.board.hamming() + movesPrior;
        }
    }

    private class SearchNodeCompare implements Comparator<SearchNode> {
        @Override
        public int compare(SearchNode o1, SearchNode o2) {
            if (o1.score == o2.score) {
                return o1.hamming() - o2.hamming();
            }
            return o1.score - o2.score;
        }
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
