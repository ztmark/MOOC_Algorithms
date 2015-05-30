/**
 * Author: Mark
 * Date  : 2015/5/19
 * Time  : 21:40
 */
public class CircularSuffixArray {

    private String str;
    private int[] index;

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s == null) {
            throw new NullPointerException();
        }
        str = s;
        index = new int[str.length()];
        for (int i = 0; i < str.length(); i++) {
            index[i] = i;
        }
        sort(0, str.length() - 1);
    }

    private void sort(int s, int e) {

        if (s >= e) return;

        if (e - s < 10) {
            insertionSort(s, e);
        }

        selectPivot(s, e);

        int low = s + 1, high = e;

        int i = s + 1;
        while (low <= high) {
            int cmp = compare(index[i], index[s]);
            if (cmp <= 0) {
                low++;
                i++;
            } else {
                swap(i, high);
                high--;
            }
        }

        swap(s, high);

        sort(s, high - 1);
        sort(high + 1, e);

    }

    private void insertionSort(int s, int e) {
        for (int i = s + 1; i <= e; i++) {
            int id = index[i];
            char c = getChar(index[i]);
            int j = i - 1;
            for (; j >= 0; j--) {
                if (getChar(index[j]) > c) {
                    index[j + 1] = index[j];
                } else {
                    break;
                }
            }
            index[j + 1] = id;
        }
    }

    private void selectPivot(int s, int e) {
        int m = s + (e - s) / 2;
        if (getChar(s) > getChar(m)) {
            swap(s, m);
        }
        if (getChar(m) > getChar(e)) {
            swap(m, e);
        }
        if (getChar(m) > getChar(s)) {
            swap(s, m);
        }
    }

    private int compare(int i, int j) {
        for (int k = 0; k < str.length(); k++) {
            if (getChar(i + k) > getChar(j + k)) {
                return 1;
            } else if (getChar(i + k) < getChar(j + k)) {
                return -1;
            }
        }
        return 0;
    }

    private char getChar(int i) {
        if (i >= str.length()) {
            i -= str.length();
        }
        return str.charAt(i);
    }

    private void swap(int i, int j) {
        int tmp = index[i];
        index[i] = index[j];
        index[j] = tmp;
    }

    // length of s
    public int length() {
        return str.length();
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        if (i < 0 || str.length() <= i) {
            throw new IndexOutOfBoundsException();
        }
        return index[i];
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        String s = "ABRACADABRA!";
        CircularSuffixArray csa = new CircularSuffixArray(s);
        System.out.println(csa.str);
        for (int i : csa.index) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

}
