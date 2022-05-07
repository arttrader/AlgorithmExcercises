/* *****************************************************************************
 *  Name:              J Hirota
 *  Coursera User ID:
 *  Last modified:     2022-5-7
 **************************************************************************** */

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/*
Hamiltonian path in a DAG.
Given a directed acyclic graph, design a linear-time algorithm to determine whether it has a Hamiltonian path (a simple path that visits every vertex), and if so, find one.
 */
public class HamiltonianPath {
    private BreadthFirstDirectedPaths bfs;
    private Digraph g;

    public HamiltonianPath(Digraph G) {
        g = G;
        bfs = new BreadthFirstDirectedPaths(G, 0);
    }

    public boolean isHamiltonian() {
        for (int v = 0; v < g.V(); v++) {
            if (!bfs.hasPathTo(v)) return false;
        }
        return true;
    }

    public Iterable<Integer> path(int v) {
        return bfs.pathTo(v);
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int s = 0;
        Digraph G = new Digraph(in);
        HamiltonianPath hp = new HamiltonianPath(G);
        if (hp.isHamiltonian())
            StdOut.println("Hamiltonian path found");
        else
            StdOut.println("No Hamiltonian path");

    }
}
