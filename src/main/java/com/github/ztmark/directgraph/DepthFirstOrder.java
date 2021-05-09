package com.github.ztmark.directgraph;

import java.util.*;

public class DepthFirstOrder {
    private final Queue<Integer> pre;
    private final Queue<Integer> post;

    private final boolean[] marked;

    public DepthFirstOrder(DiGraph graph) {
        pre = new ArrayDeque<>();
        post = new ArrayDeque<>();
        marked = new boolean[graph.V()];
        for (int i = 0; i < graph.V(); i++) {
            if (!marked[i]) {
                dfs(graph, i);
            }
        }
    }

    private void dfs(DiGraph graph, int v) {
        pre.offer(v);
        marked[v] = true;
        for (Integer w : graph.adj(v)) {
            if (!marked[w]) {
                dfs(graph, w);
            }
        }
        post.offer(v);
    }

    public Iterable<Integer> pre() {
        return pre;
    }

    public Iterable<Integer> post() {
        return post;
    }

    public Iterable<Integer> reversePost() {
        List<Integer> reverse = new ArrayList<>(post);
        Collections.reverse(reverse);
        return reverse;
    }
}
