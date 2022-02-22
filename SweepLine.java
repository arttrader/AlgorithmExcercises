/* *****************************************************************************
 *  Name:              J Hirota
 *  Coursera User ID:
 *  Last modified:     2022-2-15
 **************************************************************************** */

public class SweepLine {
    final int UPPER_LIMIT = 20;
    RBBST<Integer, Integer> bst;


    public class HSegment implements Comparable<HSegment> {
        int lx;
        int rx;
        int y;

        public int compareTo(HSegment h) {
            return this.lx - h.lx;
        }
    }

    public class YSegment implements Comparable<YSegment> {
        int ly;
        int hy;
        int x;

        public int compareTo(YSegment y) {
            return this.ly - y.ly;
        }
    }


    public SweepLine(RBBST<Integer, HSegment> hseg, int n) {
        bst = new RBBST<>();
        for (int x = 0; x < UPPER_LIMIT; x++) {

        }
    }



    public static void main(String[] args) {
        int n = 10;
        HSegment[] hseg = new HSegment[n];

    }
}
