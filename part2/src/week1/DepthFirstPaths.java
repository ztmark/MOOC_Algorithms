package week1;

import java.util.*;

/**
 * Author: Mark
 * Date  : 2015/4/19
 * Time  : 20:53
 */
public class DepthFirstPaths {

    private Set<Integer> marked;
    private Map<Integer, Integer> edgeTo;
    private final int s;

    public DepthFirstPaths(Graph g, int s) {
        marked = new HashSet<>(g.V());
        edgeTo = new HashMap<>(g.V());
        this.s = s;
        dfs(g, s);
    }

    private void dfs(Graph g, int s) {
        marked.add(s);
        for (Integer v : g.adj(s)) {
            if (!marked.contains(v)) {
                edgeTo.put(v, s);
                dfs(g, v);
            }
        }
    }

    public boolean hasPathTo(int v) {
        return marked.contains(v);
    }

    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        Stack<Integer> path = new Stack<>();
        for (int x = v; x != s; x = edgeTo.get(x)) {
            path.push(x);
        }
        path.push(s);
        return path;
    }
}
