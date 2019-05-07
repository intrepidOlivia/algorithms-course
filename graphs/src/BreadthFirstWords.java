import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;

import java.util.Iterator;

/**
 * This class performs breadth-first-search in a digraph to ultimately find the shortest ancestral path between two vertices.
 */
public class BreadthFirstWords {

    private final Digraph graph;

    private boolean[] markedS;  // marked[v] = is there an s->v path?
    private boolean[] markedV;


    private int[] edgeToS;      // edgeTo[v] = last edge on shortest s->v path
    private int[] edgeToV;

    private int[] distToS;      // distTo[v] = length of shortest s->v path
    private int[] distToV;

    private boolean sapFound = false;
    private int commonAncestor = -1;

    private int sap = Integer.MAX_VALUE;

    private Queue<Integer> sQueue;
    private Queue<Integer> vQueue;


    /**
     * Conduct breadth first search to find common ancestor between s and v
     * @param g
     * @param s
     * @param v
     */
    public BreadthFirstWords(Digraph g, int s, int v) {
        int count = g.V();

        this.graph = g;

        markedS = new boolean[count];
        markedV = new boolean[count];

        edgeToS = new int[count];
        edgeToV = new int[count];

        distToS = new int[count];
        distToV = new int[count];

        commonAncestorBFS(g, s, v);
    }

    public BreadthFirstWords(Digraph g, Iterable<Integer> sourcesS, Iterable<Integer> sourcesV) {
        int count = g.V();
        this.graph = g;

        markedS = new boolean[count];
        markedV = new boolean[count];

        edgeToS = new int[count];
        edgeToV = new int[count];

        distToS = new int[count];
        distToV = new int[count];

        commonAncestorBFS(g, sourcesS, sourcesV);
    }

    private void commonAncestorBFS(Digraph G, int s, int v) {
        sQueue = new Queue<>();
        vQueue = new Queue<>();

        markedS[s] = true;
        markedV[v] = true;

        distToS[s] = 0;
        distToV[v] = 0;

        sQueue.enqueue(s);
        vQueue.enqueue(v);

        bfSearch();
    }

    private void bfSearch() {
//        int shortestLength = Integer.MAX_VALUE;
        int currentCommon = -1;

        while ((!sQueue.isEmpty() || !vQueue.isEmpty())) {
            int sNext;
            boolean found = false;

            // Check next S path for common ancestor
            if (!sQueue.isEmpty()) {
                sNext = sQueue.dequeue();
                if (markedV[sNext]) {
                    found = true;
                    currentCommon = sNext;
                }
                    // standard bfs for S
                    for (int sAdj : graph.adj(sNext)) {
                        if (!markedS[sAdj]) {
                            edgeToS[sAdj] = sNext;
                            markedS[sAdj] = true;
                            distToS[sAdj] = distToS[sNext] + 1;
                            sQueue.enqueue(sAdj);
                        }

                }

                if (found) {
                    int newLength = doShortestPath(currentCommon);
                    if (newLength > sap) {
                        sapFound = true;
                    } else {
                        commonAncestor = currentCommon;
                        sap = newLength;
                    }
                }
            }

            int vNext;

            // Check next V path for common ancestor
            if (!vQueue.isEmpty()) {
                vNext = vQueue.dequeue();
                if (markedS[vNext]) {
                    found = true;
                    currentCommon = vNext;
                }
                    // standard bfs for V
                    for (int vAdj : graph.adj(vNext)) {
                        if (!markedV[vAdj]) {
                            edgeToV[vAdj] = vNext;
                            markedV[vAdj] = true;
                            distToV[vAdj] = distToV[vNext] + 1;
                            vQueue.enqueue(vAdj);
                        }

                }

                if (found) {
                    int newLength = doShortestPath(currentCommon);
                    if (newLength > sap) {
                        sapFound = true;
                    } else {
                        commonAncestor = currentCommon;
                        sap = newLength;
                    }
                }
            }
        }
    }

    private void commonAncestorBFS(Digraph G, Iterable<Integer> sourcesS, Iterable<Integer> sourcesV) {
        sQueue = new Queue<>();
        vQueue = new Queue<>();

        Iterator sGroup = sourcesS.iterator();
        Iterator vGroup = sourcesV.iterator();

        while (sGroup.hasNext()) {
            if (sGroup.next() == null) {
                throw new IllegalArgumentException();
            }
        }

        while (vGroup.hasNext()) {
            if (vGroup.next() == null) {
                throw new IllegalArgumentException();
            }
        }

        for (int s : sourcesS) {
            if (s < 0 || s >= G.V()) {
                throw new IllegalArgumentException();
            }

            markedS[s] = true;
            distToS[s] = 0;
            sQueue.enqueue(s);
        }

        for (int v : sourcesV) {
            if (v < 0 || v >= G.V()) {
                throw new IllegalArgumentException();
            }

            markedV[v] = true;
            distToV[v] = 0;
            vQueue.enqueue(v);
        }

        bfSearch();
    }

    private int doShortestPath(int common) {
        // count path backward for each vertex
        Stack<Integer> pathToS = new Stack<>();
        Stack<Integer> pathToV = new Stack<>();
        int[] shortest;

        // make path to s
        int i;
        for (i = common; distToS[i] > 0; i = edgeToS[i]) {
            pathToS.push(i);
        }
        pathToS.push(i);

        // make path to v
        int j;
        for (j = common; distToV[j] > 0; j = edgeToV[j]) {
            pathToV.push(j);
        }
        pathToV.push(j);

        // concatenate the two
        shortest = new int[pathToS.size() + pathToV.size() - 2];
        return shortest.length;
    }

    public int getCommonAncestor() {
        return commonAncestor;
    }

    public int shortestAncestralPath() {
        return sap;
    }
}
