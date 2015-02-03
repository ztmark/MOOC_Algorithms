/**
 * Author: Mark
 * Date  : 2015/1/30
 * Time  : 11:53
 */
public class Percolation {
    /*
    使用两个UF的目的就是为了解决 backwash 的问题

    backwash的问题就是
    由于有top和bottom，一开始，第一行都和top是connected，最后一行都和bottom是connected，
    如果最后一行有个节点是和full的，那么，最后一行的所有open节点由于都和bottom节点connected，
    那么，所有与最后一行的open节点connected的节点都是full的了，尽管这些节点其实没有full

     */
    private WeightedQuickUnionUF UF; // 有 top 和 bottom
    private WeightedQuickUnionUF simpleUF; // 只有 top
    private boolean[] isOpen;
    private int N;

    // create N-by-N grid, with all sites blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        this.N = N;
        UF = new WeightedQuickUnionUF(N * N + 2);
        simpleUF = new WeightedQuickUnionUF(N * N + 1);
        isOpen = new boolean[N * N + 2];
        isOpen[0] = true;
        isOpen[N * N + 1] = true;
        for (int i = 1; i < N * N + 1; i++) {
            isOpen[i] = false;
        }
        for (int i = 1; i <= N; i++) {
            UF.union(0, i);
            simpleUF.union(0, i);
        }
        for (int i = N * (N - 1) + 1; i <= N * N; i++) {
            UF.union(N * N + 1, i);
        }
    }

    // open site (row i, column j) if it is not open already
    public void open(int i, int j) {
        if (!inRange(i, j)) {
            throw new IndexOutOfBoundsException();
        }
        if (isOpen(i, j)) {
            return;
        }
        int p = convert(i, j);
        isOpen[p] = true;
        if (N == 1 && p == 1) { // 针对只有一个节点的情况
            UF.union(0, 1);
            UF.union(0, 2);
            simpleUF.union(0, 1);
            return;
        }
        if (inRange(i-1, j) && isOpen(i-1, j)) { // up
            UF.union(convert(i - 1, j), p);
            simpleUF.union(convert(i-1, j), p);
        }
        if (inRange(i+1, j) && isOpen(i+1, j)) { // down
            UF.union(convert(i+1, j), p);
            simpleUF.union(convert(i + 1, j), p);
        }
        if (inRange(i, j-1) && isOpen(i, j-1)) { // left
            UF.union(convert(i, j-1), p);
            simpleUF.union(convert(i, j - 1), p);
        }
        if (inRange(i, j+1) && isOpen(i, j+1)) { // right
            UF.union(convert(i, j+1), p);
            simpleUF.union(convert(i, j+1), p);
        }
    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        if (!inRange(i, j)) {
            throw new IndexOutOfBoundsException();
        }
        return isOpen[convert(i, j)];
    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        if (!inRange(i, j)) {
            throw new IndexOutOfBoundsException();
        }
        return isOpen(i, j) && simpleUF.connected(0, convert(i, j));
    }

    // does the system percolate?
    public boolean percolates() {
        return N == 1 ? isOpen[1] : UF.connected(0, N * N + 1);
    }

    private boolean inRange(int i, int j) {
        return i > 0 && i <= N && j > 0 && j <= N;
    }

    private int convert(int i, int j) {
        return (i - 1) * N + j;
    }

    // test client (optional)
    public static void main(String[] args) {
        /*Percolation percolation = new Percolation(1);
        percolation.open(1, 1);
        System.out.println(percolation.percolates());*/
//        In in = new In(args[0]);      // input file
//        int N = in.readInt();         // N-by-N percolation system


        // repeatedly read in sites to open and draw resulting system
        Percolation perc = new Percolation(1);
        //perc.open(1, 1);
        System.out.println(perc.percolates());
    }

}
