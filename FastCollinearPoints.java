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
    private ArrayList<LineSegment> segments;

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

        Arrays.sort(points, new Comparator<Point>() {
            public int compare(Point o1, Point o2) {
                return o1.compareTo(o2);
            }
        });
        // findLineSegment(0);
       for (int i = 0; i < n-3; i++) findLineSegment(i);
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
            segments.add(new LineSegment(minPoint, maxPoint));
            return es;
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
