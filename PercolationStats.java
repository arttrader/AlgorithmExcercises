/* *****************************************************************************
 *  Name:              Junichi Hirota
 *  Coursera User ID:
 *  Last modified:     2022-01-11
 **************************************************************************** */

import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;
import static edu.princeton.cs.algs4.StdOut.println;
import static java.lang.Math.sqrt;

public class PercolationStats {
    private double[] trials;
    private int nTrials;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException();

        this.nTrials = trials;
        this.trials = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation perc = new Percolation(n);
            int t = n;
            if (n < 4) t = 1;
            perc.rndOpen(t);
            while (!perc.percolates()) {
                perc.rndOpen(1);
                t++;
                if (perc.percolates()) {
                    this.trials[i] = 1.0 * t / (n * n);
                    break;
                }
            }
            println("try: " + i + "  t: " + t + "   th " + this.trials[i]);
            // if (t<3) perc.display();
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(trials);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(trials);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean()-(1.96*stddev()/sqrt(nTrials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean()+(1.96*stddev()/sqrt(nTrials));
    }


    public static void main(String[] args) {
        Stopwatch sw = new Stopwatch();
        PercolationStats stats = new PercolationStats(2, 10000);
        double time = sw.elapsedTime();
        println("mean                    = " + stats.mean());
        println("stddev                  = " + stats.stddev());
        println("95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");
        println("elapsed time: " + time);
    }
}
