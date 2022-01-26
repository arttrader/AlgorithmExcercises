/* *****************************************************************************
 *  Name:              J Hirota
 *  Coursera User ID:
 *  Last modified:     2022-1-24
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;

public class FastCollinearPoints {
    private final Point[] points;
    private final int n;
    private final ArrayList<LineSegment> segments;
    private final TreeSet<Double> slopesUsed;

    public FastCollinearPoints(Point[] points) {
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
        slopesUsed = new TreeSet<>();
//        findLineSegment(0); // for debug
        for (int i = 0; i < n-3; i++) findLineSegment(i);
    }


    private void findLineSegment(int pi) {
        // sort for slope with respect to p
        // find a group of same slope, then check if it meets condition
        // repeat until the end
        Point p = points[pi];
//        Point[] aux = new Point[n-pi-1];
//        for (int i = 0; i < n-pi-1; i++) aux[i] = points[pi+1+i];
//        Arrays.sort(aux, p.slopeOrder());
        Arrays.sort(points, pi+1, n-1, p.slopeOrder());
        // StdOut.println("p " + p.toString());
        // for (Point p1 : aux) StdOut.println(p1.toString() + "  " + p.slopeTo(p1));
        int skipCount = 0;
        for (int i = pi+1; i < n-1; i++) {
            if (p.slopeOrder().compare(points[i], points[i+1]) == 0) {
                skipCount = meetCondition(points, p, i);
                if (skipCount > 0) {
                    // StdOut.println(i + "  " + aux[i].toString() + "  " + aux[i + 1] + "  sc " + skipCount);
                    i += skipCount - 1;
                }
            }
        }
    }

    private int meetCondition(Point[] aux, Point p, int si) {
        Point minPoint = p;
        Point maxPoint = p;
        double rsl = p.slopeTo(aux[si]);
        double sl;
        // StdOut.println(points[pi].toString() + "   ref: " + rsl);
        int es = 1;
        for (int i = si+1; i < aux.length; i++) {
            sl = p.slopeTo(aux[i]);
            // StdOut.println(points[i].toString() + "   " + sl);
            if (sl > rsl || sl < rsl) {
                if (es >= 3) {
                    if (!slopesUsed.contains(sl)) {
                        segments.add(new LineSegment(minPoint, maxPoint));
                        slopesUsed.add(sl);
                        return es;
                    }
                }
            }
            if (aux[i].compareTo(minPoint) < 0) minPoint = aux[i];
            if (aux[i].compareTo(maxPoint) > 0) maxPoint = aux[i];
            es++;
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
        // Point p0 = p[0];
        // Arrays.sort(p, 1, n-1, p0.slopeOrder());
        // for (Point p1 : p) StdOut.println(p1.toString() + "  " + p0.slopeTo(p1));
        FastCollinearPoints bcp = new FastCollinearPoints(p);
        LineSegment[] segs = bcp.segments();
        StdOut.println(bcp.numberOfSegments());
        for (LineSegment seg : segs) StdOut.println(seg.toString());
    }
}
