/* *****************************************************************************
 *  Name:              J Hirota
 *  Coursera User ID:
 *  Last modified:     2022-1-29
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

public class NutsAndBolts {
    private final int n;
    private final Bolt[] bolts;
    private final Nut[] nuts;
    private final NutAndBolt[] nutAndBolts;

    public NutsAndBolts(int n) {
        this.n = n;
        bolts = new Bolt[n];
        nuts = new Nut[n];
        nutAndBolts = new NutAndBolt[n];
    }

    private class Nut implements Comparable<Bolt> {
        final double diameter;

        public Nut(double d) {
            diameter = d;
        }

        public int compareTo(Bolt o) {
            return Double.compare(this.diameter, o.diameter);
        }
    }

    private class Bolt implements Comparable<Nut> {
        final double diameter;

        public Bolt(double d) {
            diameter = d;
        }

        public int compareTo(Nut o) {
            return Double.compare(this.diameter, o.diameter);
        }
    }

    private class NutAndBolt {
        Nut nut;
        Bolt bolt;

        public NutAndBolt(Nut n, Bolt b) {
            nut = n;
            bolt = b;
        }
    }

    // standard quick sort because of randomness and no duplicate keys
    private int partition(Nut[] a, Bolt[] b, int lo, int hi) {
        int i = lo, j = hi+1;
        while (true) {
            while (less(a[++i], a[lo])) if (i == hi) break;
            while (less(a[lo], a[--j])) if (j == lo) break;
            if (nutAndBolts[i] == null) nutAndBolts[i] = new NutAndBolt(a[i], b[i]);
            if (i >= j) break;
            exch(a, i, j);
        }
        exch(a, lo, j);
        return j;
    }

    private static boolean less(Comparable a, Comparable b) {
        return a.compareTo(b) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable s = a[i];
        a[i] = a[j];
        a[j] = s;
    }

    public void sort(Nut[] a, Bolt[] b) {
        StdRandom.shuffle(a); // performance can suffer without this
        sort(a, b, 0, a.length-1);
    }

    private void sort(Nut[] a, Bolt[] b, int lo, int hi) {
        if (hi <= lo) return;
        int j = partition(a, b, lo, hi);
        sort(a, b, lo, j-1);
        sort(a, b, j+1, hi);
    }

    public static void main(String[] args) {
        int n = 100000;
        Integer[] a = new Integer[n];
        for (int i = 0; i < n; i++)
            a[i] = StdRandom.uniform(n);
/*        for (int i = 0; i < n; i++)
            StdOut.printf("%s  %s \n", i+1, a[i]);*/


    }
}
