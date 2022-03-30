/* *****************************************************************************
 *  Name:              J Hirota
 *  Coursera User ID:
 *  Last modified:     2022-3-24
 **************************************************************************** */

import edu.princeton.cs.algs4.BreadthFirstPaths;
import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;

public class DimameterCenter {
    private final Graph g;
    private final BreadthFirstPaths bfp;

    public DimameterCenter(Graph G, int s) {
        g = new Graph(G);
        bfp = new BreadthFirstPaths(g, s);
    }

    public Iterable<Integer> diameter() {
        // for each vertex
        // find distance to each vertex
        // return the longest


        return null;
    }

    public int center() {
        // for each vertex
        // find the longest distance to any vertex
        // choose the one with the shortest


        return 0;
    }


    public static void main(String[] args) {
        In in = new In(args[0]);
        Graph G = new Graph(in);
        int s = Integer.parseInt(args[1]);
        DimameterCenter dc = new DimameterCenter(G, s);
        for (int v = 0; v < G.V(); v++) {

        }
    }
}
