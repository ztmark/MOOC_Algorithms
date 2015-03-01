package week2;

import java.util.Random;

/**
 * Author: Mark
 * Date  : 2015/2/28
 * Time  : 22:39
 */
public class InsertionSort<Item extends Comparable<? super Item>> {

    public static void main(String[] args) {
        Integer[] items = new Integer[10];
        Random random = new Random(47);
        for (int i = 0; i < 10; i++) {
            items[i] = random.nextInt(50);
        }
        printArray(items);
        new InsertionSort<Integer>().sort(items, 0, items.length);
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
        for (int i = low + 1; i < high; i++) {
            Item cur = items[i];
            int j = i - 1;
            while (j >= low && items[j].compareTo(cur) > 0) {
                items[j + 1] = items[j];
                j--;
            }
            items[j + 1] = cur;
        }
    }

}
