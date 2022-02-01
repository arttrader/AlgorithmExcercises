/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

public class Ball {
    private double rx, ry;
    private double vx, vy;
    private final double radius;

    public Ball(double r)
    {
        radius = r;
    }

    public void move(double dt)
    {
        if (rx + vx * dt < radius || rx + vx * dt > 1.0 - radius) vx = -vx;
        if (ry + vy * dt < radius || ry + vy * dt > 1.0 - radius) vy = -vy;
        rx = rx + vx * dt;
        ry = ry + vy * dt;
    }

    public void draw() {

    }

    public static void main(String[] args) {

    }
}
