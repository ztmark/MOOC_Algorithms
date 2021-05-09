package com.github.ztmark.sp;

import com.github.ztmark.directgraph.DiGraph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class TopologicalBFS {

    public static void main(String[] args) {
        DiGraph diGraph = new DiGraph(13);
        diGraph.addEdges(0, 1);
        diGraph.addEdges(0, 5);
        diGraph.addEdges(0, 6);
        diGraph.addEdges(2, 0);
        diGraph.addEdges(2, 3);
        diGraph.addEdges(3, 5);
        diGraph.addEdges(5, 4);
        diGraph.addEdges(6, 4);
        diGraph.addEdges(6, 9);
        diGraph.addEdges(7, 6);
        diGraph.addEdges(8, 7);
        diGraph.addEdges(9, 10);
        diGraph.addEdges(9, 11);
        diGraph.addEdges(9, 12);
        diGraph.addEdges(11, 12);

        TopologicalBFS topologicalBFS = new TopologicalBFS(diGraph);
        System.out.println(topologicalBFS.order);
    }


    private List<Integer> order;

    public TopologicalBFS(DiGraph diGraph) {
        order = new ArrayList<>();
        int[] degree = new int[diGraph.V()];
        for (int i = 0; i < diGraph.V(); i++) {
            for (Integer v : diGraph.adj(i)) {
                degree[v]++;
            }
        }
        Queue<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < degree.length; i++) {
            if (degree[i] == 0) {
                q.offer(i);
            }
        }
        while (!q.isEmpty()) {
            Integer v = q.poll();
            order.add(v);
            for (Integer w : diGraph.adj(v)) {
                degree[w]--;
                if (degree[w] == 0) {
                    q.offer(w);
                }
            }
        }
        if (order.size() != diGraph.V()) {
            order = null;
        }
    }

    public boolean hasOrder() {
        return order != null;
    }

    public Iterable<Integer> order() {
        return order;
    }
}
