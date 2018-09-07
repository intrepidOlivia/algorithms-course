import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {

    SET<Point2D> pSet;

    /**
     * Constructs an empty set of points
     */
    public PointSET() {
        pSet = new SET<>();
    }

    public boolean isEmpty() {
        return pSet.isEmpty();
    }

    public int size() {
        return pSet.size();
    }

    public void insert(Point2D p) {
        if (p == null) { throw new IllegalArgumentException(); }
        pSet.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null ) { throw new IllegalArgumentException(); }
        return pSet.contains(p);
    }

    /**
     * draws all points to standard draw
     */
    public void draw() {
        for (Point2D p : pSet) {
            StdDraw.point(p.x(), p.y());
        }
    }

    /**
     * Retrieves all points that are inside the specified rectangle
     * @param rect The rectangle to check for points within
     * @return iterable collection of points inside or on the boundary of the rectangle
     */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) { throw new IllegalArgumentException(); }
        Stack<Point2D> included = new Stack<>();
        for (Point2D p : pSet) {
            if (rect.contains(p)) {
                included.push(p);
            }
        }
        return included;
    }

    /**
     * @param p The point to find the nearest neighbor of
     * @return The nearest neighbor to the specified point
     */
    public Point2D nearest(Point2D p) {
        if (p == null) { throw new IllegalArgumentException(); }
        if (isEmpty()) { return null; }

        double distance = 0;
        Point2D nearest = null;
        for (Point2D compare : pSet) {
            if (p.equals(compare)) { continue; }
            double cDistance = p.distanceTo(compare);
            if (distance == 0 || cDistance < distance) {
                distance = cDistance;
                nearest = compare;
            }
        }
        return nearest;
    }


    public static void main(String[] args) {
        StdDraw.setPenRadius(0.05);
        StdDraw.point(0, 0);
        StdDraw.point(1, 0);
        StdDraw.point(0, 1);
        StdDraw.point (1, 1);
    }
}
