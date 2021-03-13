package com.github.ztmark.directgraph;

public class DirectedDFS {

    private final boolean[] marked;

    public DirectedDFS(DiGraph graph, int s) {
        marked = new boolean[graph.V()];
        dfs(graph, s);
    }

    public DirectedDFS(DiGraph graph, Iterable<Integer> sources) {
        marked = new boolean[graph.V()];
        for (Integer source : sources) {
            if (!marked[source]) {
                dfs(graph, source);
            }
        }
    }

    private void dfs(DiGraph graph, int s) {
        marked[s] = true;
        Iterable<Integer> adj = graph.adj(s);
        for (Integer v : adj) {
            if (!marked[v]) {
                dfs(graph, v);
            }
        }
    }

    public boolean marked(int v) {
        if (v < 0 || v >= marked.length) {
            return false;
        }
        return marked[v];
    }
}
