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

    private class Tuple {
        int es;
        int count;

        Tuple(int count, int es) {
            this.count = count;
            this.es = es;
        }
    }

    private Tuple meetCondition(Stack<LineSegment> stack, Point p, int pi) {
        Point minPoint = p;
        Point maxPoint = p;
        double rsl = p.slopeTo(points[pi]);
        // StdOut.println(points[pi].toString() + "   ref: " + rsl);
        int es = 1;
        for (int i = pi+1; i < n; i++) {
            double sl = p.slopeTo(points[i]);
            // StdOut.println(points[i].toString() + "   " + sl);
            if (sl > rsl || sl < rsl) break;
            if (points[i].compareTo(minPoint) < 0) minPoint = points[i];
            if (points[i].compareTo(maxPoint) > 0) maxPoint = points[i];
            es++;
        }
        if (es >= 3) {
            stack.push(new LineSegment(minPoint, maxPoint));
            return new Tuple(1, es-1);
        }
        return new Tuple(0, 0);
    }

    private int findLineSegment(Stack<LineSegment> stack, int pi) {
        // sort for slope with respect to p
        // then find a group of same slope
        // check if it meets condition
        Point p = points[pi];
        Arrays.sort(points, pi+1, n, p.slopeOrder());
        int count = 0;
        int es;
        for (int i = pi+1; i < n-1; i++) {
            if (p.slopeOrder().compare(points[i], points[i+1]) == 0) {
                // StdOut.println(i + "  " + points[i].toString() + "  " + points[i+1]);
                Tuple tp = meetCondition(stack, p, i);
                count += tp.count;
                i += tp.es;
            }
        }
        return count;
    }

    public LineSegment[] segments() {
        // the line segments
        if (segments == null) {
            Stack<LineSegment> stack = new Stack<>();
            int count = 0;
            for (int i = 0; i < n-3; i++)
                count += findLineSegment(stack, i);
            segments = new LineSegment[count];
            int i = 0;
            while (!stack.isEmpty()) segments[i++] = stack.pop();
        }
        return segments.clone();
    }


    public static void main(String[] args) {
        int n = 25;
        Point[] p = new Point[n];
        p[0] = new Point(1, 1);
        p[1] = new Point(3, 5);
        p[2] = new Point(5, 5);
        p[3] = new Point(12, 3);
        p[4] = new Point(3, 2);
        p[5] = new Point(4, 1);
        p[6] = new Point(18000, 26000);
        p[7] = new Point(16, 4);
        p[8] = new Point(8, 8);
        p[9] = new Point(2, 3);
        p[10] = new Point(4, 4);
        p[11] = new Point(9000, 26000);
        p[12] = new Point(8, 2);
        p[13] = new Point(15, 3);
        p[14] = new Point(17792, 29903);
        p[15] = new Point(17440, 29991);
        p[16] = new Point(17088, 30079);
        p[17] = new Point(16736, 30167);
        p[18] = new Point(16384, 30255);
        p[19] = new Point(12652, 30395);
        p[20] = new Point(14764, 30467);
        p[21] = new Point(1000, 26000);
        p[22] = new Point(11000, 26000);
        p[23] = new Point(2, 9);
        p[24] = new Point(1000, 23000);

/*        Point p0 = p[0];
        MergesortComparator.sort(p, 1, n-1, p0.slopeOrder());
        for (Point p1 : p) StdOut.println(p1.toString() + "  " + p0.slopeTo(p1));*/
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
        for (LineSegment seg : segs) StdOut.println(seg.toString());
    }
}
