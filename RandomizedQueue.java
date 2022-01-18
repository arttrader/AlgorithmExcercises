/* *****************************************************************************
 *  Name:              J Hirota
 *  Coursera User ID:
 *  Last modified:     2022-1-18
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private final int INITIAL_SIZE = 100;
    private Object[] list = new Object[INITIAL_SIZE];
    private int n = 0;

    // construct an empty randomized queue
    public RandomizedQueue() {}

/*    private void display() {
        String s = "";
        for (int i = 0; i < n; i++) s += list[i] + ", ";
        StdOut.println(s);
    }*/

    // is the randomized queue empty?
    public boolean isEmpty() { return n == 0; }

    // return the number of items on the randomized queue
    public int n() { return n; }

    private void resize(int newSize) {
        Object[] copy = new Object[newSize];
        for (int i = 0; i < n; i++)
            copy[i] = list[i];
        list = copy;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();

        if (list.length == n) resize(2*n);
        list[n++] = item;
    }


    // remove and return a random item
    public Item dequeue() {
        if (n < 1) throw new NoSuchElementException();

        int c = StdRandom.uniform(n);
        Item item = (Item)list[c];
        n--;
        for (int i = c; i < n; i++) list[i] = list[i+1];
        if (n > 0 && n <= list.length/4) resize(list.length/2);
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (n < 1) throw new NoSuchElementException();

        int c = StdRandom.uniform(n);
        return (Item)list[c];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            int ni = n;

            public boolean hasNext() { return ni > 0; }

            public Item next() {
                int c = StdRandom.uniform(n);
                Item item = (Item)list[c];
                ni--;
                return item;
            }
        };
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue q = new RandomizedQueue();
        q.enqueue("test1");
        q.enqueue("item2");
        q.enqueue("test3");
        q.enqueue("test4");

//        q.display();
        Iterator it = q.iterator();
        while (it.hasNext()) {
            StdOut.println(it.next());
        }
    }
}
