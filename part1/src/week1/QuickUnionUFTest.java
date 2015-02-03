package week1;

import java.util.Random;

/**
 * Author: Mark
 * Date  : 2015/1/25
 * Time  : 19:58
 */
public class QuickUnionUFTest {
    public static void main(String[] args) {
        final int size = 100_000;
        QuickUnionUF uf = new QuickUnionUF(size);
        Random random = new Random(71);
        long start = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            uf.union(random.nextInt(size), random.nextInt(size));
        }
        long duration = System.currentTimeMillis() - start;
        System.out.println(duration); // 3 seconds
        start = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            uf.conected(random.nextInt(size), random.nextInt(size));
        }
        duration = System.currentTimeMillis() - start;
        System.out.println(duration); // 13 seconds
    }
}
