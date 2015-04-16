package week4;

import week3.QuickSort;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Author: Mark
 * Date  : 2015/4/16
 * Time  : 20:22
 */
public class HeapSort<T extends Comparable<T>> {

    public static void main(String[] args) {
        Integer[] items = new Integer[20];
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < 20; i++) {
            items[i] = random.nextInt(100);
        }
        printArray(items);
        new HeapSort<Integer>().sort(items);
        printArray(items);
    }


    private static void printArray(Comparable[] items) {
        for (Comparable i : items) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    public void sort(T[] items) {
        int n = items.length - 1;
        for (int i = n / 2; i >= 0; i--) {
            sink(items, i, n);
        }
        while (n > 0) {
            exch(items, 0, n--);
            sink(items, 0, n);
        }
    }

    private void exch(T[] items, int i, int j) {
        T tmp = items[i];
        items[i] = items[j];
        items[j] = tmp;
    }

    private void sink(T[] items, int k, int n) {
        while (k * 2 + 1 <= n) {
            int child = k * 2 + 1;
            if (child < n && items[child].compareTo(items[child + 1]) < 0) {
                child++;
            }
            if (items[k].compareTo(items[child]) > 0) {
                break;
            }
            exch(items, k, child);
            k = child;
        }
    }

}
