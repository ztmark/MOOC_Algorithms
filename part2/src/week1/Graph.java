package week1;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Author: Mark
 * Date  : 2015/4/19
 * Time  : 20:35
 */
public class Graph {

    private HashMap<Integer, List<Integer>> adj;
    private int V;
    private int E;

    public Graph(int v) {
        V = v;
        E = 0;
        adj = new HashMap<>(v);
    }

    public void addEdge(int v, int w) {
        List<Integer> vadj = adj.get(v);
        if (vadj == null) {
            vadj = new LinkedList<>();
            vadj.add(w);
            adj.put(v, vadj);
        }
        List<Integer> wadj = adj.get(w);
        if (wadj == null) {
            wadj = new LinkedList<>();
            wadj.add(v);
            adj.put(w, wadj);
        }
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public int degree(int v) {
        List<Integer> vadj = adj.get(v);
        if (vadj == null) {
            throw new IllegalArgumentException("vertex does not exist!");
        }
        return vadj.size();
    }

    public Iterable<Integer> adj(int v) {
        return adj.get(v);
    }
}
