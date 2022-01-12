/* *****************************************************************************
 *  Name:              Junichi Hirota
 *  Coursera User ID:
 *  Last modified:     2022-01-11
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

public class WeightedQuickUnion {
    private int[] id;
    private int[] sz;

    public WeightedQuickUnion(int n) {
        id = new int[n];
        sz = new int[n];
        for (int i = 0; i < n; i++) {
            id[i] = i;
            sz[i] = 1;
        }
    }

    public int root(int i) {
        while (i!=id[i]) {
            id[i] = id[id[i]];
            i = id[i];
        }
        return i;
    }

    public boolean connected(int p, int q) {
        return root(p)==root(q);
    }

    public void union(int p, int q) {
        int pr = root(p);
        int qr = root(q);
        if (pr == qr) return;
        if (sz[pr] < sz[qr]) {
            id[pr] = qr;
            sz[qr] += sz[pr];
        } else {
            id[qr] = pr;
            sz[pr] += sz[qr];
        }
    }

    public void print() {
        String str = "array [";
        for (int i = 0; i < id.length; i++) {
            str += " " + id[i];
        }
        str += "]";
        StdOut.println(str);
        str = "size: [";
        for (int i = 0; i < sz.length; i++) {
            str += " " + sz[i];
        }
        str += "]";
        StdOut.println(str);
    }


    public static void main(String[] args) {
        WeightedQuickUnion a = new WeightedQuickUnion(11);
        a.print();
        a.union(5,2);
        a.print();
        a.union(7,3);
        a.print();
        a.union(1,5);
        a.print();
        a.union(7,5);
        a.print();
        StdOut.println(a.connected(7,2));
        StdOut.println(a.connected(7,5));
        StdOut.println(a.connected(3,5));
    }
}
