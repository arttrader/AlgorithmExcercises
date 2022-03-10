/* *****************************************************************************
 *  Name:              J Hirota
 *  Coursera User ID:
 *  Last modified:     2022-03-04
 **************************************************************************** */

public class LinearProbingHashSTTry<Key, Value> {
    private int m = 30001;
    private Value[] vals = (Value[]) new Object[m];
    private Key[]   keys = (Key[])   new Object[m];

    private int hash(Key key) {
        /* as before */
        return 0;
    }

    public void put(Key key, Value val) {
        int i;
        for (i = hash(key); keys[i] != null; i = (i+1) % m)
            if (keys[i].equals(key))
                break;
        keys[i] = key;
        vals[i] = val;
    }

    public Value get(Key key) {
        for (int i = hash(key); keys[i] != null; i = (i+1) % m)
            if (key.equals(keys[i]))
                return vals[i];
        return null;
    }


    public static void main(String[] args) {

    }
}
