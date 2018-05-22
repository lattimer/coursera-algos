import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    
    private static final double CONFIDENCE_95 = 1.96;
    private final double[] trialArr;
    private final double mean;
    private final double stddev;

    public PercolationStats(int n, int trials) {    // perform trials independent experiments on an n-by-n grid
        
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        
        Percolation trial;
        
        this.trialArr = new double[trials];
        for (int i = 0; i < trials; i++) {
            trial = new Percolation(n);
            while (!trial.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                while (trial.isOpen(row, col)) {
                    row = StdRandom.uniform(1, n + 1);
                    col = StdRandom.uniform(1, n + 1);
                }
                trial.open(row, col);
            }
            this.trialArr[i] = (double) trial.numberOfOpenSites() / (double) (n * n);
        }
        this.mean = StdStats.mean(this.trialArr);
        this.stddev = StdStats.stddev(this.trialArr);
    }
    
    public double mean() {                          // sample mean of percolation threshold
        return this.mean;
    }
    
    public double stddev() {                     // sample standard deviation of percolation threshold
        return this.stddev;
    }   
    
    public double confidenceLo() {                // low  endpoint of 95% confidence interval
        return this.mean() - ((this.stddev() * CONFIDENCE_95) / Math.sqrt(this.trialArr.length));
    }
    public double confidenceHi() {                // high endpoint of 95% confidence interval
        return this.mean() + ((this.stddev() * CONFIDENCE_95) / Math.sqrt(this.trialArr.length));
    }

    public static void main(String[] args) {      // test client (described below)
        PercolationStats test = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.println("mean                    = " + test.mean());
        System.out.println("stddev                  = " + test.stddev());
        System.out.println("95% confidence interval = [" + test.confidenceLo() + ", " + test.confidenceHi() + "]");
    }
}