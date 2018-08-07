/**
 * This class conducts a Percolation trial to determine the percolation threshold for a system of size n x n
 */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    Site[][] grid;              // the 0 to n-1 grid that stores all Sites by row/column
    Site[] sites;               // the 0 to n*n-1 array that stores all Sites by ID
    boolean[] fullComponents;   // the 0 to n*n-1 array that stores whether a component is full or not (independent of openness)

    WeightedQuickUnionUF unionFind;
    int nSize;                  // the 1 to n value of the grid dimensions
    int openCount = 0;              // keeps track of how many sites are currently open
    boolean percolates = false;        // the boolean that stores whether the full system percolates or not

    /**
     * Creates an n-by-n grid, with all sites blocked.
     * @param n The size of the grid's dimensions
     */
    public Percolation(int n) {

        nSize = n;
        sites = new Site[n * n];
        fullComponents = new boolean[n * n];
        grid = new Site[n][n];

        // setup unionFind
        unionFind = new WeightedQuickUnionUF(n * n);
        int id = 0;

        // create grid
        for (int i = 0; i < n; i++) {   // rows
            for (int j = 0; j < n; j++) { // columns
                Site newSite = new Site(i + 1, j + 1, id);
                grid[i][j] = newSite;
                sites[id] = newSite;

                if (i == 0) {
                    fullComponents[id] = true;
                }

                id++;
            }
        }
    }

    /**
     * Opens the site at (row, col) if it is not open already.
     * @param row row of site (from 1 to n)
     * @param col column of site (from 1 to n)
     */
    public void open(int row, int col) {
        boolean siteFull = false;

        // open site
        Site site = grid[row - 1][col - 1];
        site.open();
        openCount++;

        // checks if this site is full before any union
        if (fullComponents[site.component(unionFind)]) {
            siteFull = true;
        }

        // join all open neighbors
        int[] neighborIDs = site.getNeighbors(nSize);
        for (int neighbor : neighborIDs) {
            Site neighborSite = sites[neighbor];
            if (neighborSite.isOpen()) {

                boolean neighborFull = fullComponents[neighborSite.component(unionFind)];   // checks if this neighbor is full before the union

                unionFind.union(site.getID(), neighbor);

                int joinedComponent = site.component(unionFind);    // component after joining

                // distributes fullness
                if (siteFull) {
                    fullComponents[joinedComponent] = true;
                } else if (neighborFull) {
                    fullComponents[joinedComponent] = true;
                }

                // check for percolation
                
            }
        }
    }


    /**
     * Checks to see if the site at (row, col) is open (able to be percolated into)
     * @param row row of site (from 1 to n)
     * @param col column of site (from 1 to n)
     * @return true if the site is currently open
     */
    public boolean isOpen(int row, int col) {
        return grid[row - 1][col - 1].isOpen();
    }

    /**
     * @param row row (from 1 to n)
     * @param col row (from 1 to n)
     * @return True if the site is open and full from the top
     */
    public boolean isFull(int row, int col) {
        Site site = grid[row - 1][col - 1];
        if (!site.isOpen()) {
            return false;
        }
        return fullComponents[site.component(unionFind)];
    }

    /**
     * @return the number of sites currently open in the grid
     */
    public int numberOfOpenSites() {
        return openCount;
    }

    /**
     * @return True if the system percolates from top to bottom.
     */
    public boolean percolates() {
        return this.percolates;
    }

    private class Site {

        int row, col, id;
        boolean open = false;


        /**
         * @param row from 1 to n
         * @param col from 1 to n
         * @param id the union-Find id
         */
        public Site(int row, int col, int id) {
            this.row = row;
            this.col = col;
            this.id = id;
        }

        public int[] getCoords() {
            int[] coords = {row, col};
            return coords;
        }

        public int getID() {
            return id;
        }

        public void open() {
            open = true;
        }

        /**
         * @param nSize from 1 to n
         * @return an array of all neighbors' IDs
         */
        public int[] getNeighbors(int nSize) {
            int count = 4;
            if (this.row == 1 || this.row == nSize) {
                count--;
            }

            if (this.col == 1 || this.col == nSize) {
                count--;
            }

            int[] neighbors = new int[count];
            int cursor = 0;

            if (this.col > 1) {
                neighbors[cursor++] = this.id - 1;
            }

            if (this.row > 1) {
                neighbors[cursor++] = this.id - nSize;
            }

            if (this.col < nSize) {
                neighbors[cursor++] = this.id + 1;
            }

            if (this.row < nSize) {
                neighbors[cursor] = this.id + nSize;
            }

            return neighbors;
        }

        public boolean isOpen() {
            return open;
        }

        public int component(WeightedQuickUnionUF unionFind) {
            return unionFind.find(this.id);
        }
    }

    // Test client
    public static void main(String[] args) {
        int n = 6;
        System.out.println("Initializing a system with " + n + " sites");
        Percolation p = new Percolation(n);

        int[][] toOpen = {{6, 5}, {5, 5}, {4, 5}, {3, 5}, {2, 5}, {1, 5}};

        System.out.println("Opening some sites...");

        for (int[] coords : toOpen) {
            p.open(coords[0], coords[1]);
            System.out.print("Percolates? ");
            System.out.println(p.percolates());
        }

    }
}