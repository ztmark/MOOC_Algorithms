package week1;

/**
 * Author: Mark
 * Date  : 2015/1/25
 * Time  : 20:12
 *
 * WeightedQuickUnionWithPathCompressionUF
 *
 */
public class WQUPCUF {

    private int[] id;
    private int[] size;

    public WQUPCUF(int n) {
        id = new int[n];
        size = new int[n];
        for (int i = 0; i < n; i++) {
            id[i] = i;
            size[i] = 1;
        }
    }

    public boolean conected(int p, int q) {
        return root(p) == root(q);
    }

    private int root(int i) {
        while (i != id[i]) {
            id[i] = id[id[i]]; // 小改进 让i指向爷爷
            i = id[i];
        }
        return i;
    }

    public void union(int p, int q) {
        if (!conected(p, q)) {
            int pr = root(p);
            int qr = root(q);
            if (size[pr] > size[qr]) {
                id[qr] = pr;
                size[pr] += size[qr];
            } else {
                id[pr] = qr;
                size[qr] += size[pr];
            }
        }
    }
}
