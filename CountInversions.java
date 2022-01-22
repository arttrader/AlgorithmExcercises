/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

// Hint: count while mergesorting.

public class CountInversions {
    public static void main(String[] args) {
        int n = 1000;
        Integer[] a = new Integer[n];
        for (int i = 0; i < n; i++)
            a[i] = StdRandom.uniform(n);

        Stopwatch sw = new Stopwatch();
        int c = 0;
        for (int i = 0; i < n-1; i++)
            for (int j = i; j < n; j++)
                if (a[i] > a[j]) {
//                    StdOut.printf("i,j: %s,%s  a[i],a[j] %s,%s \n",i,j, a[i],a[j]);
                    c++;
                }
        double time = sw.elapsedTime();
        StdOut.println("elapsed time: " + time);
        StdOut.println(c);
    }
}
