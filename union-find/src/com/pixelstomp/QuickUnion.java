package com.pixelstomp;

import java.util.Scanner;

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

    public int getSize(int k) {
        return size[k];
    }

    static final String INSTRUCTIONS = "Press J to join, B to bulk-join, or F to find. Or press Q to exit.";

    public void joinBulk(int[][] joined) {
        for (int i = 0; i < joined.length; i++) {
            join(joined[i][0], joined[i][1]);
        }
    }

    public void doQuickUnion(Scanner scanner) {
        boolean on = true;

        while (on == true) {
            System.out.println(INSTRUCTIONS);
            String next = scanner.next();

            if (next.equals("Q")) {
                on = false;
            }

            if (next.equals("J")) {
                System.out.println("Enter the two nodes you would like joined, separated by carriage returns.");
                int p = scanner.nextInt();
                int q = scanner.nextInt();

                this.join(p, q);

                System.out.println("Nodes successfully joined.");
            }

            if (next.equals("B")) {
                System.out.println("We'll need to create an array.");
                System.out.println("The code for this has not been written yet.");
            }

            if (next.equals("F")) {
                System.out.println("Enter two nodes to see if they are connected, separated by carriage return.");
                int p = scanner.nextInt();
                int q = scanner.nextInt();

                boolean connected = this.find(p, q);

                System.out.print("Connection found: ");
                System.out.print(connected);
                System.out.println();
            }
        }
    }
}
