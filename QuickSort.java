/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Comparator;

public class QuickSort {
    private static int partition(Comparable[] a, int lo, int hi, Comparator c) {
        int i = lo, j = hi+1;
        while (true) {
            while (less(a[++i], a[lo], c)) if (i == hi) break;
            while (less(a[lo], a[--j], c)) if (j == lo) break;

            if (i >= j) break;
            exch(a, i, j);
        }
        exch(a, lo, j);
        return j;
    }

    private static boolean less(Comparable a, Comparable b, Comparator c) {
        return c.compare(a, b) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable s = a[i];
        a[i] = a[j];
        a[j] = s;
    }

    public static void sort(Comparable[] a, Comparator c) {
        StdRandom.shuffle(a); // performance can suffer without this
        sort(a, 0, a.length-1, c);
    }

    private static void sort(Comparable[] a, int lo, int hi, Comparator c) {
        if (hi <= lo) return;
        int j = partition(a, lo, hi, c);
        sort(a, lo, j-1, c);
        sort(a, j+1, hi, c);
    }

    public static void main(String[] args) {

    }
}
