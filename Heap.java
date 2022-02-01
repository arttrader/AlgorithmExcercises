/* *****************************************************************************
 *  Name:              J Hirota
 *  Coursera User ID:
 *  Last modified:     2022-1-31
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

public class Heap {

    public static void sort(Comparable[] a) {
        int n = a.length-1;
        for (int k = n/2; k >= 1; k--)
            sink(a, k, n);
        while (n > 1) {
            exch(a, 1, n);
            sink(a, 1, --n);
        }
    }

    private static boolean less(Comparable[] a, int i, int j) {
        return a[i].compareTo(a[j]) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable s = a[i];
        a[i] = a[j];
        a[j] = s;
    }

    private static void sink(Comparable[] a, int k, int n) {
        while (2*k <= n) {
            int j = 2*k;
            if (j < n && less(a, j, j+1)) j++;
            if (!less(a, k, j)) break;
            exch(a, k, j);
            k = j;
        }
    }

    private void swim(Comparable[] a, int k) {
        while (k > 1 && less(a,k/2, k)) {
            exch(a, k,k/2);
            k = k/2;
        }
    }


    public static void main(String[] args) {
        int n = 10;
        Integer[] a = new Integer[n];
        for (int i = 0; i < n; i++)
            a[i] = StdRandom.uniform(n);
        for (int i = 0; i < n; i++)
            StdOut.printf("%s  %s \n", i+1, a[i]);
        Stopwatch sw = new Stopwatch();
        Heap.sort(a);
        double time = sw.elapsedTime();
        StdOut.println("elapsed time: " + time);
        for (int i = 0; i < n; i++)
            StdOut.printf("%s  %s \n", i+1, a[i]);
    }
}
