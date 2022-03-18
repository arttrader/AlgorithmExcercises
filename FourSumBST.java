/* *****************************************************************************
 *  Name:              J Hirota
 *  Coursera User ID:
 *  Last modified:     2022-03-04
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FourSumBST {
/*     static final int[] POWERS_OF_10 = {1, 10, 100, 1000, 10000};

    static int powerOfTen(int pow) {
        return POWERS_OF_10[pow];
    }

   public static Integer hash(ArrayList<Integer> v) {
        int h = 0;
        for (int i = 3; i >= 0; i--)
            h += v.get(i) * powerOfTen(3 - i);
        return h;
    }*/

    public static String hash(ArrayList<Integer> v) {
        if (v == null) return "";
        return v.get(0).toString() + v.get(1).toString() + v.get(2).toString() + v.get(3).toString();
    }

    class BST {
        private Node root;

        class Node {
            public final String key;
            private final ArrayList<Integer> value;
            private Node left;
            private Node right;

            public Node(ArrayList<Integer> v) {
                key = hash(v);
                value = v;
                left = null;
                right = null;
             }

            public ArrayList<Integer> get() {
                return value;
            }

            public void add(ArrayList<Integer> v) {
                int h = hash(v).compareTo(key);
                if (h < 0)
                    if (left == null) left = new Node(v);
                    else left.add(v);
                else if (h > 0)
                    if (right == null) right = new Node(v);
                    else right.add(v);
            }
        }

        public BST() {
            root = null;
        }

        public void add(ArrayList<Integer> v) {
            if (root == null) {
                root = new Node(v);
                return;
            }
            root.add(v);
        }

        public List<List<Integer>> iterator() {
            ArrayList<List<Integer>> out = new ArrayList<>();
            iterator(root, out);
            return out;
        }

        private void iterator(Node n, ArrayList<List<Integer>> out) {
            if (n == null) return;
            if (n.left != null)
                iterator(n.left, out);
            out.add(n.value);
            if (n.right != null)
                iterator(n.right, out);
        }
    }

    public List<List<Integer>> fourSum(int[] nums, int target) {
        int n = nums.length;
        BST t = new BST();
        for (int i = 0; i < n; i++)
            for (int j = i+1; j < n; j++)
                for (int k = j+1; k < n; k++)
                    for (int l = k+1; l < n; l++)
                        if (nums[i] + nums[j] + nums[k] + nums[l] == target) {
                            ArrayList<Integer> f = new ArrayList<>();
                            f.add(nums[i]);
                            f.add(nums[j]);
                            f.add(nums[k]);
                            f.add(nums[l]);
                            Collections.sort(f);
                            t.add(f);
                            StdOut.println(f + "  " + hash(f));
                        }
        List<List<Integer>> list = t.iterator();
        return list;
    }

    public static void main(String[] args) {
        int[] input = {-494,-474,-425,-424,-391,-371,-365,-351,-345,-304,-292,-289,-283,-256,-236,-236,-236,-226,-225,-223,-217,-185,-174,-163,-157,-148,-145,-130,-103,-84,-71,-67,-55,-16,-13,-11,1,19,28,28,43,48,49,53,78,79,91,99,115,122,132,154,176,180,185,185,206,207,272,274,316,321,327,327,346,380,386,391,400,404,424,432,440,463,465,466,475,486,492};
        int target = -1211;
        FourSumBST fs = new FourSumBST();
        List<List<Integer>> output = fs.fourSum(input, target);
        for (List<Integer> l: output) {
            StdOut.println(l);
        }
    }
}
