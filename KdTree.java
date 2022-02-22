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
        private final Point2D key;
        private Node left, right;
        private final boolean vertical;
        private int size;

        public Node(Point2D key, boolean vert) {
            this.key = key;
            this.vertical = vert;
            size = 1;
        }
    }

    public boolean isEmpty() {
        return (root == null);
    }

    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) return 0;
        return x.size;
    }

    private Node put(Node x, Point2D p, boolean vert) {
        if (x == null) return new Node(p, vert);
        int cmp;
        if (x.vertical) cmp = Point2D.X_ORDER.compare(p, x.key);
        else cmp = Point2D.Y_ORDER.compare(p, x.key);
        if (cmp < 0) x.left = put(x.left, p, !vert);
        else         x.right = put(x.right, p, !vert);
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        root = put(root, p, true);
    }

    private boolean contains(Point2D p, Node x) {
        if (x == null) return false;
        if (p.compareTo(x.key) == 0) return true;
        if (x.vertical)
            if (x.key.x() > p.x())
                return contains(p, x.left);
            else
                return contains(p, x.right);
        else
        if (x.key.y() > p.y())
            return contains(p, x.left);
        else
            return contains(p, x.right);
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        else return contains(p, root);
    }

    private ArrayList<Point2D> range(RectHV rect, Node x) {
        ArrayList<Point2D> r = new ArrayList<>();
        if (x == null) return r;
        if (rect.contains(x.key)) r.add(x.key);
        if (x.vertical)
            if (rect.xmax() < x.key.x())
                r.addAll(range(rect, x.left));
            else
                r.addAll(range(rect, x.right));
        else
            if (rect.ymax() < x.key.y())
                r.addAll(range(rect, x.left));
            else
                r.addAll(range(rect, x.right));
        return r;
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        ArrayList<Point2D> r = range(rect, root);
        return r;
    }

    public void draw() {
        draw(root);
    }

    private void draw(Node x) {
        if (x == null) return;
        StdDraw.setPenColor();
        x.key.draw();
//        StdDraw.setPenRadius(0.001);
        if (x.vertical) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(x.key.x(), 0.0, x.key.x(), 1.0);
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(0.0, x.key.y(), 1.0, x.key.y());
        }
        StdDraw.show();
        draw(x.left);
        draw(x.right);
    }

    private Point2D nearest(Point2D query, Node x, Point2D nearest) {
        if (x == null) return nearest;
        if (query.distanceSquaredTo(x.key) < query.distanceSquaredTo(nearest))
            nearest = x.key;
        if (x.vertical)
            if (query.x() < x.key.x())
                nearest = nearest(query, x.left, nearest);
            else
                nearest = nearest(query, x.right, nearest);
        else
            if (query.y() < x.key.y())
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
        while (!StdIn.isEmpty()) {
            double x = Double.parseDouble(StdIn.readString());
            double y = Double.parseDouble(StdIn.readString());
            Point2D p = new Point2D(x, y);
            kt.insert(p);
            StdOut.println(p.toString());
        }

        StdOut.println("size " + kt.size());

        double scale = 1.0;
        StdDraw.setCanvasSize(600, 600);
        StdDraw.setXscale(0, scale);
        StdDraw.setYscale(0, scale);
        StdDraw.setPenRadius(0.01);
        StdDraw.enableDoubleBuffering();
        kt.draw();
    }

}
