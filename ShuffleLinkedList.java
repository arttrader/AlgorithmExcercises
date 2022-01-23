/* *****************************************************************************
 *  Name:              J Hirota
 *  Coursera User ID:
 *  Last modified:     2022-1-19
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

// Hint: design a linear-time subroutine that can take two uniformly shuffled linked lists of sizes n1 and n2 and combined them into a uniformly shuffled linked lists of size n1 + n2.

public class ShuffleLinkedList<Item> implements Iterable<Item> {

    class Node {
        Item item;
        Node next;

        public Node(Item item) {
            this.item = item;
            this.next = null;
        }
    }

    private Node first, last;
    private int n;

    public ShuffleLinkedList() {
        n = 0;
        first = null;
        last = null;
    }

    private Node randomMerge(Node list1, Node list2) {
        int r = StdRandom.uniform(2);
        if (r > 0) {
            Node t1 = list1;
            while (t1.next != null) t1 = t1.next;
            t1.next = list2;
            return list1;
        } else {
            Node t2 = list2;
            while (t2.next != null) t2 = t2.next;
            t2.next = list1;
            return list2;
        }
    }

    public void add(Item item) {
        if (first==null) {
            first = new Node(item);
            last = first;
        } else {
            Node node = new Node(item);
            last.next = node;
            last = node;
        }
        n++;
    }

    public Node getFirst() {
        return first;
    }

    public Node getNext(Node node) {
        return node.next;
    }

    public void shuffle() {
        first = shuffle(first, n);
    }

    private Node shuffle(Node list, int n) {
        if (n < 2) return list;
        int mid = n / 2;
        Node a = list;
        if (mid > 1)
            for (int i = 0; i < mid-1; i++) a = a.next;
        Node b = a.next;
        a.next = null;
        if (mid > 1) a = shuffle(list, mid);
        if (n - mid > 1) b = shuffle(b, n - mid);
        return randomMerge(a, b);
    }

    private Node getNode(int index) {
        Node node = first;
        for (int i = 0; i < index; i++)
            node = node.next;
        return node;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() { return new ListIterator(); }

    private class ListIterator implements Iterator<Item> {
        private Node current;

        public ListIterator() {
            current = first;
        }

        public boolean hasNext() { return current != null; }

        public Item next() {
            if (current == null) throw new NoSuchElementException();

            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
        int n = 10;
        ShuffleLinkedList<Integer> sl = new ShuffleLinkedList<>();
        for (int i = 0; i < n; i++) sl.add(i);
        sl.shuffle();
        Iterator<Integer> it = sl.iterator();
        while (it.hasNext()) {
            StdOut.println(it.next());
        }
    }
}
