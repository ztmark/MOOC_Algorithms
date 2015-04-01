import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Author: Mark
 * Date  : 2015/3/29
 * Time  : 10:54
 */
public class WordNet {

    private Digraph digraph;
    private ST<Integer,List<String>> idToWord;
    private ST<String,List<Integer>> wordToId;

    private SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) {
            throw new NullPointerException();
        }
        idToWord = new ST<>();
        wordToId = new ST<>();


        In in = new In(synsets);
        String line = null;
        String[] seg = null;
        String[] words = null;
        Integer id;
        int V = 0;
        while (in.hasNextLine()) {
            V++;
            line = in.readLine();
            seg = line.split(",");
            words = seg[1].split(" ");
            id = Integer.parseInt(seg[0]);
            idToWord.put(id, Arrays.asList(words));
            List<Integer> ids;
            for (String word : words) {
                ids = wordToId.get(word);
                if (ids == null) {
                    ids = new ArrayList<>();
                    ids.add(id);
                    wordToId.put(word, ids);
                } else {
                    ids.add(id);
                }
            }
        }

        digraph = new Digraph(V);

        // hypernyms
        in = new In(hypernyms);
        while (in.hasNextLine()) {
            line = in.readLine();
            seg = line.split(",");
            id = Integer.parseInt(seg[0]);
            for (int i = 1; i < seg.length; i++) {
                digraph.addEdge(id, Integer.parseInt(seg[i]));
            }
        }
        if (!isRootedDAG()) {
            throw new IllegalArgumentException();
        }
        sap = new SAP(digraph);
    }

    private boolean isRootedDAG() {
        Iterable<Integer> ids = idToWord.keys();
        int root = 0;
        for (Integer id : ids) {
            if (digraph.outdegree(id) == 0) {
                root++;
            }
        }
        return root == 1;
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return wordToId.keys();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) {
            throw new NullPointerException();
        }
        return wordToId.get(word) != null;
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null) {
            throw new NullPointerException();
        }
        List<Integer> a = wordToId.get(nounA);
        List<Integer> b = wordToId.get(nounB);
        if (a == null || b == null) {
            throw new IllegalArgumentException();
        }
        return sap.length(a, b);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null) {
            throw new NullPointerException();
        }
        List<Integer> a = wordToId.get(nounA);
        List<Integer> b = wordToId.get(nounB);
        if (a == null || b == null) {
            throw new IllegalArgumentException();
        }
        Integer ancestor = sap.ancestor(a, b);
        return idToWord.get(ancestor).get(0);
    }

    // do unit testing of this class
    public static void main(String[] args) {

    }

}
