/* *****************************************************************************
 *  Name:              Junichi Hirota
 *  Last modified:     2022-05-07
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdOut;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class WebCrawler {
    private Queue<String> q = new Queue<>();
    private SET<String> marked = new SET<>();

    public WebCrawler(String url) {
        String root = url;
        q.enqueue(root);
        marked.add(root);
        String regexp = "https://(\\w+\\.)*(\\w+)";
        Pattern pattern = Pattern.compile(regexp);

        while (!q.isEmpty()) {
            String v = q.dequeue();
            StdOut.println(v);
            In in = new In(v);
            String input;
            input = in.readAll();
            Matcher matcher = pattern.matcher(input);
            while (matcher.find()) {
                String w = matcher.group();
                if (!marked.contains(w)) {
                    marked.add(w);
                    q.enqueue(w);
                }
            }
        }
    }


    public static void main(String[] args) {
        WebCrawler web = new WebCrawler("https://www.apple.com");
    }
}
