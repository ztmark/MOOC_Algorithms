package com.github.ztmark.basic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Bag<T> implements Iterable<T> {
   /* private final List<T> bag = new ArrayList<>();

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
    }*/

    // linked list implements

    private Node<T> first;
    private int size;

    public Bag() {
    }

    public void add(T item) {
        Node<T> oldFirst = first;
        first = new Node<>();
        first.item = item;
        first.next = oldFirst;
        size++;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<T> {
        private Node<T> current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            Node<T> n = current;
            current = current.next;
            return n.item;
        }
    }
}
