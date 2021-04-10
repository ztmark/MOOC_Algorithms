package com.github.ztmark.sp;

import com.github.ztmark.basic.Queue;
import com.github.ztmark.basic.Stack;

public class Topological {
    private Iterable<Integer> order;

    public Topological(EdgeWeightedDigraph digraph) {
        DirectedCycle directedCycle = new DirectedCycle(digraph);
        if (!directedCycle.hasCycle()) {
            DepthFirstOrder dfOrder = new DepthFirstOrder(digraph);
            order = dfOrder.reversePost();
        }
    }

    public boolean hasOrder() {
        return order != null;
    }

    public Iterable<Integer> order() {
        return order;
    }


    private static class DepthFirstOrder {
        private final boolean[] marked;
        private final Queue<Integer> pre;
        private final Queue<Integer> post;
        private final Stack<Integer> reversePost;

        public DepthFirstOrder(EdgeWeightedDigraph digraph) {
            marked = new boolean[digraph.V()];
            pre = new Queue<>();
            post = new Queue<>();
            reversePost = new Stack<>();
            for (int i = 0; i < digraph.V(); i++) {
                if (!marked[i]) {
                    dfs(digraph, i);
                }
            }
        }

        private void dfs(EdgeWeightedDigraph digraph, int v) {
            marked[v] = true;
            pre.enqueue(v);
            for (DirectedEdge edge : digraph.adj(v)) {
                int to = edge.to();
                if (!marked[to]) {
                    dfs(digraph, to);
                }
            }
            post.enqueue(v);
            reversePost.push(v);
        }

        public Iterable<Integer> pre() {
            return pre;
        }

        public Iterable<Integer> post() {
            return post;
        }

        public Iterable<Integer> reversePost() {
            return reversePost;
        }
    }
}
