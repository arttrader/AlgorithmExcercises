/* *****************************************************************************
 *  Name:              J Hirota
 *  Coursera User ID:
 *  Last modified:     2022-4-6
 **************************************************************************** */

import edu.princeton.cs.algs4.Graph;

import java.util.ArrayList;

public class Paths {
    private final int s;
    private ArrayList<Integer> path = new ArrayList<>();
    private final int n;
    private boolean traversed[];

    public Paths(Graph G, int s) {
        traversed = new boolean[G.V()];
        this.s = s;
        int count = 0;
        for (int a : G.adj(s)) {
            path.add(a);
            traversed[a] = false;
            count++;
        }
        n = count;
    }

    public boolean hasPathTo(int v) {
        return path.contains(v);
    }

    public Iterable<Integer> pathTo(int v) {
        return path;
    }

    public void travseTo(int v) {
        traversed[v] = true;
    }

    public boolean traversedTo(int v) {
        return traversed[v];
    }

    public void print() {

    }

    public static void main(String[] args) {

    }
}
