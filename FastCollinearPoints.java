/* *****************************************************************************
 *  Name:              J Hirota
 *  Coursera User ID:
 *  Last modified:     2022-1-24
 **************************************************************************** */

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {
    private final Point[] points;
    private final int n;
    private LineSegment[] segments;

    public FastCollinearPoints(Point[] points) {
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

    private LineSegment findLineSegment(int pi) {
        if (pi > n-4) return null;
        Point p = points[pi];
        Arrays.sort(points, pi+1, n, p.slopeOrder());
        // for (Point p1 : points) StdOut.println(p1.toString());
        // StdOut.println("For point at " + pi + "  " + points[pi].toString());
        int es = 0;
        Point minPoint = p;
        Point maxPoint = p;
        double rsl = p.slopeTo(points[pi+1]);
        for (int i = pi+1; i < n; i++) {
            double sl = p.slopeTo(points[i]);
            // StdOut.println(points[i].toString() + "   " + sl);
            if (sl > rsl || sl < rsl) break;
            if (points[i].compareTo(minPoint) < 0) minPoint = points[i];
            if (points[i].compareTo(maxPoint) > 0) maxPoint = points[i];
            es++;
        }
        if (es >= 3) {
//            StdOut.println("Segment found");
            return new LineSegment(minPoint, maxPoint);
        }
        return null;
    }

    public LineSegment[] segments() {
        // the line segments
        if (segments == null) {
            Stack<LineSegment> stack = new Stack<>();
            int ns = 0;
            for (int i = 0; i < n; i++) {
                LineSegment seg = findLineSegment(i);
                if (seg != null) {
                    stack.push(seg);
                    ns++;
                }
            }
            segments = new LineSegment[ns];
            int i = 0;
            while (!stack.isEmpty()) segments[i++] = stack.pop();
        }
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
        Point p0 = p[0];
        Arrays.sort(p, 1, n, p0.slopeOrder());
        for (Point p1 : p) StdOut.println(p1.toString());
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
            StdOut.println(p[i].toString());
            p[i].draw();
            StdDraw.show();
        }*/

        /*
        FastCollinearPoints bcp = new FastCollinearPoints(p);
        LineSegment[] segs = bcp.segments();
        StdOut.println(bcp.numberOfSegments());
        for (LineSegment seg : segs) StdOut.println(seg.toString());
        */
    }
}
