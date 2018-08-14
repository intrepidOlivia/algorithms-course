package com.pixelstomp;

import java.util.HashSet;
import java.util.Scanner;

/**
 * For the "Successor with Delete" practice problem
 */
public class Successor {

    HashSet<Integer> nSet;
    int[] parent;

    public Successor(int n) {
        parent = new int[n];
        nSet = new HashSet<>(n);

        for (int i = 0; i < n; i++) {
            nSet.add(i);
            parent[i] = i;
        }
    }

    public int getSuccessorAndRemove(int x) {
        nSet.remove(x);
        parent[x] = x + 1;
        return root(x);
    }


    private int root(int j) {
        if (parent[j] == j) {
            return j;
        } else {
            return root(parent[j]);
        }
    }

    public void doSuccessor(Scanner scanner) {
        boolean on = true;
        final String REMOVE = "R";
        final String QUIT = "Q";
        final String INSTRUCTIONS = "Press R to invoke a 'Remove and Get Successor' function, or press Q to quit.";


        while (on == true) {
            System.out.println(INSTRUCTIONS);
            String action = scanner.next();

            if (action.equals(QUIT)) {
                on = false;
            }

            if (action.equals(REMOVE)) {
                System.out.println("Enter the node you would like removed. The successor of this number will be returned.");
                int x = scanner.nextInt();


                System.out.println("Successor of " + x + " is " + getSuccessorAndRemove(x));
            }
        }
    }
}
