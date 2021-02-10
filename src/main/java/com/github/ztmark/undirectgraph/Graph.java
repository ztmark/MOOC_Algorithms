package com.github.ztmark.undirectgraph;

import com.github.ztmark.basic.Bag;

public class Graph {
    private final int V;
    private int E;

    private Bag<Integer>[] adj;

    public Graph(int v) {
        this.V = v;
        adj = new Bag[v];
        for (int i = 0; i < v; i++) {
            adj[i] = new Bag<>();
        }
    }

    public int V() {
        return V;
    }

    private int E() {
        return E;
    }

    private void addEdge(int v, int w) {
        adj[v].add(w);
        adj[w].add(v);
        E++;
    }

    public Iterable<Integer> adj(int v) {
        return adj[v];
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

    public int degree(int v) {
        int degree = 0;
        for (Integer integer : adj(v)) {
            degree++;
        }
        return degree;
    }

    public int maxDegree() {
        int max = 0;
        for (int i = 0; i < V; i++) {
            max = Math.max(degree(i), max);
        }
        return max;
    }

    public int avgDegree() {
       return 2 * E / V;
    }

    public int numOfSelfLoop() {
        int count = 0;
        for (int i = 0; i < V; i++) {
            for (Integer w : adj(V)) {
                if (w == V) {
                    count++;
                }
            }
        }
        return count; // count/2 算法书中是要除以2，说 each edge counted twice  ???
    }
}
