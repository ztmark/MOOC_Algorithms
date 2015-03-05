package week3;

import java.util.Random;

/**
 * Author: Mark
 * Date  : 2015/3/4
 * Time  : 23:44
 */
public class Selection<Item extends Comparable<? super Item>> {

    public static void main(String[] args) {
        Integer[] items = new Integer[10];
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < 10; i++) {
            items[i] = random.nextInt(100);
        }
        printArray(items);
        System.out.println(new Selection<Integer>().selectKth(items, 9));
        new QuickSort<Integer>().sort(items, 0, items.length - 1);
        printArray(items);
    }

    private static void printArray(Comparable[] items) {
        for (Comparable i : items) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    @SuppressWarnings("unchecked")
    public Item selectKth(Item[] items, int k) {
        return selectKth(items, 0, items.length - 1, k);
    }

    private Item selectKth(Item[] items, int s, int e, int k) {
        if (s < 0 || e >= items.length || k < 0 || k > (e - s + 1)) {
            throw new IllegalArgumentException();
        }
        int lo = s, hi = e;
        while (hi > lo) { // 个数大于1的时候
            int p = partition(items, lo, hi);
            if (p > k) {
                hi = p - 1;
            } else if (p < k) {
                lo = p + 1;
            } else {
                return items[k];
            }
        }
        return items[k]; // 个数只有一个时，那这个就是
    }

    private int partition(Item[] items, int s, int e) {
        int pivot = median3(items, s, e);
        swap(items, s, pivot);
        int i = s, j = e + 1;
        while (true) {
            while (items[s].compareTo(items[++i]) > 0) {
                if (i >= j) break;
            }
            while (items[s].compareTo(items[--j]) < 0) {
                if (i >= j) break;
            }
            if (i >= j) break;
            swap(items, i, j);
        }
        swap(items, s, j);
        return j;
    }

    private int median3(Item[] items, int s, int e) {
        int mid = s + (e - s) / 2;
        if (items[s].compareTo(items[mid]) > 0) {
            swap(items, s, mid);
        }
        if (items[mid].compareTo(items[e]) > 0) {
            swap(items, mid, e);
        }
        if (items[s].compareTo(items[mid]) > 0) {
            swap(items, s, mid);
        }
        return mid;
    }

    private void swap(Item[] items, int i, int j) {
        Item tmp = items[i];
        items[i] = items[j];
        items[j] = tmp;
    }


}
