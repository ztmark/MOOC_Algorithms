package com.github.ztmark.mst;


import com.github.ztmark.basic.Queue;

import java.util.PriorityQueue;

public class LazyPrimMST {

    private final boolean[] marked;
    private final Queue<Edge> mst;
    private final PriorityQueue<Edge> pq;
    private double weight;

    public LazyPrimMST(EdgeWeightedGraph graph) {
        marked = new boolean[graph.V()];
        mst = new Queue<>();
        pq = new PriorityQueue<>();

        visit(graph, 0);
        while (!pq.isEmpty()) {
            Edge edge = pq.poll();
            int v = edge.either();
            int w = edge.other(v);
            if (marked[v] && marked[w]) continue;
            mst.enqueue(edge);
            weight += edge.weight();
            if (!marked[v]) visit(graph, v);
            if (!marked[w]) visit(graph, w);
        }
    }

    private void visit(EdgeWeightedGraph graph, int v) {
        marked[v] = true;
        for (Edge edge : graph.adj(v)) {
            if (!marked[edge.other(v)]) {
                pq.add(edge);
            }
        }
    }

    public Iterable<Edge> edges() {
        return mst;
    }

    public double weight() {
        return weight;
    }
}
