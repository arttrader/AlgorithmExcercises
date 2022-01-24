/* *****************************************************************************
 *  Name:              J Hirota
 *  Coursera User ID:
 *  Last modified:     2022-1-23
 **************************************************************************** */

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    private Point[] points;
    private int n;
    Stack<LineSegment> stack = new Stack<>();
    private LineSegment[] segments;
    private int ns;

    public FastCollinearPoints(Point[] points) {
        // finds all line segments containing 4 points
        if (points == null) throw new IllegalArgumentException();

        n = points.length;
        this.points = new Point[n];
        for (int i = 0; i < n; i++) {
            if (points[i] == null) throw new IllegalArgumentException();
            for (int j = i+1; j < n; j++)
                if (points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException(points[i].toString());
            this.points[i] = points[i];
        }
        MergesortComparator.sort(points, new Point.YXOrder());
    }

    public int numberOfSegments() {
        // the number of line segments
        return ns;
    }

    private int findPoint(Point p) {
        for (int i = 0; i < n; i++)
            if (points[i].compareTo(p) == 0) return i;
        return -1;
    }

    private LineSegment findLineSegment(Point p) {
        Point[] aux = new Point[n];
        for (int k = 0; k < n; k++) aux[k] = points[k];
        MergesortComparator.sort(aux, p.slopeOrder());
        int pi = findPoint(p);
        if (pi >= n-3) return null;
        StdOut.println("origin: " + aux[pi].toString());
        StdOut.println(aux[pi+1].toString());
        StdOut.println(aux[pi+2].toString());
        StdOut.println(aux[pi+3].toString());
        double spq = aux[pi].slopeTo(aux[pi+1]);
        double spr = aux[pi].slopeTo(aux[pi+2]);
        double sps = aux[pi].slopeTo(aux[pi+3]);
        if (spq == spr && spq == sps) {
            StdOut.println("Segment found");
            StdOut.println(aux[pi].toString());
            StdOut.println(aux[pi+1].toString());
            StdOut.println(aux[pi+2].toString());
            StdOut.println(aux[pi+3].toString());
            LineSegment seg = new LineSegment(aux[pi], aux[pi+3]);
            return seg;
        }
        return null;
    }

    private LineSegment lineSegment(int p, int q, int r, int s) {
        return new LineSegment(points[p], points[s]);
    }

    public LineSegment[] segments() {
        // the line segments
        for (int i = 0; i < n; i++) {
            LineSegment seg = findLineSegment(points[i]);
            if (seg != null) {
                stack.push(seg);
                ns++;
            }
        }
        LineSegment[] segments = new LineSegment[ns];
        int i = 0;
        while (!stack.isEmpty()) segments[i++] = stack.pop();
        return segments;
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
            StdOut.println(p[i].toString());
            p[i].draw();
            StdDraw.show();
        }*/
        FastCollinearPoints bcp = new FastCollinearPoints(p);
        LineSegment[] segs = bcp.segments();
        StdOut.println(bcp.numberOfSegments());
        for (int i = 0; i < segs.length; i++) {
            StdOut.println(segs[i].toString());
        }
    }
}
