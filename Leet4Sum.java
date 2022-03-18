/* *****************************************************************************
 *  Name:              J Hirota
 *  Coursera User ID:
 *  Last modified:     2022-03-04
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Leet4Sum {
    private void combination(int[] nums, int n, int[] data, Set<List<Integer>> out, int start, int end, int index, int r, int target) {
        if (index == r) {
            if (data[0] + data[1] + data[2] + data[3] == target) {
                ArrayList<Integer> f = new ArrayList<>();
                f.add(data[0]);
                f.add(data[1]);
                f.add(data[2]);
                f.add(data[3]);
                Collections.sort(f);
                if (!out.contains(f)) out.add(f);
            }
            return;
        }

        int scount = 0;
        for (int i = start; i <= end && end-i+1 >= r-index; i++) {
            if (i > 0 && nums[i] == nums[i-1]) {
                scount++;
                if (scount > r) continue;
            } else scount = 1;
            data[index] = nums[i];
            combination(nums, n, data, out, i+1, end, index+1, r, target);
        }
    }

    public List<List<Integer>> fourSum(int[] nums, int target) {
        int n = nums.length;
        int r = 4;
        Arrays.sort(nums);
        Set<List<Integer>> out = new HashSet<>();
        int[] comb = new int[r];
        combination(nums, n, comb, out, 0, n-1, 0, r, target);
        List<List<Integer>> list = new ArrayList<>();
        out.iterator().forEachRemaining(list::add);
        return list;
    }

    public static void main(String[] args) {
/*        int[] input = {-494,-474,-425,-424,-391,-371,-365,-351,-345,-304,-292,-289,-283,-256,-236,-236,-236,-226,-225,-223,-217,-185,-174,-163,-157,-148,-145,-130,-103,-84,-71,-67,-55,-16,-13,-11,1,19,28,28,43,48,49,53,78,79,91,99,115,122,132,154,176,180,185,185,206,207,272,274,316,321,327,327,346,380,386,391,400,404,424,432,440,463,465,466,475,486,492};
        int target = -1211;*/
        int[] input = {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2};
        int target = 8;
        Leet4Sum fs = new Leet4Sum();
        List<List<Integer>> output = fs.fourSum(input, target);
        for (List<Integer> o: output) {
            StdOut.println(o);
        }
    }
}
