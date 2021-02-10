package com.github.ztmark.basic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Bag<T> implements Iterable<T> {
    private final List<T> bag = new ArrayList<>();

    public void add(T item) {
        bag.add(item);
    }

    public boolean isEmpty() {
        return bag.isEmpty();
    }

    public int size() {
        return bag.size();
    }

    @Override
    public Iterator<T> iterator() {
        return bag.iterator();
    }
}
