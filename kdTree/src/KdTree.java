import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

public class KdTree {

    Node root;
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    public KdTree() {

    }

    public boolean isEmpty() {

    }

    public int size() {

    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) { throw new IllegalArgumentException(); }
        if (root == null) {
            root = new Node(p, false, BLACK);
        }

        Node located = locate(root, p);
        if (!located.point.equals(p)) {
            boolean isHorizontal = !located.isHorizontal;
            if (isHorizontal) {
                // insert new node to left or right depending on y value
                // if insert right, rotate to be on left or flip colors
            } else {
                // insert new node to left or right depending on x value
                // if insert right, rotate to be on left or flip colors
            }
        }
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) { throw new IllegalArgumentException(); }
        if (root == null) { return false; }

        Node located = locate(root, p);
        if (located.point.equals(p)) {
            return true;
        }
        return false;
    }

    // checks n for p, sends search to either left, right, or (place to be inserted?)

    /**
     * @param n The node to investigate for equality with p
     * @param p The point to locate equality for
     * @return either the Node that contains the point, or the Node at which to insert the point (check for point equality)
     */
    private Node locate(Node n, Point2D p) {
        int yCompare = comparePoints(n.point.y(), p.y());
        int xCompare = comparePoints(n.point.x(), p.x());
        if (yCompare == 0 && xCompare == 0) {
            return n;
        }
        Node next = null;
        if (n.isHorizontal) {
            // check y value
            if (yCompare < 0) {
                next = n.left;
            }
            if (yCompare > 0) {
                next = n.right;
            }
        } else {
            // check x value
            if (xCompare < 0) {
                next = n.left;
            }
            if (xCompare > 0) {
                next = n.right;
            }
        }
        if (next == null) {
            return n;
        }
        return locate(next, p);
    }

    public void draw() {

    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {

    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {

    }

    private int comparePoints(double thisCoord, double thatCoord) {
        if (thisCoord < thatCoord) {
            return -1;
        }
        if (thisCoord == thatCoord) {
            return 0;
        }
        return 1;
    }

    private boolean isRed(Node n) {
        if (n == null) { return false; }
        return n.color == RED;
    }

    /**
     * Rotates the left and right Nodes (so that the red link is on the left)
     * @param n The parent of the left and right nodes that need to rotate
     * @return The new parent node
     */
    private Node rotateLeft(Node n) {
        assert isRed(n.right);  // only perform this if the red link is on the wrong side
        Node x = n.right;
        n.right = x.left;
        x.left = n;
        x.color = n.color;
        n.color = RED;
        return x;
    }

    /**
     * Orients a left-leaning red link to temporarily lean right
     * (this should occur when ______)
     * @param n The parent node
     * @return The new parent node
     */
    private Node rotateRight(Node n) {
        assert isRed(n.left);
        Node x = n.left;
        n.left = x.right;
        x.right = n;
        x.color = n.color;
        n.color = RED;
        return x;
    }

    private void flipColors(Node n) {
        assert !isRed(n);
        assert isRed(n.left);
        assert isRed(n.right);
        n.color = RED;
        n.left.color = BLACK;
        n.right.color = BLACK;
    }

    private class Node implements Comparable<Node> {
        Point2D point;
        Node left = null;
        Node right = null;
        boolean isHorizontal;
        boolean color;  // color of link to parent. Red == true

        Node(Point2D point, boolean isHorizontal, boolean color) {
            this.point = point;
            this.isHorizontal = isHorizontal;
            this.color = color;
        }

        public int compareTo(Node that) {
            if (isHorizontal) {
                // compare y values
                int yCompare = comparePoints(this.point.y(), that.point.y());
                if (yCompare == 0 && comparePoints(this.point.x(), that.point.x()) == 0) {
                    return 0;
                }
                return yCompare;
            } else {
                // compare x values
                int xCompare = comparePoints(this.point.x(), that.point.x());
                if (xCompare == 0 && comparePoints(this.point.y(), that.point.y()) == 0) {
                    return 0;
                }
                return xCompare;
            }
        }
    }

    public static void main(String[] args) {}                 // unit testing of the methods (optional)
}
