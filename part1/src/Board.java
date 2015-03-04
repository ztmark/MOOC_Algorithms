
/**
 * Author: Mark
 * Date  : 2015/3/4
 * Time  : 16:43
 */
public class Board {

    private int[][] blocks;
    private int N;
    private int hamming = -1;
    private int manhattan = -1;

    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        N = blocks.length;
        this.blocks = new int[N][N];
        for (int i = 0; i < N; i++) {
            System.arraycopy(blocks[i], 0, this.blocks[i], 0, N);
        }
    }

    // board dimension N
    public int dimension() {
        return N;
    }

    // number of blocks out of place
    public int hamming() {
        if (hamming == -1) {
            hamming = 0;
            int num = 1;
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (num > N * N - 1) {
                        break;
                    }
                    if (blocks[i][j] != num++) {
                        hamming++;
                    }
                }
            }
        }
        return hamming;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        if (manhattan == -1) {
            manhattan = 0;
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (blocks[i][j] == 0) continue;
                    //i * N + j + 1 = goal[i][j]
                    int bi = (blocks[i][j] - 1) / N;
                    int bj = (blocks[i][j] - 1) % N;
                    manhattan += (Math.abs(bi - i) + Math.abs(bj - j));
                }
            }
        }
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0 || manhattan() == 0;
    }

    // a boadr that is obtained by exchanging two adjacent blocks in the same row
    public Board twin() {
        Board twin = new Board(blocks);
        if (twin.blocks[0][0] != 0 && twin.blocks[0][1] != 0) {
            swap(twin.blocks, 0, 0, 0, 1);
        } else {
            swap(twin.blocks, 1, 0, 1, 1);
        }
        return twin;
    }

    private void swap(int[][] blocks, int ai, int aj, int bi, int bj) {
        int tmp = blocks[ai][aj];
        blocks[ai][aj] = blocks[bi][bj];
        blocks[bi][bj] = tmp;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (this == y) return true;
        if (y == null) return false;
        if (!(y instanceof Board)) {
            return false;
        }
        Board by = (Board) y;
        if (blocks == by.blocks) return true;
        if (N != by.blocks.length) return false;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (blocks[i][j] != by.blocks[i][j]) return false;
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Queue<Board> q = new Queue<>();
        int zi = 0, zj = 0;
        outer:for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (blocks[i][j] == 0) {
                    zi = i;
                    zj = j;
                    break outer;
                }
            }
        }
        if (zi != 0) { // up
            Board up = new Board(blocks);
            swap(up.blocks, zi, zj, zi - 1, zj);
            q.enqueue(up);
        }
        if (zi != N - 1) { // down
            Board down = new Board(blocks);
            swap(down.blocks, zi, zj, zi + 1, zj);
            q.enqueue(down);
        }
        if (zj != 0) { // left
            Board left = new Board(blocks);
            swap(left.blocks, zi, zj, zi, zj - 1);
            q.enqueue(left);
        }
        if (zj != N - 1) { //right
            Board right = new Board(blocks);
            swap(right.blocks, zi, zj, zi, zj + 1);
            q.enqueue(right);
        }
        return q;
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N).append("\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // unit tests (not graded)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        System.out.println(initial.manhattan());
    }

}
