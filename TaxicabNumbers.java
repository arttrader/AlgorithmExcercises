/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

public class TaxicabNumbers {

    public static void main(String[] args) {
        int n = 50;
        int breakPoint = n;
        // brute force way
        for (int a = 1; a < n; a++) {
            for (int b = a+1; b < n - 1; b++) {
                long tc = a*a*a + b*b*b;
                if (tc < 0) {
                    breakPoint = b;
                    break;
                }
                for (int c = 1; c < n; c++) {
                    for (int d = c+1; d < n; d++) {
                        if (a == c || b == d) continue;
                        long rtc = c*c*c + d*d*d;
                        if (rtc < 0) {
                            break;
                        }
                        if (tc == rtc)
                            StdOut.printf("%s^3  %s^3 = %s^3 + %s^3 = %s\n", a, b, c, d, tc);
                    }
                }
            }
            if (a > breakPoint) break;
        }
    }
}
