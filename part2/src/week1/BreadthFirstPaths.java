package week1;

import java.util.*;

/**
 * Author: Mark
 * Date  : 2015/4/19
 * Time  : 21:13
 */
public class BreadthFirstPaths {

    private Set<Integer> marked;
    private Map<Integer, Integer> edgeTo;
    private final int s;

    public BreadthFirstPaths(Graph g, int s) {
        marked = new HashSet<>(g.V());
        edgeTo = new HashMap<>(g.V());
        this.s = s;
        bfs(g, s);
    }

    private void bfs(Graph g, int s) {
        Queue<Integer> queue = new LinkedList<>();
        marked.add(s);
        queue.add(s);
        while (!queue.isEmpty()) {
            int v = queue.remove();
            for (Integer w : g.adj(v)) {
                if (!marked.contains(w)) {
                    marked.add(w);
                    edgeTo.put(w, v);
                    queue.add(w);
                }
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
