/* *****************************************************************************
 *  Name:              J Hirota
 *  Coursera User ID:
 *  Last modified:     2022-1-16
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

public class QueueSimple {
    private Node first, last;

    private class Node {
        String item;
        Node next;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public void enqueue(String s) {
        Node n = new Node();
        n.item = s;
        n.next = null;
        if (isEmpty()) {
            first = n;
            last = n;
        } else {
            last.next = n;
            last = n;
        }
    }

    public String dequeue() {
        String s = first.item;
        first = first.next;
        if (isEmpty()) last = null;
        return s;
    }

    public static void main(String[] args) {
        QueueSimple q = new QueueSimple();
        q.enqueue("test1");
        q.enqueue("test2");
        StdOut.println(q.dequeue());
        q.enqueue("test3");
        StdOut.println(q.dequeue());
    }
}
