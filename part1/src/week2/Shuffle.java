package week2;

import java.util.Random;

/**
 * Author: Mark
 * Date  : 2015/3/2
 * Time  : 20:05
 */
public class Shuffle {

    public static void main(String[] args) {
        Integer[] items = new Integer[10];
        Random random = new Random(47);
        for (int i = 0; i < 10; i++) {
            items[i] = random.nextInt(50);
        }
        printArray(items);
        new InsertionSort<Integer>().sort(items, 0, items.length);
        printArray(items);

        shuffle(items, 0, items.length);
        printArray(items);
    }

    private static void printArray(Comparable[] items) {
        for (Comparable i : items) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    public static void shuffle(Object[] items, int low, int high) {
        Random random = new Random(System.currentTimeMillis());
        for (int i = low + 1; i < high; i++) {
            int r = random.nextInt(i - low) + low;
            swap(items, r, i);
        }
    }

    private static void swap(Object[] items, int n, int m) {
        Object tmp = items[n];
        items[n] = items[m];
        items[m] = tmp;
    }

}
