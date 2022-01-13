/* *****************************************************************************
 *  Name:              Junichi Hirota
 *  Coursera User ID:
 *  Last modified:     2022-01-11
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[] sites;
    private int n;
    private WeightedQuickUnionUF qu;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        this.n = n;
        sites = new boolean[n*n];
        for (int i = 0; i < n*n; i++) {
            sites[i] = false;
        }
        qu = new WeightedQuickUnionUF(n*n);
    }

    private void checkLimits(int row, int col) {
        if (row < 1 || col < 1 || row > n || col > n)
            throw new IllegalArgumentException();
    }

    private int index(int row, int col) {
        checkLimits(row, col);
        return (row-1) * n + col-1;
    }

    private boolean connected(int row1, int col1, int row2, int col2) {
        int p = index(row1, col1);
        int q = index(row2, col2);
        return qu.find(p) == qu.find(q);
    }


    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        int i = index(row, col);
        if (!sites[i]) {
            sites[i] = true;
            // check 4 sides
            if (row > 1) {
                int j = index(row-1, col);
                if (sites[j]) qu.union(i, j);
            }
            if (col > 1) {
                int j = index(row, col-1);
                if (sites[j]) qu.union(i, j);
            }
            if (row < n) {
                int j = index(row+1, col);
                if (sites[j]) qu.union(i, j);
            }
            if (col < n) {
                int j = index(row, col+1);
                if (sites[j]) qu.union(i, j);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return sites[index(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return !isOpen(row, col);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int no = 0;
        for (int i = 0; i < n * n; i++)
            if (sites[i]) no++;
        return no;
    }

    // does the system percolate?
    public boolean percolates() {
        for (int i = 1; i <= n; i++) {
            if (isOpen(1, i)) {
                for (int j = 1; j <= n; j++)
                    if (isOpen(n, j))
                        if (connected(1, i, n, j)) return true;
            }
        }

        return false;
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
