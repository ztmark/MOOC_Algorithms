package week2;

import java.util.NoSuchElementException;

/**
 * Author: Mark
 * Date  : 2015/2/9
 * Time  : 15:24
 */
public class SingleLinkedList<T> {

    private Node list;
    private int size;

    public SingleLinkedList() {
        list = null;
        size = 0;
    }

    public void add(T v) {
        Node n = new Node(v, list);
        list = n;
    }

    public T get(int i) {
        if (i < 0 || i > size) {
            throw new NoSuchElementException();
        }
        return getNode(i).item;
    }

    public T remove() {
        if (size <= 0) {
            throw new NoSuchElementException();
        }
        T tmp = list.item;
        list = list.next;
        return tmp;
    }

    public T remove(int i) {
        if (i < 0 || i > size) {
            throw new NoSuchElementException();
        }
        if (i == 0) {
            return remove();
        }
        Node pre = getNode(i-1);
        Node deleted = pre.next;
        pre.next = deleted.next;
        deleted.next = null;
        return deleted.item;
    }

    private Node getNode(int i) {
        Node tmp = list;
        for (int j = 0; j < i; j++) {
            tmp = tmp.next;
        }
        return tmp;
    }

    private class Node {
        T item;
        Node next;

        public Node(T item, Node next) {
            this.item = item;
            this.next = next;
        }
    }


}
