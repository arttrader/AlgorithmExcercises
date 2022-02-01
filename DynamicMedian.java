/* *****************************************************************************
 *  Name:              J Hirota
 *  Coursera User ID:
 *  Last modified:     2022-2-1
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

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
        
        if (less(minPq.peek(), maxPq.peek())) { // swap
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
            return minPq.peek();
        else
            return maxPq.peek();
    }

    public Key delMedian() {
        if (minPq.size() > maxPq.size())
            return minPq.delMin();
        else
            return maxPq.delMax();
    }

    private void print() {
        minPq.print();
        maxPq.print();
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
