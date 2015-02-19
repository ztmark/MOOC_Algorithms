import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Author: Mark
 * Date  : 2015/2/19
 * Time  : 14:07
 */
public class Brute {

    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            points[i] = new Point(in.readInt(), in.readInt());
        }
//        HashMap<Point, Point> map = new HashMap<>();
        HashSet<String> set = new HashSet<>(n);
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    for (int l = k + 1; l < n; l++) {
                        Point p = points[i];
                        Point q = points[j];
                        Point r = points[k];
                        Point s = points[l];
                        StdDraw.setXscale(0, 32768);
                        StdDraw.setYscale(0, 32768);
                        if (!set.contains(p.toString())) {
                            p.draw();
                            set.add(p.toString());
                        }
                        if (!set.contains(q.toString())) {
                            q.draw();
                            set.add(q.toString());
                        }
                        if (!set.contains(r.toString())) {
                            r.draw();
                            set.add(r.toString());
                        }
                        if (!set.contains(s.toString())) {
                            s.draw();
                            set.add(s.toString());
                        }


                        if (p.SLOPE_ORDER.compare(q, r) == 0
                                && p.SLOPE_ORDER.compare(r, s) == 0) {
                            Point[] four = new Point[4];
                            four[0] = p;
                            four[1] = q;
                            four[2] = r;
                            four[3] = s;
                            Arrays.sort(four);
                            /*if (map.get(four[0]) != null && map.get(four[0]).toString().equals(four[3].toString())) {
                                continue;
                            }
                            map.put(four[0], four[3]);*/


                            four[0].drawTo(four[3]);
                            System.out.print(four[0]);
                            for (int m = 1; m < four.length; m++) {
                                System.out.print(" -> ");
                                System.out.print(four[m]);
                            }
                            System.out.println();
                        }
                    }
                }
            }
        }
    }

}
