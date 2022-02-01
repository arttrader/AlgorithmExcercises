/* *****************************************************************************
 *  Name:              J Hirota
 *  Coursera User ID:
 *  Last modified:     2022-1-29
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class QuckSort3Way {
    private static void exch(Comparable[] a, int i, int j) {
        Comparable s = a[i];
        a[i] = a[j];
        a[j] = s;
    }

    private static void sort(Comparable[] a, int lo, int hi) {
        if (hi <= lo) return;
        int lt = lo;
        int gt = hi;
        Comparable v = a[lo];
        int i = lo;
        while (lt < gt) {
            int cmp = a[i].compareTo(v);
            if      (cmp < 0) exch(a, lt++, i++);
            else if (cmp > 0) exch(a, i, gt--);
            else i++;
        }
        sort(a, lo, lt-1);
        sort(a, gt+1, hi);
    }

    public static void sort(Comparable[] a) {
        StdRandom.shuffle(a); // performance can suffer without this
        sort(a, 0, a.length-1);
    }


    public static void main(String[] args) {
        int n = 10;
        Integer[] a = new Integer[n];
        for (int i = 0; i < n; i++) a[i] = StdRandom.uniform(n);
        for (int i = 0; i < n; i++)
            StdOut.printf("%s  %s \n", i+1, a[i]);
        QuckSort3Way.sort(a);
        for (int i = 0; i < n; i++)
            StdOut.printf("%s  %s \n", i+1, a[i]);
    }
}
