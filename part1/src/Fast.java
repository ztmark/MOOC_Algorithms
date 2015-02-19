import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Author: Mark
 * Date  : 2015/2/19
 * Time  : 15:40
 */
public class Fast {



    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            points[i] = new Point(in.readInt(), in.readInt());
        }
        HashMap<Point, Point> map = new HashMap<>();
        HashSet<String> set = new HashSet<>(n);
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (int i = 0; i < n; i++) {
            Point p = points[i];
            if (!set.contains(p.toString())) {
                p.draw();
                set.add(p.toString());
            } else {
                continue;
            }
            Point[] otherPoint = new Point[n - 1 - i];
            System.arraycopy(points, i+1, otherPoint, 0, n - i - 1);
            Arrays.sort(otherPoint, p.SLOPE_ORDER);
            int low = 0, high = 0;
            while (high < n - i - 1) {
                while (high < n - i - 1 && p.SLOPE_ORDER.compare(otherPoint[high], otherPoint[low]) == 0) {
                    high++;
                }
                if (high - low > 2) {
                    draw(otherPoint, p, low, high, map);
                }
                low = high;
            }
        }
    }

    private static void draw(Point[] points, Point p, int lo, int hi, HashMap<Point, Point> map) {


        Point[] line = new Point[hi - lo + 1];
        line[0] = p;
        for (int i = lo, j = 1; i < hi; i++, j++) {
            line[j] = points[i];
        }
        Arrays.sort(line);
        if (map.get(line[0]) != null && map.get(line[0]).toString().equals(line[hi - lo].toString())) {
            return;
        }
        line[0].drawTo(line[hi - lo]);
        System.out.print(line[0]);
        for (int i = 1; i < line.length; i++) {
            System.out.print(" -> ");
            System.out.print(line[i]);
            line[i-1].drawTo(line[i]);
        }
        System.out.println();
        map.put(line[0], line[hi - lo]);
    }
}
