/* *****************************************************************************
 *  Name:              J Hirota
 *  Coursera User ID:
 *  Last modified:     2022-1-31
 **************************************************************************** */

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

public class Particle {
    private double rx, ry;      // position
    private double vx, vy;
    private final double radius;
    private final double mass;
    private int count;          // number of collisions

    public Particle() {
        radius = 0.5;
        mass = 0.5;
        count = 10;
        rx = StdRandom.uniform(0, 1.0);
        ry = StdRandom.uniform(0, 1.0);
        vx = StdRandom.uniform(0.01, 0.1);
        vy = StdRandom.uniform(0.01, 0.1);
    }

    public void move(double dt) {
        if (rx + vx * dt < radius || rx + vx * dt > 1.0 - radius) vx = -vx;
        if (ry + vy * dt < radius || ry + vy * dt > 1.0 - radius) vy = -vy;
        rx = rx + vx * dt;
        ry = ry + vy * dt;
    }

    public void draw() {
        StdDraw.filledCircle(rx, ry, radius);
    }

    // predict collision with particle or wall
    public double timeToHit(Particle that) {
        if (this == that) return Double.POSITIVE_INFINITY;
        double dx  = that.rx - this.rx, dy  = that.ry - this.ry;
        double dvx = that.vx - this.vx;
        double dvy = that.vy - this.vy;
        double dvdr = dx*dvx + dy*dvy;
        if (dvdr > 0) return Double.POSITIVE_INFINITY;
        double dvdv = dvx*dvx + dvy*dvy;
        double drdr = dx*dx + dy*dy;
        double sigma = this.radius + that.radius;
        double d = (dvdr*dvdr) - dvdv * (drdr - sigma*sigma);
        if (d < 0) return Double.POSITIVE_INFINITY;
        return -(dvdr + Math.sqrt(d)) / dvdv;
    }

    public double timeToHitVerticalWall() { return 0; }
    public double timeToHitHorizontalWall() { return 0; }

    // resolve collision with particle or wall
    public void bounceOff(Particle that) {
        double dx  = that.rx - this.rx, dy  = that.ry - this.ry;
        double dvx = that.vx - this.vx, dvy = that.vy - this.vy;
        double dvdr = dx*dvx + dy*dvy;
        double dist = this.radius + that.radius;
        double J = 2 * this.mass * that.mass * dvdr / ((this.mass + that.mass) * dist);
        double Jx = J * dx / dist;
        double Jy = J * dy / dist;
        this.vx += Jx / this.mass;
        this.vy += Jy / this.mass;
        that.vx -= Jx / that.mass;
        that.vy -= Jy / that.mass;
        this.count++;
        that.count++;
    }

    public void bounceOffVerticalWall() { }
    public void bounceOffHorizontalWall() { }


    public static void main(String[] args) {

    }
}
