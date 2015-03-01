package week2;

import java.util.Random;

/**
 * Author: Mark
 * Date  : 2015/2/28
 * Time  : 22:02
 */
public class SelectionSort<Item extends Comparable<? super Item>> {

    public static void main(String[] args) {
        Integer[] items = new Integer[10];
        Random random = new Random(47);
        for (int i = 0; i < 10; i++) {
            items[i] = random.nextInt(50);
        }
        printArray(items);
        new SelectionSort<Integer>().sort(items, 0, items.length - 4);
        printArray(items);
    }

    private static void printArray(Comparable[] items) {
        for (Comparable i : items) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    public void sort(Item[] items, int low, int high) {
        if (low < 0 || high > items.length || low > high) {
            throw new IllegalArgumentException();
        }
        for (int i = low; i < high; i++) {
            Item min = items[i];
            int minIndex = i;
            for (int j = i + 1; j < high; j++) {
                if (items[j].compareTo(min) < 0) {
                    min = items[j];
                    minIndex = j;
                }
            }
            if (i != minIndex) {
                swap(items, i, minIndex);
            }
        }
    }

    private void swap(Item[] items, int i, int j) {
        Item tmp = items[i];
        items[i] = items[j];
        items[j] = tmp;
    }

}
