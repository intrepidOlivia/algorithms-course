import edu.princeton.cs.algs4.StdIn;

/**
 * takes an integer k as a command-line argument; reads in a sequence of strings from standard input using StdIn.readString();
 * and prints exactly k of them, uniformly at random. Print each item from the sequence at most once.
 */
public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> q = new RandomizedQueue<>();

        while (!StdIn.isEmpty() && StdIn.hasNextLine()) {
            q.enqueue(StdIn.readString());
        }

        int size = q.size();
        for (int i = 0; i < size; i++) {
            System.out.println(q.dequeue());
        }
    }
}
