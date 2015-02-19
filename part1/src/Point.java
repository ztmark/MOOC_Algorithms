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
        if (that == null) {
            throw new NullPointerException();
        }
        int diffX = that.x - this.x;
        int diffY = that.y - this.y;
        if (diffX == 0 && diffY == 0) {
            return Double.NEGATIVE_INFINITY;
        } else if (diffX == 0) {
            return Double.POSITIVE_INFINITY;
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
        if (that == null) {
            throw new NullPointerException();
        }
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
        Point p = new Point(214, 207);
        Point q = new Point(214, 21);
        System.out.println(p.slopeTo(q));
    }
}