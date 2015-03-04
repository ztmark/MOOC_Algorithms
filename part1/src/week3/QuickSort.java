package week3;

import java.util.Random;

/**
 * Author: Mark
 * Date  : 2015/3/3
 * Time  : 22:25
 */
public class QuickSort<Item extends Comparable<? super Item>> {

    public static void main(String[] args) {
        Integer[] items = new Integer[10];
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < 10; i++) {
            items[i] = random.nextInt(100);
        }
        printArray(items);
        new QuickSort<Integer>().sort(items, 0, items.length - 1);
        printArray(items);
    }

    private static void printArray(Comparable[] items) {
        for (Comparable i : items) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    public void sort(Item[] items, int low, int high) {
        if (outOfRange(items, low, high)) {
            throw new IllegalArgumentException();
        }
        if (high <= low) return;
        int p = partition(items, low, high);
        sort(items, low, p - 1);
        sort(items, p + 1, high);
    }

    private int partition(Item[] items, int low, int high) {
        selectPivot(items, low, high);
        Item pivot = items[low];
        int i = low, j = high + 1;
        while (true) {
            while (pivot.compareTo(items[++i]) > 0) {
                if (i == high) break;
            }
            while (pivot.compareTo(items[--j]) < 0) {
                if (j == low) break;
            }
            if (i >= j) break;
            swap(items, i, j);
        }
        swap(items, low, j);
        return j;
    }

    // 将最低位 mid 最高位 三个位置的元素的中值放置到第一个位置
    private void selectPivot(Item[] items, int low, int high) {
        int mid = low + (high - low) / 2;
        if (items[low].compareTo(items[mid]) > 0) {
            swap(items, low, mid);
        }
        if (items[mid].compareTo(items[high]) > 0) {
            swap(items, mid, high);
        }
        if (items[low].compareTo(items[mid]) < 0) {
            swap(items, low, mid);
        }
    }

    private boolean outOfRange(Item[] items, int low, int high) {
        return low < 0 || high >= items.length;
    }

    private void swap(Item[] items, int i, int j) {
        Item tmp = items[i];
        items[i] = items[j];
        items[j] = tmp;
    }

}
