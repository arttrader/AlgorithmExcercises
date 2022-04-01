/* *****************************************************************************
 *  Name:              J Hirota
 *  Coursera User ID:
 *  Last modified:     2022-3-31
 **************************************************************************** */

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;

public class EulerCycle {
    private final Graph g;
    private boolean[] marked;

    public EulerCycle(Graph G) {
        g = new Graph(G);
        marked = new boolean[G.E()];
    }

    public boolean eulerCycle() {
        for (int i = 0; i < g.V(); i++) {
            if (g.degree(i) % 2 != 0) return false;
        }
        return true;
    }

    private void findEulerPath(int v, List<Integer> list) {
        marked[v] = true;
        for (int a: g.adj(v)) {
            if (!marked[a]) {
                findEulerPath(a, list);
            }
        }
        list.add(v);
        marked[v] = true;
    }

    public Iterable<Integer> findEulerCycle() {
        List<Integer> list = new ArrayList<>();
        findEulerPath(0, list);
        return list;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        Graph G = new Graph(in);
        EulerCycle e = new EulerCycle(G);
        if (e.eulerCycle()) {
            StdOut.println("Euler Cycle");
            Iterable<Integer> it = e.findEulerCycle();
            StdOut.println(it);
        }
    }
}
