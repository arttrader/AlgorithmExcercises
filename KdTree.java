/* *****************************************************************************
 *  Name:              J Hirota
 *  Coursera User ID:
 *  Last modified:     2022-2-18
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class KdTree {
    private Node root;

    private class Node {
        Point2D key;
        Node left, right;
        boolean vertical;

        public Node(Point2D key, boolean vert) {
            this.key = key;
            this.vertical = vert;
        }
    }

    private Node put(Node x, Point2D p, boolean vert) {
        if (x == null) return new Node(p, vert);
        int cmp;
        if (x.vertical) cmp = p.X_ORDER.compare(p, x.key);
        else cmp = p.Y_ORDER.compare(p, x.key);
        if (cmp < 0) put(x.left, p, !vert);
        else put(x.right, p, !vert);
        return x;
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        root = put(root, p, true);
    }

    private ArrayList<Point2D> range(RectHV rect, Node x) {
        if (x == null) return null;
        ArrayList<Point2D> r = new ArrayList<>();
        if (rect.contains(x.key)) r.add(x.key);
        if (x.vertical)
            if (x.key.x() > rect.xmax())
                r.addAll(range(rect, x.left));
            else
                r.addAll(range(rect, x.right));
        else
            if (x.key.y() > rect.ymax())
                r.addAll(range(rect, x.left));
            else
                r.addAll(range(rect, x.right));

        return r;
    }

    public Point2D[] range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        Object[] r = range(rect, root).toArray();
        return (Point2D[]) r;
    }

    public void draw() {
        draw(root);
    }

    private void draw(Node x) {
        if (x == null) return;
        StdDraw.setPenColor();
        x.key.draw();
        if (x.vertical) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(x.key.x(), 0, x.key.x(), 1);
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(0, x.key.y(), 1, x.key.x());
        }
        StdDraw.show();
        draw(x.left);
        draw(x.right);
    }

    private Point2D nearest(Point2D query, Node x, Point2D nearest) {
        if (x == null) return nearest;
        if (query.distanceTo(x.key) < query.distanceTo(nearest))
            nearest = x.key;
        if (x.vertical)
            if (x.key.x() > query.x())
                nearest = nearest(query, x.left, nearest);
            else
                nearest = nearest(query, x.right, nearest);
        else
            if (x.key.y() > query.y())
                nearest = nearest(query, x.left, nearest);
            else
                nearest = nearest(query, x.right, nearest);

        return nearest;
    }

    public Point2D nearest(Point2D query) {
        if (query == null) throw new IllegalArgumentException();
        return nearest(query, root, root.key);
    }


    public static void main(String[] args) {
        KdTree kt = new KdTree();
        int n = 0;
        for (int i = 0; !StdIn.isEmpty(); i++) {
            Double x = Double.parseDouble(StdIn.readString());
            Double y = Double.parseDouble(StdIn.readString());
            Point2D p = new Point2D(x, y);
            kt.insert(p);
            StdOut.println(p.toString());
            n++;
        }

        double scale = 1.0;
        StdDraw.setCanvasSize(800, 800);
        StdDraw.setXscale(0, scale);
        StdDraw.setYscale(0, scale);
        //        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setPenRadius(0.01);
        StdDraw.enableDoubleBuffering();
        kt.draw();
    }

}
