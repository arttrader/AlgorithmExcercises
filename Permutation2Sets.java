/* *****************************************************************************
 *  Name:              J Hirota
 *  Coursera User ID:
 *  Last modified:     2022-1-19
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;

public class Permutation2Sets {
    int[] a, b;

    public Permutation2Sets(int[] a1, int[] b1) {
        this.a = a1;
        this.b = b1;
        for (int i = 0; i < a.length; i++) {
            StdOut.printf("i %s: %s %s \n", i, a[i], b[i]);
        }
    }

    public boolean countIntersection() {
        Arrays.sort(a);
        Arrays.sort(b);
        int c = 0;
        for (int i = 0; i < a.length; i++) {
            if (a[i]!=b[i]) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        int n = 5;
        int[] a = new int[n];
        int[] b = new int[n];

        for (int i = 0; i < n; i++) {
            a[i] = StdRandom.uniform(n);
            b[i] = StdRandom.uniform(n);
        }
        Permutation2Sets is = new Permutation2Sets(a, b);
        StdOut.println(is.countIntersection());
    }
}
