/**
 * Author: Mark
 * Date  : 2015/5/20
 * Time  : 16:38
 */
public class MoveToFront {

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        int[] acs = new int[256];
        for (int i = 0; i < acs.length; i++) {
            acs[i] = i;
        }
        while (!BinaryStdIn.isEmpty()) {
            int b =  (BinaryStdIn.readByte() & 0xff);
            int c = acs[0];
            int i = 0;
            for (i = 1; i < acs.length; i++) {
                if (c != b) {
                    int tmp = acs[i];
                    acs[i] = c;
                    c = tmp;
                } else {
                    BinaryStdOut.write(i - 1, 8);
                    acs[0] = c;
                    break;
                }
            }
            if (i == acs.length) {
                BinaryStdOut.write(i - 1, 8);
                acs[0] = c;
            }
            BinaryStdOut.flush();
        }
        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        int[] acs = new int[256];
        for (int i = 0; i < acs.length; i++) {
            acs[i] = i;
        }
        char[] str = BinaryStdIn.readString().toCharArray();
        for (char b : str) {
            int c = acs[b];
            BinaryStdOut.write(c, 8);
            BinaryStdOut.flush();
            System.arraycopy(acs, 0, acs, 1, b);
            acs[0] = c;
        }
        BinaryStdOut.close();
    }

    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
    public static void main(String[] args) {
        if      (args[0].equals("-")) encode();
        else if (args[0].equals("+")) decode();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}
