/* *****************************************************************************
 *  Name:              J Hirota
 *  Coursera User ID:
 *  Last modified:     2022-1-17
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private class Node {
        Item item;
        Node prev;
        Node next;
    }

    private Node first, last, current;
    private int size = 0;

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        current = null;
    }

    // is the deque empty?
    public boolean isEmpty() { return size == 0; }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();

        current = new Node();
        current.item = item;
        current.prev = null;
        current.next = first;
        if (first == null) last = current;
        else first.prev = current;
        first = current;
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();

        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.prev = oldLast;
        if (oldLast == null) first = last;
        else oldLast.next = last;
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (first == null) throw new NoSuchElementException();

        Item item = first.item;
        first = first.next;
        if (first != null) first.prev = null;
        else last = null;
        size--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (last == null) throw new NoSuchElementException();

        Item item = last.item;
        last = last.prev;
        if (last != null) last.next = null;
        else first = null;
        size--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        current = first;

        return new Iterator<Item>() {

            public boolean hasNext() {
                return current != null;
            }

            public Item next() {
                if (current == null) throw new NoSuchElementException();

                Item item = current.item;
                current = current.next;
                return item;
            }
        };
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> q = new Deque<>();
        q.addLast("test2");
        q.addFirst("item1");
        q.addLast("test3");
        q.removeFirst();
        q.addLast("test4");

        Iterator<String> it = q.iterator();
        while (it.hasNext()) {
             StdOut.println(it.next());
        }
    }
}
