/* *****************************************************************************
 *  Name:              J Hirota
 *  Coursera User ID:
 *  Last modified:     2022-1-18
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private static final int INITIAL_SIZE = 100;
    private Object[] list;
    private int n;

    // construct an empty randomized queue
    public RandomizedQueue() {
        list = new Object[INITIAL_SIZE];
        n = 0;
    }

/*    private void display() {
        String s = "";
        for (int i = 0; i < n; i++) s += list[i] + ", ";
        StdOut.println(s);
    }*/

    private void resize(int newSize) {
        Object[] copy = new Object[newSize];
        for (int i = 0; i < n; i++) {
            copy[i] = list[i];
            list[i] = null; // to prevent loitering
        }
        list = copy;
    }

    private void swap(int i, int j) {
        Object swap = list[i];
        list[i] = list[j];
        list[j] = swap;
    }

    // is the randomized queue empty?
    public boolean isEmpty() { return n < 1; }

    // return the number of items on the randomized queue
    public int size() { return n; }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();

        if (list.length == n) resize(2*n);
        list[n] = item;
        if (n > 1) {
            int c = StdRandom.uniform(n+1);
            if (c != n) swap(c, n);
        }
        n++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (n < 1) throw new NoSuchElementException();

        Item item = (Item) list[--n];
        list[n] = null; // to prevent loitering
        if (n > 0 && n <= list.length/4) resize(list.length/2);
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (n < 1) throw new NoSuchElementException();

        int c = StdRandom.uniform(n);
        return (Item) list[c];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() { return new ListIterator(); }

    private class ListIterator implements Iterator<Item> {
        private int ni = 0;
        private Object[] ui = new Object[n];

        public ListIterator() {
            for (int i = 0; i < n; i++) ui[i] = list[i];
            for (int i = n-1; i > 1; i--)
                swap(i-1, StdRandom.uniform(i));
        }

        private void swap(int i, int j) {
            Object swap = ui[i];
            ui[i] = ui[j];
            ui[j] = swap;
        }

        public boolean hasNext() { return n-ni > 0; }

        public Item next() {
            if (n-ni <= 0) throw new NoSuchElementException();

            Item item = (Item) ui[ni];
            ni++;
            return item;
        }
    }


    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> q = new RandomizedQueue<>();
        q.enqueue("test1");
        q.enqueue("item2");
        q.enqueue("test3");
        q.enqueue("test4");
        q.dequeue();
//        q.display();
        Iterator<String> it = q.iterator();
        while (it.hasNext()) {
            StdOut.println(it.next());
        }

        int n = 20;
        int k = 10;
        int[] oc = new int[n];
        for (int i = 0; i < n; i++) oc[i] = 0;

        for (int i = 0; i < 10000; i++) {
            RandomizedQueue<Integer> queue = new RandomizedQueue<>();
            for (int j = 1; j <= n; j++) queue.enqueue(j);
            Iterator<Integer> iterator = queue.iterator();
            for (int j = 1; j <= k; j++) oc[iterator.next()-1]++;
        }

        for (int i = 0; i < n; i++)
            StdOut.println(i+1 + "  " + oc[i]);
        StdOut.println(StdStats.stddev(oc));
    }
}
