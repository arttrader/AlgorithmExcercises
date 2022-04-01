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
    private boolean[] marked; // marked[v] = is there an s-v path
    private int[] edgeTo;   // edgeTo[v] = previous edge on shortest s-v path
    private int[] distTo;   // distTo[v] = number of edges shortest s-v path
    private int count;

    public DFSNonRecursive(Graph G, int s) {
        marked = new boolean[G.V()];
        distTo = new int[G.V()];
        edgeTo = new int[G.V()];
        dfs(G, s);
    }

    private void dfs(Graph G, int s) {
        Stack<Integer> stack = new Stack<>();
        for (int v = 0; v < G.V(); v++)
            distTo[v] = Integer.MAX_VALUE;
        distTo[s] = 0;
        marked[s] = true;
        count = 1;
        stack.push(s);

        while (!stack.isEmpty()) {
            int v = stack.pop();
            for (int w: G.adj(v)) {
                if (!marked[w]) {
                    count++;
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                    marked[w] = true;
                    stack.push(w);
                }
            }
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
