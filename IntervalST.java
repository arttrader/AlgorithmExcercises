/* *****************************************************************************
 *  Name:              J Hirota
 *  Coursera User ID:
 *  Last modified:     2022-2-17
 **************************************************************************** */

import edu.princeton.cs.algs4.Queue;

public class IntervalST<Value> {
    private static final boolean RED   = true;
    private static final boolean BLACK = false;
    private Node root;

    private class Node {
        private Interval key;
        private int rMax;
        private Value val;
        private Node left, right;
        private boolean color;

        public Node(int lo, int hi, Value val, boolean c) {
            key = new Interval(lo, hi);
            this.val = val;
            this.rMax = hi;
            this.color = c;
        }

        public boolean intersects(int lo, int hi) {
            return this.key.intersects(lo, hi);
        }
    }

    private Node rotateLeft(Node h) {
        assert isRed(h.right);
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        h.rMax = hiMax(h.left) + hiMax(h.right) + 1;
        x.rMax = h.rMax + hiMax(x.right) + 1;
        return x;
    }

    private Node rotateRight(Node h) {
        assert isRed(h.left);
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        h.rMax = hiMax(h.left) + hiMax(h.right) + 1;
        x.rMax = hiMax(x.left) + h.rMax + 1;
        return x;
    }

    private void filipColors(Node h) {
        assert !isRed(h);
        assert isRed(h.left);
        assert isRed(h.right);
        h.color = RED;
        h.left.color = BLACK;
        h.right.color = BLACK;
    }

    public void put(int lo, int hi, Value val) {
        root = put(root, lo, hi, val);
    }

    private Node put(Node x, int lo, int hi, Value val) {
        if (x == null) return new Node(lo, hi, val, RED);
        Interval in = new Interval(lo, hi);
        int cmp = in.compareTo(x.key);
        if      (cmp < 0) x.left  = put(x.left,  lo, hi, val);
        else if (cmp > 0) x.right = put(x.right, lo, hi, val);
        else              x.val   = val;
        x.rMax = hiMax(x.left) + hiMax(x.right) + 1;

        if (!isRed(x.left) && isRed(x.right)) x = rotateLeft(x);
        if (isRed(x.left) && isRed(x.left.left)) x = rotateRight(x);
        if (isRed(x.left) && isRed(x.right)) filipColors(x);
        return x;
    }

    private boolean isRed(Node x) {
        if (x == null) return false;
        return x.color == RED;
    }

    public Iterable<Interval> traverse() {
        if (root == null) return null;

        return traverse(min(), max());
    }

    public Iterable<Interval> traverse(Interval lo, Interval hi) {
        // traverse with constant extra space
        Queue<Interval> queue = new Queue<Interval>();
        traverse(root, queue, lo, hi);
        return queue;
    }

    private void traverse(Node x, Queue<Interval> queue, Interval lo, Interval hi) {
        if (x == null) return;
        int cmplo = lo.compareTo(x.key);
        int cmphi = hi.compareTo(x.key);
        if (cmplo < 0) keys(x.left, queue, lo, hi);
        if (cmplo <= 0 && cmphi >= 0) queue.enqueue(x.key);
        if (cmphi > 0) keys(x.right, queue, lo, hi);
    }

    public boolean contains(Interval key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }

    public Value get(Interval key) {
        return get(root, key);
    }

    private Value get(Node x, Interval key) {
        if (key == null) throw new IllegalArgumentException("calls get() with a null key");
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) return get(x.left, key);
        else if (cmp > 0) return get(x.right, key);
        else              return x.val;
    }

    public boolean isEmpty() { return root == null; }

    public Interval min() {
        if (root == null) return null;
        return min(root).key;
    }

    private Node min(Node n) {
        if (n.left == null) return n;
        return min(n.left);
    }

    public Interval max() {
        if (root == null) return null;
        return max(root).key;
    }

    private Node max(Node n) {
        if (n.right == null) return n;
        return max(n.right);
    }

    public int hiMax() { return hiMax(root); }

    private int hiMax(Node x) {
        if (x == null) return 0;
        else return x.rMax;
    }

    public Iterable<Interval> keys(Interval lo, Interval hi) {
        Queue<Interval> queue = new Queue<Interval>();
        keys(root, queue, lo, hi);
        return queue;
    }

    private void keys(Node x, Queue<Interval> queue, Interval lo, Interval hi) {
        if (x == null) return;
        int cmplo = lo.compareTo(x.key);
        int cmphi = hi.compareTo(x.key);
        if (cmplo < 0) keys(x.left, queue, lo, hi);
        if (cmplo <= 0 && cmphi >= 0) queue.enqueue(x.key);
        if (cmphi > 0) keys(x.right, queue, lo, hi);
    }


/*    public void put(int lo, int hi, Value val) {

    }*/

    public Value get(int lo, int hi) {
        return null;
    }

    public void delete(int lo, int hi) {

    }

    private Node intersects(int lo, int hi, Node h) {
        if (h.intersects(lo, hi)) return h;
        return null;
    }

    public Iterable<Interval> intersects() {
        if (isEmpty()) return new Queue<Interval>();
        return keys(min(), max());
    }

    public Iterable<Interval> intersects(Interval lo, Interval hi) {
        Queue<Interval> queue = new Queue<Interval>();
        intersects(root, queue, lo, hi);
        return queue;
    }

    private void intersects(Node x, Queue<Interval> queue, Interval lo, Interval hi) {
        if (x == null) return;
        int cmplo = lo.compareTo(x.key);
        int cmphi = hi.compareTo(x.key);
        if (cmplo < 0) keys(x.left, queue, lo, hi);
        if (cmplo <= 0 && cmphi >= 0) queue.enqueue(x.key);
        if (cmphi > 0) keys(x.right, queue, lo, hi);
    }

    public class Interval {
        int lo;
        int hi;

        public Interval(int lo, int hi) {
            this.lo = lo;
            this.hi = hi;
        }

        int compareTo(Interval k) {
            return this.lo - k.lo;
        }

        boolean intersects(int lo, int hi) {
            if (this.hi -lo > 0 && this.lo - lo < 0) return true;
            if (this.lo - hi < 0 && this.hi - hi > 0) return true;
            return false;
        }
    }


    public static void main(String[] args) {
        IntervalST<Integer> ist = new IntervalST<>();
        ist.put(5, 10, 1);

    }
}
