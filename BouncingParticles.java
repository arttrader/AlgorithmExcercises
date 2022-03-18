/* *****************************************************************************
 *  Name:              J Hirota
 *  Coursera User ID:
 *  Last modified:     2022-1-31
 **************************************************************************** */

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdDraw;

public class BouncingParticles {
    private final int n;
    private final CollisionSystem cs;

    public class Event implements Comparable<Event> {
        private double time;
        private Particle a, b;
        private int countA, countB;
        private boolean valid;

        public Event(double t, Particle a, Particle b) {
            time = t;
            this.a = a;
            this.b = b;
        }

        public int compareTo(Event that) {
            return Double.compare(this.time, that.time);
        }

        public boolean isValid() {
            return true;
        }
    }

    public class CollisionSystem {
        private final MinPQ<Event> pq;
        private double t = 0.0;
        private final Particle[] particle;

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
            for (int i = 0; i < n; i++) {
                particle[i].draw();
            }
            StdDraw.show();
//            StdDraw.pause(10);
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

                if (a != null && b != null) a.bounceOff(b);
                else if (a != null && b == null) a.bounceOffVerticalWall();
                else if (a == null && b != null) b.bounceOffHorizontalWall();
                else {
                    redraw();
                    pq.insert(new Event(t+1, null, null));
                }

                predict(a);
                predict(b);
            }
        }
    }

    public BouncingParticles(int n) {
        this.n = n;
        Particle[] balls = new Particle[n];
        for (int i = 0; i < n; i++)
            balls[i] = new Particle();
        cs = new CollisionSystem(balls);
    }

    public void start() {
        cs.simulate();
    }


    public static void main(String[] args) {
/*        double scale = 1;
        StdDraw.setCanvasSize(600, 600);
        StdDraw.setXscale(0, scale);
        StdDraw.setYscale(0, scale);*/
        BouncingParticles bp = new BouncingParticles(10);
        bp.start();
    }
}
