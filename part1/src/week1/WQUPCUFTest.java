package week1;

import java.util.Random;

/**
 * Author: Mark
 * Date  : 2015/1/25
 * Time  : 20:21
 *
 * WeightedQuickUnionWithPathCompressionUFTest
 *
 */
public class WQUPCUFTest {
    public static void main(String[] args) {
        final int size = 100_000;
        WQUPCUF uf = new WQUPCUF(size);
        Random random = new Random(71);
        long start = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            uf.union(random.nextInt(size), random.nextInt(size));
        }
        long duration = System.currentTimeMillis() - start;
        System.out.println(duration); // 32 milli seconds
        start = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            uf.conected(random.nextInt(size), random.nextInt(size));
        }
        duration = System.currentTimeMillis() - start;
        System.out.println(duration); // 14 milli seconds
    }
}
