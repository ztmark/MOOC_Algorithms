package week1;

import java.util.Random;

/**
 * Author: Mark
 * Date  : 2015/1/25
 * Time  : 19:35
 */
public class QuickFindUFTest {
    public static void main(String[] args) {
        final int size = 100_000;
        QuickFindUF uf = new QuickFindUF(size);
        Random random = new Random(71);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100_000; i++) {
            uf.union(random.nextInt(size), random.nextInt(size));
        }
        long duration = System.currentTimeMillis() - start;
        System.out.println(duration); // 10 seconds
    }
}
