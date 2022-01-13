/* *****************************************************************************
 *  Name:              Junichi Hirota
 *  Coursera User ID:
 *  Last modified:     2022-01-11
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private final int nTrials;
    private final double[] trials;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException();

        this.nTrials = trials;
        this.trials = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation perc = new Percolation(n);
            int t = 0;
            do {
                rndOpen(1, n, perc);
                t++;
            } while (!perc.percolates());
            this.trials[i] = 1.0 * t / (n * n);
//            StdOut.println(i + "  t: " + t + "   th " + this.trials[i]);
//            StdOut.println(perc.WQUcount);
        }
    }

    private void rndOpen(int t, int n, Percolation perc) {
        int i = 0;
        while (i < t) {
            int row = StdRandom.uniform(n) + 1;
            int col = StdRandom.uniform(n) + 1;
            if (!perc.isOpen(row, col)) {
                perc.open(row, col);
                i++;
            }
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
        return mean()-(CONFIDENCE_95*stddev()/Math.sqrt(nTrials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean()+(CONFIDENCE_95*stddev()/Math.sqrt(nTrials));
    }


    public static void main(String[] args) {
        int n, t;
        if (args.length == 2) {
            n = Integer.parseInt(args[0]);
            t = Integer.parseInt(args[1]);
        } else {
            n = 200;
            t = 100;
        }
//        Stopwatch sw = new Stopwatch();
        PercolationStats stats = new PercolationStats(n, t);
//        double time = sw.elapsedTime();
        StdOut.println("mean                    = " + stats.mean());
        StdOut.println("stddev                  = " + stats.stddev());
        StdOut.println("95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");
        // StdOut.println("elapsed time: " + time);
    }
}
