package com.github.ztmark.directgraph;

import java.util.Iterator;

public class Topological {

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

        Topological topological = new Topological(diGraph);
        System.out.println(topological.order());
    }

    private Iterable<Integer> order;

    public Topological(DiGraph graph) {
        DirectedCycle directedCycle = new DirectedCycle(graph);
        if (!directedCycle.hasCycle()) {
            DepthFirstOrder depthFirstOrder = new DepthFirstOrder(graph);
            order = depthFirstOrder.reversePost();
        }
    }

    public boolean isDAG() {
        return order != null;
    }

    public Iterable<Integer> order() {
        return order;
    }
}
