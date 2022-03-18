/* *****************************************************************************
 *  Name:              J Hirota
 *  Coursera User ID:  123456
 *  Last modified:     2022-1-16
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;


class StackOfStrings {
    private final int INITIAL_SIZE = 100;
    private String[] stack = new String[INITIAL_SIZE];
    private int n = 0;

    public boolean isEmpty() {
        return n == 0;
    }

    private void resize(int newSize) {
        String[] copy = new String[newSize];
        for (int i = 0; i < n; i++)
            copy[i] = stack[i];
        stack = copy;
    }

    public String pop() {
        String s = stack[--n];
        stack[n] = null;
        if (n > 0 && n <= stack.length/4) resize(stack.length/2);
        return s;
    }

    public void push(String s) {
        if (stack.length == n) resize(2*n);
        stack[n++] = s;
    }

    public static void main(String[] args) {
        StackOfStrings stack = new StackOfStrings();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if (s.equals("-")) StdOut.print(stack.pop()+" ");
            else               stack.push(s);
        }
    }
}
