/**
 * Author: Mark
 * Date  : 2015/1/27
 * Time  : 19:21
 */
public class PercolationStats {

    private double[] fra;
    private double mean;
    private double stddev;
    private double confidenceLo;
    private double confidenceHi;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        fra = new double[T];
        test(N);
        double sum = 0;
        for (double aFra : fra) {
            sum += aFra;
        }
        mean = sum / T;
        sum = 0;
        for (double aFra : fra) {
            sum += (aFra - mean) * (aFra - mean);
        }
        stddev = Math.sqrt(sum / (T - 1));
        double tmp = 1.96 * stddev / Math.sqrt(T);
        confidenceLo = mean - tmp;
        confidenceHi = mean + tmp;
    }

    private void test(int N) {
        for (int i = 0; i < fra.length; i++) {
            Percolation p = new Percolation(N);
            int open = 0;
            while (!p.percolates()) {
                int j = StdRandom.uniform(N)+1, k = StdRandom.uniform(N)+1;
                while (p.isOpen(j, k)) {
                    j = StdRandom.uniform(N)+1;
                    k = StdRandom.uniform(N)+1;
                }
                p.open(j, k);
                open++;
            }
            fra[i] = open*1.0/(N*N);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return confidenceLo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return confidenceHi;
    }

    // test client (described below)
    public static void main(String[] args) {
        PercolationStats ps = new PercolationStats(200, 100);
        System.out.println(ps.mean());
        System.out.println(ps.stddev());
        System.out.println(ps.confidenceHi());
        System.out.println(ps.confidenceLo());
    }
}
