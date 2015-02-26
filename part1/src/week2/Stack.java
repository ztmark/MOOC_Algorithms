package week2;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 * Author: Mark
 * Date  : 2015/2/26
 * Time  : 22:11
 */
public class Stack<Item> implements Iterable<Item> {


    public static void main(String[] args) {
        Stack<Integer> stk = new Stack<>();
        Random random = new Random(47);
        for (int i = 0; i < 10; i++) {
            stk.push(random.nextInt(20));
        }
        for (int i : stk) {
            System.out.println(i);
        }
        System.out.println(stk.isEmpty());
        stk.push(1);
        stk.push(11);
        stk.push(111);
        System.out.println(stk.isEmpty());
        System.out.println(stk.pop());
        System.out.println(stk.size());
    }

    private Node stack;
    private int size;

    public Stack() {
        stack = null;
        size = 0;
    }

    public void push(Item item) {
        Node node = new Node(item);
        if (stack == null) {
            stack = node;
            stack.next = null;
        } else {
            node.next = stack;
            stack = node;
        }
        size++;
    }

    public Item pop() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Node node = stack;
        stack = stack.next;
        node.next = null;
        size--;
        return node.val;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    @Override
    public Iterator<Item> iterator() {
        return new StackIterator(stack);
    }

    private class Node {
        Item val;
        Node next;

        public Node(Item val) {
            this.val = val;
        }
    }

    private class StackIterator implements Iterator<Item> {

        private Node cur;

        public StackIterator(Node cur) {
            this.cur = cur;
        }

        @Override
        public boolean hasNext() {
            return cur != null;
        }

        @Override
        public Item next() {
            Item val = cur.val;
            cur = cur.next;
            return val;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
