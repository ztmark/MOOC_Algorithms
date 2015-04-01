/**
 * Author: Mark
 * Date  : 2015/3/29
 * Time  : 16:48
 */
public class SAP {

    private Digraph g;

    /*private BreadthFirstDirectedPaths bfdpv;
    private BreadthFirstDirectedPaths bfdpw;
    private int len;
    private int ancestor;

    private BreadthFirstDirectedPaths listv;
    private BreadthFirstDirectedPaths listw;
    private Iterable<Integer> lv;
    private Iterable<Integer> lw;
    private int llen;
    private int lancestor;*/


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
//        len = -1;
//        ancestor = -1;
//        llen = -1;
//        lancestor = -1;
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        /*if (check(v, w)){
            process();
        }*/
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
        /*if (check(v, w)){
            process();
        }*/
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
        //return ancestor;
    }

    /*private void process() {
        int minLen = Integer.MAX_VALUE;
        boolean hasAncestor = false;
        for (int x = 0; x < g.V(); x++) {
            if (bfdpv.hasPathTo(x) && bfdpw.hasPathTo(x)) {
                hasAncestor = true;
                int len = bfdpv.distTo(x) + bfdpw.distTo(x);
                if (len < minLen) {
                    minLen = len;
                    ancestor = x;
                }
            }
        }
        if (hasAncestor) {
            len = minLen;
        } else {
            len = -1;
            ancestor = -1;
        }
    }*/

    /*private boolean check(int v, int w) {
        boolean changed = false;
        if (v != V) {
            V = v;
            bfdpv = new BreadthFirstDirectedPaths(g, v);
            changed = true;
        }
        if (w != W) {
            W = w;
            bfdpw = new BreadthFirstDirectedPaths(g, w);
            changed = true;
        }
        return changed;
    }*/


    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) {
            throw new NullPointerException();
        }
        /*if (check(v, w)) {
            processMult();
        }*/
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
//        return llen;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) {
            throw new NullPointerException();
        }
        /*if (check(v, w)) {
            processMult();
        }*/
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
//        return lancestor;
    }

    /*private void processMult() {
        int minLen = Integer.MAX_VALUE;
        boolean hasAncestor = false;
        for (int x = 0; x < g.V(); x++) {
            if (listv.hasPathTo(x) && listw.hasPathTo(x)) {
                hasAncestor = true;
                int len = listv.distTo(x) + listw.distTo(x);
                if (len < minLen) {
                    minLen = len;
                    lancestor = x;
                }
            }
        }
        if (hasAncestor) {
            llen = minLen;
        } else {
            llen = -1;
            lancestor = -1;
        }
    }*/

    /*private boolean check(Iterable<Integer> v, Iterable<Integer> w) {
        Iterator<Integer> sit = lv.iterator();
        Iterator<Integer> nit = v.iterator();
        boolean vchanged = false;
        while (sit.hasNext() && nit.hasNext()) {
            if (!sit.next().equals(nit.next())) {
                vchanged = true;
                break;
            }
        }
        if (sit.hasNext() || nit.hasNext()) {
            vchanged = true;
        }
        if (vchanged) {
            listv = new BreadthFirstDirectedPaths(g, v);
        }
        sit = lw.iterator();
        nit = w.iterator();
        boolean wchanged = false;
        while (sit.hasNext() && nit.hasNext()) {
            if (!sit.next().equals(nit.next())) {
                wchanged = true;
                break;
            }
        }
        if (sit.hasNext() || nit.hasNext()) {
            wchanged = true;
        }
        if (wchanged) {
            listw = new BreadthFirstDirectedPaths(g, w);
        }
        return vchanged || wchanged;
    }*/

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
