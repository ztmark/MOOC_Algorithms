package com.github.ztmark.undirectgraph;

public class TwoColor {
    private final boolean[] marked;
    private final boolean[] color;
    private boolean isTwoColorable = true;

    public TwoColor(Graph graph) {
        marked = new boolean[graph.V()];
        color = new boolean[graph.V()];
        for (int i = 0; i < graph.V(); i++) {
            if (!marked[i]) {
                dfs(graph, i);
            }
        }
    }

    private void dfs(Graph graph, int s) {
        marked[s] = true;
        for (Integer w : graph.adj(s)) {
            if (!marked[w]) {
                color[w] = !color[s];
                dfs(graph, w);
            } else if (color[w] == color[s]) {
                isTwoColorable = false;
            }
        }
    }

    public boolean isBipartite() {
        return isTwoColorable;
    }
}
