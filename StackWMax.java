/* *****************************************************************************
 *  Name:              J Hirota
 *  Coursera User ID:  123456
 *  Last modified:     2022-1-16
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

class StackWMax {
    private final int INITIAL_SIZE = 100;
    private double[] stack1 = new double[INITIAL_SIZE];
    private double[] stack2 = new double[INITIAL_SIZE];
    private int n1 = 0;
    private int n2 = 0;

    public boolean isEmpty() {
        return n1 == 0;
    }

    public double pop() {
        if (n1 == 0) throw new java.util.NoSuchElementException();

        if (n2 > 0 && stack1[n1-1] == stack2[n2-1]) n2--;
        return stack1[--n1];
    }

    private void resize(int newSize) {
        double[] copy = new double[newSize];
        for (int i = 0; i < n1; i++)
            copy[i] = stack1[i];
        stack1 = copy;
    }

    public void push(double v) {
        if (stack1.length == n1) resize(2*n1);
        stack1[n1++] = v;
        if (n2 > 0) {
//            StdOut.println("v="+v+"  stack2: "+stack2[n2-1]);
            if (v > stack2[n2-1]) stack2[n2++] = v;
        } else {
            stack2[n2++] = v;
        }
    }

    public double getMax() {
        if (n2 > 0)
            return stack2[n2-1];
        else
            return 0;
    }

    private void display() {
        String s = "stack1 ";
        for (int i = 0; i < n1; i++) s += stack1[i] + " ";
        s += "stack2 ";
        for (int i = 0; i < n2; i++) s += stack2[i] + " ";
        StdOut.println(s);
    }

    public static void main(String[] args) {
        StackWMax stack = new StackWMax();
        stack.push(5.2);
        stack.push(2.6);
        stack.push(1.5);
        stack.display();
        StdOut.println(stack.pop());
        stack.display();
        StdOut.println(stack.pop());
        StdOut.println("max: " + stack.getMax());
        stack.display();
        stack.push(3.5);
        stack.display();
    }
}
