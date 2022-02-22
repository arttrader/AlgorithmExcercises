/* *****************************************************************************
 *  Name:              J Hirota
 *  Coursera User ID:
 *  Last modified:     2022-2-18
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.TreeSet;
import java.util.stream.Collectors;

public class
PointSET {
    private final TreeSet<Point2D> set;
    // construct an empty set of points
    public PointSET() {
        set = new TreeSet<>();
    }

    // is the set empty?
    public boolean isEmpty() { return set.isEmpty(); }

    // number of points in the set
    public int size() { return set.size(); }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (!set.contains(p)) set.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        return set.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D p : set) {
            p.draw();
            StdDraw.show();
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        return set.stream().filter(p -> rect.contains(p)).collect(Collectors.toSet());
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        Point2D nearest = null;
        double shortest = Double.POSITIVE_INFINITY;
        for (Point2D ep : set) {
            double dist = p.distanceSquaredTo(ep);
            if (dist < shortest) {
                nearest = ep;
                shortest = dist;
            }
        }
        return nearest;
    }


    public static void main(String[] args) {
        PointSET set = new PointSET();
        while (!StdIn.isEmpty()) {
            double x = Double.parseDouble(StdIn.readString());
            double y = Double.parseDouble(StdIn.readString());
            Point2D p = new Point2D(x, y);
            set.insert(p);
        }
        for (Point2D p : set.set) StdOut.println(p.toString());

        double scale = 1.0;
        StdDraw.setCanvasSize(600, 600);
        StdDraw.setXscale(0, scale);
        StdDraw.setYscale(0, scale);
        StdDraw.setPenRadius(0.01);
        StdDraw.enableDoubleBuffering();
        set.draw();
    }
}
