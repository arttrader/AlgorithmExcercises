/* *****************************************************************************
 *  Name:              J Hirota
 *  Coursera User ID:
 *  Last modified:     2022-2-3
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;

public class Board {
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    private final int[][] tiles;
    private final int n;
    private ArrayList<Board> moves;
    private Board twin;
    private int zx = 0;
    private int zy = 0;

    public Board(int[][] tiles) {
        n = tiles.length;
        this.tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                this.tiles[i][j] = tiles[i][j];
            }
        locateBlank();
    }

    // string representation of this board
    public String toString() {
        String s = String.format("%s\n", n);
        StringBuilder sb = new StringBuilder(s);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sb.append(" ");
                sb.append(tiles[i][j]);
             }
            sb.append("\n");
        }
        return sb.toString();
    }

    // board dimension n
    public int dimension() { return n; }

    // number of tiles out of place
    public int hamming() {
        int h = 0;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (tiles[i][j] != i*n+j+1 && tiles[i][j] > 0) h++;
        return h;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int m = 0;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] > 0) {
                    int dv = (tiles[i][j]-1) / n - i;
                    int dh = (tiles[i][j]-1) % n - j;
                    m += Math.abs(dv) + Math.abs(dh);
                }
            }
        return m;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) return false;
        if (!y.getClass().equals(this.getClass())) return false;
        if (((Board) y).dimension() != dimension()) return false;
        return y.toString().equals(this.toString());
    }

    private void createMoves() {
        // make a list of neighbor boards
        moves = new ArrayList<>();
        if (zx > 0) {
            Board board1 = copy();
            if (board1.moveTo(0, -1)) moves.add(board1);
        }
        if (zx < n-1) {
            Board board2 = copy();
            if (board2.moveTo(0, 1)) moves.add(board2);
        }
        if (zy > 0) {
            Board board3 = copy();
            if (board3.moveTo(-1, 0)) moves.add(board3);
        }
        if (zy < n-1) {
            Board baord4 = copy();
            if (baord4.moveTo(1, 0)) moves.add(baord4);
        }
    }


    // all neighboring boards
    public Iterable<Board> neighbors() {
        createMoves();
        return moves;
    }

    private void locateBlank() {
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (tiles[i][j] == 0) {
                    zx = j;
                    zy = i;
                    break;
                }
    }

    private boolean moveTo(int dy, int dx) {
         if (dx != 0) {
            swap(zy, zx, zy, zx + dx);
            zx += dx;
            return true;
        }
        if (dy != 0) {
            swap(zy, zx, zy + dy, zx);
            zy += dy;
            return true;
        }
        return false;
    }

    private void swap(int y0, int x0, int y1,  int x1) {
        int t = tiles[y0][x0];
        tiles[y0][x0] = tiles[y1][x1];
        tiles[y1][x1] = t;
    }

    private Board copy() {
        return new Board(tiles);
    }

    private void rndSwap() {
        // this is only for twin
        int y0;
        int x0;
        int y1;
        int x1;
        do {
            y0 = StdRandom.uniform(n);
            x0 = StdRandom.uniform(n);
        } while (tiles[y0][x0] == 0);
        do {
            y1 = StdRandom.uniform(n);
            x1 = StdRandom.uniform(n);
        } while ((y1 == y0 && x1 == x0) || tiles[y1][x1] == 0);
        swap(y0, x0, y1, x1);
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        if (twin == null) {
            twin = copy();
            twin.rndSwap();
        }
        return twin;
    }


    public static void main(String[] args) {
        // create board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board b = new Board(tiles);
        assert b.twin().equals(b.twin());
        StdOut.println("twin");
        StdOut.println(b.twin().toString());
        // StdOut.println(b.toString());
        // b.moveTo(1, 0);
        // StdOut.println(b.toString());
        // b.moveTo(0, -1);
        // StdOut.println(b.toString());
        Iterable<Board> it = b.neighbors();
        for (Board nb : it) {
            StdOut.print(nb.toString());
            StdOut.println("manhattan: " + nb.manhattan());
            if (b.equals(nb)) StdOut.println("the same board");
        }
    }
}
