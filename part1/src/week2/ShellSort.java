package week2;

import java.util.Random;

/**
 * Author: Mark
 * Date  : 2015/3/2
 * Time  : 19:41
 */
public class ShellSort<Item extends Comparable<? super Item>> {


    public static void main(String[] args) {
        Integer[] items = new Integer[10];
        Random random = new Random(47);
        for (int i = 0; i < 10; i++) {
            items[i] = random.nextInt(50);
        }
        printArray(items);
        new ShellSort<Integer>().sort(items, 0, items.length);
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
        int gap = (high - low) / 2;
        for (int g = gap; g > 0; g--) {
            for (int i = low + g; i < high; i += g) {
                Item cur = items[i];
                int j = i - g;
                while (j >= low && items[j].compareTo(cur) > 0) {
                    items[j + g] = items[j];
                    j -= g;
                }
                items[j + g] = cur;
            }
        }
    }

}
