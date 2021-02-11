package com.github.ztmark.undirectgraph;

public class CC {

    private int count;
    private final boolean[] marked;
    private final int[] id;

    public CC(Graph g) {
        marked = new boolean[g.V()];
        id = new int[g.V()];
        for (int i = 0; i < g.V(); i++) {
            if (!marked[i]) {
                dfs(g, i);
                count++;
            }
        }
    }

    private void dfs(Graph g, int s) {
        marked[s] = true;
        id[s] = count;
        for (Integer w : g.adj(s)) {
            if (!marked[w]) {
                dfs(g, w);
            }
        }
    }

    public boolean connected(int v, int w) {
        return id[v] == id[w];
    }

    private int id(int v) {
        return id[v];
    }

    private int count() {
        return count;
    }
}
