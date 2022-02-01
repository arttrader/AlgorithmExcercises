/* *****************************************************************************
 *  Name:              J Hirota
 *  Coursera User ID:
 *  Last modified:     2022-1-31
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class MinPQ<Key extends Comparable<Key>> {
    private final Key[] pq;
    private final int N;
    private int n;

    public MinPQ(int capacity) {
        N = capacity;
        pq = (Key[]) new Comparable[N+1];
        n = 0;
    }

    public void insert(Key x) {
        if (n >= N) delMin();
        pq[++n] = x;
        swim(n);
    }

    public int size() { return n; }

    public Key peek() { return pq[1]; }

    public Key delMin() {
        Key min = pq[1];
        exch(1, n--);
        sink(1);
        pq[n+1] = null; // to prevent loitering
        return min;
    }

    private void swim(int k) {
        while (k > 1 && greater(k/2, k)) {
            exch(k,k/2);
            k = k/2;
        }
    }

    private void sink(int k) {
        int j = 2*k;
        if (j > n) return;
        if (j < n && greater(j, j+1)) j++;
        if (!greater(k, j)) return;
        exch(j, k);
        sink(j);
    }

    private void exch(int i, int j) {
        Key s = pq[i];
        pq[i] = pq[j];
        pq[j] = s;
    }

    private boolean less(int i, int j) {
        return pq[i].compareTo(pq[j]) < 0;
    }

    private boolean greater(int i, int j) {
        return pq[i].compareTo(pq[j]) > 0;
    }

    public void print() {
        for (int i = 1; i <= n; i++)
            StdOut.printf("%s  %s \n", i, pq[i]);
    }

    public static void main(String[] args) {
        final int M = 10;
        MinPQ<Integer> pq = new MinPQ<>(M);
/*        while (StdIn.hasNextLine())
        {
            String line = StdIn.readLine();
            Transaction item = new Transaction(line);
            pq.insert(item);
            if (pq.size() > M) pq.delMin();
        }*/
        for (int i = 0; i < M; i++) {
            Integer a = StdRandom.uniform(M);
            StdOut.printf("%s  %s \n", i+1, a);
            pq.insert(a);
        }
        StdOut.println();
        pq.print();
        pq.delMin();
        pq.print();
        pq.delMin();
        pq.print();
    }
}
