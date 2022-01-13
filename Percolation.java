/* *****************************************************************************
 *  Name:              Junichi Hirota
 *  Coursera User ID:
 *  Last modified:     2022-01-11
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final boolean[] open;
    private final int n;
    private final int vb;
    private final WeightedQuickUnionUF qu;
    private int openCount = 0;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        this.n = n;
        open = new boolean[n*n+2];
        for (int i = 0; i < n*n+2; i++) {
            open[i] = false;
        }
        vb = n*n+1;
        qu = new WeightedQuickUnionUF(n*n+2);
        for (int i = 1; i <= n; i++) {
            qu.union(i, 0);
            qu.union(n*(n-1)+i, vb);
        }
    }

    private int index(int row, int col) {
        if (row < 1 || col < 1 || row > n || col > n)
            throw new IllegalArgumentException();

        return (row-1) * n + col;
    }


    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        int i = index(row, col);
        if (!open[i]) {
            open[i] = true;
            openCount++;
            if (row == 1) open[0] = true;
            // check 4 sides
            if (row > 1) {
                int j = index(row-1, col);
                if (open[j]) qu.union(i, j);
            }
            if (row < n) {
                int j = index(row+1, col);
                if (open[j]) qu.union(i, j);
            }
            if (col > 1) {
                int j = index(row, col-1);
                if (open[j]) qu.union(i, j);
            }
            if (col < n) {
                int j = index(row, col+1);
                if (open[j]) qu.union(i, j);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return open[index(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        int i = index(row, col);
        if (!open[i]) return false;
        int p = qu.find(0);
        int q = qu.find(i);
        return (p == q);
    }

    // returns the number of open sites
    public int numberOfOpenSites() { return openCount; }

    // does the system percolate?
    public boolean percolates() {
        if (!open[0]) return false;
        int p = qu.find(0);
        int q = qu.find(vb);
        return (p == q);
    }

    public static void main(String[] args) {
        Stopwatch sw = new Stopwatch();
        Percolation percolation = new Percolation(10);
        percolation.open(5, 6);
        StdOut.println(percolation.numberOfOpenSites());
        StdOut.println(percolation.percolates());
        double time = sw.elapsedTime();
        StdOut.println("elapsed time: " + time);
    }
}
