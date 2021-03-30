package com.github.ztmark.sp;


import com.github.ztmark.basic.Stack;

import java.util.Arrays;

public class AcyclicSP {
    private final DirectedEdge[] edgeTo;
    private final Double[] distTo;

    public AcyclicSP(EdgeWeightedDigraph digraph, int s) {
        edgeTo = new DirectedEdge[digraph.V()];
        distTo = new Double[digraph.V()];
        Arrays.fill(distTo, Double.POSITIVE_INFINITY);
        distTo[s] = 0.0;
        Topological top = new Topological(digraph);
        for (Integer v : top.order()) {
            relax(digraph, v);
        }
    }

    private void relax(EdgeWeightedDigraph digraph, Integer v) {
        for (DirectedEdge edge : digraph.adj(v)) {
            int to = edge.to();
            if (distTo[to] > distTo[v] + edge.weight()) {
                distTo[to] = distTo[v] + edge.weight();
                edgeTo[to] = edge;
            }
        }
    }

    public double distTo(int v) {
        return distTo[v];
    }

    public boolean hasPathTo(int v) {
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    public Iterable<DirectedEdge> pathTo(int v) {
        if (!hasPathTo(v)) {
            return null;
        }
        Stack<DirectedEdge> path = new Stack<>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
            path.push(e);
        }
        return path;
    }
}
