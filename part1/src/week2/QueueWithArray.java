package week2;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Author: Mark
 * Date  : 2015/2/27
 * Time  : 22:25
 */
public class QueueWithArray<Item> implements Iterable<Item> {


    public static void main(String[] args) {
        QueueWithArray<Integer> queue = new QueueWithArray<>();
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


    private Item[] items;
    private int head;
    private int tail;
    private int size;

    @SuppressWarnings("unchecked")
    public QueueWithArray() {
        items = (Item[]) new Object[256];
        head = -1;
        tail = -1;
        size = 0;
    }

    public void enqueue(Item item) {
        if (isEmpty()) {
            items[++head] = item;
            tail = head;
        } else {
            if (tail + 1 >= items.length) {
                tail = -1;
            }
            items[++tail] = item;
        }
        size++;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item res = items[head++];
        items[head - 1] = null;
        size--;
        if (head >= items.length) {
            head = 0;
        }
        return res;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private boolean isFull() {
        return size == items.length;
    }

    public int size() {
        return size;
    }

    @Override
    public Iterator<Item> iterator() {
        return new QueueIterator(head);
    }

    private class QueueIterator implements Iterator<Item> {

        private int cur;

        public QueueIterator(int cur) {
            this.cur = cur;
        }

        @Override
        public boolean hasNext() {
            return cur != tail;
        }

        @Override
        public Item next() {
            Item res = items[cur++];
            if (cur == items.length) {
                cur = 0;
            }
            return res;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
