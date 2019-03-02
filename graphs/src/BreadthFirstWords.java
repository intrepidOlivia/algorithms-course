import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;

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

    private boolean ancestorFound = false;
    private int commonAncestor = -1;

    private int[] shortestPath;

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

//        this.s = s;
//        this.v = v;

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
        while (!ancestorFound && (!sQueue.isEmpty() || !vQueue.isEmpty())) {
            int sNext, vNext;

            // Check next S path for common ancestor
            if (!sQueue.isEmpty()) {
                sNext = sQueue.dequeue();
                if (markedV[sNext]) {
                    ancestorFound = true;
                    commonAncestor = sNext;
                } else {
                    // standard bfs for S
                    for (int sAdj : graph.adj(sNext)) {
                        if (!markedS[sAdj]) {
                            edgeToS[sAdj] = sNext;
                            markedS[sAdj] = true;
                            distToS[sAdj] = distToS[sNext] + 1;
                            sQueue.enqueue(sAdj);
                        }
                    }
                }
            }

            // Check next V path for common ancestor
            if (!vQueue.isEmpty()) {
                vNext = vQueue.dequeue();
                if (markedS[vNext]) {
                    ancestorFound = true;
                    commonAncestor = vNext;
                } else {
                    // standard bfs for V
                    for (int vAdj : graph.adj(vNext)) {
                        if (!markedV[vAdj]) {
                            edgeToV[vAdj] = vNext;
                            markedV[vAdj] = true;
                            distToV[vAdj] = distToV[vNext] + 1;
                            vQueue.enqueue(vAdj);
                        }
                    }
                }
            }

            if (ancestorFound) {
                doShortestPath();
            }
        }
    }

    private void commonAncestorBFS(Digraph G, Iterable<Integer> sourcesS, Iterable<Integer> sourcesV) {
        sQueue = new Queue<>();
        vQueue = new Queue<>();

        for (int s : sourcesS) {
            markedS[s] = true;
            distToS[s] = 0;
            sQueue.enqueue(s);
        }

        for (int v : sourcesV) {
            markedV[v] = true;
            distToV[v] = 0;
            vQueue.enqueue(v);
        }

        bfSearch();
    }

    private void doShortestPath() {
        // count path backward for each vertex
        Stack<Integer> pathToS = new Stack<>();
        Stack<Integer> pathToV = new Stack<>();

        // make path to s
        int i;
        for (i = commonAncestor; distToS[i] > 0; i = edgeToS[i]) {
            pathToS.push(i);
        }
        pathToS.push(i);

        // make path to v
        int j;
        for (j = commonAncestor; distToV[j] > 0; j = edgeToV[j]) {
            pathToV.push(j);
        }
        pathToV.push(j);

        // concatenate the two
        shortestPath = new int[pathToS.size() + pathToV.size() - 2];
    }

    public int getCommonAncestor() {
        return commonAncestor;
    }

    public int[] getShortestPath() {
        return shortestPath;
    }
}
