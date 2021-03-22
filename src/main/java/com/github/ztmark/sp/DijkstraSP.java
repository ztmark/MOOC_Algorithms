package com.github.ztmark.sp;

import com.github.ztmark.basic.IndexMinPQ;
import com.github.ztmark.basic.Stack;

public class DijkstraSP {

    private final DirectedEdge[] edgeTo;
    private final double[] distTo;
    private final IndexMinPQ<Double> pq;

    public DijkstraSP(EdgeWeightedDigraph digraph, int s) {
        edgeTo = new DirectedEdge[digraph.V()];
        distTo = new double[digraph.V()];
        pq = new IndexMinPQ<>(digraph.V());
        for (int i = 0; i < digraph.V(); i++) {
            distTo[i] = Double.POSITIVE_INFINITY;
        }
        distTo[s] = 0;
        pq.insert(s, 0.0);
        while (!pq.isEmpty()) {
            relax(digraph, pq.delMin());
        }
    }

    private void relax(EdgeWeightedDigraph digraph, int v) {
        for (DirectedEdge edge : digraph.adj(v)) {
            int to = edge.to();
            if (distTo[to] > distTo[edge.from()] + edge.weight()) {
                distTo[to] = distTo[edge.from()] + edge.weight();
                edgeTo[to] = edge;
                if (pq.contains(to)) {
                    pq.changeKey(to, distTo[to]);
                } else {
                    pq.insert(to, distTo[to]);
                }
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
        for (DirectedEdge edge = edgeTo[v];  edge != null; edge = edgeTo[edge.from()]) {
            path.push(edge);
        }
        return path;
    }
}
