/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

public class AutoBox {

    public static void main(String[] args) {
        int n = 1;
        double c = -0.0;
        for (int i = -1; i < n; i++) {
            double a = c / i;
            double b = c / i;
            Double x = c / i;
            Double y = c / i;
            if (a == b && !(x.equals(y))) StdOut.printf("1 a %s  b %s  x %s  y %s\n", a, b, x, y);
            if (!(a == b) && x.equals(y)) StdOut.printf("2 a %s  b %s  x %s  y %s\n", a, b, x, y);
        }
    }
}
