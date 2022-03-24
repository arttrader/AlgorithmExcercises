/* *****************************************************************************
 *  Name:              J Hirota
 *  Coursera User ID:
 *  Last modified:     2022-3-24
 **************************************************************************** */

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class DFSNonRecursive {
    private boolean[] marked;
    private int count;
    private final Stack<Integer> stack;

    public DFSNonRecursive(Graph G, int s) {
        marked = new boolean[G.V()];
        stack = new Stack<>();
        dfs(G, s);
    }

    private void dfs(Graph G, int v) {
        count++;
        marked[v] = true;
        boolean done = false;
        int w = v;
        while (!done) {
            stack.push(w);
            for (int x: G.adj(w)) {
                if (!marked[x]) {
                    w = x;
                    break;
                }
            }
            if (stack.isEmpty()) done = true;
            else w = stack.pop();
        }
    }

    public boolean marked(int v) {
        return marked[v];
    }

    public int count() {
        return count;
    }


    public static void main(String[] args) {
        In in = new In(args[0]);
        Graph G = new Graph(in);
        int s = Integer.parseInt(args[1]);
        DFSNonRecursive search = new DFSNonRecursive(G, s);
        for (int v = 0; v < G.V(); v++) {
            if (search.marked(v))
                StdOut.print(v + " ");
        }

        StdOut.println();
        if (search.count() != G.V()) StdOut.println("NOT connected");
        else                         StdOut.println("connected");
    }
}
