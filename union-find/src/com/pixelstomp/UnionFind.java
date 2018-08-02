package com.pixelstomp;

public class UnionFind {

    private int[] network;

    public UnionFind(int n) {
        network = new int[n];
        for (int i = 0; i < n; i++) {
            network[i] = i;
        }
    }

    private boolean quickFind(int p, int q) {
        return network[p] == network[q];
    }

    private void quickFindJoin(int p, int q) {
        int compID = network[p];
        if (network[q] != compID) {
            for (int i = 0; i < network.length; i++) {
                if (network[i] == network[q]) {
                    network[i] = compID;
                }
            }
        }
    }


}
