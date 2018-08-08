/**
 * This class conducts a Percolation trial to determine the percolation threshold for a system of size n x n
 */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    final private int[][] grid;                   // The indices of each site in the system, modeled as an x/y plane.
    final private WeightedQuickUnionUF unionFind; // The union-find network that will control which sites are in which components.
    private boolean[] siteOpen;             // The array of ID's that will indicate whether a site is open or not
    private int openSiteCount = 0;          // The count of how many sites are currently open
    private boolean[] full;                 // The indices match the component id
    private boolean[] grounded;             // The indices match the component id
    private boolean percolates;             // Keeps track of whether the system has fully percolated or not

    /**
     * Creates an n-by-n grid, with all sites blocked.
     * @param n The size of the grid's dimensions
     */
    public Percolation(int n) {
        if (n < 1) {
            throw new IllegalArgumentException();
        }

        int id = 0;
        siteOpen = new boolean[n * n];
        full = new boolean[n * n];
        grounded = new boolean[n * n];
        grid = new int[n][n];

        for (int i = 0; i < n; i++) {       // rows
            for (int j = 0; j < n; j++) {   // columns
                siteOpen[id] = false;           // sets all sites to closed
                grid[i][j] = id++;
            }
        }

        unionFind = new WeightedQuickUnionUF(id + 1);   // Sets up a union-find system with each site included.
    }

    /**
     * Opens the site at (row, col) if it is not open already.
     * @param row row of site (from 1 to n)
     * @param col column of site (from 1 to n)
     */
    public void open(int row, int col) {
        if (!isValidInput(row, col)) {
            throw new IllegalArgumentException();
        }

        int id = grid[row - 1][col - 1];
        int idComp;

        if (siteOpen[id]) {
            return;
        } else {
            siteOpen[id] = true;
            openSiteCount++;

            if (row == 1) {
                full[unionFind.find(id)] = true;
            }

            if (row == grid.length) {
                grounded[unionFind.find(id)] = true;
            }

            int[][] adjacentSites = adjacentSites(row - 1, col - 1);    // Returns rows from 0 to n - 1

            for (int[] site : adjacentSites) {
                int adj = grid[site[0]][site[1]];
                if (siteOpen[adj]) {
                    boolean sitePercolation = full[unionFind.find(id)]; // status of site's fullness before joining
                    boolean adjPercolation = full[unionFind.find(adj)]; // status of neighbor's fullness before joining
                    boolean siteGrounded = grounded[unionFind.find(id)]; // status of site's groundedness before joining
                    boolean adjGrounded = grounded[unionFind.find(adj)]; // status of neighbor's groundedness before joining

                    unionFind.union(id, adj);

                    idComp = unionFind.find(id);
                    full[idComp] = sitePercolation || adjPercolation;
                    grounded[idComp] = siteGrounded || adjGrounded;

                    if (full[idComp] && grounded[idComp]) {
                        percolates = true;
                    }
                }
            }
        }
    }

    /**
     * Retrieves all adjacent sites for a provided (row, column) coordinate
     * @param row   The coordinate row (from 0 to n - 1)
     * @param col   The coordinate column (from 0 to n - 1)
     * @return  An array of all sites adjacent to the coordinates, in [row, column] format.
     */
    private int[][] adjacentSites(int row, int col) {
        int[][] adjacent;
        boolean[] validCoordinates = new boolean[] {true, true, true, true};
        int adjLength = 4;

        int[][] abstractAdjacents = new int[4][2];

        abstractAdjacents[0] = new int[] {row - 1, col}; // previous row
        abstractAdjacents[1] = new int[] {row + 1, col}; // next row
        abstractAdjacents[2] = new int[] {row, col - 1}; // previous column
        abstractAdjacents[3] = new int[] {row, col + 1}; // next column

        // check whether each coordinate value is within the grid
        for (int i = 0; i < abstractAdjacents.length; i++) {
            if (!within0Bounds(abstractAdjacents[i][0])) {
                adjLength--;
                validCoordinates[i] = false;
            }

            if (!within0Bounds(abstractAdjacents[i][1])) {
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
        if (c < 0 || c >= grid.length) {
            return false;
        }
        return true;
    }

    /**
     *
     * @param c A row or column value
     * @return true if the provided integer is within the bounds of N (from 1 to N)
     */
    private boolean withinNBounds(int c) {
        if (c < 1 || c > grid.length) {
            return false;
        }
        return true;
    }

    /**
     * @param row from 1 to n
     * @param col from 1 to n
     * @return True if the input is valid
     */
    private boolean isValidInput(int row, int col) {
        return withinNBounds(row) && withinNBounds(col);
    }

    /**
     * Checks to see if the site at (row, col) is open (able to be percolated into)
     * @param row row of site (from 1 to n)
     * @param col column of site (from 1 to n)
     * @return true if the site is currently open
     */
    public boolean isOpen(int row, int col) {
        if (!isValidInput(row, col)) {
            throw new IllegalArgumentException();
        }

        int id = grid[row - 1][col - 1];
        return siteOpen[id];
    }

    /**
     * @param row row (from 1 to n)
     * @param col row (from 1 to n)
     * @return True if the site is open and full from the top
     */
    public boolean isFull(int row, int col) {
        if (!isValidInput(row, col)) {
            throw new IllegalArgumentException();
        }

        if (!isOpen(row, col)) {
            return false;
        }

        return full[unionFind.find(grid[row - 1][col - 1])];
    }

    /**
     * @return the number of sites currently open in the grid
     */
    public int numberOfOpenSites() {
        return openSiteCount;
    }

    /**
     * @return True if the system percolates from top to bottom.
     */
    public boolean percolates() {
        return percolates;
    }

    // Test client
    public static void main(String[] args) {
        // Various testing scripts used to go here
        Percolation p = new Percolation(10);
        p.open(10, -1);
    }
}