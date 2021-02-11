package com.github.ztmark.undirectgraph;

public class Cycle {

    private final boolean[] marked;
    private boolean hasCycle;

    public Cycle(Graph graph) {
        marked = new boolean[graph.V()];
        for (int i = 0; i < graph.V(); i++) {
            if (!marked[i]) {
                dfs(graph, i, i);
            }
        }
    }

    private void dfs(Graph graph, int s, int u) {
        marked[s] = true;
        for (Integer w : graph.adj(s)) {
            if (!marked[w]) {
                dfs(graph, w, s);
            } else if (w != u) { // w == u 说明这条边是连接的父节点，如果不是且已经marked说明有环
                hasCycle = true;
            }
        }
    }

    public boolean hasCycle() {
        return hasCycle;
    }

}
