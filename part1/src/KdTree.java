/**
 * Author: Mark
 * Date  : 2015/3/16
 * Time  : 16:21
 */
public class KdTree {

    private Node root;
    private int size;

    // construct an empty set of points
    public KdTree() {
        root = null;
        size = 0;
    }

    // is the set empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new NullPointerException();
        root = insert(null, root, p, true, true);
    }

    /**
     *
     * @param pre 父节点
     * @param cur 当前节点
     * @param p 待插入点
     * @param isEvenLevel 当前节点所在层是否是偶数层
     * @param isLeft 当前节点是否是父节点的左孩子
     * @return 当前节点
     */
    private Node insert(Node pre, Node cur, Point2D p, boolean isEvenLevel, boolean isLeft) {
        if (cur == null) {
            size++;
            Node node = new Node(p, null, null);
            if (pre == null) {
                node.rect = new RectHV(0.0, 0.0, 1.0, 1.0);
            } else {
                if (isEvenLevel) {
                    if (isLeft) {
                        node.rect = new RectHV(pre.rect.xmin(), pre.rect.ymin(), pre.rect.xmax(), pre.p.y());
                    } else {
                        node.rect = new RectHV(pre.rect.xmin(), pre.p.y(), pre.rect.xmax(), pre.rect.ymax());
                    }
                } else {
                    if (isLeft) {
                        node.rect = new RectHV(pre.rect.xmin(), pre.rect.ymin(), pre.p.x(), pre.rect.ymax());
                    } else {
                        node.rect = new RectHV(pre.p.x(), pre.rect.ymin(), pre.rect.xmax(), pre.rect.ymax());
                    }
                }
            }
            return node;
        }
        if (isEqual(cur.p, p)) return cur;
        if (isEvenLevel) {
            if (p.x() < cur.p.x()) {
                cur.lb = insert(cur, cur.lb, p, false, true);
            } else {
                cur.rt = insert(cur, cur.rt, p, false, false);
            }
        } else {
            if (p.y() < cur.p.y()) {
                cur.lb = insert(cur, cur.lb, p, true, true);
            } else {
                cur.rt = insert(cur, cur.rt, p, true, false);
            }
        }
        return cur;
    }


    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new NullPointerException();
        return contains(root, p, true);
    }

    /**
     *
     * @param cur 当前节点
     * @param p 待查找点
     * @param isEvenLevel 当前节点所在的层是否是偶数层
     * @return boolean 是否存在
     */
    private boolean contains(Node cur, Point2D p, boolean isEvenLevel) {
        if (cur == null) return false;
        if (isEqual(p, cur.p)) return true;
        if (isEvenLevel) {
            if (p.x() < cur.p.x()) {
                return contains(cur.lb, p, false);
            } else {
                return contains(cur.rt, p, false);
            }
        } else {
            if (p.y() < cur.p.y()) {
                return contains(cur.lb, p, true);
            } else {
                return contains(cur.rt, p, true);
            }
        }
    }

    private boolean isEqual(Point2D p, Point2D q) {
        double x = Math.abs(p.x() - q.x());
        double y = Math.abs(p.y() - q.y());
        double delta = 10e-11;
        return x < delta && y < delta;
    }

    // draw all points to standard draw
    public void draw() {
        if (root == null) return;
        draw(root, true);
    }

    private void draw(Node cur, boolean isEvenLevel) {
        if (cur == null) return;
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        cur.p.draw();
        StdDraw.setPenRadius();
        if (isEvenLevel) {
            StdDraw.setPenColor(StdDraw.RED);
            double x = cur.p.x();
            Point2D p = new Point2D(x, cur.rect.ymin());
            Point2D q = new Point2D(x, cur.rect.ymax());
            p.drawTo(q);
            draw(cur.lb, false);
            draw(cur.rt, false);
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            double y = cur.p.y();
            Point2D p = new Point2D(cur.rect.xmin(), y);
            Point2D q = new Point2D(cur.rect.xmax(), y);
            p.drawTo(q);
            draw(cur.lb, true);
            draw(cur.rt, true);
        }
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new NullPointerException();
        Queue<Point2D> queue = new Queue<>();
        return range(root, rect, queue);
    }

    /*
    Range search. To find all points contained in a given query rectangle,
    start at the root and recursively search for points in both subtrees using the following pruning rule:
    if the query rectangle does not intersect the rectangle corresponding to a node,
    there is no need to explore that node (or its subtrees).
    A subtree is searched only if it might contain a point contained in the query rectangle.
     */
    private Iterable<Point2D> range(Node cur, RectHV rect, Queue<Point2D> queue) {
        if (cur == null) return queue;
        if (!cur.rect.intersects(rect)) return queue;
        if (rect.contains(cur.p)) {
            queue.enqueue(cur.p);
        }
        range(cur.lb, rect, queue);
        range(cur.rt, rect, queue);
        return queue;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new NullPointerException();
        if (root == null) return null;
        return nearest(root, p, root.p);
    }

    /*
    Nearest neighbor search. To find a closest point to a given query point, start at the root and recursively search in both subtrees using the following pruning rule:
    if the closest point discovered so far is closer than the distance between the query point and the rectangle corresponding to a node,
    there is no need to explore that node (or its subtrees).
    That is, a node is searched only if it might contain a point that is closer than the best one found so far.
    The effectiveness of the pruning rule depends on quickly finding a nearby point.
    To do this, organize your recursive method so that when there are two possible subtrees to go down,
    you always choose the subtree that is on the same side of the splitting line as the query point as
    the first subtree to explore—the closest point found while exploring the first subtree may enable pruning of the second subtree.
     */
    private Point2D nearest(Node cur, Point2D p, Point2D near) {
        if (cur == null) return near;
        double neardist = p.distanceSquaredTo(near);
        double squdist = cur.rect.distanceSquaredTo(p);
        if (neardist <= squdist) return near;
        double curdist = p.distanceSquaredTo(cur.p);
        if (curdist < neardist) {
            near = cur.p;
        }
        if (cur.lb != null && cur.rt != null) {
            if (cur.lb.rect.contains(p)) {
                Point2D ln = nearest(cur.lb, p, near);
                if (!isEqual(ln, near)) {
                    double lndist = ln.distanceSquaredTo(p);
                    if (lndist < neardist) {
                        near = ln;
                        neardist = lndist;
                    }
                }
                Point2D rn = nearest(cur.rt, p, near);
                if (!isEqual(rn, near)) {
                    double rndist = rn.distanceSquaredTo(p);
                    if (rndist < neardist) {
                        near = rn;
                        neardist = rndist;
                    }
                }
                return near;
            } else {
                Point2D rn = nearest(cur.rt, p, near);
                if (!isEqual(rn, near)) {
                    double rndist = rn.distanceSquaredTo(p);
                    if (rndist < neardist) {
                        near = rn;
                        neardist = rndist;
                    }
                }
                Point2D ln = nearest(cur.lb, p, near);
                if (!isEqual(ln, near)) {
                    double lndist = ln.distanceSquaredTo(p);
                    if (lndist < neardist) {
                        near = ln;
                        neardist = lndist;
                    }
                }
                return near;
            }
        }
        Point2D ln = nearest(cur.lb, p, near);
        if (!isEqual(ln, near)) {
            double lndist = ln.distanceSquaredTo(p);
            if (lndist < neardist) {
                near = ln;
                neardist = lndist;
            }
        }
        Point2D rn = nearest(cur.rt, p, near);
        if (!isEqual(rn, near)) {
            double rndist = rn.distanceSquaredTo(p);
            if (rndist < neardist) {
                near = rn;
                neardist = rndist;
            }
        }
        return near;
    }



    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree

        public Node(Point2D p, Node lb, Node rt) {
            this.p = p;
            this.lb = lb;
            this.rt = rt;
        }
    }


    // unit testing of the methods (optional)
    public static void main(String[] args) {
        In in = new In(args[0]);
        KdTree kd = new KdTree();
        while (!in.isEmpty()) {
            Point2D p = new Point2D(in.readDouble(), in.readDouble());
            kd.insert(p);
            System.out.println(kd.contains(p));
        }
        Iterable<Point2D> it = kd.range(new RectHV(0.0, 0.3, 0.6, 0.6));
        for (Point2D point2D : it) {
            System.out.println(point2D);
        }
        System.out.println(kd.nearest(new Point2D(0.81, 0.3)));
        kd.draw();
    }
}
