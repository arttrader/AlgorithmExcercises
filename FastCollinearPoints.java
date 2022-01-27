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

public class FastCollinearPoints {
    private final Point[] points;
    private final int n;
    private final ArrayList<LineSegment> lineSegments;
    private final ArrayList<LineSeg> segments;
    private final boolean useSegmentCheck = false;

    public FastCollinearPoints(Point[] data) {
        // finds all line segments containing 4 points or more
        if (data == null) throw new IllegalArgumentException();
        n = data.length;
        this.points = new Point[n];
        for (int i = 0; i < n; i++)
            if (data[i] == null) throw new IllegalArgumentException();
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++)
                if (data[i].compareTo(data[j]) == 0)
                    throw new IllegalArgumentException(data[i].toString());
            this.points[i] = data[i];
        }
        lineSegments = new ArrayList<LineSegment>();
        segments = new ArrayList<LineSeg>();
        Arrays.sort(this.points, new PointNaturalOrder());
        // findLineSegment(0);
       for (int i = 0; i < n-3; i++) findLineSegment(i);
    }

    private class PointNaturalOrder implements Comparator<Point> {
        public int compare(Point o1, Point o2) { return o1.compareTo(o2); }
    }
    
    private class LineSeg implements Comparable<LineSeg> {
        Point minPoint;
        Point maxPoint;
        double slope;

        public LineSeg(Point min, Point max, double sl) {
            minPoint = min;
            maxPoint = max;
            slope = sl;
        }

        public int compareTo(LineSeg c) {
            int ds = Double.compare(slope, c.slope);
            if (ds != 0) return ds;
            if (c.maxPoint.compareTo(maxPoint) == 0)
                return c.maxPoint.compareTo(maxPoint);
            else
                return c.maxPoint.compareTo(maxPoint);
        }

        public boolean collinear(LineSeg c) {
            int ds = Double.compare(slope, c.slope);
            if (ds != 0) return false;
            return c.maxPoint.compareTo(maxPoint) == 0;
        }

        public Comparator<LineSeg> slopeOrder() {
            return new LineSeg.SlopeOrder();
        }

        private class SlopeOrder implements Comparator<LineSeg> {
            public int compare(LineSeg a, LineSeg b) {
                return a.compareTo(b);
            }
        }

    }

    private boolean less(LineSeg a, LineSeg b) {
        return a.compareTo(b) < 0;
    }

    private boolean containsSegment(LineSeg key) {
        // use binary search when greater than 6
        if (segments.size() > 6) {
            int lo = 0;
            int hi = segments.size()-1;
            while (lo <= hi) {
                int mid = lo + (hi-lo)/2;
                LineSeg midPoint = segments.get(mid);
                if (less(key, midPoint)) hi = mid - 1;
                else if (less(midPoint, key)) lo = mid + 1;
                else return key.collinear(midPoint);
            }
        } else {
            for (LineSeg c : segments)
                if (key.collinear(c)) return true;
        }
        return false;
    }


    private void findLineSegment(int pi) {
        // sort for slope with respect to p
        // find a group of same slope, then check if it meets condition
        // repeat until the end
        Point p = points[pi];
        Point[] aux = new Point[n-pi-1];
        for (int i = 0; i < n-pi-1; i++) aux[i] = points[pi+1+i];
        Arrays.sort(aux, p.slopeOrder());
/*        StdOut.println("p " + p.toString());
        for (Point pc: aux) StdOut.println(pc.toString() + "   " + p.slopeTo(pc));*/
        int skipCount = 0;
        for (int i = 0; i < n-pi-2; i++) {
            if (p.slopeOrder().compare(aux[i], aux[i+1]) == 0) {
                skipCount = meetCondition(aux, p, i);
                if (skipCount > 0) {
                    i += skipCount - 1;
                }
            }
        }
    }

    private int meetCondition(Point[] aux, Point p, int si) {
        Point minPoint = p;
        Point maxPoint = p;
        double rsl = p.slopeTo(aux[si]);
        // StdOut.println(points[pi].toString() + "   ref: " + rsl);
        int es = 0;
        for (int i = si; i < aux.length; i++) {
            double sl = p.slopeTo(aux[i]);
            // StdOut.println(points[i].toString() + "   " + sl);
            if (sl > rsl || sl < rsl) break;
            if (aux[i].compareTo(minPoint) < 0) minPoint = aux[i];
            if (aux[i].compareTo(maxPoint) > 0) maxPoint = aux[i];
            es++;
        }
        if (es >= 3) {
            if (useSegmentCheck) {
                LineSeg cp = new LineSeg(minPoint, maxPoint, rsl);
                if (!containsSegment(cp)) {
                    lineSegments.add(new LineSegment(minPoint, maxPoint));
                    segments.add((cp));
                    if (segments.size() > 6) segments.sort(cp.slopeOrder());
                    return es;
                }
            } else {
                lineSegments.add(new LineSegment(minPoint, maxPoint));
                return es;
            }
        }
        return 0;
    }

    public int numberOfSegments() {
        // the number of line segments
        return lineSegments.size();
    }

    public LineSegment[] segments() {
        // the line segments
        final LineSegment[] segs = new LineSegment[lineSegments.size()];
        lineSegments.toArray(segs);
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
        for (LineSegment seg : segs) {
            StdOut.println(seg.toString());
            // seg.draw();
            // StdDraw.show();
        }
    }
}
