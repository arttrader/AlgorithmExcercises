/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class QueueWStack {
    private Stack<String> stack1 = new Stack<String>();
    private Stack<String> stack2 = new Stack<String>();
    private int active = 1;

    public boolean isEmpty() {
        if (active == 1) return stack1.isEmpty();
        else return stack2.isEmpty();
    }

    public void enqueue(String s) {
        if (active == 1) stack1.push(s);
        else stack2.push(s);
    }

    public String dequeue() {
        if (active == 1) {
            while (!stack1.isEmpty()) {
                stack2.push(stack1.pop());
            }
            active = 2;
            return stack2.pop();
        } else {
            while (!stack2.isEmpty()) {
                stack1.push(stack2.pop());
            }
            active = 1;
            return stack1.pop();
        }
    }

    public static void main(String[] args) {
        QueueWStack q = new QueueWStack();
        q.enqueue("test1");
        q.enqueue("test2");
        StdOut.println(q.dequeue());
        q.enqueue("test3");
        StdOut.println(q.dequeue());
    }
}
