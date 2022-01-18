/* *****************************************************************************
 *  Name:              J Hirota
 *  Coursera User ID:
 *  Last modified:     2022-1-18
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> q = new RandomizedQueue<>();

        int i = 0;
        while (!StdIn.isEmpty()) {
            i++;
            String s = StdIn.readString();
            q.enqueue(s);
//            System.out.println(s);
        }
        int n = i;

        if (k <= n) {
            Iterator<String> it = q.iterator();
            for (i = 0; i < k; i++)
                StdOut.println(it.next());
        }
    }
}
