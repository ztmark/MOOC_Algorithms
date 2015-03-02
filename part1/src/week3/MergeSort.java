package week3;

import java.util.Random;

/**
 * Author: Mark
 * Date  : 2015/3/2
 * Time  : 20:47
 */
public class MergeSort<Item extends Comparable<? super Item>> {

    public static void main(String[] args) {
        Integer[] items = new Integer[10];
        Random random = new Random(47);
        for (int i = 0; i < 10; i++) {
            items[i] = random.nextInt(50);
        }
        printArray(items);
        new MergeSort<Integer>().sort(items, 0, items.length);
        printArray(items);
    }

    private static void printArray(Comparable[] items) {
        for (Comparable i : items) {
            System.out.print(i + " ");
        }
        System.out.println();
    }


    @SuppressWarnings("unchecked")
    public void sort(Item[] items, int low, int high) {
        Item[] tmp = (Item[]) new Comparable[items.length];
        mergeSort(items, tmp, low, high);
    }

    private void mergeSort(Item[] items, Item[] tmp, int low, int high) {
        if (high - low <= 1) return;
        int mid = low + (high - low) / 2;
        mergeSort(items, tmp, low, mid);
        mergeSort(items, tmp, mid, high);
        merge(items, tmp, low, mid, high);
    }

    private void merge(Item[] items, Item[] tmp, int low, int mid, int high) {
        System.arraycopy(items, low, tmp, low, high - low);
        int i = low, j = mid;
        for (int k = low; k < high; k++) {
            if (i >= mid) items[k] = tmp[j++];
            else if (j >= high) items[k] = tmp[i++];
            else if (tmp[i].compareTo(tmp[j]) < 0) items[k] = tmp[i++];
            else items[k] = tmp[j++];
        }
    }

}
