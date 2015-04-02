/**
 * Author: Mark
 * Date  : 2015/3/29
 * Time  : 16:48
 */
public class SAP {

    private Digraph g;


    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) {
            throw new NullPointerException();
        }
        int gV = G.V();
        g = new Digraph(gV);
        for (int v = 0; v < gV; v++) {
            for (int w : G.adj(v)) {
                g.addEdge(v, w);
            }
        }
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        BreadthFirstDirectedPaths bfdpv = new BreadthFirstDirectedPaths(g, v);
        BreadthFirstDirectedPaths bfdpw = new BreadthFirstDirectedPaths(g, w);
        int anc = -1;
        int minLen = Integer.MAX_VALUE;
        for (int x = 0; x < g.V(); x++) {
            if (bfdpv.hasPathTo(x) && bfdpw.hasPathTo(x)) {
                int len = bfdpv.distTo(x) + bfdpw.distTo(x);
                if (len < minLen) {
                    minLen = len;
                    anc = x;
                }
            }
        }
        if (anc == -1) minLen = -1;
        return minLen;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        BreadthFirstDirectedPaths bfdpv = new BreadthFirstDirectedPaths(g, v);
        BreadthFirstDirectedPaths bfdpw = new BreadthFirstDirectedPaths(g, w);
        int anc = -1;
        int minLen = Integer.MAX_VALUE;
        for (int x = 0; x < g.V(); x++) {
            if (bfdpv.hasPathTo(x) && bfdpw.hasPathTo(x)) {
                int len = bfdpv.distTo(x) + bfdpw.distTo(x);
                if (len < minLen) {
                    minLen = len;
                    anc = x;
                }
            }
        }
        return anc;
    }


    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) {
            throw new NullPointerException();
        }
        BreadthFirstDirectedPaths bfdpv = new BreadthFirstDirectedPaths(g, v);
        BreadthFirstDirectedPaths bfdpw = new BreadthFirstDirectedPaths(g, w);
        int anc = -1;
        int minLen = Integer.MAX_VALUE;
        for (int x = 0; x < g.V(); x++) {
            if (bfdpv.hasPathTo(x) && bfdpw.hasPathTo(x)) {
                int len = bfdpv.distTo(x) + bfdpw.distTo(x);
                if (len < minLen) {
                    minLen = len;
                    anc = x;
                }
            }
        }
        if (anc == -1) minLen = -1;
        return minLen;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) {
            throw new NullPointerException();
        }
        BreadthFirstDirectedPaths bfdpv = new BreadthFirstDirectedPaths(g, v);
        BreadthFirstDirectedPaths bfdpw = new BreadthFirstDirectedPaths(g, w);
        int anc = -1;
        int minLen = Integer.MAX_VALUE;
        for (int x = 0; x < g.V(); x++) {
            if (bfdpv.hasPathTo(x) && bfdpw.hasPathTo(x)) {
                int len = bfdpv.distTo(x) + bfdpw.distTo(x);
                if (len < minLen) {
                    minLen = len;
                    anc = x;
                }
            }
        }
        return anc;
    }


    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }

}
