/* *****************************************************************************
 *  Name:              J Hirota
 *  Coursera User ID:
 *  Last modified:     2022-2-1
 **************************************************************************** */

import edu.princeton.cs.algs4.MaxPQ;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class DynamicMedian<Key extends Comparable<Key>> {
    private final int N;
    private final MaxPQ<Key> maxPq; // note: minPq > maxPq
    private final MinPQ<Key> minPq;

    public DynamicMedian(int capacity) {
        N = capacity;
        maxPq = new MaxPQ<>(N);
        minPq = new MinPQ<>(N);
    }

    public int size() { return maxPq.size() + minPq.size(); }

    public void insert(Key x) {
        if (minPq.size() < maxPq.size())
            minPq.insert(x);
        else
            maxPq.insert(x);
        if (minPq.size() > 0 && maxPq.size() > 0)
            if (less(minPq.min(), maxPq.max())) { // swap
                Key key = maxPq.delMax();
                maxPq.insert(minPq.delMin());
                minPq.insert(key);
            }
    }

    private boolean less(Key a, Key b) {
        return a.compareTo(b) < 0;
    }

    public Key findMedian() {
        if (minPq.size() > maxPq.size())
            return minPq.min();
        else
            return maxPq.max();
    }

    public Key delMedian() {
        if (minPq.size() > maxPq.size())
            return minPq.delMin();
        else
            return maxPq.delMax();
    }

    private void print() {
        Iterator<Key> minIt = minPq.iterator();
        int i = 0;
        while (minIt.hasNext()) {
            StdOut.printf("%s  %s \n", i++, minIt.next());
        }
        Iterator<Key> maxIt = maxPq.iterator();
        i = 0;
        while (minIt.hasNext()) {
            StdOut.printf("%s  %s \n", i++, maxIt.next());
        }
    }


    public static void main(String[] args) {
        final int M = 10;
        DynamicMedian<Integer> dm = new DynamicMedian<>(M);
        for (int i = 0; i < M; i++) {
            Integer a = StdRandom.uniform(M);
            StdOut.printf("%s  %s \n", i+1, a);
            dm.insert(a);
        }
        StdOut.println();
        dm.print();
    }
}
