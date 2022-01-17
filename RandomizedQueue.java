/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private class Node {
        Item item;
        Node prev;
        Node next;
    }

    private Node first, last, current;
    private int size = 0;

    // construct an empty randomized queue
    public RandomizedQueue() {
        first = null; last = null; current = null;
    }

    // is the randomized queue empty?
    public boolean isEmpty() { return size == 0; }

    // return the number of items on the randomized queue
    public int size() { return size; }

    // add the item
    public void enqueue(Item item) {
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

    // remove and return a random item
    public Item dequeue() {
        if (first == null) throw new NoSuchElementException();

        Item item = first.item;
        first = first.next;
        if (first != null) first.prev = null;
        else last = null;
        size--;
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        return current.item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            public boolean hasNext() {
                return false;
            }

            public Item next() {
                return null;
            }
        };
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue q = new RandomizedQueue();
        q.enqueue("test1");
        q.enqueue("item2");

        Iterator i = q.iterator();
        while (i != null) {
            StdOut.println(i.next());
        }
    }
}
