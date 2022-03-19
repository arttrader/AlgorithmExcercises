/* *****************************************************************************
 *  Name:              J Hirota
 *  Coursera User ID:
 *  Last modified:     2022-1-31
 **************************************************************************** */

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Particle;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

import java.awt.Color;
import java.util.Iterator;

public class BouncingParticles {
    private final int n;
    private final CollisionSystem cs;

    public class CollisionSystem {
        private final MinPQ<Event> pq;
        private double t = 0.0;
        private final Particle[] particle;

        public class Event implements Comparable<Event> {
            private final double time;
            private final Particle a, b;
            private int countA, countB;
            private boolean valid;

            public Event(double t, Particle a, Particle b) {
                time = t;
                this.a = a;
                this.b = b;
                valid = true;
            }

            public int compareTo(Event that) {
                return Double.compare(this.time, that.time);
            }

            public boolean isValid() {
                return valid;
            }

            public void invalidate() { valid = false; }
        }

        public CollisionSystem(Particle[] particle) {
            this.particle = particle;
            pq = new MinPQ<Event>();
        }

        private void predict(Particle a) {
            if (a == null) return;
            for (int i = 0; i < n; i++) {
                double dt = a.timeToHit(particle[i]);
                pq.insert(new Event(t + dt, a, particle[i]));
            }
            pq.insert(new Event(t + a.timeToHitVerticalWall(), a, null));
            pq.insert(new Event(t + a.timeToHitHorizontalWall(), null, a));
        }

        private void redraw() {
            StdDraw.clear();
            for (int i = 0; i < n; i++) particle[i].draw();
            StdDraw.show();
            StdDraw.enableDoubleBuffering();
        }

        public void simulate() {
            for (int i = 0; i < n; i++) predict(particle[i]);
            pq.insert(new Event(0, null, null));

            while (!pq.isEmpty()) {
                Event event = pq.delMin();
                if (!event.isValid()) continue;
                Particle a = event.a;
                Particle b = event.b;

                for (int i = 0; i < n; i++)
                    particle[i].move(event.time - t);
                t = event.time;

                if (a != null && b != null) {
                    a.bounceOff(b);
                    Iterator<Event> list = pq.iterator();
                    while (list.hasNext()) {
                        Event e = list.next();
                        if ((e.a != null && (e.a.equals(a) || e.a.equals(b))) ||
                                (e.b != null && (e.b.equals(a) || e.b.equals(b))))
                            e.invalidate();
                    }
                }
                else if (a != null && b == null) a.bounceOffVerticalWall();
                else if (a == null && b != null) b.bounceOffHorizontalWall();
                else {
                    redraw();
                    pq.insert(new Event(t+0.1, null, null));
                }

                predict(a);
                predict(b);
            }
        }
    }

    public BouncingParticles(int n) {
        this.n = n;
        Particle[] balls = new Particle[n];
        double radius = 0.005;
        double mass   = 0.3;
        Color color  = Color.BLACK;
        for (int i = 0; i < n; i++) {
            double rx     = StdRandom.uniform(0.0, 1.0);
            double ry     = StdRandom.uniform(0.0, 1.0);
            double vx     = StdRandom.uniform(-0.005, 0.005);
            double vy     = StdRandom.uniform(-0.005, 0.005);
            balls[i] = new Particle(rx, ry, vx, vy, radius, mass, color);
        }
        cs = new CollisionSystem(balls);
    }

    public void start() {
        cs.simulate();
    }


    public static void main(String[] args) {
        double scale = 1;
        StdDraw.setCanvasSize(800, 800);
        StdDraw.setXscale(0, scale);
        StdDraw.setYscale(0, scale);
        BouncingParticles bp = new BouncingParticles(80);
        bp.start();
    }
}
