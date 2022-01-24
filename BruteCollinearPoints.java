/* *****************************************************************************
 *  Name:              J Hirota
 *  Coursera User ID:
 *  Last modified:     2022-1-24
 **************************************************************************** */

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private final Point[] points;
    private final int n;
    private LineSegment[] segments;

    public BruteCollinearPoints(Point[] points) {
        // finds all line segments containing 4 points
        if (points == null) throw new IllegalArgumentException();
        n = points.length;
        this.points = new Point[n];
        for (int i = 0; i < n; i++)
            if (points[i] == null) throw new IllegalArgumentException();
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++)
                if (points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException(points[i].toString());
            this.points[i] = points[i];
        }
    }

    public int numberOfSegments() {
        // the number of line segments
        if (segments != null) return segments.length;
        return 0;
    }

    private LineSegment lineSegment(Point p, Point q, Point r, Point s) {
        Point[] aux = new Point[4];
        aux[0] = p;
        aux[1] = q;
        aux[2] = r;
        aux[3] = s;
        Point minPoint = p;
        Point maxPoint = p;
        for (int i = 1; i < 4; i++) {
            if (aux[i].compareTo(minPoint) < 0) minPoint = aux[i];
            if (aux[i].compareTo(maxPoint) > 0) maxPoint = aux[i];
        }
        return new LineSegment(minPoint, maxPoint);
    }

    public LineSegment[] segments() {
        // the line segments
        if (segments != null) return segments.clone();

        Stack<LineSegment> stack = new Stack<>();
        int ns = 0;
        for (int p = 0; p < n; p++)
            for (int q = p+1; q < n; q++) {
                double spq = points[p].slopeTo(points[q]);
                for (int r = q + 1; r < n; r++) {
                    double spr = points[p].slopeTo(points[r]);
                    if (spq == spr) {
                        for (int s = r + 1; s < n; s++) {
                            double sps = points[p].slopeTo(points[s]);
                            if (spq == sps) {
    /*                            StdOut.println(points[p].toString());
                                StdOut.println(points[q].toString());
                                StdOut.println(points[r].toString());
                                StdOut.println(points[s].toString());*/
                                LineSegment seg = lineSegment(points[p], points[q], points[r],
                                                              points[s]);
                                stack.push(seg);
                                ns++;
                            }
                        }
                    }
                }
            }
        segments = new LineSegment[ns];
        int i = 0;
        while (!stack.isEmpty()) segments[i++] = stack.pop();
        return segments.clone();
    }


    public static void main(String[] args) {
        int n = 14;
        Point[] p = new Point[n];
        p[0] = new Point(1, 1);
        p[1] = new Point(3, 5);
        p[2] = new Point(5, 5);
        p[3] = new Point(12, 3);
        p[4] = new Point(3, 2);
        p[5] = new Point(4, 1);
        p[6] = new Point(2, 9);
        p[7] = new Point(16, 4);
        p[8] = new Point(8, 8);
        p[9] = new Point(2, 3);
        p[10] = new Point(4, 4);
        p[11] = new Point(1, 5);
        p[12] = new Point(8, 2);
        p[13] = new Point(15, 3);
/*        int scale = 50;
        StdDraw.setCanvasSize(800, 800);
        StdDraw.setXscale(0, scale);
        StdDraw.setYscale(0, scale);
        StdDraw.setPenRadius(0.01);
        StdDraw.enableDoubleBuffering();
        for (int i = 0; i < n; i++) {
            // int x = StdRandom.uniform(scale);
            // int y = StdRandom.uniform(scale);
            // p[i] = new Point(x, y);
            p[i].draw();
            StdDraw.show();
        }*/
        BruteCollinearPoints bcp = new BruteCollinearPoints(p);
        LineSegment[] segs = bcp.segments();
        StdOut.println(bcp.numberOfSegments());
        for (int i = 0; i < segs.length; i++)
            StdOut.println(segs[i].toString());
    }
}
