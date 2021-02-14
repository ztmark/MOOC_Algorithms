package com.github.ztmark.undirectgraph;

import java.util.HashMap;
import java.util.Map;

public class SymbolGraph {

    private Map<String, Integer> symbolTable;
    private String[] keys;
    private Graph graph;

    public SymbolGraph() {
        symbolTable = new HashMap<>();
        /// todo construct graph
        graph = new Graph(0);
        keys = new String[graph.V()];
    }

    public boolean contains(String key) {
        return symbolTable.containsKey(key);
    }

    public int index(String key) {
        return symbolTable.get(key);
    }

    public String name(int v) {
        return keys[v];
    }

    public Graph graph() {
        return graph;
    }
}
