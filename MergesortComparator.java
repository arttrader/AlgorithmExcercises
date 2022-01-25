/* *****************************************************************************
 *  Name:              J Hirota
 *  Coursera User ID:
 *  Last modified:     2022-1-21
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.Comparator;

public class MergesortComparator {
    private static final int CUTOFF = 7;

    private static void merge(Object[] a, Object[] aux, int lo, int mid, int hi, Comparator c) {
        assert isSorted(a, lo, mid, c);
        assert isSorted(a, mid+1, hi, c);

        for (int k = lo; k <= hi; k++) aux[k] = a[k];

        int i = lo, j = mid+1;
        for (int k = lo; k <= hi; k++) {
        if (i > mid)                      { a[k] = aux[j++]; break; }
            else if (j > hi)                a[k] = aux[i++];
            else if (less(aux[j], aux[i], c))  a[k] = aux[j++];
            else                            a[k] = aux[i++];
        }
    }

    private static void sort(Object[] a, Object[] aux, int lo, int hi, Comparator c) {
        if (hi <= lo + CUTOFF - 1) {
            InsertionComparator.sort(a, lo, hi, c);
            return;
        }
        int mid = lo + (hi - lo) / 2;
        sort(a, aux, lo, mid, c);
        sort(a, aux, mid+1, hi, c);
        merge(a, aux, lo, mid, hi, c);
    }

    public static void sort(Object[] a, Comparator c) {
        Object[] aux = new Object[a.length];
        sort(a, aux, 0, a.length-1, c);
    }

    public static void sort(Object[] a, int lo, int hi, Comparator c) {
        Object[] aux = new Object[a.length];
        sort(a, aux, lo, hi, c);
    }

    private static boolean isSorted(Object[] a, int lo, int hi, Comparator c) {
        for (int i = lo; i < hi; i++)
            if (less(a[i], a[i+1], c)) return false;
        return true;
    }

    private static boolean less(Object a, Object b, Comparator c) {
        return c.compare(a, b) < 0;
    }

    public static void main(String[] args) {
        int n = 100000;
        Integer[] a = new Integer[n];
        for (int i = 0; i < n; i++)
            a[i] = StdRandom.uniform(n);

        Comparator c = new InsertionComparator.Int.Regular();

/*        for (int i = 0; i < n; i++)
            StdOut.printf("%s  %s \n", i+1, a[i]);*/
        Stopwatch sw = new Stopwatch();
        MergesortComparator.sort(a, c);
        double time = sw.elapsedTime();
        StdOut.println("elapsed time: " + time);
/*        for (int i = 0; i < n; i++)
            StdOut.printf("%s  %s \n", i+1, a[i]);*/
    }
}
