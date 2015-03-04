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
        System.out.println(new Selection<Integer>().selectKth(items, 3));
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

    @SuppressWarnings("unchecked")
    public Item selectKth(Item[] items, int low, int high, int k) {
        Item[] tmp = (Item[]) new Comparable[high - low + 1];
        System.arraycopy(items, low, tmp, 0, high - low + 1);
        int mid = partition(tmp, 0, high - low + 1);
        if (k == mid) return tmp[mid];
        else if (k < mid) {
            return selectKth(tmp, 0, mid - 1, k);
        } else {
            return selectKth(tmp, mid + 1, high, k - mid);
        }
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

    private void swap(Item[] items, int i, int j) {
        Item tmp = items[i];
        items[i] = items[j];
        items[j] = tmp;
    }

}
