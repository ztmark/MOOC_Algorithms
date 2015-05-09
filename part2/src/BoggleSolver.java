/**
 * Author: Mark
 * Date  : 2015/5/9
 * Time  : 21:00
 */
public class BoggleSolver {

    private TrieST<Integer> dict;

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        dict = new TrieST<>();
        for (String w : dictionary) {
            dict.put(w, 1);
        }
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        return null;
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
        In in = new In("dictionary-common.txt");
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        System.out.println(solver.scoreOf("XXXXXXXXXZZZZZ"));
        System.out.println(solver.scoreOf("A"));
        System.out.println(solver.scoreOf("ABC"));
        System.out.println(solver.scoreOf("ABED"));
        System.out.println(solver.scoreOf("ABDUCT"));
        System.out.println(solver.scoreOf("ABERRATE"));
        System.out.println(solver.scoreOf("ABHORRENT"));
    }

}
