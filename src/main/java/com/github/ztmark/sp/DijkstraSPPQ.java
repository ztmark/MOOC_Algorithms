package com.github.ztmark.sp;

import com.github.ztmark.basic.Stack;

import java.util.PriorityQueue;

public class DijkstraSPPQ {
    private final DirectedEdge[] edgeTo;
    private final double[] distTo;

    public DijkstraSPPQ(EdgeWeightedDigraph digraph, int s) {
        edgeTo = new DirectedEdge[digraph.V()];
        distTo = new double[digraph.V()];
        for (int i = 0; i < digraph.V(); i++) {
            distTo[i] = Double.POSITIVE_INFINITY;
        }
        distTo[s] = 0;
        PriorityQueue<Vertex> pq = new PriorityQueue<>();
        pq.add(new Vertex(s, 0.0));
        while (!pq.isEmpty()) {
            relax(digraph, pq.poll().v, pq);
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

    private static class Vertex implements Comparable<Vertex> {
        int v;
        double dist;

        public Vertex(int v, double dist) {
            this.v = v;
            this.dist = dist;
        }

        @Override
        public int compareTo(Vertex o) {
            return Double.compare(this.dist, o.dist);
        }
    }
}
