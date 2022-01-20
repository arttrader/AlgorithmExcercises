/* *****************************************************************************
 *  Name:              J Hirota
 *  Coursera User ID:
 *  Last modified:     2022-1-19
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;

public class GrahamScan {
    Stack<Point2D> hull = new Stack<>();

    public void scan(Point2D[] p) {
        int n = p.length;

        Arrays.sort(p, Point2D.Y_ORDER);
        Arrays.sort(p, Point2D.R_ORDER);

        hull.push(p[0]);
        hull.push(p[1]);

        for (int i = 2; i < n; i++) {
            Point2D top = hull.pop();
            while (!hull.isEmpty() && Point2D.ccw(hull.peek(), top, p[i]) <= 0)
                top = hull.pop();
            top.drawTo(p[i]);
            StdDraw.show();
            StdDraw.pause(10);
            hull.push(top);
            hull.push(p[i]);
        }
    }

    public void plot() {

/*        while (!hull.isEmpty()) {
            Point2D p = hull.pop();
            p.drawTo(points[i]);
            StdDraw.show();
            StdDraw.pause(10);
        }*/
    }

    public static void main(String[] args) {
        int n = 100;
        Point2D[] p = new Point2D[n];
        int scale = 50;
        StdDraw.setCanvasSize(800, 800);
        StdDraw.setXscale(0, scale);
        StdDraw.setYscale(0, scale);
        StdDraw.setPenRadius(0.01);
        StdDraw.enableDoubleBuffering();
        for (int i = 0; i < n; i++) {
            int x = StdRandom.uniform(scale);
            int y = StdRandom.uniform(scale);
            p[i] = new Point2D(x, y);
            p[i].draw();
            StdDraw.show();
        }
        StdDraw.setPenRadius(0.001);
        GrahamScan gs = new GrahamScan();
        gs.scan(p);
//        gs.plot();
    }
}
