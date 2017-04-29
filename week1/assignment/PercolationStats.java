import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
    @author: Coy Sanders
    @version: 04/28/2017

    Compilation: javac-algs4 PercolationClass.java
    Execution: java-algs4 PercolationStats [gridDimension] [trials]

    Runs a series of computational experiments to compute statistical measurements of percolation thresholds on a sizeable grid
     run over a series of trials.
    Each trial will randomly open up a site on the grid until the system can percolate. Results will be collected for each trial
     and public functions can be run to gather statistical analysis on the results.
*/
public class PercolationStats {
    
    private double[] results;
    private int trials;

    /**
      @param n the number of sites on one side of the square grid.
      @param trials number of experiments to run.
    */
    public PercolationStats(int n, int trials) {
        Percolation p;
        int randx, randy;
        this.trials = trials;
        results = new double[trials];
        if (n <= 0 || trials <= 0) {
            throw new java.lang.IllegalArgumentException("n and trials must be greater than 0");
        }
        for (int i = 0; i < trials; i++) {
            p = new Percolation(n);

            while (!p.percolates()) {
                randx = StdRandom.uniform(1, n+1); // isOpen() requires range (1-n), not zero-indexed.
                randy = StdRandom.uniform(1, n+1);

                if (!p.isOpen(randx, randy)) p.open(randx, randy);
            }

            results[i] = p.numberOfOpenSites()*1.0/ (n*n); 
        }
    }

    /**
      Sample mean of the percolation threshold over all trials.
      @return returns a floating point value of the mean result.
    */
    public double mean() {
        return StdStats.mean(results);
    }

    /**
      Sample standard deviation of the percolation threshold over all trials.
      @return returns a floating point value of the standard deviation result.
    */
    public double stddev() {
        if (trials == 1) return Double.NaN;
        return StdStats.stddev(results);
    }

    /**
      @return returns a double representing the low endpoint of the 95% confidence interval.
    */
    public double confidenceLo() {
        if (trials == 1) return Double.NaN;
        return mean() - 1.960 * (stddev()/Math.sqrt(trials));
    }

    /**
      @return returns a double representing the high endpoint of the 95% confidence interval.
    */
    public double confidenceHi() {
        if (trials == 1) return Double.NaN;
        return mean() + 1.960 * (stddev()/Math.sqrt(trials));
    }
    
    /**
      Test client, user can run this test manually and view the results.
      Execution: java-algs4 PercolationStats [gridDimension] [trials]
      @param args takes two arguments, size of dimension of the grid and number of trials to run.
    */
    public static void main(String[] args) {
        PercolationStats ps = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.println("mean = " + ps.mean());
        System.out.println("stddev = " + ps.stddev());
        System.out.println("95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
    }
}
