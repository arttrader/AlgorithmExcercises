/* *****************************************************************************
 *  Name:              J Hirota
 *  Coursera User ID:
 *  Last modified:     2022-3-24
 **************************************************************************** */

import edu.princeton.cs.algs4.BreadthFirstPaths;
import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;

import java.util.ArrayList;
import java.util.List;

public class DimameterCenter {
    private final Graph g;
    private final BreadthFirstPaths bfp;
    private final int s;

    public DimameterCenter(Graph G, int s) {
        g = new Graph(G);
        bfp = new BreadthFirstPaths(g, s);
        this.s = s;
    }

    public Iterable<Integer> diameter() {
        // for each vertex
        // find distance to each vertex
        // return the longest
        int v = g.V();
        int e = g.E();
        int longest = 0;
        int longestV = s;
        int longest2 = 0;
        int longestV2 = s;
        for (int i = 0; i < v; i++) {
            int d = bfp.distTo(i);
            if (d > longest) {
                longest2 = longest;
                longestV2 = i;
                longest = d;
                longestV = i;
            }
        }
        Iterable<Integer> list = bfp.pathTo(longestV);
        Iterable<Integer> list2 = bfp.pathTo(longestV2);
        List<Integer> result = new ArrayList<>();
        for (int i : list) result.add(i);
        Stack<Integer> reverse = new Stack<>();
        for (int i : list2) if (i != s) reverse.push(i);
        for (int i : reverse) result.add(i);
        return result;
    }

    public int center() {
        // get diameter
        // find vertex at diameter / 2
        Iterable<Integer> d = diameter();
        List<Integer> list = new ArrayList<>();
        int len = 0;
        for (int i: d) {
            list.add(i);
            len++;
        }
        int m = len / 2;
        return list.get(m);
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
