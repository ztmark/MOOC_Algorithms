package com.github.ztmark.mst;

import com.github.ztmark.basic.MinPQ;
import com.github.ztmark.basic.Queue;
import com.github.ztmark.basic.UF;

public class KruskalMST {
    private Queue<Edge> mst;
    private double weight;

    public KruskalMST(EdgeWeightedGraph graph) {
        mst = new Queue<>();
        MinPQ<Edge> pq = new MinPQ<>(graph.E());
        for (Edge edge : graph.edges()) {
            pq.inset(edge);
        }
        UF uf = new UF(graph.V());
        while (!pq.isEmpty() && mst.size() < graph.V() - 1) {
            Edge edge = pq.delMin();
            int v = edge.either();
            int w = edge.other(v);
            if (uf.connected(v, w)) continue;
            uf.connected(v, w);
            mst.enqueue(edge);
            weight += edge.weight();
        }
    }

    public Iterable<Edge> edges() {
        return mst;
    }

    public double weight() {
        return weight;
    }
}
