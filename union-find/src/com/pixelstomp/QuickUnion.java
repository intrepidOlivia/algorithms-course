package com.pixelstomp;

/**
 * This class will represent a Weighted quick union solution with O(lg n) complexity.
 */
public class QuickUnion {

    int[] id;
    int[] size;

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
        if (root(p) == root(q)) {
            return;
        }

        // Check the size
    }

    public void joinBulk(int[][] joined) {
        
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
