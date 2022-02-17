/* *****************************************************************************
 *  Name:              J Hirota
 *  Coursera User ID:
 *  Last modified:     2022-2-17
 **************************************************************************** */

public class IntervalST<Key extends Comparable<Key>, Value> {
    RedBlackBST<Key, Value> bst;


    public void put(Key lo, Key hi, Value val) {

    }

    public Value get(Key lo, Key hi) {
        return null;
    }

    public void delete(Key lo, Key hi) {

    }

    public Iterable<Value> intersects(Key lo, Key hi) {

        return null;
    }

    public class Interval {
        Integer lo;
        Integer hi;

        public Interval(int lo, int hi) {
            this.lo = lo;
            this.hi = hi;
        }

        boolean intersects(Integer lo, Integer hi) {
            if (this.hi.compareTo(lo) > 0 && this.lo.compareTo(lo) < 0) return true;
            if (this.lo.compareTo(hi) < 0 && this.hi.compareTo(hi) > 0) return true;
            return false;
        }
    }

    public void main(String[] args) {
        IntervalST<Integer, Interval> ist = new IntervalST<>();

    }
}
