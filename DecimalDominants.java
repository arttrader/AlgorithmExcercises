/* *****************************************************************************
 *  Name:              J Hirota
 *  Coursera User ID:
 *  Last modified:     2022-1-29
 **************************************************************************** */

public class DecimalDominants {
    private final Integer[] a;
    private final int n;
    private final int[] c;

    public DecimalDominants(int[] a) {
        this.n = a.length;
        this.a = new Integer[n];
        for (int i = 0; i < n; i++) this.a[i] = a[i];
        c = new int[n];
        sort(this.a, 0, n-1);

    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable s = a[i];
        a[i] = a[j];
        a[j] = s;
    }

    private static void sort(Comparable[] a, int lo, int hi) {
        if (hi <= lo) return;
        int lt = lo;
        int gt = hi;
        Comparable v = a[lo];
        int i = lo;
        while (lt < gt) {
            int cmp = a[i].compareTo(v);
            if      (cmp < 0) exch(a, lt++, i++);
            else if (cmp > 0) exch(a, i, gt--);
            else i++;
        }
        sort(a, lo, lt-1);
        sort(a, gt+1, hi);
    }

    public int[] getFreqOccurrences() {
        int fc = n / 10;
        // assuming keys are 0..n-1, otherwise requires key value pair as data structure
        for (int i = 0; i < fc; i++) {
            c[a[i]]++;
        }
        return c;
    }

    public static void main(String[] args) {

    }
}
