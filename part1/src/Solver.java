import java.util.Comparator;

/**
 * Author: Mark
 * Date  : 2015/3/4
 * Time  : 19:53
 */
public class Solver {

    private int moves = -1;
    private Stack<Board> steps;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        MinPQ<SearchNode> pqInit = new MinPQ<>(new SearchNodeComparator());
        MinPQ<SearchNode> pqTwin = new MinPQ<>(new SearchNodeComparator());
        pqInit.insert(new SearchNode(initial, null, 0));
        pqTwin.insert(new SearchNode(initial.twin(), null, 0));
        SearchNode initTmp, twinTmp;
        boolean isSolvable = true;
        int initMoves = 0, twinMoves = 0;
        while (true) {
            initTmp = pqInit.delMin();
            if (initTmp.board.isGoal()) {
                break;
            } else {
                Iterable<Board> it = initTmp.board.neighbors();
//                initMoves++;
                initMoves = initTmp.moves + 1;
                for (Board b : it) {
                    if (initTmp.pre == null) {
                        pqInit.insert(new SearchNode(b, initTmp, initMoves));
                    } else {
                        if (!b.equals(initTmp.pre.board)) {
                            pqInit.insert(new SearchNode(b, initTmp, initMoves));
                        }
                    }
                }
            }
            twinTmp = pqTwin.delMin();
            if (twinTmp.board.isGoal()) {
                isSolvable = false;
                break;
            } else {
                Iterable<Board> it = twinTmp.board.neighbors();
//                twinMoves++;
                twinMoves = twinTmp.moves + 1;
                for (Board b : it) {
                    if (twinTmp.pre == null) {
                        pqTwin.insert(new SearchNode(b, twinTmp, twinMoves));
                    } else {
                        if (!b.equals(twinTmp.pre.board)) {
                            pqTwin.insert(new SearchNode(b, twinTmp, twinMoves));
                        }
                    }
                }
            }
        }
        if (isSolvable) {
            steps = new Stack<>();
            SearchNode cur = initTmp;
            int ms = -1; // initial board 不算
            while (cur != null) {
                ms++;
                steps.push(cur.board);
                cur = cur.pre;
            }
            moves = ms;
        }
    }



    // is the initial board solvable?
    public boolean isSolvable() {
        return moves != -1;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return steps;
    }

    private class SearchNodeComparator implements Comparator<SearchNode> {

        @Override
        public int compare(SearchNode o1, SearchNode o2) {
            if (o1.equals(o2)) return 0;
            if (o1.board.manhattan() + o1.moves == o2.board.manhattan() + o2.moves) {
                return o1.moves < o2.moves ? -1 : 1;
            }
            return o1.board.manhattan() + o1.moves < o2.board.manhattan() + o2.moves ? -1 : 1;
        }
    }

    private class SearchNode {
        Board board;
        SearchNode pre;
        int moves;

        public SearchNode(Board board, SearchNode pre, int moves) {
            this.board = board;
            this.pre = pre;
            this.moves = moves;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof SearchNode)) return false;

            SearchNode that = (SearchNode) o;

            if (board != null ? !board.equals(that.board) : that.board != null) return false;
            if (moves != that.moves) return false;
            return true;
        }

    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
