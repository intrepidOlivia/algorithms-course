public class Outcast {
    private final WordNet wordnet;

    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        int[] lengthSums = new int[nouns.length];
        int maxLength = 0;
        String maxOutcast = "";
        for (int i = 0; i < nouns.length; i++) {
            lengthSums[i] = 0;
            for (int j = 0; j < nouns.length; j++) {
                if (i == j) {
                    continue;
                }
                lengthSums[i] += wordnet.distance(nouns[i], nouns[j]);
                if (lengthSums[i] > maxLength) {
                    maxLength = lengthSums[i];
                    maxOutcast = nouns[i];
                }
            }
        }
        return maxOutcast;
    }

    public static void main(String[] args) { }
}
