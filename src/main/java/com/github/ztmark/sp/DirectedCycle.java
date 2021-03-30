package com.github.ztmark.sp;

import com.github.ztmark.basic.Stack;

public class DirectedCycle {
    private final boolean[] marked;
    private final int[] edgeTo;
    private final boolean[] onStack;
    private Stack<Integer> cycle;

    public DirectedCycle(EdgeWeightedDigraph digraph) {
        marked = new boolean[digraph.V()];
        edgeTo = new int[digraph.V()];
        onStack = new boolean[digraph.V()];
        for (int i = 0; i < digraph.V(); i++) {
            edgeTo[i] = i;
        }
        for (int i = 0; i < digraph.V(); i++) {
            if (!marked[i]) {
                dfs(digraph, i);
            }
        }
    }

    private void dfs(EdgeWeightedDigraph digraph, int s) {

        marked[s] = true;
        onStack[s] = true;
        for (DirectedEdge edge : digraph.adj(s)) {
            if (hasCycle()) {
                return;
            }
            int to = edge.to();
            if (!marked[to]) {
                edgeTo[to] = s;
                dfs(digraph, to);
            } else {
                if (onStack[to]) {
                    cycle = new Stack<>();
                    for (int i = s; i != to; i = edgeTo[i]) {
                        cycle.push(i);
                    }
                    cycle.push(to);
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
        return cycle;
    }
}
