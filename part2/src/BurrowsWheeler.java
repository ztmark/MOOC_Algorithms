import java.util.Arrays;

/**
 * Author: Mark
 * Date  : 2015/5/20
 * Time  : 14:12
 */
public class BurrowsWheeler {

    // apply Burrows-Wheeler encoding, reading from standard input and writing to standard output
    public static void encode() {
        String s = BinaryStdIn.readString();
//        String s = "ABRACADABRA!";
        CircularSuffixArray csa = new CircularSuffixArray(s);
        int first = 0;
        for (int i = 0; i < s.length(); i++) {
            if (compare(s, csa.index(i)) == 0) {
                first = i;
                break;
            }
        }
        BinaryStdOut.write(first);
        for (int i = 0; i < s.length(); i++) {
            BinaryStdOut.write(getChar(s, csa.index(i) + s.length() - 1), 8);
        }
        BinaryStdOut.close();
    }


    private static int compare(String str, int i) {
        for (int k = 0; k < str.length(); k++) {
            if (getChar(str, i + k) > str.charAt(k)) {
                return 1;
            } else if (getChar(str, i + k) < str.charAt(k)) {
                return -1;
            }
        }
        return 0;
    }

    private static char getChar(String str, int i) {
        if (i >= str.length()) {
            i -= str.length();
        }
        return str.charAt(i);
    }

    // apply Burrows-Wheeler decoding, reading from standard input and writing to standard output
    public static void decode() {
        int first = BinaryStdIn.readInt();
        String s = BinaryStdIn.readString();
//        int first = 3;
//        String s = "ARD!RCAAAABB";
        int[] next = new int[s.length()];
        char[] t = s.toCharArray();
        int[] aph = new int[256];
        for (char a : t) {
            aph[a]++;
        }
        for (int i = 1; i < aph.length; i++) {
            aph[i] += aph[i - 1];
        }
        System.arraycopy(aph, 0, aph, 1, aph.length - 1);
        aph[0] = 0;
        for (int i = 0; i < t.length; i++) {
            next[aph[t[i]]] = i;
            aph[t[i]]++;
        }

        Arrays.sort(t);

        for (int i = 0, n = first; i < next.length; i++) {
            BinaryStdOut.write(t[n]);
            n = next[n];
        }
        BinaryStdOut.close();
    }

    // if args[0] is '-', apply Burrows-Wheeler encoding
    // if args[0] is '+', apply Burrows-Wheeler decoding
    public static void main(String[] args) {
        if      (args[0].equals("-")) encode();
        else if (args[0].equals("+")) decode();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}
