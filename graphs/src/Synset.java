import java.util.HashSet;
import java.util.Set;

public class Synset {
    public final int index;
    private Set<Integer> hypernyms = new HashSet<>();
    private Set<Integer> hyponyms = new HashSet<>();
    private Set<String> words = new HashSet<>();
    private String noun;
    private String definition;

    public Synset(int index, String noun, String definition) {
        this.index = index;
        this.definition = definition;
        this.noun = noun;
    }

    public void addHypernym(int h) {
        hypernyms.add(h);
    }

    public void addHyponym(int h) {
        hyponyms.add(h);
    }

    public Iterable<Integer> getHypernyms() {
        return hypernyms;
    }

    public Iterable<Integer> getHyponyms() {
        return hyponyms;
    }

    public String getNoun() {
        return noun;
    }
}
