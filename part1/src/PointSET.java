import java.util.*;

/**
 * Author: Mark
 * Date  : 2015/3/16
 * Time  : 15:19
 */
public class PointSET {

    private TreeSet<Point2D> points;

    // construct an empty set of points
    public PointSET() {
        points = new TreeSet<>(new PointComparator());
    }

    // is the set empty?
    public boolean isEmpty() {
        return points.isEmpty();
    }

    // number of points in the set
    public int size() {
        return points.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new NullPointerException();
        points.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new NullPointerException();
        return points.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D point : points) {
            point.draw();
        }
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new NullPointerException();
        List<Point2D> list = new ArrayList<>(points.size());
        for (Point2D point : points) {
            if (rect.contains(point)) {
                list.add(point);
            }
        }
        return list;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new NullPointerException();
        if (isEmpty()) return null;
        Iterator<Point2D> iter = points.iterator();
        Point2D nearest = null;
        double distance = 0.0;
        if (iter.hasNext()) {
            nearest = iter.next();
            distance = p.distanceSquaredTo(nearest);
        }
        Point2D tmp = null;
        double dist = 0.0;
        while (iter.hasNext()) {
            tmp = iter.next();
            dist = p.distanceSquaredTo(tmp);
            if (dist < distance) {
                distance = dist;
                nearest = tmp;
            }
        }
        return nearest;
    }

    private class PointComparator implements Comparator<Point2D> {

        @Override
        public int compare(Point2D o1, Point2D o2) {
            if (o1 == o2) return 0;
            double delta = 1e-11;
            double y = Math.abs(o1.y() - o2.y());
            double x = Math.abs(o1.x() - o2.x());
            if (y < delta && x < delta) return 0;
            if (o1.y() < o2.y()) return -1;
            if (o1.y() > o2.y()) return 1;
            if (o1.x() < o2.x()) return -1;
            if (o1.x() > o2.x()) return 1;
            return 0;
        }
    }


    // unit testing of the methods (optional)
    public static void main(String[] args) {
        In in = new In(args[0]);
        PointSET ps = new PointSET();
        while (!in.isEmpty()) {
            ps.insert(new Point2D(in.readDouble(), in.readDouble()));
        }
        System.out.println(ps.isEmpty());
        System.out.println(ps.size());
        for (int i = 1; i < 10; i++) {
            System.out.println(ps.contains(new Point2D(0.1 * i, 0.5)));
        }
        Iterable<Point2D> iterable = ps.range(new RectHV(0.1, 0.1, 0.5, 0.5));
        for (Point2D point2D : iterable) {
            System.out.println(point2D);
        }
        System.out.println(ps.nearest(new Point2D(0.2, 0.6)));
        ps.draw();
    }

}
