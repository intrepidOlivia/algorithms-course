package com.pixelstomp;

import java.util.Scanner;

/**
 * This class will represent a Weighted quick union solution with O(lg n) complexity.
 */
public class QuickUnion {

    int[] id;
    int[] size;
    int[] largest;

    public QuickUnion(int count) {
        id = new int[count];
        size = new int[count];
        largest = new int[count];
        for (int i = 0; i < count; i++) {
            id[i] = i;
            size[i] = 1;
            largest[i] = i;
        }
    }

    public void join(int p, int q) {
        // Performs root first
        int pRoot = root(p);
        int qRoot = root(q);
        int newRoot;

        if (pRoot == qRoot) {
            return;
        }

        // Check the size
        if (size[pRoot] < size[qRoot]) {
            // join p to q's root
            id[pRoot] = qRoot;
            newRoot = qRoot;
            size[qRoot] += size[pRoot];
            size[pRoot] = 0;
        } else {
            // join q to p's root
            id[qRoot] = pRoot;
            newRoot = pRoot;
            size[pRoot] += size[qRoot];
            size[qRoot] = 0;
        }

        // enable the Find functionality
        if (largest[newRoot] < p)  {
            largest[newRoot] = p;
        }

        if (largest[newRoot] < q) {
            largest[newRoot] = q;
        }

    }

    public boolean connected(int p, int q) {
        // Finds the root of the nodes and checks to see if they are the same
        return root(p) == root(q);
    }

    public int find(int n) {
        return largest[root(n)];
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

    public void joinBulk(int[][] joined) {
        for (int i = 0; i < joined.length; i++) {
            join(joined[i][0], joined[i][1]);
        }
    }

    public void doQuickUnion(Scanner scanner) {
        final String FIND = "F";
        final String CONNECTED = "C";
        final String JOIN = "J";
        final String QUIT = "Q";
        final String INSTRUCTIONS = "Press " + JOIN + " to join, " + FIND + " to find the largest, or " + CONNECTED + " to find connected. Or press " + QUIT + " to exit.";

        boolean on = true;

        while (on == true) {
            System.out.println(INSTRUCTIONS);
            String next = scanner.next();

            if (next.equals(QUIT)) {
                on = false;
            }

            if (next.equals(JOIN)) {
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

            if (next.equals(CONNECTED)) {
                System.out.println("Enter two nodes to see if they are connected, separated by carriage return.");
                int p = scanner.nextInt();
                int q = scanner.nextInt();

                boolean connected = this.connected(p, q);

                System.out.print("Connection found: ");
                System.out.print(connected);
                System.out.println();
            }

            if (next.equals(FIND)) {
                System.out.println("Enter a node to find the largest value in its component.");
                int n = scanner.nextInt();

                System.out.print("Largest node in component: ");
                System.out.print(find(n));
                System.out.println();
            }
        }
    }
}
