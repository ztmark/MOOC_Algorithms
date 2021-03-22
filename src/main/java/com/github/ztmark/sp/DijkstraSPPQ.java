package com.github.ztmark.sp;

import java.util.PriorityQueue;

public class DijkstraSPPQ {
    private final DirectedEdge[] edgeTo;
    private final double[] distTo;
    private final int s;

    public DijkstraSPPQ(EdgeWeightedDigraph digraph, int s) {
        this.s = s;
        edgeTo = new DirectedEdge[digraph.V()];
        distTo = new double[digraph.V()];
        for (int i = 0; i < digraph.V(); i++) {
            distTo[i] = Double.POSITIVE_INFINITY;
        }
        distTo[s] = 0;
        PriorityQueue<Vertex> pq = new PriorityQueue<>();
        pq.add(new Vertex(s, 0.0));
        while (!pq.isEmpty()) {
            relax(digraph,pq.poll().v, pq);
        }
    }

    private void relax(EdgeWeightedDigraph digraph, int s, PriorityQueue<Vertex> pq) {
        for (DirectedEdge edge : digraph.adj(s)) {
            int to = edge.to();
            if (distTo[to] > distTo[edge.from()] + edge.weight()) {
                distTo[to] = distTo[edge.from()] + edge.weight();
                edgeTo[to] = edge;
                pq.add(new Vertex(to, distTo[to]));
            }
        }
    }

    private static class Vertex implements Comparable<Vertex> {
        int v;
        double weight;

        public Vertex(int v, double weight) {
            this.v = v;
            this.weight = weight;
        }

        @Override
        public int compareTo(Vertex o) {
            return Double.compare(this.weight, o.weight);
        }
    }
}
