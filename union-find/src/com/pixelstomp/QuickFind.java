package com.pixelstomp;

import java.util.Scanner;
import com.pixelstomp.Timer;

public class QuickFind {

    int[] id;
    String INSTRUCTIONS = "Press J to join or F to find. Or press Q to exit.";
    Timer timer = new Timer();

    public QuickFind(int count) {
        id = new int[count];

        timer.start();
        for (int i = 0; i < count; i++) {
            id[i] = i;
        }
        timer.stop();

        System.out.print("Initializing QuickFind nodes took ");
        System.out.print(timer.getElapsed());
        System.out.print(" ms to complete.");
        System.out.println();
    }

    public void join(int p, int q) {
        int compID = id[p];
        if (q != compID) {
            id[q] = compID;
        }
    }

    public boolean find(int p, int q) {
        return id[p] == id[q];
    }

    public void doQuickFind(Scanner scanner) {
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

                timer.start();
                this.join(p, q);
                timer.stop();

                System.out.print("Nodes successfully joined. Operation took ");
                System.out.print(timer.getElapsed());
                System.out.print(" ms to complete.");
                System.out.println();
            }

            if (next.equals("F")) {
                System.out.println("Enter two nodes to see if they are connected, separated by carriage return.");
                int p = scanner.nextInt();
                int q = scanner.nextInt();

                timer.start();
                boolean connected = this.find(p, q);
                timer.stop();

                System.out.print("Connection found: ");
                System.out.print(connected);
                System.out.println();

                System.out.print("Operation took ");
                System.out.print(timer.getElapsed());
                System.out.print(" ms to complete.");
                System.out.println();
            }

        }
    }
}
