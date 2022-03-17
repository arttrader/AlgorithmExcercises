/* *****************************************************************************
 *  Name:              J Hirota
 *  Coursera User ID:
 *  Last modified:     2022-1-31
 **************************************************************************** */

import edu.princeton.cs.algs4.StdDraw;

public class BouncingBalls {

    public static void main(String[] args) {
        int n = 100;
        Ball[] balls = new Ball[n];
        for (int i = 0; i < n; i++)
            balls[i] = new Ball();
        while (true) {
            StdDraw.clear();
            for (int i = 0; i < n; i++) {
                balls[i].move(0.5);
                balls[i].draw();
            }
            StdDraw.show();
            StdDraw.pause(50);
            StdDraw.enableDoubleBuffering();
        }
    }
}
