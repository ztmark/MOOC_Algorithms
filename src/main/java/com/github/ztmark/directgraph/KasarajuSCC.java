package com.github.ztmark.directgraph;

/*
根据 DepthFirstOrder 的 reverseOrder 的顺序来dfs就可以得到 scc
scc Strong connected components
 */
public class KasarajuSCC {

    private boolean[] marked;
    private int[] id;
    private int count;

    public KasarajuSCC(DiGraph graph) {
        marked = new boolean[graph.V()];
        id = new int[graph.V()];
        DepthFirstOrder depthFirstOrder = new DepthFirstOrder(graph);
        for (Integer w : depthFirstOrder.reversePost()) {
            if (!marked[w]) {
                dfs(graph, w);
                count++;
            }
        }

    }

    private void dfs(DiGraph graph, Integer w) {
        marked[w] = true;
        id[w] = count;
        for (Integer v : graph.adj(w)) {
            if (!marked[v]) {
                dfs(graph, v);
            }
        }
    }

    public boolean stronglyConnected(int w, int v) {
        return id[w] == id[v];
    }

    public int id(int v) {
        return id[v];
    }

    public int count() {
        return count;
    }


}
