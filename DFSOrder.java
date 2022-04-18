/* *****************************************************************************
 *  Name:              J Hirota
 *  Coursera User ID:
 *  Last modified:     2022-4-18
 **************************************************************************** */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class DFSOrder {
    private boolean[] marked;
    private Stack<Integer> reversePost;

    public DFSOrder(Digraph G) {
        marked = new boolean[G.V()];
        reversePost = new Stack<>();
        for (int v = 0; v < G.V(); v++)
            if (!marked[v]) dfs(G, v);
    }

    private void dfs(Digraph G, int v) {
        marked[v] = true;
        for (int w: G.adj(v))
            if (!marked[w]) dfs(G, w);
        reversePost.push(v);
    }

    public Iterable<Integer> reversePost() {
        return reversePost;
    }


    public static void main(String[] args) {
        In in = new In(args[0]);
        int s = 0;
        Digraph G = new Digraph(in);
        DFSOrder dfo = new DFSOrder(G);
        Iterable<Integer> order = dfo.reversePost();
        for (int v: order)
            StdOut.println(v);
    }
}
