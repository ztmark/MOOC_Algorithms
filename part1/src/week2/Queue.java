package week2;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Author: Mark
 * Date  : 2015/2/27
 * Time  : 22:12
 */
public class Queue<Item> implements Iterable<Item> {


    public static void main(String[] args) {
        Queue<Integer> queue = new Queue<>();
        for (int i = 0; i < 10; i++) {
            queue.enqueue(i);
        }
        System.out.println(queue.size());
        System.out.println(queue.isEmpty());
        System.out.println(queue.dequeue());
        for (int i : queue) {
            System.out.println(i);
        }
        System.out.println(queue.isEmpty());
        System.out.println(queue.size());
        while (!queue.isEmpty()) {
            System.out.println(queue.dequeue());
        }
    }


    private Node head;
    private Node tail;
    private int size;

    public Queue() {
        head = null;
        tail = null;
        size = 0;
    }

    public void enqueue(Item item) {
        if (isEmpty()) {
            head = new Node(item);
            tail = head;
        } else {
            tail.next = new Node(item);
            tail = tail.next;
        }
        size++;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Node node = head;
        head = head.next;
        node.next = null;
        size--;
        if (isEmpty()) {
            tail = head;
        }
        return node.value;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    @Override
    public Iterator<Item> iterator() {
        return new QueueIterator(head);
    }

    private class QueueIterator implements Iterator<Item> {

        Node cur;

        public QueueIterator(Node cur) {
            this.cur = cur;
        }

        @Override
        public boolean hasNext() {
            return cur != null;
        }

        @Override
        public Item next() {
            Node res = cur;
            cur = cur.next;
            return res.value;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }


    private class Node {
        Item value;
        Node next;

        public Node(Item value) {
            this.value = value;
        }
    }
}
