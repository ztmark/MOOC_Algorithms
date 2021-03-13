package com.github.ztmark.directgraph;

public class Topological {

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
