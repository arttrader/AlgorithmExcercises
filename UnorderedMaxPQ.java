/* *****************************************************************************
 *  Name:              J Hirota
 *  Coursera User ID:
 *  Last modified:     2022-1-31
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.Transaction;

public class UnorderedMaxPQ<Key extends Comparable<Key>> {
    private final Key[] pq;
    private int n;

    public UnorderedMaxPQ(int capacity) {
        n = capacity;
        pq = (Key[]) new Comparable[capacity];
    }

    public boolean isEmpty() { return n == 0; }

    public void insert(Key x) {
        pq[n++] = x;
    }

    private boolean less(int i, int j) {
        return pq[i].compareTo(pq[j]) < 0;
    }

    private void exch(int i, int j) {
        Key s = pq[i];
        pq[i] = pq[j];
        pq[j] = s;
    }

    public Key delMax() {
        int max = 0;
        for (int i = 1; i <= n; i++) if (less(max, i)) max = i;
        exch(max, n -1);
        return pq[--n];
    }

    public int size() {
        return 0;
    }

    public static void main(String[] args) {
        final int M = 1000;

        UnorderedMaxPQ<Transaction> pq = new UnorderedMaxPQ<>(M);
        while (StdIn.hasNextLine())
        {
            String line = StdIn.readLine();
            Transaction item = new Transaction(line);
            pq.insert(item);
            if (pq.size() > M) pq.delMax();
        }
    }
}
