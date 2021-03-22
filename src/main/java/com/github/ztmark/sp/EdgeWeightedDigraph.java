package com.github.ztmark.sp;

import com.github.ztmark.basic.Bag;

public class EdgeWeightedDigraph {

    private final int V;
    private int E;
    private final Bag<DirectedEdge>[] adj;
    public EdgeWeightedDigraph(int V) {
        this.V = V;
        adj = new Bag[V];
        for (int i = 0; i < V; i++) {
            adj[i] = new Bag<>();
        }
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public void addEdge(DirectedEdge edge) {
        adj[edge.from()].add(edge);
        E++;
    }

    public Iterable<DirectedEdge> adj(int v) {
        return adj[v];
    }

    public Iterable<DirectedEdge> edges() {
        Bag<DirectedEdge> edges = new Bag<>();
        for (Bag<DirectedEdge> bag : adj) {
            for (DirectedEdge edge : bag) {
                edges.add(edge);
            }
        }
        return edges;
    }
}
