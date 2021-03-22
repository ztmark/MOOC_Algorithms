package com.github.ztmark.sp;

public class DijkstraAllPairsSP {
    private final DijkstraSP[] all;

    public DijkstraAllPairsSP(EdgeWeightedDigraph digraph) {
        all = new DijkstraSP[digraph.V()];
        for (int i = 0; i < digraph.V(); i++) {
            all[i] = new DijkstraSP(digraph, i);
        }
    }

    public Iterable<DirectedEdge> path(int s, int v) {
        return all[s].pathTo(v);
    }

    public double dist(int s, int t) {
        return all[s].distTo(t);
    }
}
