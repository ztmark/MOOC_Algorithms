import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Author: Mark
 * Date  : 2015/2/10
 * Time  : 21:22
 */
public class Deque<Item> implements Iterable<Item> {

    private Node header;
    private Node tail;
    private int size;

    // construct an empty deque
    public Deque() {
        header = null;
        tail = null;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        Node node = new Node(item);
        if (size == 0) {
            header = node;
            tail = node;
        } else {
            node.next = header;
            header.pre = node;
            header = node;
        }
        size++;
    }

    // add the item to the end
    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        Node node = new Node(item);
        if (size == 0) {
            tail = node;
            header = node;
        } else {
            node.pre = tail;
            tail.next = node;
            tail = node;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        Node deleted = header;
        if (size == 1) {
            tail = header = null;
        } else {
            header = header.next;
            header.pre = null;
            deleted.next = null;
        }
        size--;
        return deleted.value;
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        Node deleted = tail;
        if (size == 1) {
            tail = header = null;
        } else {
            tail = tail.pre;
            tail.next = null;
            deleted.pre = null;
        }
        size--;
        return deleted.value;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new DequeIterator(header);
    }

    private class DequeIterator implements Iterator<Item> {

        private Node cur;

        public DequeIterator(Node cur) {
            this.cur = cur;
        }

        @Override
        public boolean hasNext() {
            return cur != null;
        }

        @Override
        public Item next() {
            if (cur == null) {
                throw new NoSuchElementException();
            }
            Item value = cur.value;
            cur = cur.next;
            return value;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private class Node {
        private Item value;
        private Node pre;
        private Node next;

        Node(Item value) {
            this.value = value;
        }

        Node() {
        }
    }

    // unit testing
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        for (int i = 0; i < 3; i++) {
            deque.addFirst(i);
        }
        for (int i = 3; i < 6; i++) {
            deque.addLast(i);
        }
        for (int integer : deque) {
            System.out.println(integer);
        }
        deque.removeFirst();
        deque.removeLast();
        for (int integer : deque) {
            System.out.println(integer);
        }
    }

}
