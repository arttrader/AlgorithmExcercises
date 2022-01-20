/* *****************************************************************************
 *  Name:              J Hirota
 *  Coursera User ID:
 *  Last modified:     2022-1-19
 **************************************************************************** */

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

import java.awt.Color;

public class DutchFlag {
    private int n;
    private Pebble[] buckets;
    private int swapCount = 0;
    private int colorCount = 0;
    private Stack<Pebble> reds, whites, blues;

    public DutchFlag(int n) {
        this.n = n;
        buckets = new Pebble[n];
        for (int i = 0; i < n; i++)
            buckets[i] = new Pebble();
        reds = new Stack<>();
        whites = new Stack<>();
        blues = new Stack<>();
    }

    public class Pebble implements Comparable<Pebble> {
        private int color;

        public Pebble() {
            this.color = StdRandom.uniform(3);
        }

        public int compareTo(Pebble o) {
            if (this.color < o.color) return -1;
            if (this.color > o.color) return 1;
            return 0;
        }

        public Color color() {
            colorCount++;
            switch (color) {
                case 0: return Color.RED;
                case 1: return Color.WHITE;
                case 2: return Color.BLUE;
            }
            return null;
        }
    }

    public void sort() {
        for (int i = 0; i < n; i++) {
            Color c = buckets[i].color();
            if (c == Color.RED)
                reds.push(buckets[i]);
            else if (c == Color.WHITE)
                whites.push(buckets[i]);
            else
                blues.push(buckets[i]);
        }
        int i = 0;
        while (!reds.isEmpty())
            buckets[i++] = reds.pop();
        while (!whites.isEmpty())
            buckets[i++] = whites.pop();
        while (!blues.isEmpty())
            buckets[i++] = blues.pop();
    }

    public void sort1() {
        int h = 1;
        while (h < n/3) h = 3*h+1;
        while (h >= 1) {
            for (int i = h; i < n; i++)
                for (int j = i; j >= h && less(buckets[j], buckets[j-h]); j -= h)
                    swap(buckets, j, j-h);
            h = h/3;
        }
    }

    private boolean less(Pebble v, Pebble w) {
        return v.compareTo(w) < 0;
    }

    private void swap(Pebble[] a, int i, int j) {
        swapCount++;
        Pebble s = a[i];
        a[i] = a[j];
        a[j] = s;
    }


    public void getCounts() {
        StdOut.printf("color: %s, swap: %s  \n", colorCount, swapCount);
    }

    public void showBukets() {
        for (int i = 0; i < n; i++)
            StdOut.printf("%s  %s \n", i+1, buckets[i]);
    }

    public static void main(String[] args) {
        int n = 1000;

        DutchFlag df = new DutchFlag(n);
        Stopwatch sw = new Stopwatch();
        df.sort();
        double time = sw.elapsedTime();
        StdOut.println("elapsed time: " + time);
        df.getCounts();

        DutchFlag df1 = new DutchFlag(n);
        Stopwatch sw1 = new Stopwatch();
        df1.sort1();
        double time1 = sw1.elapsedTime();
        StdOut.println("elapsed time: " + time1);
        df1.getCounts();

/*        for (int i = 0; i < n; i++)
            StdOut.printf("%s  %s \n", i+1, a[i]);*/
    }
}
