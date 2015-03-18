
/**
 * Author: Mark
 * Date  : 2015/3/16
 * Time  : 16:21
 */
public class KdTreeSlow {

    private Node root;
    private int size;

    // construct an empty set of points
    public KdTreeSlow() {
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
        //if (contains(p)) return;
        root = insert(null, root, p, 0);

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
            size++;
            return n;
        }
        if (isEqual(root.p, p)) return root;
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
            if (rect.contains(root.p)) {
                queue.enqueue(root.p);
            }
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
        /*double dis = p.distanceSquaredTo(root.p);
        if (dis < nearest) {
            near = root.p;
            nearest = dis;
            flag.changed = true;
        }*/
        Flag f = new Flag();
        if (root.lb == null && root.rt == null) {
            return near;
        } else if (root.lb == null) {
            if (nearest <= root.rt.rect.distanceSquaredTo(p)) {
                return near;
            } else {
                double dist = p.distanceSquaredTo(root.rt.p);
                if (dist < nearest) {
                    near = root.rt.p;
                    nearest = dist;
                    flag.changed = true;
                }
                Point2D n = nearest(root.rt, p, near, nearest, f);
                if (f.changed) {
                    flag.changed = true;
                    return n;
                }
                return near;
            }
        } else if (root.rt == null) {
            if (nearest <= root.lb.rect.distanceSquaredTo(p)) {
                return near;
            } else {
                double dist = p.distanceSquaredTo(root.lb.p);
                if (dist < nearest) {
                    near = root.lb.p;
                    nearest = dist;
                    flag.changed = true;
                }
                Point2D n = nearest(root.lb, p, near, nearest, f);
                if (f.changed) {
                    flag.changed = true;
                    return n;
                }
                return near;
            }
        } else {
            if (root.lb.rect.contains(p)) {
                double dist = p.distanceSquaredTo(root.lb.p);
                if (dist < nearest) {
                    near = root.lb.p;
                    nearest = dist;
                    flag.changed = true;
                }
                Point2D ln = nearest(root.lb, p, near, nearest, f);
                double ldis = ln.distanceSquaredTo(p);
                if (ldis < nearest) {
                    nearest = ldis;
                    near = ln;
                }
                if (f.changed) {
                    near = ln;
                    nearest = ln.distanceSquaredTo(p);
                    flag.changed = true;
                    if (nearest <= root.rt.rect.distanceSquaredTo(p)) {
                        return near;
                    }
                }

                f.changed = false;
                dist = p.distanceSquaredTo(root.rt.p);
                if (dist < nearest) {
                    near = root.rt.p;
                    nearest = dist;
                    flag.changed = true;
                }
                Point2D rn = nearest(root.rt, p, near, nearest, f);
                if (f.changed) {
                    flag.changed = true;
                    return rn;
                }
                return near;
            } else {
                double dist = p.distanceSquaredTo(root.rt.p);
                if (dist < nearest) {
                    near = root.rt.p;
                    nearest = dist;
                    flag.changed = true;
                }
                Point2D rn = nearest(root.rt, p, near, nearest, f);
                double rdis = rn.distanceSquaredTo(p);
                if (rdis < nearest) {
                    nearest = rdis;
                    near = rn;
                }
                if (f.changed) {
                    near = rn;
                    nearest = rn.distanceSquaredTo(p);
                    flag.changed = true;
                    if (nearest <= root.lb.rect.distanceSquaredTo(p)) {
                        return near;
                    }
                }
                f.changed = false;
                dist = p.distanceSquaredTo(root.lb.p);
                if (dist < nearest) {
                    near = root.lb.p;
                    nearest = dist;
                    flag.changed = true;
                }
                Point2D ln = nearest(root.lb, p, near, nearest, f);
                if (f.changed) {
                    flag.changed = true;
                    return ln;
                }
                return near;
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
        KdTreeSlow kd = new KdTreeSlow();
        while (!in.isEmpty()) {
            kd.insert(new Point2D(in.readDouble(), in.readDouble()));
        }

    }
}
