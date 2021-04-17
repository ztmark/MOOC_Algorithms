package com.github.ztmark.sp;

import com.github.ztmark.basic.Queue;
import com.github.ztmark.basic.Stack;

import java.util.Arrays;

/*
可以处理有负权重环的有向图的最短路问题
 */
public class BellmanFordSP {

    private final double[] distTo;
    private final DirectedEdge[] edgeTo;
    private final Queue<Integer> queue;
    private final boolean[] onQueue;
    private int cost;  // relax 调用的次数
    private Iterable<DirectedEdge> cycle;

    public BellmanFordSP(EdgeWeightedDigraph digraph, int s) {
        distTo = new double[digraph.V()];
        edgeTo = new DirectedEdge[digraph.V()];
        queue = new Queue<>();
        onQueue = new boolean[digraph.V()];

        Arrays.fill(distTo, Double.POSITIVE_INFINITY);
        queue.enqueue(s);
        onQueue[s] = true;
        distTo[s] = 0;

        while (!queue.isEmpty() && !hasNegativeCycle()) {
            Integer v = queue.dequeue();
            onQueue[v] = false;
            relax(digraph, v);
        }
    }

    private void relax(EdgeWeightedDigraph digraph, int v) {
        for (DirectedEdge edge : digraph.adj(v)) {
            int to = edge.to();
            if (distTo[to] > distTo[v] + edge.weight()) {
                distTo[to] = distTo[v] + edge.weight();
                edgeTo[to] = edge;
                if (!onQueue[to]) {
                    queue.enqueue(to);
                    onQueue[to] = true;
                }
            }
            if (cost++ % digraph.V() == 0) {
                findNegativeCycle();
            }
        }
    }

    private void findNegativeCycle() {
        int V = edgeTo.length;
        EdgeWeightedDigraph spt = new EdgeWeightedDigraph(V);
        for (DirectedEdge directedEdge : edgeTo) {
            if (directedEdge != null) {
                spt.addEdge(directedEdge);
            }
        }
        EdgeWeightedDirectedCycle cf = new EdgeWeightedDirectedCycle(spt);
        if (cf.hasCycle()) {
            cycle = cf.cycle();
        }
    }

    public boolean hasNegativeCycle() {
        return cycle != null;
    }

    public Iterable<DirectedEdge> negativeCycle() {
        return cycle;
    }

    public double distTo(int v) {
        return distTo[v];
    }

    public boolean hasPathTo(int v) {
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    public Iterable<DirectedEdge> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        Stack<DirectedEdge> path = new Stack<>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
            path.push(e);
        }
        return path;
    }
}
