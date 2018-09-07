import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

public class KdTree {

    Node root;

    public KdTree() {

    }

    public boolean isEmpty() {

    }

    public int size() {

    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {

    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) { throw new IllegalArgumentException(); }
        if (root == null) { return false; }


    }

    // checks n for p, sends search to either left, right, or (place to be inserted?)

    /**
     *
     * @param n
     * @param p
     * @return either the Node that contains the point, or the Node at which to insert the point
     */
    private Node locate(Node n, Point2D p) {
        int yCompare = comparePoints(n.point.y(), p.y());
        int xCompare = comparePoints(n.point.x(), p.x());
        if (yCompare == 0 && xCompare == 0) {
            return n;
        }
        if (n.isHorizontal) {
            // check y value
            if (yCompare < 0) {
                return locate(n.left, p);
            }
            if (yCompare > 0) {
                return locate(n.right, p);
            }
        } else {
            // check x value
            if (xCompare < 0) {
                return locate(n.left, p);
            }
            if (xCompare > 0) {
                return locate(n.right, p);
            }
        }
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


    private class Node implements Comparable<Node> {
        Point2D point;
        Node left = null;
        Node right = null;
        boolean isHorizontal;

        Node(Point2D point) {
            this.point = point;
            this.isHorizontal = isHorizontal;
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
