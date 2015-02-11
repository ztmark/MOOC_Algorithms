/**
 * Author: Mark
 * Date  : 2015/2/11
 * Time  : 16:20
 */
public class Subset {
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            rq.enqueue(StdIn.readString());
        }
        int k = Integer.valueOf(args[0]);
        while (k-- != 0) {
            System.out.println(rq.dequeue());
        }
    }
}
