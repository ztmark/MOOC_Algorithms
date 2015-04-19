package week1;

import java.util.HashSet;
import java.util.Set;

/**
 * Author: Mark
 * Date  : 2015/4/19
 * Time  : 20:48
 */
public class DepthFirstSearch {

    private Set<Integer> marked;
    private int count;

    public DepthFirstSearch(Graph g, int s) {
        marked = new HashSet<>(g.V());
        count = 0;
        dfs(g, s);
    }

    private void dfs(Graph g, int s) {
        marked.add(s);
        count++;
        for (Integer v : g.adj(s)) {
            if (!marked.contains(v)) {
                dfs(g, v);
            }
        }
    }

    public boolean marked(int v) {
        return marked.contains(v);
    }

    public int count() {
        return count;
    }

}
