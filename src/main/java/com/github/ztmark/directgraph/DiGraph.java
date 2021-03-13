package com.github.ztmark.directgraph;

import com.github.ztmark.basic.Bag;

public class DiGraph {
    private int V;
    private int E;
    private Bag<Integer>[] adj;

    public DiGraph(int v) {
        this.V = v;
        this.E = 0;
        adj = new Bag[v];
        for (int i = 0; i < v; i++) {
            adj[i] = new Bag<>();
            this.E++;
        }
    }

    public void addEdges(int v, int w) {
        adj[v].add(w);
        E++;
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public Iterable<Integer> adj(int v) {
        return adj[v];
    }

    public DiGraph reverse() {
        DiGraph diGraph = new DiGraph(V);
        for (int v = 0; v < V; v++) {
            for (Integer w : adj[v]) {
                diGraph.addEdges(w, v);
            }
        }
        return diGraph;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder(V + " vertices, " + E + " edges\n");
        for (int i = 0; i < V; i++) {
            s.append(V).append(": ");
            for (Integer w : adj(i)) {
                s.append(w).append(" ");
            }
            s.append("\n");
        }
        return s.toString();
    }


}
