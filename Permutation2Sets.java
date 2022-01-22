/* *****************************************************************************
 *  Name:              J Hirota
 *  Coursera User ID:
 *  Last modified:     2022-1-19
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;

public class Permutation2Sets {
    Point2D[] a, b;

    public Permutation2Sets(Point2D[] a1, Point2D[] b1) {
        Arrays.sort(a1, Point2D.Y_ORDER);
        this.a = a1;
        Arrays.sort(b1, Point2D.Y_ORDER);
        this.b = b1;
    }

    public int countIntersection1() {
        int c = 0;
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b.length; j++) {
                if (a[i].compareTo(b[j]) == 0) {
                    StdOut.printf("i %s j %s: x %s y %s, x %s y %s *\n", i, j, a[i].x(), a[i].y(), b[j].x(), b[j].y());
                    c++;
                }
            }
        }
        return c;
    }

    public int countIntersection() {
        int count = 0;
        int j = 0;
        int lastJ = j;
        for (int i = 0; i < a.length; i++) {
             while (j < b.length && a[i].y() >= b[j].y()) {
                if (b[j].y() > b[lastJ].y()) lastJ = j;
                int l = a[i].compareTo(b[j]);
                if (l == 0) {
                    StdOut.printf("i %s j %s: x %s y %s, x %s y %s *\n", i, j, a[i].x(), a[i].y(), b[j].x(), b[j].y());
                    count++;
                }
                j++;
            }
            j = lastJ;
        }
        return count;
    }


    public static void main(String[] args) {
        int n = 100;
        int scale = 50;
        Point2D[] a = new Point2D[n];
        Point2D[] b = new Point2D[n];

/*        StdDraw.setCanvasSize(800, 800);
        StdDraw.setXscale(0, scale);
        StdDraw.setYscale(0, scale);
        StdDraw.setPenRadius(0.01);
        StdDraw.enableDoubleBuffering();
        StdDraw.setPenColor(StdDraw.RED);*/
        for (int i = 0; i < n; i++) {
            int x = StdRandom.uniform(scale);
            int y = StdRandom.uniform(scale);
            a[i] = new Point2D(x, y);
            // a[i].draw();
            // StdDraw.show();
        }
        // StdDraw.setPenColor(StdDraw.BLUE);
        for (int i = 0; i < n; i++) {
            int x = StdRandom.uniform(scale);
            int y = StdRandom.uniform(scale);
            b[i] = new Point2D(x, y);
            // b[i].draw();
            // StdDraw.show();
         }
        // StdDraw.setPenRadius(0.001);
        // StdDraw.setPenColor(StdDraw.BLACK);
        Intersection2Sets is = new Intersection2Sets(a, b);
        StdOut.println(is.countIntersection1());
        StdOut.println();
        StdOut.println(is.countIntersection());
    }
}
