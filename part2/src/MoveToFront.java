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
            int b = BinaryStdIn.readByte() & 0xff;
            int c = acs[0];
            for (int i = 1; i < acs.length; i++) {
                if (c != b) {
                    c = acs[i];
                    acs[i] = c;
                } else {
                    BinaryStdOut.write((i - 1), 8);
                    acs[0] = c;
                    break;
                }
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
        while (!BinaryStdIn.isEmpty()) {
            int b = BinaryStdIn.readByte() & 0xff;
            int c = acs[b];
            BinaryStdOut.write(c, 8);
            BinaryStdOut.flush();
            for (int i = b; i > 0; i--) {
                acs[i] = acs[i - 1];
            }
            acs[0] = c;
        }
        BinaryStdOut.close();
    }

    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
    public static void main(String[] args) {
        encode();
//        decode();
    }
}
