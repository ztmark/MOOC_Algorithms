package com.github.ztmark.basic;

import java.util.Iterator;

public class Stack<Item> implements Iterable<Item> {

    private Node<Item> top;
    private int size;

    public Stack() {

    }

    public void push(Item item) {
        Node<Item> node = new Node<>();
        node.item = item;
        node.next = top;
        top = node;
        size++;
    }

    public Item pop() {
        Node<Item> t = top;
        top = top.next;
        size--;
        return t.item;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public int size() {
        return size;
    }

    @Override
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node<Item> current = top;
        @Override
        public boolean hasNext() {
            return top != null;
        }

        @Override
        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

}
