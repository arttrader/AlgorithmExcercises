/* *****************************************************************************
 *  Name:              J Hirota
 *  Coursera User ID:
 *  Last modified:     2022-2-1
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomPQ<Key extends Comparable<Key>> {
    private final Key[] pq;
    private final int N;
    private int n;

    public RandomPQ(int capacity) {
        N = capacity;
        pq = (Key[]) new Comparable[N+1];
        n = 0;
    }

    public boolean isEmpty() { return n == 0; }

    public int size() { return n; }

    public Key sample() {
        int i = StdRandom.uniform(n) + 1;
        return pq[i];
    }

    public Key delRandom() {
        int i = StdRandom.uniform(n) + 1;
        Key rnd = pq[i];
        exch(i, n--);
        if (less(i, n)) sink(i);
        else swim(i);
        pq[n+1] = null; // to prevent loitering
        return rnd;
    }

    public void insert(Key x) {
        if (n >= N) return;
        pq[++n] = x;
        swim(n);
    }

    private void swim(int k) {
        while (k > 1 && less(k/2, k)) {
            exch(k,k/2);
            k = k/2;
        }
    }

    private void sink(int k) {
        int j = 2*k;
        if (j > n) return;
        if (j < n && less(j, j+1)) j++;
        if (!less(k, j)) return;
        exch(j, k);
        sink(j);
    }

    private boolean less(int i, int j) {
        return pq[i].compareTo(pq[j]) < 0;
    }

    private void exch(int i, int j) {
        Key s = pq[i];
        pq[i] = pq[j];
        pq[j] = s;
    }

    private void print() {
        for (int i = 1; i <= n; i++)
            StdOut.printf("%s  %s \n", i, pq[i]);
    }


    public static void main(String[] args) {
        final int M = 10;
        RandomPQ<Integer> pq = new RandomPQ<>(M);
/*        while (StdIn.hasNextLine())
        {
            String line = StdIn.readLine();
            Transaction item = new Transaction(line);
            pq.insert(item);
            if (pq.size() > M) pq.delMax();
        }*/
        for (int i = 0; i < M; i++) {
            Integer a = StdRandom.uniform(M);
            StdOut.printf("%s  %s \n", i+1, a);
            pq.insert(a);
        }
        StdOut.println();
        pq.print();
        pq.delRandom();
        pq.print();
        pq.delRandom();
        pq.print();
    }
}
