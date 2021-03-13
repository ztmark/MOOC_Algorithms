package com.github.ztmark.directgraph;

import java.util.Stack;

public class DirectedCycle {

    private boolean[] marked;
    private boolean[] onStack;
    private int[] edgeTo;
    private Stack<Integer> cycle;

    public DirectedCycle(DiGraph graph) {
        marked = new boolean[graph.V()];
        onStack = new boolean[graph.V()];
        edgeTo = new int[graph.V()];
        for (int i = 0; i < graph.V(); i++) {
            edgeTo[i] = i;
        }
        for (int i = 0; i < graph.V(); i++) {
            if (!marked[i]) {
                dfs(graph, i);
            }
        }
    }

    private void dfs(DiGraph graph, int s) {
        marked[s] = true;
        onStack[s] = true;
        for (Integer w : graph.adj(s)) {
            if (hasCycle()) return;
            if (!marked[w]) {
                edgeTo[w] = s;
                dfs(graph, w);
            } else {
                if (onStack[w]) { // has cycle
                    cycle = new Stack<>();
                    for (int v = s; v != w; v=edgeTo[v]) {
                        cycle.push(v);
                    }
                    cycle.push(w);
                    cycle.push(s);
                    return;
                }
            }
        }
        onStack[s] = false;
    }

    public boolean hasCycle() {
        return cycle != null;
    }

    public Iterable<Integer> cycle() {
        if (hasCycle()) {
            return cycle;
        }
        return null;
    }
}
