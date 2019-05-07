import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

public class SAP {

    private final Digraph graph;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        graph = new Digraph(G);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        if (v < 0 || w < 0 || v >= graph.V() || w >= graph.V()) {
            throw new IllegalArgumentException("Values less than 0");
        }

        BreadthFirstWords search = new BreadthFirstWords(graph, v, w);
        if (search.getCommonAncestor() >= 0) {
            return search.shortestAncestralPath();
        } else {
            return -1;
        }
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        if (v < 0 || w < 0 || v >= graph.V() || w >= graph.V()) {
            throw new IllegalArgumentException("Values less than 0");
        }

        BreadthFirstWords search = new BreadthFirstWords(graph, v, w);
        return search.getCommonAncestor();
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) {
            throw new IllegalArgumentException("Null to length");
        }

        BreadthFirstWords search = new BreadthFirstWords(graph, v, w);
        if (search.getCommonAncestor() >= 0) {
            return search.shortestAncestralPath();
        } else {
            return -1;
        }
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) {
            throw new IllegalArgumentException("Null to ancestory");
        }

        BreadthFirstWords search = new BreadthFirstWords(graph, v, w);
        return search.getCommonAncestor();
    }

    public static void main(String[] args) {
    }
}
