import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Author: Mark
 * Date  : 2015/2/10
 * Time  : 21:56
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int size;
    private int capacity = 16;

    // construct an empty randomized queue
    @SuppressWarnings("unchecked")
    public RandomizedQueue() {
        items = (Item[]) new Object[capacity];
        size = 0;
    }

    // is the queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        items[size++] = item;
        if (size >= capacity) {
            ensureCapacity(capacity * 2 + 1);
            capacity = capacity * 2 + 1;
        }
    }

    @SuppressWarnings("unchecked")
    private void ensureCapacity(int newSize) {
        Item[] old = items;
        items = (Item[]) new Object[newSize];
        System.arraycopy(old, 0, items, 0, size);
    }

    // remove and return a random item
    public Item dequeue() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        Item ans = randomDequeue();
        if (size <= capacity / 4) {
            ensureCapacity(capacity / 2);
            capacity /= 2;
        }
        return ans;
    }

    private Item randomDequeue() {
        int index = StdRandom.uniform(size);
        Item del = items[index];
        System.arraycopy(items, index + 1, items, index, size - index - 1);
        items[size-1] = null;
        size--;
        return del;
    }

    // return (but do not remove) a random item
    public Item sample() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return items[StdRandom.uniform(size)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RQIterator(size);
    }

    private class RQIterator implements Iterator<Item> {

        private boolean[] visited;
        private int visitedCount;

        public RQIterator(int size) {
            visited = new boolean[size];
            visitedCount = 0;
        }

        @Override
        public boolean hasNext() {
            return visitedCount != visited.length;
        }

        @Override
        public Item next() {
            if (visitedCount == visited.length) {
                throw new NoSuchElementException();
            }
            int index = StdRandom.uniform(visited.length);
            while (visited[index]) {
                index = StdRandom.uniform(visited.length);
            }
            visited[index] = true;
            visitedCount++;
            return items[index];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        for (int i = 0; i < 10; i++) {
            rq.enqueue(i);
        }
        System.out.println(rq.dequeue());
        rq.enqueue(10);
        for (int integer : rq) {
            System.out.println(integer);
        }
    }
}
