import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

public class KdTree {

    SET<Node> nSet;

    public KdTree() {
        nSet = new SET<>();
    }

    public boolean isEmpty() {}                     // is the set empty?
    public int size() {}                        // number of points in the set
    public void insert(Point2D p) {}             // add the point to the set (if it is not already in the set)
    public boolean contains(Point2D p) {}           // does the set contain point p?
    public void draw() {}                        // draw all points to standard draw
    public Iterable<Point2D> range(RectHV rect) {}            // all points that are inside the rectangle (or on the boundary)
    public Point2D nearest(Point2D p) {}            // a nearest neighbor in the set to point p; null if the set is empty

    private class Node implements Comparable<Node> {
        Point2D point;
        Node left = null;
        Node right = null;
        boolean isHorizontal;

        Node(Point2D point, boolean isHorizontal) {
            this.point = point;
            this.isHorizontal = isHorizontal;
        }

        int compareTo(Node that) {
            if (isHorizontal) {
                // compare y values
                double thisY = this.point.y();
                double thatY = that.point.y();
                if (thisY < thatY) {
                    return -1;
                }
                if (thisY == thatY && this.point.x() == that.point.x()) {
                    return 0;
                }
                return 1;
            } else {
                // compare x values
            }
            return 0;
        }
    }

    public static void main(String[] args) {}                 // unit testing of the methods (optional)
}
