package com.pixelstomp;

import java.util.Scanner;
import com.pixelstomp.UnionFind;

public class Main {

    public static void main(String[] args) {
	// write your code here

    Scanner scanner = new Scanner(System.in);
    QuickFind qf;

    System.out.println("Now evaluating QuickFind algorithm. Please enter the number of nodes you would like to instantiate.");
    qf = new QuickFind(scanner.nextInt());

    qf.doQuickFind(scanner);

    }

    private static void startQuickFind() {}

    private static void startQuickUnion() {}

}
