/* *****************************************************************************
 *  Name:              J Hirota
 *  Coursera User ID:
 *  Last modified:     2022-1-24
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class FastCollinearPointsWExtraList {
    private final Point[] points;
    private final int n;
    private final ArrayList<LineSegment> segments;
    private final ArrayList<CollinearPoint> discovered;

    public FastCollinearPointsWExtraList(Point[] points) {
        // finds all line segments containing 4 points or more
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

        segments = new ArrayList<LineSegment>();
        discovered = new ArrayList<>();
        Arrays.sort(this.points, new Comparator<Point>() {
            public int compare(Point o1, Point o2) {
                return o1.compareTo(o2);
            }
        }); // natural order sort before sort by slope
        // findLineSegment(17); // for debug
        for (int i = 0; i < n; i++) findLineSegment(i);
    }

    private class CollinearPoint implements Comparable<CollinearPoint> {
        Point minPoint;
        Point maxPoint;
        double slope;

        public CollinearPoint(Point min, Point max, double sl) {
            minPoint = min;
            maxPoint = max;
            slope = sl;
        }

        public int compareTo(CollinearPoint c) {
            int ds = Double.compare(slope, c.slope);
            if (ds != 0) return ds;
            if (c.minPoint.compareTo(minPoint) == 0)
                return c.maxPoint.compareTo(maxPoint);
            else
                return c.minPoint.compareTo(minPoint);
        }

        public Comparator<CollinearPoint> slopeOrder() {
            return new SlopeOrder();
        }

        private class SlopeOrder implements Comparator<CollinearPoint> {
            public int compare(CollinearPoint a, CollinearPoint b) {
                return a.compareTo(b);
            }
        }
    }

    private boolean less(CollinearPoint a, CollinearPoint b) {
        return a.compareTo(b) < 0;
    }

    private boolean containsCollinearPoint(CollinearPoint key) {
        // use binary search when greater than 6
        if (discovered.size() > 6) {
            int lo = 0;
            int hi = discovered.size()-1;
            while (lo <= hi) {
                int mid = lo + (hi-lo)/2;
                CollinearPoint midPoint = discovered.get(mid);
                if (less(key, midPoint)) hi = mid - 1;
                else if (less(midPoint, key)) lo = mid + 1;
                else return midPoint.compareTo(key) == 0;
            }
        } else {
            for (CollinearPoint c : discovered)
                if (c.compareTo(key) == 0) return true;
        }
        return false;
    }

    private void findLineSegment(int pi) {
        // sort for slope with respect to p
        // find a group of same slope, then check if it meets condition
        // repeat until the end
        Point p = points[pi];
        Point[] aux = new Point[n];
        for (int i = 0; i < n; i++) aux[i] = points[i];
        Arrays.sort(aux, p.slopeOrder());
/*        StdOut.println("p " + p.toString());
        for (Point pc: aux) StdOut.println(pc.toString() + "   " + p.slopeTo(pc));*/
        int skipCount = 0;
        for (int i = 0; i < n-1; i++) {
            if (p.slopeOrder().compare(aux[i], aux[i+1]) == 0) {
                skipCount = meetCondition(aux, p, i);
                if (skipCount > 0) {
                    i += skipCount - 1;
                }
            }
        }
    }

    private int checkCountAndRegister(int es, Point min, Point max, double rsl) {
        if (es >= 3) {
            CollinearPoint cp = new CollinearPoint(min, max, rsl);
            if (!containsCollinearPoint(cp)) {
                segments.add(new LineSegment(min, max));
                discovered.add(cp);
                if (discovered.size() > 6) discovered.sort(cp.slopeOrder());
                return es;
            }
        }
        return 0;
    }

    private int meetCondition(Point[] aux, Point p, int si) {
        Point minPoint = p;
        Point maxPoint = p;
        double rsl = p.slopeTo(aux[si]);
        double sl;
        int es = 0;
        for (int i = si; i < n; i++) {
            sl = p.slopeTo(aux[i]);
            if (Double.compare(rsl, sl) != 0) break;
            if (aux[i].compareTo(minPoint) < 0) minPoint = aux[i];
            if (aux[i].compareTo(maxPoint) > 0) maxPoint = aux[i];
            es++;
        }
        return checkCountAndRegister(es, minPoint, maxPoint, rsl);
    }

    public int numberOfSegments() {
        // the number of line segments
        return segments.size();
    }

    public LineSegment[] segments() {
        // the line segments
        final LineSegment[] segs = new LineSegment[segments.size()];
        segments.toArray(segs);
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
        Point p = points[17];
        StdOut.println("p " + p.toString());
        for (Point p1 : points) StdOut.println(p1.toString() + "  " + p.slopeTo(p1));*/

/*        Arrays.sort(p);
        Point p0 = p[0];
        Arrays.sort(p, 1, n-1, p0.slopeOrder());
        for (Point p1 : p) StdOut.println(p1.toString() + "  " + p0.slopeTo(p1));
        int scale = 50000;
        StdDraw.setCanvasSize(800, 800);
        StdDraw.setXscale(0, scale);
        StdDraw.setYscale(0, scale);
        StdDraw.setPenRadius(0.01);
        StdDraw.enableDoubleBuffering();
        for (i = 0; i < n; i++) {
            p[i].draw();
            StdDraw.show();
            StdDraw.pause(100);
        }*/

        FastCollinearPointsWExtraList bcp = new FastCollinearPointsWExtraList(points);
        LineSegment[] segs = bcp.segments();
        StdOut.println(bcp.numberOfSegments());
        for (LineSegment seg : segs) StdOut.println(seg.toString());
    }
}
