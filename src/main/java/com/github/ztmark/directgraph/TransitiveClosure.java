package com.github.ztmark.directgraph;

/*
传递闭包
对于一个有向图G，它的传递闭包是相同的节点组成的一个新图
在该图中存在一个 v 指向 w 的边当且仅当 G 中 w 是从 v 可达的
 */
public class TransitiveClosure {

    private final DirectedDFS[] all;

    public TransitiveClosure(DiGraph graph) {
        all = new DirectedDFS[graph.V()];
        for (int i = 0; i < graph.V(); i++) {
            all[i] = new DirectedDFS(graph, i);
        }
    }

    public boolean reachable(int v, int w) {
        return all[v].marked(w);
    }
}
