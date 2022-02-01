/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;

/*
    Hint: there are two basic approaches.
        Approach A: Compute the median in a[] and the median in b[]. Recur in a subproblem of roughly half the size.
        Approach B: Design a constant-time algorithm to determine whether a[i] is a key of rank k. Use this subroutine and binary search.

        Dealing with corner cases can be tricky. */
public class SelectionInTwoArrays {
    private final int k;
    private final int[] a;
    private final int[] b;
    private final int[] c;
    private final int n1;
    private final int n2;

    public SelectionInTwoArrays(int[] a, int[] b, int k) {
        this.n1 = a.length;
        this.a = new int[n1];
        for (int i = 0; i < n1; i++) this.a[i] = a[i];
        this.n2 = b.length;
        this.b = new int[n2];
        for (int i = 0; i < n2; i++) this.b[i] = b[i];
        this.k = k;
        c = new int[n1+n2];
        merge(this.a, this.b, c, 0, n1-1, n1+n2-1);
    }

    public int[] result() {
        return c;
    }

    public int getKth() {
        // use binary search to find k
        int lo = 0;
        int hi = n1+n2-1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (c[mid] > k) hi = mid - 1;
            else if (c[mid] < k) lo = mid + 1;
            else return mid+1;
        }
//        for (int i = 0; i < n1+n2; i++) if (c[i] == k) return i+1;
        return -1;
    }

    private static boolean less(int p, int r) {
        return p < r;
    }

    private static void merge(int[] a, int[] b, int[] c, int lo, int mid, int hi) {
        // assume a,b are sorted
        int n1 = a.length;
        int n2 = b.length;
        int[] aux = new int[n1+n2];
        for (int k = lo; k < a.length; k++) {
            aux[k] = a[k];
            c[k] = a[k];
        }
        for (int k = lo; k < b.length; k++) {
            aux[k+n1] = b[k];
            c[k+n1] = b[k];
        }

        int i = lo, j = mid+1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid)                  { c[k] = aux[j]; break; }
            else if (j > hi)                c[k] = aux[i++];
            else if (less(aux[j], aux[i]))  c[k] = aux[j++];
            else                            c[k] = aux[i++];
        }
    }

    public static void main(String[] args) {
        int n = 10;
        int[] a = new int[n];
        int[] b = new int[n];
        for (int i = 0; i < n; i++) a[i] = StdRandom.uniform(n);
        for (int i = 0; i < n; i++) b[i] = i;
        int k = n / 2;
        Arrays.sort(a);
        SelectionInTwoArrays si2 = new SelectionInTwoArrays(a, b, k);
        int[] c = si2.result();
        for (int i : c) StdOut.println(i);
        StdOut.println("k: " + k + "   " + si2.getKth());
    }
}
