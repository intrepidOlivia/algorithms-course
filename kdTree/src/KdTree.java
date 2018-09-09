import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;

public class KdTree {

    private Node root;
    private int count;
    private Stack<Point2D> pointsInsideRect;
    private double minDistance;
    private Point2D minPoint;

    public KdTree() {

    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return count;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) { throw new IllegalArgumentException(); }

        if (root == null) {
            root = new Node(p, false);
            count++;
            return;
        }

        insertIntoSubtree(root, p);
    }

    // inserts into left or right if it belongs there, or sends it down to the next subtree
    private void insertIntoSubtree(Node n, Point2D p) {
        if (n == null) {
            System.out.println("Something went wrong, cannot insert into null node.");
            return;
        }

        if (n.point.equals(p)) {
            return;
        }

        if (isLessThan(n, p)) {
            // check for null
            if (n.left == null) {
                n.left = new Node(p, !n.isHorizontal);
                count++;
                return;
            }
            // check for equality
            if (n.left.point.equals(p)) {
                return;
            }
            // insert into subtree
            insertIntoSubtree(n.left, p);
        } else {
            // check for null
            if (n.right == null) {
                n.right = new Node(p, !n.isHorizontal);
                count++;
                return;
            }
            // check for equality
            if (n.right.point.equals(p)) {
                return;
            }
            // insert into subtree
            insertIntoSubtree(n.right, p);
        }
    }


    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) { throw new IllegalArgumentException(); }
        if (root == null) { return false; }

        if (root.point.equals(p)) {
            return true;
        }

        Node located = locateInSubtree(root, p);

        if (located == null) {
            return false;
        }

        return true;
    }

    private Node locateInSubtree(Node n, Point2D p) {
        if (n == null) {
            return null;
        }

        if (n.left != null && n.left.point.equals(p)) {
            return n.left;
        }

        if (n.right != null && n.right.point.equals(p)) {
            return n.right;
        }

        if (isLessThan(n, p)) {
            return locateInSubtree(n.left, p);
        } else {
            return locateInSubtree(n.right, p);
        }
    }

    // returns true if p would belong on th left side of Node n
    private boolean isLessThan(Node n, Point2D p) {
        double pValue, nValue;
        if (n.isHorizontal) {
            // check y values
            pValue = p.y();
            nValue = n.point.y();
        } else {
            // check x values
            pValue = p.x();
            nValue = n.point.x();
        }
        return pValue < nValue;
    }

    private boolean isGreaterThan(Node n, Point2D p) {
        double pValue, nValue;
        if (n.isHorizontal) {
            // check y values
            pValue = p.y();
            nValue = n.point.y();
        } else {
            // check x values
            pValue = p.x();
            nValue = n.point.x();
        }
        return pValue > nValue;
    }

    public void draw() {
        if (root == null) {
            return;
        }

        // root is always vertical
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.point(root.point.x(), root.point.y());
//        drawVertical(root.point.x());

        drawSubtree(root);
    }

    // Draws the left and right nodes of n
    private void drawSubtree(Node n) {
        if (n.left != null) {
            StdDraw.point(n.left.point.x(), n.left.point.y());
            drawSubtree(n.left);
        }

        if (n.right != null) {
            StdDraw.point(n.right.point.x(), n.right.point.y());
            drawSubtree(n.right);
        }
    }

    private void drawVertical(double xValue) {
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.line(xValue, 0, xValue, 1);
    }

    private void drawHorizontal(double yValue) {
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.line(0, yValue, 1, yValue);
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) { throw new IllegalArgumentException(); }

        pointsInsideRect = new Stack<>();
        checkNodeRect(root, rect);
        return pointsInsideRect;
    }

    private void checkNodeRect(Node n, RectHV rect) {
        if (n == null) {
            return;
        }

        if (nodeInRectangle(n, rect)) {
            pointsInsideRect.push(n.point);
            // check both subtrees
            checkNodeRect(n.left, rect);
            checkNodeRect(n.right, rect);
        } else {
            int compare = compareNodeToRect(n, rect);
            if (compare == 0) {
                // check both subtrees
                checkNodeRect(n.left, rect);
                checkNodeRect(n.right, rect);
            } else if (compare < 0) {
                // check left subtree
                checkNodeRect(n.left, rect);
            } else {
                // check right subtree
                checkNodeRect(n.right, rect);
            }
        }
    }

    private boolean nodeInRectangle(Node n, RectHV rect) {
        if (rect.contains(n.point)) {
            return true;
        }
        return false;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) { throw new IllegalArgumentException(); }

        if (root == null) {
            return null;
        }

        // start w root
        minDistance = root.point.distanceSquaredTo(p);
        minPoint = root.point;

        checkSubtreeForNearest(root, p);

        return minPoint;
    }

    private void checkSubtreeForNearest(Node n, Point2D p) {
        if (n == null) {
            return;
        }

        double distance;

        distance = n.point.distanceSquaredTo(p);
        if (distance < minDistance) {
            minDistance = distance;
            minPoint = n.point;
        }

        if (isLessThan(n, p)) {
            // check left first
            checkSubtreeForNearest(n.left, p);
            if (n.isHorizontal) {
                if (minDistance >= p.distanceSquaredTo(new Point2D(p.y(), n.point.x()))) {
                    checkSubtreeForNearest(n.right, p);
                }
            } else {
                if (minDistance >= p.distanceSquaredTo(new Point2D(n.point.x(), p.y()))) {
                    checkSubtreeForNearest(n.right, p);
                }
            }

        } else {
            // check right first
            checkSubtreeForNearest(n.right, p);
            if (n.isHorizontal) {
                if (minDistance >= p.distanceSquaredTo(new Point2D(p.y(), n.point.x()))) {
                    checkSubtreeForNearest(n.left, p);
                }
            } else {
                if (minDistance >= p.distanceSquaredTo(new Point2D(n.point.x(), p.y()))) {
                    checkSubtreeForNearest(n.left, p);
                }
            }
        }
    }

    private double horizDistanceFrom(Node n, Point2D p) {
        return getAbsoluteValue(p.x() - n.point.x());
    }

    private double vertDistanceFrom(Node n, Point2D p) {
        return getAbsoluteValue(p.y() - n.point.y());
    }

    private double getAbsoluteValue(double i) {
        if (i < 0) {
            return -i;
        }
        return i;
    }

    /**
     * @param n
     * @param rect
     * @return -1 if rect is in left subtree of node, 1 if rect is in right subtree of node, 0 if rect intersects both subtrees
     */
    private int compareNodeToRect(Node n, RectHV rect) {
        Point2D rectMax = new Point2D(rect.xmax(), rect.ymax());
        Point2D rectMin = new Point2D(rect.xmin(), rect.ymin());

        if (isLessThan(n, rectMax) && isLessThan(n, rectMin)) {
            // rectangle is on left side
            return -1;
        }

        if (isGreaterThan(n, rectMax) && isGreaterThan(n, rectMin)) {
            return 1;
        }

        return 0;
    }

    private class Node {
        Point2D point;
        Node left = null;
        Node right = null;
        boolean isHorizontal;

        Node(Point2D point, boolean isHorizontal) {
            this.point = point;
            this.isHorizontal = isHorizontal;
        }
    }

    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
        }
//        StdDraw.setPenRadius(0.02);
//        kdtree.draw();
        System.out.println("Point nearest to 0.73, 0.33: " + kdtree.nearest(new Point2D(0.73, 0.33)));
    }
}
