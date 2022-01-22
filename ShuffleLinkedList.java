/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
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
        Node a = first;
        Node b;
        for (int i = 1; i < n; i++) {
            int r = StdRandom.uniform(i+1);
            if (r < i) {
                b = getNode(r);
                swap(a, b);
            }
            a = a.next;
        }

    }

    private void swap(Node a, Node b) {
        Node t = a;
        a = b;
        b = t;
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
        Node current;

        public ListIterator() {
            current = first;
        }

        public boolean hasNext() { return current.next != null; }

        public Item next() {
            if (current.next == null) throw new NoSuchElementException();

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
