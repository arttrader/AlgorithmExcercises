/* *****************************************************************************
 *  Name:              J Hirota
 *  Coursera User ID:
 *  Last modified:     2022-2-9
 **************************************************************************** */

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class RBBST<Key extends Comparable<Key>, Value> {
    private static final boolean RED   = true;
    private static final boolean BLACK = false;
    private Node root;

    private class Node {
        Key key;
        Value val;
        Node left, right;
        int size;
        boolean color;

        public Node(Key key, Value val, int size, boolean c) {
            this.key = key;
            this.val = val;
            this.size = size;
            this.color = c;
        }
    }

    private Node rotateLeft(Node h) {
        assert isRed(h.right);
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        h.size = size(h.left) + size(h.right) + 1;
        x.size = h.size + size(x.right) + 1;
        return x;
    }

    private Node rotateRight(Node h) {
        assert isRed(h.left);
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        h.size = size(h.left) + size(h.right) + 1;
        x.size = size(x.left) + h.size + 1;
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

    public void put(Key key, Value val) { root = put(root, key, val); }

    private Node put(Node x, Key key, Value val) {
        if (x == null) return new Node(key, val, 1, RED);
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) x.left  = put(x.left,  key, val);
        else if (cmp > 0) x.right = put(x.right, key, val);
        else              x.val   = val;
        x.size = size(x.left) + size(x.right) + 1;

        if (!isRed(x.left) && isRed(x.right)) x = rotateLeft(x);
        if (isRed(x.left) && isRed(x.left.left)) x = rotateRight(x);
        if (isRed(x.left) && isRed(x.right)) filipColors(x);
        return x;
    }

    private boolean isRed(Node x) {
        if (x == null) return false;
        return x.color == RED;
    }

    public Iterable<Key> traverse() {
        if (root == null) return null;

        return traverse(min(), max());
    }

    public Iterable<Key> traverse(Key lo, Key hi) {
        // traverse with constant extra space
        Queue<Key> queue = new Queue<Key>();
        traverse(root, queue, lo, hi);
        return queue;
    }

    private void traverse(Node x, Queue<Key> queue, Key lo, Key hi) {
        if (x == null) return;
        int cmplo = lo.compareTo(x.key);
        int cmphi = hi.compareTo(x.key);
        if (cmplo < 0) keys(x.left, queue, lo, hi);
        if (cmplo <= 0 && cmphi >= 0) queue.enqueue(x.key);
        if (cmphi > 0) keys(x.right, queue, lo, hi);
    }

    public boolean contains(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }

    public Value get(Key key) {
        return get(root, key);
    }

    private Value get(Node x, Key key) {
        if (key == null) throw new IllegalArgumentException("calls get() with a null key");
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) return get(x.left, key);
        else if (cmp > 0) return get(x.right, key);
        else              return x.val;
    }


    public boolean isEmpty() { return size() == 0; }

    public int size() { return size(root); }

    private int size(Node x) {
        if (x == null) return 0;
        else return x.size;
    }

    public Key min() {
        if (root == null) return null;
        return min(root).key;
    }

    private Node min(Node n) {
        if (n.left == null) return n;
        return min(n.left);
    }

    public Key max() {
        if (root == null) return null;
        return max(root).key;
    }

    private Node max(Node n) {
        if (n.right == null) return n;
        return max(n.right);
    }

    public int rank(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to rank() is null");
        return rank(key, root);
    }

    // Number of keys in the subtree less than key.
    private int rank(Key key, Node x) {
        if (x == null) return 0;
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) return rank(key, x.left);
        else if (cmp > 0) return 1 + size(x.left) + rank(key, x.right);
        else              return size(x.left);
    }

    public Iterable<Key> keys() {
        if (isEmpty()) return new Queue<Key>();
        return keys(min(), max());
    }

    public Iterable<Key> keys(Key lo, Key hi) {
        Queue<Key> queue = new Queue<Key>();
        keys(root, queue, lo, hi);
        return queue;
    }

    private void keys(Node x, Queue<Key> queue, Key lo, Key hi) {
        if (x == null) return;
        int cmplo = lo.compareTo(x.key);
        int cmphi = hi.compareTo(x.key);
        if (cmplo < 0) keys(x.left, queue, lo, hi);
        if (cmplo <= 0 && cmphi >= 0) queue.enqueue(x.key);
        if (cmphi > 0) keys(x.right, queue, lo, hi);
    }

    public Iterable<Key> levelOrder() {
        Queue<Key> keys = new Queue<Key>();
        Queue<Node> queue = new Queue<Node>();
        queue.enqueue(root);
        while (!queue.isEmpty()) {
            Node x = queue.dequeue();
            if (x == null) continue;
            keys.enqueue(x.key);
            queue.enqueue(x.left);
            queue.enqueue(x.right);
        }
        return keys;
    }

    public Iterable<Key> rangeSearch(Key lo, Key hi) {
        return keys(lo, hi);
    }

    public int rangeCount(Key lo, Key hi) {
        if (contains(hi)) return rank(hi) - rank(lo) + 1;
        else return rank(hi) - rank(lo);
    }

    public boolean isBST() {
        return isBST(root, min(), max());
    }

    public boolean isBST(Node x, Key min, Key max) {
        if (x == null) return false;
        if (x.left == null)
            if (x.key.compareTo(min) <= 0) return false;
        else
            if (!isBST(x.left, min, x.key)) return false;
        if (x.right == null)
            if (x.key.compareTo(max) >= 0) return false;
        else
            if (!isBST(x.right, x.key, max)) return false;
        return true;
    }

    public static void main(String[] args) {
        RBBST<Integer, Integer> st = new RBBST<>();
        int n = 0;
        for (int i = 0; !StdIn.isEmpty(); i++) {
            Integer key = Integer.parseInt(StdIn.readString());
            st.put(key, i);
            n++;
        }

        for (Integer s : st.levelOrder())
            StdOut.println(s + " " + st.get(s));

        StdOut.println();

        for (Integer s : st.keys())
            StdOut.println(s + " " + st.get(s) + " " + st.rank(s));

       // StdOut.println(st.isBST());
        Integer lo = 3000;
        Integer hi = 5000;
        StdOut.printf("lo %s  rank %s\n", lo, st.rank(lo));
        StdOut.printf("lo %s  rank %s\n", hi, st.rank(hi));
        StdOut.printf("range Count: %s\n", st.rangeCount(lo, hi));
        for (Integer s : st.rangeSearch(lo, hi))
            StdOut.println(s + " " + st.get(s));
    }
}
