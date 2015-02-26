package week2;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 * Author: Mark
 * Date  : 2015/2/26
 * Time  : 22:28
 */
public class StackWithArray<Item> implements Iterable<Item> {


    public static void main(String[] args) {
        StackWithArray<Integer> stk = new StackWithArray<>();
        Random random = new Random(47);
        for (int i = 0; i < 20; i++) {
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

    private Item[] items;
    private int size;
    private int capacity;

    @SuppressWarnings("unchecked")
    public StackWithArray() {
        capacity = 16;
        items = (Item[]) new Object[capacity];
        size = 0;
    }

    public void push(Item item) {
        if (size >= capacity) {
            ensureCapacity(capacity * 2 + 1);
        }
        items[size++] = item;
    }

    @SuppressWarnings("unchecked")
    private void ensureCapacity(int newCapacity) {
        Item[] old = items;
        items = (Item[]) new Object[newCapacity];
        System.arraycopy(old, 0, items, 0, size);
    }

    public Item pop() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item res = items[--size];
        items[size + 1] = null;
        return res;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    @Override
    public Iterator<Item> iterator() {
        return new StackIterator(size);
    }

    private class StackIterator implements Iterator<Item> {

        private int cur;

        public StackIterator(int cur) {
            this.cur = cur;
        }

        @Override
        public boolean hasNext() {
            return cur != 0;
        }

        @Override
        public Item next() {
            return items[--cur];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
