/* *****************************************************************************
 *  Name:              J Hirota
 *  Coursera User ID:
 *  Last modified:     2022-1-17
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private class Node {
        Item item;
        Node prev;
        Node next;
    }

    private Node first, last;
    private int size = 0;

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
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

        Node node = new Node();
        node.item = item;
        node.prev = null;
        node.next = first;
        if (first == null) last = node;
        else first.prev = node;
        first = node;
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

        Node newFirst = first.next;
        Item item = first.item;
        first = null;
        if (newFirst == null) last = null;
        else newFirst.prev = null;
        first = newFirst;
        size--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (last == null) throw new NoSuchElementException();

        Node newLast = last.prev;
        Item item = last.item;
        last = null;
        if (newLast != null) newLast.next = null;
        else first = null;
        last = newLast;
        size--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {

        return new Iterator<Item>() {
            Node current = first;

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

        Deque<Integer> queue = new Deque<>();
        for (int i = 1; i <= 20; i++)
            if (StdRandom.uniform(10) > 5)
                queue.addFirst(i);
            else
                queue.addLast(i);
        for (int i = 1; i <= 20; i++)
            if (StdRandom.uniform(10) > 5)
                StdOut.println(queue.removeFirst());
            else
                StdOut.println(queue.removeLast());
        Iterator<Integer> iterator = queue.iterator();
        while (iterator.hasNext()) {
            StdOut.println(iterator.next());
        }
    }
}
