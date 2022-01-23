/* *****************************************************************************
 *  Name:              J Hirota
 *  Coursera User ID:
 *  Last modified:     2022-1-22
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

// Hint: count while mergesorting.

public class CountInversions {
    int count = 0;

    private void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
        assert isSorted(a, lo, mid);
        assert isSorted(a, mid+1, hi);

        for (int k = lo; k <= mid; k++) aux[k] = a[k];

        int i = lo, j = mid+1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid)                  { a[k] = a[j]; break; }
            else if (j > hi)                a[k] = aux[i++];
            else if (less(a[j], aux[i]))  {
                if (i < j) count++;
                a[k] = a[j++]; }
            else                            a[k] = aux[i++];
        }
    }

    private void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {
        int mid = lo + (hi - lo) / 2;
        Insertion.sort(a, lo, mid);
        Insertion.sort(a, mid+1, hi);
        merge(a, aux, lo, mid, hi);
    }

    public void sort(Comparable[] a) {
        Comparable[] aux = new Comparable[a.length / 2];
        sort(a, aux, 0, a.length-1);
    }

    public int count() { return count; }

    private static boolean isSorted(Comparable[] a, int lo, int hi) {
        for (int i = lo; i < hi; i++)
            if (less(a[i], a[i+1])) return false;
        return true;
    }

    private static boolean less(Comparable a, Comparable b) {
        return a.compareTo(b) < 0;
    }


    public static void main(String[] args) {
        int n = 10;
        Integer[] a = new Integer[n];
        for (int i = 0; i < n; i++)
            a[i] = StdRandom.uniform(n);

        CountInversions ci = new CountInversions();
        Stopwatch sw = new Stopwatch();
        ci.sort(a);
        double time = sw.elapsedTime();
        StdOut.println("elapsed time: " + time);
        StdOut.println(ci.count());
    }
}
