/* *****************************************************************************
 *  Name:              J Hirota
 *  Coursera User ID:
 *  Last modified:     2022-03-04
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.HashMap;

public class Binary {
    HashMap<String, String> h;

    public static void main(String[] args) {
        int m = 117;
        String key = "polygenelubricants";

        StdOut.println(key.hashCode());
        StdOut.println(Math.abs(key.hashCode()));
        StdOut.println(key.hashCode() % m);
        StdOut.println(Integer.toBinaryString(key.hashCode()));
        StdOut.println(String.format("%32s", Integer.toBinaryString(0x7fffffff)).replace(' ', '0'));
        StdOut.println(String.format("%32s", Integer.toBinaryString(key.hashCode() & 0x7fffffff)).replace(' ', '0'));

        StdOut.println(key.hashCode() & 0x7fffffff);
        StdOut.println((key.hashCode() & 0x7fffffff) % m);
    }
}
