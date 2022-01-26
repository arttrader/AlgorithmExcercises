/* *****************************************************************************
 *  Name:              J Hirota
 *  Coursera User ID:
 *  Last modified:     2022-1-24
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class BruteCollinearPoints {
    private final Point[] points;
    private final int n;
    private final ArrayList<LineSegment> segments;

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
        segments = new ArrayList<LineSegment>();
        processSegments();
    }

    public int numberOfSegments() {
        // the number of line segments
        return segments.size();
    }

    private void processSegments() {
        for (int p = 0; p < n; p++)
            for (int q = p + 1; q < n; q++) {
                double spq = points[p].slopeTo(points[q]);
                for (int r = q + 1; r < n; r++) {
                    double spr = points[p].slopeTo(points[r]);
                    if (spq == spr) {
                        for (int s = r + 1; s < n; s++) {
                            double sps = points[p].slopeTo(points[s]);
                            if (spq == sps) {
                                LineSegment seg = lineSegment(points[p], points[q], points[r], points[s]);
                                segments.add(seg);
                            }
                        }
                    }
                }
            }
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
        final LineSegment[] segs = new LineSegment[segments.size()];
        segments.toArray(segs);
        return segs;
    }


    public static void main(String[] args) {
        String line = StdIn.readString();
        int n = Integer.parseInt(line);
        StdOut.printf("Input N: %s\n", n);
        Point[] p = new Point[n];
        int i = 0;
        while (!StdIn.isEmpty()) {
            String s1 = StdIn.readString();
            String s2 = StdIn.readString();
            int d1 = Integer.parseInt(s1);
            int d2 = Integer.parseInt(s2);
            p[i++] = new Point(d1, d2);
        }
        BruteCollinearPoints bcp = new BruteCollinearPoints(p);
        LineSegment[] segs = bcp.segments();
        StdOut.println(bcp.numberOfSegments());
        for (LineSegment seg : segs) StdOut.println(seg.toString());
    }
}
