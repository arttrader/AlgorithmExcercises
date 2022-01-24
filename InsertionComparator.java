/* *****************************************************************************
 *  Name:              J Hirota
 *  Coursera User ID:
 *  Last modified:     2022-1-19
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.Comparator;

public class InsertionComparator {
    public static void sort(Object[] a, int lo, int hi, Comparator c) {
        for (int i = lo; i <= hi; i++)
            for (int j = i; j > lo; j--)
                if (less(a[j], a[j-1], c))
                    exch(a, j, j-1);
                else break;
    }

    private static boolean less(Object a, Object b, Comparator c) {
        return c.compare(a, b) < 0;
    }

    private static void exch(Object[] a, int i, int j) {
        Object s = a[i];
        a[i] = a[j];
        a[j] = s;
    }

    public static class Int {
        public final Comparator<Integer> REGULAR = new Regular();

        public static class Regular implements Comparator<Integer> {
            public int compare(Integer a, Integer b) {
                return Integer.compare(a, b);
            }
        }
    }



    public static void main(String[] args) {
        int n = 100000;
        Integer[] a = new Integer[n];
        for (int i = 0; i < n; i++)
            a[i] = StdRandom.uniform(n);

        Comparator c = new Int.Regular();

/*        for (int i = 0; i < n; i++)
            StdOut.printf("%s  %s \n", i+1, a[i]);*/
        Stopwatch sw = new Stopwatch();
        InsertionComparator.sort(a,0, n-1, c);
        double time = sw.elapsedTime();
        StdOut.println("elapsed time: " + time);
/*        for (int i = 0; i < n; i++)
            StdOut.printf("%s  %s \n", i+1, a[i]);*/
    }
}
