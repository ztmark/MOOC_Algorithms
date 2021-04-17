package com.github.ztmark.sp;

import com.github.ztmark.basic.Stack;

public class EdgeWeightedDirectedCycle {

    private DirectedEdge[] edgeTo;
    private boolean[] visited;
    private boolean[] onStack;
    private Stack<DirectedEdge> cycle;

    public EdgeWeightedDirectedCycle(EdgeWeightedDigraph digraph) {
        edgeTo = new DirectedEdge[digraph.V()];
        visited = new boolean[digraph.V()];
        onStack = new boolean[digraph.V()];
        for (int i = 0; i < digraph.V(); i++) {
            if (!visited[i]) {
                dfs(digraph, i);
            }
        }
    }

    private void dfs(EdgeWeightedDigraph digraph, int v) {
        visited[v] = true;
        onStack[v] = true;
        for (DirectedEdge edge : digraph.adj(v)) {
            if (cycle != null) {
                return;
            }
            int to = edge.to();
            if (!visited[to]) {
                edgeTo[to] = edge;
                dfs(digraph, to);
            } else if (onStack[to]) {
                cycle = new Stack<>();
                DirectedEdge e = edge;
                while (e.from() != to) {
                    cycle.push(e);
                    e = edgeTo[e.from()];
                }
                cycle.push(e);
                return;
            }
        }
        onStack[v] = false;
    }

    public boolean hasCycle() {
        return cycle != null;
    }

    public Iterable<DirectedEdge> cycle() {
        return cycle;
    }
}
