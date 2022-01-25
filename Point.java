/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *  
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Comparator;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param  x the <em>x</em>-coordinate of the point
     * @param  y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        /* YOUR CODE HERE */
        if (that == null) throw new NullPointerException();
        if (that.y - this.y == 0)
            if (that.x - this.x == 0)
                return Double.NEGATIVE_INFINITY;
            else
                return 0.0;
        else
            if (that.x - this.x == 0)
                return Double.POSITIVE_INFINITY;
            else
                return 1.0 * (that.y - this.y) / (that.x - this.x);
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    public int compareTo(Point that) {
        /* YOUR CODE HERE */
        if (that == null) throw new NullPointerException();
        if (this.y < that.y) return -1;
        if (this.y > that.y) return +1;
        if (this.x < that.x) return -1;
        if (this.x > that.x) return +1;
        return 0;
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        /* YOUR CODE HERE */
        return new SlopeOrder();
    }

    private class SlopeOrder implements Comparator<Point> {
        public int compare(Point a, Point b) {
            double slope1 = slopeTo(a);
            double slope2 = slopeTo(b);
            // StdOut.println(a.toString() + "  " + b.toString());
            // StdOut.printf("a %s, b %s\n", slope1, slope2);
/*            if (slope1 < slope2) return -1;
            if (slope1 > slope2) return +1;
            else return 0;*/
           return Double.compare(slope1, slope2);
        }
    }

    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        /* YOUR CODE HERE */
        int n = 100;
        Point[] p = new Point[n];
/*        p[0] = new Point(4555, 19085);
        p[1] = new Point(38, 19085);
        StdOut.println(p[0].slopeTo(p[1]));
        p[2] = new Point(4555, 19085);
        p[3] = new Point(38, 19085);
        StdOut.println(p[2].slopeTo(p[3]));
        p[4] = new Point(7, 2);
        p[5] = new Point(8, 2);
        p[6] = new Point(1, 2);
        Comparator c = p[4].slopeOrder();
        StdOut.println(c.compare(p[5], p[6]));*/
         int scale = 50;
        StdDraw.setCanvasSize(800, 800);
        StdDraw.setXscale(0, scale);
        StdDraw.setYscale(0, scale);
        StdDraw.setPenRadius(0.005);
        StdDraw.enableDoubleBuffering();
        for (int i = 0; i < n; i++) {
            int x = StdRandom.uniform(scale);
            int y = StdRandom.uniform(scale);
            p[i] = new Point(x, y);
            p[i].draw();
            StdDraw.show();
        }
/*        Comparator<Point> c = new Point.NaturalOrder();
        Arrays.sort(p, c);*/
/*        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(Color.PINK);
        for (int i = 0; i < n; i++) {
            p[i].draw();
            StdDraw.show();
            StdDraw.pause(50);
        }*/
    }
}
