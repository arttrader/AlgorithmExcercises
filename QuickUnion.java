/* *****************************************************************************
 *  Name:              J Hirota
 *  Coursera User ID:
 *  Last modified:     January 14, 2022
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

public class QuickUnion {
    private int[] id;

    public QuickUnion(int n) {
        id = new int[n];
        for (int i = 0; i < n; i++) {
            id[i] = i;
        }
    }

    public int root(int i) {
        while (i != id[i]) i = id[i];
        return i;
    }

    public int find(int i) {
        int lgt = i;
        while (i != id[i]) {
            StdOut.println(i);
            if (i > lgt) lgt = i;
            i = id[i];
        }
        if (lgt > i) return lgt;
        else return i;
    }

    public boolean connected(int p, int q) { return root(p) == root(q); }

    public void union(int p, int q) {
        int pr = root(p);
        int qr = root(q);
        if (pr == qr) return;
        if (pr < qr) {
            id[pr] = qr;
        } else {
            id[qr] = pr;
        }
    }


    public static void main(String[] args) {
        QuickUnion qu = new QuickUnion(10);
        qu.union(1, 2);
        qu.union(2, 6);
        qu.union(2, 6);
        for (int i = 0; i < qu.id.length; i++)
            StdOut.print(qu.id[i] + " ");
        StdOut.println();
        StdOut.println(qu.find(1));
    }
}
