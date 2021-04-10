package com.github.ztmark.sp;


import com.github.ztmark.basic.Stack;

import java.util.Arrays;

public class AcyclicLP {

    private final DirectedEdge[] edgeTo;
    private final double[] distTo;

    public AcyclicLP(EdgeWeightedDigraph digraph, int s) {
        EdgeWeightedDigraph graph = new EdgeWeightedDigraph(digraph.V());
        for (DirectedEdge edge : digraph.edges()) {
            graph.addEdge(new DirectedEdge(edge.from(), edge.to(), -edge.weight()));
        }
        distTo = new double[graph.V()];
        Arrays.fill(distTo, Double.NEGATIVE_INFINITY); // 注意是 NEGATIVE_INFINITY
        edgeTo = new DirectedEdge[graph.V()];
        distTo[s] = 0.0;
        Topological top = new Topological(graph);
        if (top.hasOrder()) {
            for (Integer v : top.order()) {
                relax(graph, v);
            }
        }

    }

    private void relax(EdgeWeightedDigraph graph, Integer v) {
        for (DirectedEdge edge : graph.adj(v)) {
            int to = edge.to();
            if (distTo[to] < distTo[edge.from()] + edge.weight()) { // 注意是 <
                distTo[to] = distTo[edge.from()] + edge.weight();
                edgeTo[to] = edge;
            }
        }
    }

    public boolean hasPathTo(int w) {
        return distTo[w] > Double.NEGATIVE_INFINITY;
    }

    public Iterable<DirectedEdge> pathTo(int w) {
        if (!hasPathTo(w)) return null;
        Stack<DirectedEdge> path = new Stack<>();
        for (DirectedEdge e = edgeTo[w]; e != null; e = edgeTo[e.from()]) {
            path.push(e);
        }
        return path;
    }

    public double distTo(int w) {
        return distTo[w];
    }
}
