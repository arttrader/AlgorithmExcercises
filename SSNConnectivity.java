/* *****************************************************************************
 *  Name:              J Hirota
 *  Coursera User ID:  123456
 *  Last modified:     2022-1-14
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class SSNConnectivity {
    private final boolean[] open;
    private final int n;
    private final int vb;
    private final WeightedQuickUnionUF uf;
    private int openCount = 0;
    private int topCount = 0, botCount = 0;

    public SSNConnectivity(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        this.n = n;
        open = new boolean[n*n+2];
        for (int i = 0; i < n*n+2; i++) {
            open[i] = false;
        }
        vb = n*n+1;
        uf = new WeightedQuickUnionUF(n*n+2);
        for (int i = 1; i <= n; i++) {
            uf.union(i, 0);
            uf.union(n*(n-1)+i, vb);
        }
    }

    private int index(int row, int col) {
        if (row < 1 || col < 1 || row > n || col > n)
            throw new IllegalArgumentException();

        return (row-1) * n + col;
    }

    public void open(int row, int col) {
        int i = index(row, col);
        if (!open[i]) {
            open[i] = true;
            openCount++;
            // check 4 sides
            if (row > 1) {
                int j = index(row-1, col);
                if (open[j]) {
                    uf.union(i, j);
                }
            } else {
                topCount++;
                if (topCount == n) {
                    uf.union(i, 0);
                    open[0] = true;
                }
            }
            if (row < n) {
                int j = index(row+1, col);
                if (open[j]) uf.union(i, j);
            } else if (row == n) {
                botCount++;
                if (botCount == n) {
                    uf.union(n*(n-1)+i, vb);
                    open[vb] = true;
                }
            }
            if (col > 1) {
                int j = index(row, col-1);
                if (open[j]) uf.union(i, j);
            }
            if (col < n) {
                int j = index(row, col+1);
                if (open[j]) uf.union(i, j);
            }
        }
    }

    public boolean allConnected() {
        return open[0] && open[vb];
    }


    public static void main(String[] args) {

    }
}
