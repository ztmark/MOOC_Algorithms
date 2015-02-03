package week1;

/**
 * Author: Mark
 * Date  : 2015/1/25
 * Time  : 19:55
 */
public class QuickUnionUF {
    private int[] id;

    public QuickUnionUF(int n) {
        id = new int[n];
        for (int i = 0; i < n; i++) {
            id[i] = i;
        }
    }

    public boolean conected(int p, int q) {
        return root(p) == root(q);
    }

    private int root(int p) {
        while (id[p] != p) {
            p = id[p];
        }
        return p;
    }

    public void union(int p, int q) {
        if (!conected(p, q)) {
            int qr = root(q);
            int pr = root(p);
            id[pr] = qr;
        }
    }
}
