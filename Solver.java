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
    private final MinPQ<Node> gameTree;
    private final Iterable<Board> solution;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();
        gameTree = new MinPQ<>();
        Board goal = getGoal(initial.dimension());
        solution = aStar(initial, goal);
    }

    private class Node implements Comparable<Node> {
        private Board b;
        private int moves;
        private int manhattan;
        private Node cameFrom;

        public Node(Board board, int moves, int manhattan, Node from) {
            b = board;
            this.moves = moves;
            this.manhattan = manhattan;
            cameFrom = from;
        }

        public int compareTo(Node n) {
            return this.manhattan - n.manhattan;
        }

        public Node prev() { return cameFrom; }

        public Board board() { return b; }
    }

    private Iterable<Board> aStar(Board start, Board goal) {
        Node currentNode = new Node(start, 0, start.manhattan(), null);
        gameTree.insert(currentNode);
        while (!gameTree.isEmpty()) {
            if (currentNode.board().equals(goal))
                return reconstructPath(currentNode);
            Iterable<Board> it = next(currentNode);
            for (Board b : it) {
                int cManhattan = b.manhattan();
                if (currentNode.prev() == null || !currentNode.prev().board().equals(b))
                    gameTree.insert(new Node(b, currentNode.moves + 1, cManhattan, currentNode));
            }
            currentNode = gameTree.delMin();
//            StdOut.println(currentNode.board().toString());
        }
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

    private Iterable<Board> next(Node n) {
        return n.board().neighbors();
    }

    private Board getGoal(int n) {
        int[][] t = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                t[i][j] = i*n + j + 1;
        t[n-1][n-1] = 0; // last one should be blank
        return new Board(t);
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solution != null;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        int m = 0;
        for (Board b : solution) m++;
        if (m > 0) m--;
        return m;
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
