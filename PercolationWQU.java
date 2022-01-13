/* *****************************************************************************
 *  Name:              Junichi Hirota
 *  Coursera User ID:
 *  Last modified:     2022-01-11
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationWQU {
    class Grid {
        public int group;
        public int size;
        public boolean isOpen;

        public Grid(int p) {
            isOpen = false;
            group = p;
            size = 1;
        }
    }

    private Grid[] sites;
    private final int n;
    private final int vb;
    private int openCount = 0;

    // creates n-by-n grid, with all sites initially blocked
    public PercolationWQU(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        this.n = n;
        sites = new Grid[n*n+2];
        for (int i = 0; i < n*n+2; i++) {
            sites[i] = new Grid(i);
        }
        vb = n*n+1;
        for (int i = 1; i <= n; i++) {
            union(i, 0);
            union(n*(n-1)+i, vb);
        }
    }

    private int index(int row, int col) {
        if (row < 1 || col < 1 || row > n || col > n)
            throw new IllegalArgumentException();

        return (row-1) * n + col-1;
    }

    private int root(int i) {
        while (i!=sites[i].group) {
            i = sites[i].group;
        }
        return i;
    }

    private void union(int p, int q) {
        int pr = root(p);
        int qr = root(q);
        if (pr == qr) return;
        if (sites[pr].size < sites[qr].size) {
            sites[pr].group = qr;
            sites[qr].size += sites[pr].size;
        } else {
            sites[qr].group = pr;
            sites[pr].size += sites[qr].size;
        }
    }

    private int find(int p) {
        return root(p);
    }

    public boolean connected(int row1, int col1, int row2, int col2) {
        int p = index(row1, col1);
        int q = index(row2, col2);
        return root(p) == root(q);
    }


    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        int i = index(row, col);
        if (!sites[i].isOpen) {
            sites[i].isOpen = true;
            openCount++;
            if (row == 1) sites[0].isOpen = true;
            // check 4 sides
            if (row > 1) {
                int j = index(row-1, col);
                if (sites[j].isOpen) union(i, j);
            }
            if (row < n) {
                int j = index(row+1, col);
                if (sites[j].isOpen) union(i, j);
            }
            if (col > 1) {
                int j = index(row, col-1);
                if (sites[j].isOpen) union(i, j);
            }
            if (col < n) {
                int j = index(row, col+1);
                if (sites[j].isOpen) union(i, j);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return sites[index(row, col)].isOpen;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!isOpen(row, col)) return false;
        int p = find(0);
        int q = find(index(row, col));
        return (p == q);
    }

    // returns the number of open sites
    public int numberOfOpenSites() { return openCount; }

    // does the system percolate?
    public boolean percolates() {
        if (!sites[0].isOpen) return false;
        int p = find(0);
        int q = find(vb);
        return (p == q);
    }

    public void display() {
        String str = "";
        for (int i = 1; i <= n; i++) {
            if (i == 1) str += "[ ";
            else str += "  ";
            for (int j = 1; j <= n; j++) {
                str += isOpen(i, j) ? "1 " : "0 ";
            }
            if (i == n) str += "]\n";
            else str += " \n";
        }
        StdOut.print(str);
        str = "";
        for (int i = 0; i < n*n; i++) {
            str += sites[i].group + " ";
        }
        StdOut.print(str);
        StdOut.println(", percolates: " + (percolates() ? "true" : "false"));
    }

    public static void main(String[] args) {
        Stopwatch sw = new Stopwatch();
        PercolationWQU percolation = new PercolationWQU(10);
        StdOut.println(percolation.isOpen(10, 2));
        StdOut.println(percolation.isOpen(2, 2));
        StdOut.println(percolation.percolates());
        percolation.open(3, 3);
        StdOut.println(percolation.percolates());
        percolation.open(7, 3);
        StdOut.println(percolation.numberOfOpenSites());
        StdOut.println(percolation.percolates());
        double time = sw.elapsedTime();
        StdOut.println("elapsed time: " + time);
    }
}
