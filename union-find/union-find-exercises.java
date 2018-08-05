/* Social network connectivity.
Given a social network containing nn members and a log file containing mm timestamps
at which times pairs of members formed friendships,
design an algorithm to determine the earliest time at which all members are connected
(i.e., every member is a friend of a friend of a friend ... of a friend).
Assume that the log file is sorted by timestamp and that friendship is an equivalence relation.
The running time of your algorithm should be m \log nmlogn or better and use extra space proportional to nn. */

// Here for reference. WIll probably need different structures.
public class QuickUnion {

    int[] id;
    int[] size;
    static final String INSTRUCTIONS = "Press J to join, B to bulk-join, or F to connected. Or press Q to exit.";

    public QuickUnion(int count) {
        id = new int[count];
        size = new int[count];
        for (int i = 0; i < count; i++) {
            id[i] = i;
            size[i] = 1;
        }
    }

    public void join(int p, int q) {
        // Performs root first
        int pRoot = root(p);
        int qRoot = root(q);
        if (pRoot == qRoot) {
            return;
        }

        // Check the size
        if (size[pRoot] < size[qRoot]) {
            // join p to q's root
            id[pRoot] = qRoot;
            size[qRoot] += size[pRoot];
            size[pRoot] = 0;
        } else {
            // join q to p's root
            id[qRoot] = pRoot;
            size[pRoot] += size[qRoot];
            size[qRoot] = 0;
        }

    }

    public void joinBulk(int[][] joined) {
        for (int i = 0; i < joined.length; i++) {
            join(joined[i][0], joined[i][1]);
        }
    }

    public boolean find(int p, int q) {
        // Finds the root of the nodes and checks to see if they are the same
        return root(p) == root(q);
    }

    private int root(int j) {
        if (id[j] == j) {
            return j;
        } else {
            return root(id[j]);
        }
    }
  }
