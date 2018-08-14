/**
 * This class works in conjunction with the Percolation class to determine the mean percolation threshold after T trials.
 */

import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {

    private double[] openSites;    // Stored values of the open sites when the system percolates
    final private int nSize;          // Size of n x n grid
    final private int siteCount;      // Total number of sites in the grid
    final private int numTrials;      // Number of trials performed
    final private double CONF_95 = 1.96;    // used in calculating confidence
    private double mean;              // storage for mean value
    private double stddev;            // storage for standard deviation value

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials  <= 0) {
            throw new IndexOutOfBoundsException();
        }

        openSites = new double[trials];
        nSize = n;
        siteCount = n * n;
        numTrials = trials;

        Stopwatch stopwatch = new Stopwatch();
        doTrials(n, trials);
        System.out.println("Elapsed time for trials with " + n + "-size grid and " + trials + " trials: " + stopwatch.elapsedTime() + " seconds.");
    }

    private void doTrials(int n, int trials) {
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);

            while (!p.percolates()) {
                int a = getRandomInt(nSize);
                int b = getRandomInt(nSize);
                p.open(a, b);
            }

            openSites[i] = p.numberOfOpenSites();
        }
    }

    private int getRandomInt(int n) {
        return StdRandom.uniform(1, n + 1);
    }

    /**
     * @return the mean value of the percolation threshold
     */
    public double mean() {
        mean = StdStats.mean(openSites) / siteCount;
        return mean;
    }

    /**
     * @return the standard deviation of the percolation threshold
     */
    public double stddev() {
        stddev = StdStats.stddev(openSites) / siteCount;
        return stddev;
    }

    /**
     * @return low  endpoint of 95% confidence interval
     */
    public double confidenceLo() {
        return mean - (CONF_95 * stddev / Math.sqrt(numTrials));
    }

    /**
     * @return high endpoint of 95% confidence interval
     */
    public double confidenceHi() {
        return mean + (CONF_95 * stddev / Math.sqrt(numTrials));
    }

    /**
     * Conducts a series of experiments on a percolation grid.
     * @param args [n, T] where n is the size of the n x n grid, and T is the number of trials to perform.
     */
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);

        if (n < 1 || T < 1) {
            throw new IllegalArgumentException();
        }

        PercolationStats stats = new PercolationStats(n, T);

        System.out.println(stats.mean());
        System.out.println(stats.stddev());
        System.out.println("[" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");
    }
}
