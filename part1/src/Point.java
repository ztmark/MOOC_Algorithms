/*************************************************************************
 * Name:
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new PointComparator();       // YOUR DEFINITION HERE

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        /* YOUR CODE HERE */
        int diffX = that.x - this.x;
        int diffY = that.y - this.y;
        if (diffX == 0) {
            if (diffY > 0) {
                return Double.POSITIVE_INFINITY;
            } else {
                return Double.NEGATIVE_INFINITY;
            }
        } else if (diffY == 0) {
            return 0;
        } else {
            return diffY * 1.0 / diffX;
        }
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        /* YOUR CODE HERE */
        if (this.y < that.y) {
            return -1;
        } else if (this.y > that.y) {
            return 1;
        } else if (this.x < that.x) {
            return -1;
        } else if (this.x > that.x) {
            return 1;
        } else {
            return 0;
        }
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }


    private class PointComparator implements Comparator<Point> {

        @Override
        public int compare(Point o1, Point o2) {
            Double slope1 = slopeTo(o1);
            Double slope2 = slopeTo(o2);
            return slope1.compareTo(slope2);
        }
    }

    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
        /*Point p1 = new Point(5, 6);
        Point p2 = new Point(5, 8);
        Point p3 = new Point(6, 9);
        Point p4 = new Point(12, 9);
        System.out.println(p1.slopeTo(p2));
        System.out.println(p1.slopeTo(p3));
        System.out.println(p1.slopeTo(p4));
        System.out.println(p2.slopeTo(p1));
        System.out.println(p2.slopeTo(p3));
        System.out.println(p2.slopeTo(p4));
        System.out.println(p3.slopeTo(p4));
        System.out.println(p4.slopeTo(p3));*/
        double d1 = Double.NEGATIVE_INFINITY;
        double d2 = Double.POSITIVE_INFINITY;
        double d3 = 5.6;
        double d4 = 5.59;
        System.out.println(d1 > d2);
        System.out.println(d1 == Double.NEGATIVE_INFINITY);
        System.out.println(d1 > d3);
        System.out.println(d1 > d4);
        System.out.println(d2 > d3);
        System.out.println(d2 > d4);
        System.out.println(d3 > d4);
    }
}