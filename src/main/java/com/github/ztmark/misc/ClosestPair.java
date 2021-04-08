package com.github.ztmark.misc;

import java.util.Arrays;
import java.util.Comparator;

public class ClosestPair {

    private Point point1;
    private Point point2;
    private double distance;

    public ClosestPair(Point[] points) {
        if (points == null || points.length <= 1) {
            return;
        }

        // check if two point is equal
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].equals(points[i + 1])) {
                point1 = points[i];
                point2 = points[i+1];
                return;
            }
        }

        // 将点根据x排序
        Point[] orderByX = new Point[points.length];
        Point[] orderByY = new Point[points.length];
        System.arraycopy(points, 0, orderByX, 0, points.length);
        Arrays.sort(orderByX, Point.comparator);
        System.arraycopy(orderByX, 0, orderByY, 0, orderByX.length);

        closest(orderByX, orderByY, new Point[points.length], 0, points.length-1);

    }


    // 该方法结束后，orderByY 的 lo 到 hi 是以及根据 y 排好序的
    private double closest(Point[] orderByX, Point[] orderByY, Point[] aux, int lo, int hi) {
        if (lo >= hi) {
            return Double.POSITIVE_INFINITY;
        }
        // 将
        int mid = lo + (hi - lo) / 2;
        Point median = orderByX[mid];

        // 找到左右两边的最小距离
        double leftMin = closest(orderByX, orderByY, aux, lo, mid);
        double rightMin = closest(orderByX, orderByY, aux, mid + 1, hi);
        // orderByY 两边分别是排好序的

        // 两者最短的那个
        double delta = Math.min(leftMin, rightMin);

        merge(orderByY, aux, lo, mid, hi); // orderByY lo 到 hi 排好序了

        // 找到 median 两边 delta 距离内的点
        int m = 0;
        for (int i = lo; i <= hi; i++) {
            if (Math.abs(median.x - orderByY[i].x) < delta) {
                aux[m++] = orderByY[i];
            }
        }

        // aux[0..m] 是根据 y 排好序的
        for (int i = 0; i < m; i++) {
            for (int j = i+1; j < m && (aux[j].y - aux[i].y < delta); j++) {
                double dist = aux[j].distantTo(aux[i]);
                if (dist < delta) {
                    delta = dist;
                    if (dist < this.distance) {
                        this.distance = dist;
                        this.point1 = aux[j];
                        this.point2 = aux[i];
                    }
                }

            }
        }

        return delta;
    }

    private void merge(Point[] points, Point[] aux, int lo, int mid, int hi) {
        System.arraycopy(points, lo, aux, lo, hi-lo+1);
        int i = lo;
        int j = mid+1;
        for (int k=lo; k<=hi; k++) {
            if (i > mid) {
                points[k]=aux[i++];
            } else if (j > hi) {
                points[k] = aux[j++];
            } else if (Point.comparatorY.compare(aux[i], aux[j]) < 0) {
                points[k] = aux[i++];
            } else {
                points[k] = aux[j++];
            }
        }
    }

    private static class Point  {
        static Comparator<Point> comparator = Comparator.comparing(Point::getX).thenComparing(Point::getY);
        static Comparator<Point> comparatorX = Comparator.comparing(Point::getX);
        static Comparator<Point> comparatorY = Comparator.comparing(Point::getY);

        int x,y;

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public double distantTo(Point point) {
            int dx = this.x - point.x;
            int dy = this.y - point.y;
            return Math.sqrt(dx * dx + dy * dy);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Point point = (Point) o;

            if (x != point.x) return false;
            return y == point.y;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }
    }

}
