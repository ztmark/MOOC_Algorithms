
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
        if (contains(p)) return;
        root = insert(null, root, p, 0);
        size++;
    }

    private Node insert(Node pre, Node root, Point2D p, int level) {
        if (root == null) {
            Node n = new Node(p, null, null, level);
            if (pre == null) {
                n.rect = new RectHV(0.0, 0.0, 1.0, 1.0);
            } else {
                if ((level-1) % 2 == 0) {
                    double prex = pre.p.x();
                    if (p.x() < prex) {
                        n.rect = new RectHV(pre.rect.xmin(), pre.rect.ymin(), prex, pre.rect.ymax());
                    } else {
                        n.rect = new RectHV(prex, pre.rect.ymin(), pre.rect.xmax(), pre.rect.ymax());
                    }
                } else {
                    double prey = pre.p.y();
                    if (p.y() < prey) {
                        n.rect = new RectHV(pre.rect.xmin(), pre.rect.ymin(), pre.rect.xmax(), prey);
                    } else {
                        n.rect = new RectHV(pre.rect.xmin(), prey, pre.rect.xmax(), pre.rect.ymax());
                    }
                }
            }
            return n;
        }
        if (level % 2 == 0) {
            //第偶数层 root为第0层
            if (p.x() < root.p.x()) {
                root.lb = insert(root, root.lb, p, ++level);
            } else {
                root.rt = insert(root, root.rt, p, ++level);
            }
        } else {
            //第奇数层
            if (p.y() < root.p.y()) {
                root.lb = insert(root, root.lb, p, ++level);
            } else {
                root.rt = insert(root, root.rt, p, ++level);
            }
        }
        return root;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new NullPointerException();
        return contains(root, p, 0);
    }

    private boolean contains(Node root, Point2D p, int level) {
        if (root == null) {
            return false;
        }
//        double delta = 1e-11;
//        double y = Math.abs(root.p.y() - p.y());
//        double x = Math.abs(root.p.x() - p.x());
//        if (y < delta && x < delta) return true;
        if (isEqual(root.p, p)) return true;
        level++;
        if (level % 2 == 0) {
            if (root.p.y() > p.y()) {
                return contains(root.lb, p, level);
            } else {
                return contains(root.rt, p, level);
            }
        } else {
            if (root.p.x() > p.x()) {
                return contains(root.lb, p, level);
            } else {
                return contains(root.rt, p, level);
            }
        }
    }

    private boolean isEqual(Point2D p, Point2D q) {
        double delta = 1e-11;
        double y = Math.abs(p.y() - q.y());
        double x = Math.abs(p.x() - q.x());
        return y < delta && x < delta;
    }

    // draw all points to standard draw
    public void draw() {
        if (root == null) return;
        Queue<Node> q = new Queue<>();
        q.enqueue(root);
        while (!q.isEmpty()) {
            Node n = q.dequeue();
            if (n.lb != null) {
                q.enqueue(n.lb);
            }
            if (n.rt != null) {
                q.enqueue(n.rt);
            }
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            n.p.draw();
            StdDraw.setPenRadius();
            if (n.level % 2 == 0) {
                StdDraw.setPenColor(StdDraw.RED);
                new Point2D(n.p.x(), n.rect.ymin()).drawTo(new Point2D(n.p.x(), n.rect.ymax()));
            } else {
                StdDraw.setPenColor(StdDraw.BLUE);
                new Point2D(n.rect.xmin(), n.p.y()).drawTo(new Point2D(n.rect.xmax(), n.p.y()));
            }
        }
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new NullPointerException();
        Queue<Point2D> queue = new Queue<>();
        Stack<Node> stack = new Stack<>();
        if (root != null) {
            stack.push(root);
        }
        while (!stack.isEmpty()) {
            Node node = stack.pop();
            if (rect.contains(node.p)) {
                queue.enqueue(node.p);
            }
            if (node.rt != null && rect.intersects(node.rt.rect)) {
                stack.push(node.rt);
            }
            if (node.lb != null && rect.intersects(node.lb.rect)) {
                stack.push(node.lb);
            }
        }
        return queue;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new NullPointerException();
        if (isEmpty()) return null;
        Point2D near = root.p;
        return nearest(root, p, near, p.distanceSquaredTo(root.p), new Flag());
    }

    private Point2D nearest(Node root, Point2D p, Point2D near, double nearest, Flag flag) {
        double dis = p.distanceSquaredTo(root.p);
        if (dis < nearest) {
            near = root.p;
            nearest = dis;
            flag.changed = true;
        }
        Flag f = new Flag();
        if (root.lb == null && root.rt == null) {
            return near;
        } else if (root.lb == null) {
            if (nearest <= root.rt.rect.distanceSquaredTo(p)) {
                return near;
            } else {

                Point2D n = nearest(root.rt, p, near, nearest, f);
                if (f.changed) {
                    flag.changed = true;
                    return n;
                }
                return near;
//                return f.changed ? n : near;
//                return nearest < n.distanceSquaredTo(p) ? near : n;
            }
        } else if (root.rt == null) {
            if (nearest <= root.lb.rect.distanceSquaredTo(p)) {
                return near;
            } else {
                Point2D n = nearest(root.lb, p, near, nearest, f);
                if (f.changed) {
                    flag.changed = true;
                    return n;
                }
                return near;
//                return f.changed ? n : near;
//                return nearest < n.distanceSquaredTo(p) ? near : n;
            }
        } else {
            if (root.lb.rect.contains(p)) {
                Point2D ln = nearest(root.lb, p, near, nearest, f);
                /*double ldis = ln.distanceSquaredTo(p);
                if (ldis < nearest) {
                    nearest = ldis;
                    near = ln;
                }*/
                if (f.changed) {
                    near = ln;
                    nearest = ln.distanceSquaredTo(p);
                    flag.changed = true;
                    if (nearest <= root.rt.rect.distanceSquaredTo(p)) {
                        return near;
                    }
                }

                f.changed = false;
                Point2D rn = nearest(root.rt, p, near, nearest, f);
                if (f.changed) {
                    flag.changed = true;
                    return rn;
                }
                return near;
//                return f.changed ? rn : near;
//                return nearest < rn.distanceSquaredTo(p) ? near : rn;
            } else {
                Point2D rn = nearest(root.rt, p, near, nearest, f);
                /*double rdis = rn.distanceSquaredTo(p);
                if (rdis < nearest) {
                    nearest = rdis;
                    near = rn;
                }*/
                if (f.changed) {
                    near = rn;
                    nearest = rn.distanceSquaredTo(p);
                    flag.changed = true;
                    if (nearest <= root.lb.rect.distanceSquaredTo(p)) {
                        return near;
                    }
                }
                f.changed = false;
                Point2D ln = nearest(root.lb, p, near, nearest, f);
                if (f.changed) {
                    flag.changed = true;
                    return ln;
                }
                return near;
//                return f.changed ? ln : near;
//                return nearest < ln.distanceSquaredTo(p) ? near : ln;
            }
        }
    }

    private static class Flag {
        boolean changed = false;
    }

    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree
        private int level;

        public Node(Point2D p, Node lb, Node rt, int level) {
            this.p = p;
            this.lb = lb;
            this.rt = rt;
            this.level = level;
        }
    }


    // unit testing of the methods (optional)
    public static void main(String[] args) {
        In in = new In(args[0]);
        KdTree kd = new KdTree();
        while (!in.isEmpty()) {
            kd.insert(new Point2D(in.readDouble(), in.readDouble()));
        }
/*        System.out.println(kd.isEmpty());
        System.out.println(kd.size());
        for (int i = 1; i < 10; i++) {
            System.out.println(kd.contains(new Point2D(0.1 * i, 0.5)));
        }
        Iterable<Point2D> it = kd.range(new RectHV(0.0, 0.0, 0.3, 0.5));
        for (Point2D point2D : it) {
            System.out.println(point2D);
        }*/
        System.out.println(kd.nearest(new Point2D(0.81, 0.3)));
        kd.draw();
        /*Point2D p = new Point2D(0.81, 0.3);
        Point2D p1 = new Point2D(0.975528, 0.345492);
        Point2D p2 = new Point2D(0.761250, 0.317125);
        System.out.println(p.distanceSquaredTo(p1));
        System.out.println(p.distanceSquaredTo(p2));*/
    }
}
