package com.github.ztmark.mst;

import com.github.ztmark.basic.Bag;

public class EdgeWeightedGraph {

    private int V;
    private int E;
    private Bag<Edge>[] adj;


    public EdgeWeightedGraph(int v) {
        this.V = v;
        adj = new Bag[v];
        for (int i = 0; i < v; i++) {
            adj[i] = new Bag<>();
        }
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public void addEdge(Edge e) {
        int w = e.either();
        int v = e.other(w);
        adj[w].add(e);
        adj[v].add(e);
        E++;
    }

    public Iterable<Edge> adj(int v) {
        return adj[v];
    }

    public Iterable<Edge> edges() {
        Bag<Edge> edges = new Bag<>();
        for (int i = 0; i < V; i++) {
            for (Edge edge : adj[i]) {
                if (edge.other(i) > i) { // 无向边，只加一次
                    edges.add(edge);
                }
            }
        }
        return edges;
    }

    @Override
    public String toString() {
        return "EdgeWeightedGraph{}";
    }
}
