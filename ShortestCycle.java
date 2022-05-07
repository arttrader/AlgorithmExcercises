/* *****************************************************************************
 *  Name:              J Hirota
 *  Coursera User ID:
 *  Last modified:     2022-4-18
 **************************************************************************** */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;

/*
Shortest directed cycle.
Given a digraph G, design an efficient algorithm to find a directed cycle with the minimum number of edges (or report that the graph is acyclic). The running time of your algorithm should be at most proportional to V(E+V) and use space proportional to E+V, where V is the number of vertices and E is the number of edges.
 */
public class ShortestCycle {
    private boolean[] marked;
    private int[] edgeTo;
    private Stack<Integer> stack;
    private List<Stack<Integer>> cycles;
    private boolean[] onStack;

    public ShortestCycle(Digraph G) {
        onStack = new boolean[G.V()];
        edgeTo = new int[G.V()];
        marked = new boolean[G.V()];
        cycles = new ArrayList<>();
        for (int v = 0; v < G.V(); v++)
            if (!marked[v]) dfs(G, v);
        int shortest = Integer.MAX_VALUE;
        for (Stack<Integer> cycle : cycles)
            if (cycle.size() < shortest) {
                stack = cycle;
                shortest = cycle.size();
            }
    }

    private void dfs(Digraph G, int v) {
        StdOut.println("Checking " + v);
        onStack[v] = true;
        marked[v] = true;
        for (int w: G.adj(v))
            if (this.hasCycle()) {
                StdOut.println("Cycle found: length=" + stack.size());
                for (int i: stack)
                    StdOut.print(i + " ");
                StdOut.println();
                cycles.add(stack);
                stack = null;
                onStack[v] = false;
                return;
            } else if (!marked[w]) {
                edgeTo[w] = v;
                dfs(G, w);
            } else if (onStack[w]) {
                stack = new Stack<>();
                for (int x = v; x != w; x = edgeTo[x])
                    stack.push(x);
                stack.push(w);
                stack.push(v);
            }
        onStack[v] = false;
    }

    public boolean hasCycle() {
        return stack != null;
    }

    public Iterable<Integer> cycle() {
        return stack;
    }


    public static void main(String[] args) {
        In in = new In(args[0]);
        int s = 0;
        Digraph G = new Digraph(in);
        ShortestCycle dc = new ShortestCycle(G);
        StdOut.println("Has cycle: " + dc.hasCycle());
        if (dc.hasCycle()) {
            StdOut.println("Shortest cycle");
            Iterable<Integer> cycle = dc.cycle();
            for (int v: cycle)
                StdOut.print(v + " ");
        }
    }
}
