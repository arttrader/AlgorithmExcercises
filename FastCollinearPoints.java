/* *****************************************************************************
 *  Name:              J Hirota
 *  Coursera User ID:
 *  Last modified:     2022-1-24
 **************************************************************************** */

import edu.princeton.cs.algs4.BST;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;
import java.util.Comparator;

public class FastCollinearPoints {
    private final Point[] points;
    private final int n;
    private final BST<LineSegmentPlus, LineSegmentPlus> segments;

    public FastCollinearPoints(Point[] data) {
        // finds all line segments containing 4 points or more
        if (data == null) throw new IllegalArgumentException();
        n = data.length;
        points = new Point[n];
        for (int i = 0; i < n; i++)
            if (data[i] == null) throw new IllegalArgumentException();
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++)
                if (data[i].compareTo(data[j]) == 0)
                    throw new IllegalArgumentException(data[i].toString());
            points[i] = data[i];
        }

        segments = new BST<LineSegmentPlus, LineSegmentPlus>();
        Arrays.sort(this.points, new PointNaturalOrder());
        // findLineSegment(0);
       for (int i = 0; i < n-3; i++) findLineSegment(i);
    }

    private class PointNaturalOrder implements Comparator<Point> {
        public int compare(Point o1, Point o2) { return o1.compareTo(o2); }
    }
    
    private class LineSegmentPlus implements Comparable<LineSegmentPlus> {
        private final Point p;
        private final Point q;
        private final double slope;

        public LineSegmentPlus(Point min, Point max, double sl) {
            p = min;
            q = max;
            slope = sl;
        }

        public int compareTo(LineSegmentPlus c) {
            int ds = Double.compare(slope, c.slope);
            if (ds != 0) return ds;
            return q.compareTo(c.q);
        }

        public boolean collinear(LineSegmentPlus c) {
            return this.compareTo(c) == 0;
        }

        public Comparator<LineSegmentPlus> slopeOrder() {
            return new LineSegmentPlus.SlopeOrder();
        }

        private class SlopeOrder implements Comparator<LineSegmentPlus> {
            public int compare(LineSegmentPlus a, LineSegmentPlus b) {
                return a.compareTo(b);
            }
        }
    }

    private boolean containsSegment(LineSegmentPlus key) {
        LineSegmentPlus seg = segments.get(key);
        if (seg == null) return false;
        else return seg.collinear(key);
    }


    private void findLineSegment(int pi) {
        // sort for slope with respect to p
        // find a group of same slope, then check if it meets condition
        // repeat until the end
        Point p = points[pi];
        Arrays.sort(points, pi+1, n, p.slopeOrder());
        int skipCount = 0;
        for (int i = pi+1; i < n-1; i++) {
            if (p.slopeOrder().compare(points[i], points[i+1]) == 0) {
                skipCount = meetCondition(points, p, i);
                if (skipCount > 0) i += skipCount - 1;
            }
        }
    }

    private int meetCondition(Point[] aux, Point p, int si) {
        Point minPoint = p;
        Point maxPoint = p;
        double rsl = p.slopeTo(aux[si]);
        int es = 0;
        for (int i = si; i < aux.length; i++) {
            double sl = p.slopeTo(aux[i]);
            if (sl > rsl || sl < rsl) break;
            if (aux[i].compareTo(minPoint) < 0) minPoint = aux[i];
            if (aux[i].compareTo(maxPoint) > 0) maxPoint = aux[i];
            es++;
        }
        if (es >= 3) {
            LineSegmentPlus cp = new LineSegmentPlus(minPoint, maxPoint, rsl);
            if (!containsSegment(cp)) {
                segments.put(cp, cp);
                return es;
            }
        }
        return 0;
    }

    public int numberOfSegments() {
        // the number of line segments
        return segments.size();
    }

    public LineSegment[] segments() {
        // the line segments
        final LineSegment[] segs = new LineSegment[segments.size()];
        int i = 0;
        for (LineSegmentPlus s : segments.keys())
            segs[i++] = new LineSegment(s.p, s.q);
        return segs;
    }


    public static void main(String[] args) {
        String line = StdIn.readString();
        int n = Integer.parseInt(line);
        StdOut.printf("Input N: %s\n", n);
        Point[] points = new Point[n];
        int i = 0;
        while (!StdIn.isEmpty()) {
            String s1 = StdIn.readString();
            String s2 = StdIn.readString();
            int d1 = Integer.parseInt(s1);
            int d2 = Integer.parseInt(s2);
            points[i++] = new Point(d1, d2);
        }

/*        Arrays.sort(points, new Comparator<Point>() {
            public int compare(Point o1, Point o2) {
                return o1.compareTo(o2);
            }
        });
        Point p = points[0];
        StdOut.println("p " + p.toString());
        for (Point p1 : points) StdOut.println(p1.toString() + "  " + p.slopeTo(p1));*/

        // Arrays.sort(points);
        // Point p0 = points[0];
        // Arrays.sort(points, 1, n-1, p0.slopeOrder());
        // for (Point p1 : points) StdOut.println(p1.toString() + "  " + p0.slopeTo(p1));
/*        int scale = 50000;
        StdDraw.setCanvasSize(800, 800);
        StdDraw.setXscale(0, scale);
        StdDraw.setYscale(0, scale);
        StdDraw.setPenRadius(0.01);
        StdDraw.enableDoubleBuffering();
        for (i = 0; i < n; i++) {
            points[i].draw();
            StdDraw.show();
            StdDraw.pause(100);
        }
        StdDraw.setPenRadius(0.001);*/

        FastCollinearPoints bcp = new FastCollinearPoints(points);
        LineSegment[] segs = bcp.segments();
        StdOut.println(bcp.numberOfSegments());
        for (LineSegment seg : segs) StdOut.println(seg.toString());
        // mutate points random
         for (int j = 0; j < 10; j++) {
             for (i = 0; i < n; i++) {
                 int x = StdRandom.uniform(10000);
                 int y = StdRandom.uniform(10000);
                 points[i] = new Point(x, y);
             }
             segs = bcp.segments();
         }
         StdOut.println(bcp.numberOfSegments());
         for (LineSegment seg : segs) {
             StdOut.println(seg.toString());
             // seg.draw();
             // StdDraw.show();
         }
    }
}
