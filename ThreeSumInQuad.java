/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class ThreeSumInQuad {
    public int[] num;

    public ThreeSumInQuad(int[] inputArr) {
        num = inputArr;
    }

    public void sort() {
        Arrays.sort(num);
    }

    public int find3Sums() {
        int count = 0;
        for (int i = 0; i < num.length; i++) {
            for (int j = i+1; j < num.length; j++) {
                int sum = num[i] + num[j];
                int k = Arrays.binarySearch(num, -sum);
                if (k >= 0 && num[j] < num[k]) {
                    count++;
                    StdOut.println(num[i] + "  " + num[j] + "  " + num[k]);
                }
            }
        }
        return count;
    }


    public static void main(String[] args) {
        ThreeSumInQuad tsq = new ThreeSumInQuad(new int[] { -50, -20, -40, 20, 30, 45, 50, 60, 65, 70});
        StdOut.println(tsq.find3Sums());
    }
}
