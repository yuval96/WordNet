import edu.princeton.cs.algs4.*;
import java.util.HashMap;

public class WordNet {

    private HashMap<String, Bag<Integer>> synsetsMap;
    private HashMap<Integer, String> allSynsets;
    private Digraph wordnet;
    private SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null)
            throw new IllegalArgumentException("Argument is null");

        allSynsets = new HashMap<Integer, String>();
        synsetsMap = new HashMap<String, Bag<Integer>>();
        int count = readSynsets(synsets);
        wordnet = new Digraph(count);
        readHypernyms(hypernyms);

        // check wordnet graph does not correspond to a rooted DAG.
        DirectedCycle dc = new DirectedCycle(wordnet);
        if (dc.hasCycle())
            throw new IllegalArgumentException("Input has cycle, exiting.");

        // check that the graph is single rooted
        int root = 0;
        for (int i = 0; i<count; i++) {
            if (wordnet.outdegree(i) == 0)
                root++;
            if (root > 1) {
                throw new IllegalArgumentException("Input is not single rooted, exiting");
            }
        }

        //create new sap instance
        sap = new SAP(wordnet);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return synsetsMap.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) {
            throw new IllegalArgumentException("Null argument");
        }
        return synsetsMap.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        Iterable<Integer> v = synsetsMap.get(nounA);
        Iterable<Integer> w = synsetsMap.get(nounB);

        return sap.length(v, w);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        Iterable<Integer> v = synsetsMap.get(nounA);
        Iterable<Integer> w = synsetsMap.get(nounB);

        int ancestor = sap.ancestor(v, w);
        return allSynsets.get(ancestor);
    }

    // read synsets from file, return the number of synsets.
    // save synsets in hashmap
    private int readSynsets(String synsets)
    {
        In in = new In(synsets);
        int count = 0;
        String[] line;
        int id;
        String[] nouns;

        while (in.hasNextLine()) {
            count++;
            line = in.readLine().split(",");
            // find the id and synset nouns
            id = Integer.parseInt(line[0]);
            allSynsets.put(id, line[1]);
            nouns = line[1].split(" ");
            // for each noun in the synset, check if it exists in the map.
            // if not, add it by creating a new back. if yes, update the existing bag.
            for (String noun : nouns) {
                if (synsetsMap.get(noun) != null) {
                    synsetsMap.get(noun).add(id);
                }
                else {
                    Bag<Integer> bag = new Bag<Integer>();
                    bag.add(id);
                    synsetsMap.put(noun, bag);
                }
            }
        }
        return count;
    }

    // read hyernyms from file, for each line add the sysnset and it's hypernyms to the graph as vertices.
    private void readHypernyms(String hypernyms) {

        In in = new In(hypernyms);
        String[] line;
        int v, w;

        while (in.hasNextLine()) {
            line = in.readLine().split(",");
            v = Integer.parseInt(line[0]);
            for (int i = 1 ; i < line.length; i++)
            {
                w = Integer.parseInt(line[i]);
                wordnet.addEdge(v, w);
            }
        }
    }

    // do unit testing of this class
//    public static void main(String[] args) {
//
//        WordNet wn = new WordNet(args[0], args[1]);
//
//        System.out.println("Distance between communications and spunk = " + wn.distance("actin", "chondrin"));
//        System.out.println("SAP for communications and spunk = " + wn.sap("actin", "chondrin"));
//    }
}
