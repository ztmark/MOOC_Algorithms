package com.github.ztmark.basic;

import java.util.Iterator;

public class Queue<Item> implements Iterable<Item> {

    private Node<Item> first;
    private Node<Item> last;
    private int size;

    public Queue() {
    }

    public void enqueue(Item item) {
        Node<Item> oldLast = last;
        last = new Node<>();
        last.item = item;
        if (isEmpty()) {
            first = last;
        } else {
            oldLast.next = last;
        }
        size++;
    }

    public Item dequeue() {
        Item item = first.item;
        first = first.next;
        size--;
        if (isEmpty()) {
            last = null;
        }
        return item;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return size;
    }

    @Override
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node<Item> current = first;
        @Override
        public boolean hasNext() {
            return first != null;
        }

        @Override
        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
}
