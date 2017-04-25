import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    
    private Percolation p;
    private double[] results;
    private int trials;

    // perform trials of independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        int randx, randy;
        this.trials = trials;
        results = new double[trials];
        if (n < 0 || trials < 0) {
            throw new java.lang.IllegalArgumentException("n and trials must be greater than 0");
        }    
        for (int i = 0; i < trials; i++) {
            p = new Percolation(n);
            while (!p.percolates()) {
                
                randx = StdRandom.uniform(n);
                randy = StdRandom.uniform(n);

                if (!p.isOpen(randx, randy)) {
                    p.open(randx, randy);        
                }
            }
            results[i] = p.numberOfOpenSites()*1.0/ (n*n); 
        }
        System.out.println("mean = " + mean());
        System.out.println("stddev = " + stddev());
        System.out.println("95% confidence interval = [" + confidenceLo() + ", " + confidenceHi() + "]");

    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(results);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(results);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.960 * (stddev()/Math.sqrt(trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.960 * (stddev()/Math.sqrt(trials));
    }

    public static void main(String[] args) {
        PercolationStats run 
            = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
    }
}
