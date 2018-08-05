/**
 * This class works in conjunction with the Percolation class to determine the mean percolation threshold after T trials.
 */

import edu.princeton.cs.algs4.StdStats;
import static edu.princeton.cs.algs4.StdRandom.uniform;

public class PercolationStats {

    private double[] thresholds;    // Stored values of the calculated thresholds after Percolation trials
    private double[] openSites;    // Stored values of the open sites when the system percolates
    private int nSize;          // Size of n x n grid
    private int siteCount;      // Total number of sites in the grid
    private int numTrials;      // Number of trials performed

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials  <= 0) {
            throw new IndexOutOfBoundsException();
        }

        thresholds = new double[trials];
        openSites = new double[trials];
        nSize = n;
        siteCount = n * n;
        numTrials = trials;

        doTrials(n, trials);
    }

    private void doTrials(int n, int T) {
        for (int i = 0; i < T; i++) {
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
        return uniform(1, n + 1);
    }

    /**
     * @return the mean value of the percolation threshold
     */
    public double mean() {
        return StdStats.mean(openSites) / siteCount;
    }

    /**
     * @return the standard deviation of the percolation threshold
     */
    public double stddev() {
        return StdStats.stddev(openSites) / siteCount;
    }

    /**
     * @return low  endpoint of 95% confidence interval
     */
    public double confidenceLo() {
        return mean() - (1.96 * stddev() / numTrials);
    }

    /**
     * @return high endpoint of 95% confidence interval
     */
    public double confidenceHi() {
        return mean() + (1.96 * stddev() / numTrials);
    }

    /**
     * Conducts a series of experiments on a percolation grid.
     * @param args [n, T] where n is the size of the n x n grid, and T is the number of trials to perform.
     */
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, T);

        System.out.println(stats.mean());
        System.out.println(stats.stddev());
        System.out.println("[" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");
    }
}
