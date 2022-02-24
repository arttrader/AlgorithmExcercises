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

        double x() { return key.x(); }
        double y() { return key.y(); }
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
        else if (cmp > 0) x.right = put(x.right, p, !vert);
        else if (!contains(p, x)) x.right = put(x.right, p, !vert);
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
            if (x.x() > p.x())
                return contains(p, x.left);
            else
                return contains(p, x.right);
        else
        if (x.y() > p.y())
            return contains(p, x.left);
        else
            return contains(p, x.right);
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        else return contains(p, root);
    }

    private ArrayList<Point2D> range(RectHV rect, Node node) {
        ArrayList<Point2D> r = new ArrayList<>();
        if (node == null) return r;
        if (rect.contains(node.key)) r.add(node.key);
        if (node.vertical) {
            if (rect.xmin() <= node.x())
                r.addAll(range(rect, node.left));
            if (rect.xmax() >= node.x())
                r.addAll(range(rect, node.right));
        } else {
            if (rect.ymin() <= node.y())
                r.addAll(range(rect, node.left));
            if (rect.ymax() >= node.y())
                r.addAll(range(rect, node.right));
        }
        return r;
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        ArrayList<Point2D> r = range(rect, root);
        return r;
    }

    public void draw() {
        draw(root, 0.0, 1.0, 0.0, 1.0, 0);
        StdDraw.show();
    }

    private void draw(Node x, double xmin, double xmax, double ymin, double ymax, int index) {
        if (x == null) return;
        StdDraw.setPenColor();
        StdDraw.setPenRadius(0.01);
        // x.key.draw();
        // char c = (char)(index + 65);
        if (x.vertical) {
            // StdDraw.text(x.key.x()+.02, x.key.y(), String.valueOf(c));
            StdDraw.setPenRadius(0.001);
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(x.key.x(), ymin, x.key.x(), ymax);
            draw(x.left, xmin, x.key.x(), ymin, ymax, ++index);
            draw(x.right, x.key.x(), xmax, ymin, ymax, ++index);
        } else {
            // StdDraw.text(x.key.x(), x.key.y()-.02, String.valueOf(c));
            StdDraw.setPenRadius(0.001);
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(xmin, x.key.y(), xmax, x.key.y());
            draw(x.left, xmin, xmax, ymin, x.key.y(), ++index);
            draw(x.right, xmin, xmax, x.key.y(), ymax, ++index);
        }
    }

    private Point2D nearest(Point2D query, Node node, Point2D nearest) {
        // to optimize further, need to pass current boundary to children to check
        // if there's chance of finding a nearer point
        if (node == null) return nearest;
        double qnst = query.distanceSquaredTo(nearest);
        double qnd = query.distanceSquaredTo(node.key);
        if (qnd < qnst) nearest = node.key;
        if (node.vertical) {
            double xDist = node.x() - query.x();
            if (query.x() < node.x()) {
                nearest = nearest(query, node.left, nearest);
                if (xDist*xDist < query.distanceSquaredTo(nearest))
                    nearest = nearest(query, node.right, nearest);
            } else {
                nearest = nearest(query, node.right, nearest);
                if (xDist*xDist < query.distanceSquaredTo(nearest))
                    nearest = nearest(query, node.left, nearest);
            }
        } else {
            double yDist = node.y() - query.y();
            if (query.y() < node.y()) {
                nearest = nearest(query, node.left, nearest);
                if (yDist*yDist < query.distanceSquaredTo(nearest))
                    nearest = nearest(query, node.right, nearest);
            } else {
                nearest = nearest(query, node.right, nearest);
                if (yDist*yDist < query.distanceSquaredTo(nearest))
                    nearest = nearest(query, node.left, nearest);
            }
        }
        return nearest;
    }

    public Point2D nearest(Point2D query) {
        if (query == null) throw new IllegalArgumentException();
        if (isEmpty()) return null;
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
        Point2D qp = new Point2D(0.26, 0.47);
        StdDraw.setPenColor();
        StdDraw.setPenRadius(0.01);
        qp.draw();
        StdDraw.show();
    }

}
