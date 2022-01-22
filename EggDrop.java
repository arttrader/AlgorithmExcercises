/* *****************************************************************************
 *  Name:              J Hirota
 *  Coursera User ID:
 *  Last modified:     2022-1-15
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

public class EggDrop {
    private final int n;
    private final int t;
    private int tosses;
    private int eggs;

    EggDrop(int n, int t) {
        this.n = n;
        this.t = t;
        this.tosses = 0;
        this.eggs = 0;
    }

    private boolean broke(int f) {
        tosses++;
        if (f >= t) {
            eggs++;
            return true;
        }
        return false;
    }

    public void getCount() {
        StdOut.printf("  Eggs: %s, Tosses: %s", eggs, tosses);
    }

    public int findV0() {
        for (int i = 1; i <= n; i++)
            if (broke(i)) return i;

        return -1;
    }

    public int findV1() {
        int lo = 1;
        int hi = n;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
//            StdOut.println("lo: " + lo + " mid: " + mid + " hi: " + hi);
            if (broke(mid)) hi = mid;
            else lo = mid + 1;
            if (lo == hi) return lo;
        }
        return -1;
    }

    public int findV2() {
        // find an interval containing T of size <= 2T then binary search
        int lo = 1;
        int hi = n;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
//            StdOut.println("lo: " + lo + " mid: " + mid + " hi: " + hi);
            if (broke(mid)) hi = mid;
            else lo = mid + 1;
            if (lo == hi) return lo;
        }
        return -1;
    }

    public int findV3() {
        // find an interval of size sqrt(n), then sequential search,  can be improved to ~ sqrt(2n) tosses
        int lo = 1;
        int hi = n;
//        int mid = (int)(Math.sqrt(n));
        int mid = lo + (hi - lo) / 2;
        if (broke(mid)) hi = mid - 1;
        else lo = mid + 1;
        for (int i = lo; i <= hi; i++)
            if (broke(i)) return i;

        return -1;
    }

    public int findV4() {
        // 1+2+3+…+t ∼ 1/2t^2
        int i;
        for (i = 1; i <= Math.sqrt(n); i++)
            if (broke(i)) return i;
        int lo = i;
        int hi = n;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (broke(mid)) hi = mid - 1;
            else lo = mid + 1;
            if (lo == hi) return lo;
        }
        return -1;
    }

    public static void main(String[] args) {
        int n = 50;
        int t = 35;
        StdOut.printf("n: %s, T: %s\n", n, t);
        StdOut.printf("V0:  1 eggs, <=T tosses: %s", t);
        EggDrop ed0 = new EggDrop(n, t);
        ed0.findV0();
        ed0.getCount();
        StdOut.println();
        StdOut.printf("V1:  1 lg n eggs: %s, 1 lg n tosses: %s", Math.log(n)/Math.log(2), Math.log(n)/Math.log(2));
        EggDrop ed1 = new EggDrop(n, t);
        ed1.findV1();
        ed1.getCount();
        StdOut.println();
        StdOut.printf("V2:  1 lg T eggs: %s, 2 lg T tosses: %s", Math.log(t)/Math.log(2), 2*Math.log(t)/Math.log(2));
        EggDrop ed2 = new EggDrop(n, t);
        ed2.findV2();
        ed2.getCount();
        StdOut.println();
        StdOut.printf("V3:  2 eggs, 2*sqrt(n) tosses: %s", 2*Math.sqrt(n));
        EggDrop ed3 = new EggDrop(n, t);
        ed3.findV3();
        ed3.getCount();
        StdOut.println();
        StdOut.printf("V4:  2 eggs, <= c*sqrt(T) tosses: %s", 2*Math.sqrt(t));
        EggDrop ed4 = new EggDrop(n, t);
        ed4.findV4();
        ed4.getCount();
    }
}
