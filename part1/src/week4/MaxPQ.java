package week4;

import java.util.NoSuchElementException;
import java.util.Random;

/**
 * Author: Mark
 * Date  : 2015/3/19
 * Time  : 21:36
 */
public class MaxPQ<Key extends Comparable<Key>> {


    public static void main(String[] args) {
        MaxPQ<Integer> mp = new MaxPQ<>();
        Random random = new Random(47);
        for (int i = 0; i < 100; i++) {
            int n = random.nextInt(200);
            System.out.print(n + "----" + ((Comparable[])mp.pq).length + " ");
            mp.insert(n);
        }
        System.out.println();
        while (!mp.isEmpty()) {
            System.out.println(mp.delMax() + "---"+ ((Comparable[])mp.pq).length);
        }
    }

    private Key[] pq;
    private int size;
    private static final int DEFAULT_CAPACITY = 16;

    @SuppressWarnings("unchecked")
    public MaxPQ(int capacity) {
        pq = (Key[]) new Comparable[capacity];
        size = 0;
    }

    @SuppressWarnings("unchecked")
    public MaxPQ() {
        pq = (Key[]) new Comparable[DEFAULT_CAPACITY];
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public Key max() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return pq[1];
    }

    public int size() {
        return size;
    }

    public void insert(Key key) {
        if (size + 1 >= pq.length) {
            ensureCapacity(2 * size + 1);
        }
        pq[++size] = key;
        swim(size);
    }

    public Key delMax() {
        exch(1, size);
        Key max = pq[size];
        pq[size--] = null;
        sink(1);
        if (pq.length > DEFAULT_CAPACITY && size < pq.length / 4) {
            shrink(pq.length / 2 + 1);
        }
        return max;
    }

    @SuppressWarnings("unchecked")
    private void ensureCapacity(int newCapacity) {
        Key[] old = pq;
        Key[] newPQ = (Key[]) new Comparable[newCapacity];
        System.arraycopy(old, 1, newPQ, 1, size);
        pq = newPQ;
        old = null;
    }

    private void shrink(int newCapacity) {
        if (newCapacity <= size) {
            throw new IllegalArgumentException();
        }
        ensureCapacity(newCapacity);
    }

    private void swim(int k) {
        while (k > 1 && less(k / 2, k)) {
            exch(k / 2, k);
            k /= 2;
        }
    }

    private void sink(int k) {
        while (k * 2 <= size) {
            int child = k * 2;
            if (child + 1 <= size && less(child, child + 1)) {
                child++;
            }
            if (!less(k, child)) break;
            exch(k, child);
            k = child;
        }
    }

    private boolean less(int i, int j) {
        return pq[i].compareTo(pq[j]) < 0;
    }

    private void exch(int i, int j) {
        Key tmp = pq[i];
        pq[i] = pq[j];
        pq[j] = tmp;
    }
}
