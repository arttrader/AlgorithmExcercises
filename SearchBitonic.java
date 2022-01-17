/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

public class SearchBitonic {
    private int[] arr;
    private int n;
    private int btp;

    public SearchBitonic(int[] inputArr) {
        this.arr = inputArr;
        this.n = arr.length;
        btp = findBitonicPoint();
        StdOut.println("n: " + n + "  BTP: " + btp);
    }

    private int findBitonicPoint() {
        int lo = 0;
        int hi = n;

        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (arr[mid] < arr[mid+1]) lo = mid+1;
            else if (arr[mid] > arr[mid+1]) hi = mid-1;
            if (lo >= hi) return lo;
        }
        return n;
    }

    private int binarySearch(int lo, int hi, int si) {
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (arr[mid] > si) hi = mid - 1;
            else if (arr[mid] < si) lo = mid + 1;
            else return mid;
        }
        return -1;
    }

    private int binarySearchBackward(int lo, int hi, int si) {
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            StdOut.println("lo: " + lo + " mid: " + mid + " hi: " + hi);
            if (arr[mid] < si) hi = mid - 1;
            else if (arr[mid] > si) lo = mid + 1;
            else return mid;
        }
        return -1;
    }

    public int searchNumber(int key) {
        int k = binarySearch(0, btp, key);
        if (k >= 0) return k;
        int j = binarySearchBackward(btp+1, n, key);
        if (j >= 0) return j;
        else return -1;
    }

    public static void main(String[] args) {
        SearchBitonic sb = new SearchBitonic(
                new int[] {-50, -40, -30, 20, 30, 45, 50, 60, 65, 70, 60, 40, 30, 25, 5, -10, -25 -60});
        StdOut.println(sb.searchNumber(5));
        StdOut.println(sb.searchNumber(25));
        StdOut.println(sb.searchNumber(-10));
        StdOut.println(sb.searchNumber(-60));
    }
}
