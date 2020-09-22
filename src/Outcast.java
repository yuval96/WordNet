import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashMap;

public class Outcast {

    private WordNet wn;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        this.wn = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        int minDist = 0;
        String outcast = nouns[0];
        int tempDist = 0;

        for (String noun : nouns) {
            tempDist = sumDistance(noun, nouns);
            if (tempDist > minDist) {
                minDist = tempDist;
                outcast = noun;
            }
        }
        return outcast;
    }

    private int sumDistance(String noun, String[] nouns) {
        int sum = 0;
        for (String n : nouns) {
            sum += wn.distance(noun, n);
        }
        return sum;
    }

    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
