/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        String selected = "";
        int i = 0;
        while (!StdIn.isEmpty()) {
            i++;
            String word = StdIn.readString();
//                System.out.println(word);
            if (i==1 || StdRandom.bernoulli(1.0 / i))
                selected = word;
        }
        System.out.println(selected);
    }
}
