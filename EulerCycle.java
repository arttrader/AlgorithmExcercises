/* *****************************************************************************
 *  Name:              J Hirota
 *  Coursera User ID:
 *  Last modified:     2022-3-31
 **************************************************************************** */

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class EulerCycle {
    private final Graph g;
    private boolean[] marked;
    private boolean hasCycle;
    private boolean eulerCycle;
    private int count;
    private boolean[][] edgeMarked;
    private int last;

    public EulerCycle(Graph G) {
        g = G;
        marked = new boolean[g.V()];
        edgeMarked = new boolean[g.V()][g.V()];
        last = -1;
        dfs(0, 0);
    }

    private void dfs(int v, int u) {
        marked[v] = true;
        if (v != u) {
            edgeMarked[v][u] = true;
            edgeMarked[u][v] = true;
            count++;
        }
        for (int w: g.adj(v)) {
            if (marked[w] && w != u) hasCycle = true;
            if (!edgeMarked[w][v]) {
                last = w;
                StdOut.println(v + "-" + w + " ");
                dfs(w, v);
            }
        }
        if (v == u) { // first recursive call
            StdOut.printf("v %s   last %s   count %s  \n", v, last, count);
            if (count == g.E() && last == v)
                eulerCycle = true;
        }
    }

    public boolean hasCycle() { return hasCycle; }

    public boolean hasEulerCycle() { return eulerCycle; }


    public static void main(String[] args) {
        In in = new In(args[0]);
        Graph G = new Graph(in);
        EulerCycle c = new EulerCycle(G);
        if (c.hasCycle()) StdOut.println("Has cycle");
        if (c.hasEulerCycle()) StdOut.println("Has Euler Cycle");
    }
}
