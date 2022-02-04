/* *****************************************************************************
 *  Name:              J Hirota
 *  Coursera User ID:
 *  Last modified:     2022-2-3
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class Solver {
    private final Iterable<Board> solution;
    private int minMoves = -1; // default must be unsolvable

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();
        solution = aStar(initial);
    }

    private class Node implements Comparable<Node> {
        private final Board b;
        private final int steps;
        private final int manhattan;
        private final Node cameFrom;

        public Node(Board board, int moves, int manhattan, Node from) {
            b = board;
            this.steps = moves;
            this.manhattan = manhattan;
            cameFrom = from;
        }

        public int compareTo(Node n) {
            return this.priority() - n.priority();
        }

        public Node prev() { return cameFrom; }

        public Board board() { return b; }

        public int priority() { return manhattan + steps; }
    }

    private Iterable<Board> aStar(Board start) {
        int sManhattan = start.manhattan();
        MinPQ<Node> gt = new MinPQ<>(sManhattan);
        Node currentNode = new Node(start, 0, sManhattan, null);
        Board twin = start.twin();
        int tManhattan = twin.manhattan();
        MinPQ<Node> tGt = new MinPQ<>(tManhattan);
        Node tCurrentNode = new Node(twin, 0, tManhattan, null);
        // int count = 0;
        do {
            // StdOut.println(currentNode.board().toString());
            if (currentNode.board().isGoal()) {
                minMoves = currentNode.steps;
                return reconstructPath(currentNode);
            }
            for (Board nb : currentNode.board().neighbors()) {
                if (currentNode.prev() == null || !currentNode.prev().board().equals(nb))
                    gt.insert(
                            new Node(nb, currentNode.steps + 1, nb.manhattan(), currentNode));
            }
            currentNode = gt.delMin();
            if (tCurrentNode.board().isGoal()) return null; // not solvable
            for (Board nb : tCurrentNode.board().neighbors()) {
                if (tCurrentNode.prev() == null || !tCurrentNode.prev().board().equals(nb))
                    tGt.insert(
                            new Node(nb, tCurrentNode.steps + 1, nb.manhattan(), tCurrentNode));
            }
            tCurrentNode = tGt.delMin();
            // StdOut.printf("count %s\n", count++);
        } while (!gt.isEmpty());
        return null;
    }

    private Iterable<Board> reconstructPath(Node n) {
        ArrayList<Board> totalPath = new ArrayList<>();
        totalPath.add(n.board());
        while (n.prev() != null) {
            n = n.prev();
            totalPath.add(0, n.board());
        }
        return totalPath;
    }

/*    private Board getGoal(int n) {
        int[][] t = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                t[i][j] = i*n + j + 1;
        t[n-1][n-1] = 0; // last one should be blank
        return new Board(t);
    }*/

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solution != null;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return minMoves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return solution;
    }


    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);
        StdOut.println("Initial Board");
        StdOut.println(initial.toString());
        // solve the puzzle
        Solver solver = new Solver(initial);
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
