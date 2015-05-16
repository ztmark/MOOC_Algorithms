import java.util.ArrayList;
import java.util.List;

/**
 * Author: Mark
 * Date  : 2015/5/9
 * Time  : 21:00
 */
public class BoggleSolver {

    private TST<Integer> dict;

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
//        dict = new TrieST<>();
//        dict = new SET<>();
        dict = new TST<>();
        for (String w : dictionary) {
            dict.put(w, 1);
//            dict.add(w);
        }
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {

        List<String> words = new ArrayList<>();
        boolean[][] marked = new boolean[board.rows()][board.cols()];
        for (int i = 0; i < board.rows(); i++) {
            for (int j = 0; j < board.cols(); j++) {
                getFrom(board, i, j, marked, "", words);
            }
        }
        return words;
    }

    private void getFrom(BoggleBoard board, int row, int col, boolean[][] marked, String word, List<String> words) {
        int[] dir = {-1, 0, 1};
        marked[row][col] = true;

        String curWord = word + board.getLetter(row, col);
        if (board.getLetter(row, col) == 'Q') {
            curWord += "U";
        }
        if (curWord.length() > 2 && dict.contains(curWord) && !words.contains(curWord)) {
            words.add(curWord);
        }

        if (!dict.keysWithPrefix(curWord).iterator().hasNext()) {
            marked[row][col] = false;
            return;
        }

        for (int i = 0; i < 3; i++) {
            int nextI = row + dir[i];
            if (!isValidRow(nextI, board)) continue;
            for (int j = 0; j < 3; j++) {
                int nextJ = col + dir[j];
                if (!isValidCol(nextJ, board)) continue;
                if (marked[nextI][nextJ]) continue;
                getFrom(board, nextI, nextJ, marked, curWord, words);
            }
        }

        marked[row][col] = false;

    }


    private boolean isValidRow(int row, BoggleBoard board) {
        if (row < 0 || board.rows() <= row) return false;
        return true;
    }

    private boolean isValidCol(int col, BoggleBoard board) {
        if (col < 0 || board.cols() <= col) return false;
        return true;
    }

    private int merge(int i, int j, int cols) {
        return i * cols + j;
    }

    private int extractRow(int n, int cols) {
        return (n - n % cols) / cols;
    }

    private int extractCol(int n, int cols) {
        return n % cols;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (!dict.contains(word)) return 0;
        int len = word.length();
        int score = 0;
        switch (len) {
            case 0:
            case 1:
            case 2:
                score = 0; break;
            case 3:
            case 4:
                score = 1; break;
            case 5:
                score = 2; break;
            case 6:
                score = 3; break;
            case 7:
                score = 5; break;
            default:
                score = 11; break;
        }
        return score;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board))
        {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }

}
