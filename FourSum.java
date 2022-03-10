/* *****************************************************************************
 *  Name:              J Hirota
 *  Coursera User ID:
 *  Last modified:     2022-03-04
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class FourSum {
    private final int[] array;
    private final int n;
    private final Node[] hm;

    public class Data {
        private final int a;
        private final int b;

        public Data(int a, int b) {
            this.a = a;
            this.b = b;
        }

        public int a() { return a; }

        public int b() { return b; }

        public String toString() {
            return String.format("%s %s", a, b);
        }
    }

    private class Node {
        private final Data d;
        private Node next;

        public Node(Data d) {
            this.d = d;
            next = null;
        }

        public Data data() {
            return d;
        }

        public void add(Node node) {
            Node n = this;
            while (n.next != null) n = n.next;
            n.next = node;
        }

        public boolean isMoreThanOne() {
            return (next != null);
        }
    }

    public int hash(int i, int j) {
        return array[i] + array[j];
    }

    public void add(int i, int j) {
        int h = hash(i, j);
        Node d = new Node(new Data(i, j));
        if (hm[h] == null)
            hm[hash(i,j)] = d;
        else
            hm[h].add(d);
    }

    public FourSum(int[] data) {
        n = data.length;
        array = new int[n];
        for (int i = 0; i < n; i++)
            array[i] = data[i];

        hm = new Node[2*n];
        for (int i = 0; i < n; i++)
            for (int j = i + 1; j < n; j++)
                add(i, j);
    }

    public void bruteFourSum() {
        int c = 0;
        for (int i = 0; i < n; i++)
            for (int j = i+1; j < n; j++)
                for (int k = i+1; k < n; k++)
                    for (int l = k+1; l < n; l++)
                        if (array[i] + array[j] == array[k] + array[l]) {
                            StdOut.printf("%s %s  %s %s : %s + %s = %s + %s\n", i, j, k, l, array[i], array[j], array[k], array[l]);
                            c++;
                        }
        StdOut.println(c);
    }

    public void data() {
        int c = 0;
        for (Node k : hm)
            if (k != null && k.isMoreThanOne()) {
                String si = "";
                String sa = ": ";
                while (k != null) {
                    si += k.data().toString() + " ";
                    sa += array[k.data().a()] + " + " + array[k.data().b()];
                    if (k.next != null) {
                        sa += " = ";
                        si += " ";
                    }
                    k = k.next;
                }
                StdOut.println(si + sa);
                c++;
            }
        StdOut.println(c);
    }

    public static void main(String[] args) {
        int n = 10;
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = StdRandom.uniform(n);
            StdOut.print(a[i] + " ");
        }
        StdOut.println();
        StdOut.println();
        FourSum f = new FourSum(a);
        f.bruteFourSum();
        f.data();
        // it.forEach(
        //     x -> StdOut.printf("%s %s: %s %s\n", x.a(), x.b(), a[x.a()], a[x.b()])
        // );
    }
}
