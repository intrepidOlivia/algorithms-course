import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import java.util.HashMap;

public class WordNet {

    private final Synset[] synsets;
    private final HashMap<String, Bag<Integer>> nouns = new HashMap<>();
    private final Digraph graph;

    // constructor takes the name of the two input files
    public WordNet(String synsetsFile, String hypernymsFile) {
        if (synsetsFile == null || hypernymsFile == null) {
            throw new IllegalArgumentException("Null input");
        }

        In synIn = new In(synsetsFile);
        In hyperIn = new In(hypernymsFile);

        String allSynsets = synIn.readAll();
        String allHypernyms = hyperIn.readAll();



        String[] synLines = allSynsets.split("\n");
        String[] hyperLines = allHypernyms.split("\n");

        synsets = new Synset[synLines.length];
        graph = new Digraph(synLines.length);

        for (int i = 0; i < synLines.length; i++) {
            String[] synset = synLines[i].split(",");
            String[] words = synset[1].split(" ");

            // Maps each word in the synset to include the current index
            for (String w : words) {
                Bag<Integer> indices = nouns.get(w);
                if (indices == null) {
                    Bag<Integer> newBag = new Bag<>();
                    newBag.add(i);
                    nouns.put(w, newBag);
                } else {
                    indices.add(i);
                    nouns.put(w, indices);
                }
            }

            Synset s = new Synset(Integer.parseInt(synset[0]), synset[1], synset[2]);

            // Get hypernyms and add to graph
            String[] hyperSet = hyperLines[i].split(",");
            for (int j = 1; j < hyperSet.length; j++) {

                int h = Integer.parseInt(hyperSet[j]);

                s.addHypernym(h);
                graph.addEdge(i, h);
            }

            synsets[i] = s;
        }

        // Check that the graph is a rooted synset tree
        boolean rooted = true;
        for (int r : graph.adj(38003)) {
            rooted = false;
        }
        if (!rooted) {
            throw new IllegalArgumentException("Bad hypernyms input");
        }
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nouns.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) {
            throw new IllegalArgumentException("Null argument");
        }

        if (nouns.get(word) == null) {
            return false;
        }
        return true;
    }

    // distance between nounA and nounB
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null) {
            throw new IllegalArgumentException("Null argument");
        }

        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException("The arguments provided are not sufficiently defined as WordNet nouns.");
        }

        SAP spath = new SAP(graph);
        return spath.length(nouns.get(nounA), nouns.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null) {
            throw new IllegalArgumentException();
        }

        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException("The arguments provided are not sufficiently defined as WordNet nouns.");
        }

        SAP spath = new SAP(graph);
        int ancestor = spath.ancestor(nouns.get(nounA), nouns.get(nounB));
        return synsets[ancestor].getNoun();
    }
}