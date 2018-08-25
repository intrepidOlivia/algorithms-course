import java.util.Arrays;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;

// Finds whether four or more points are collinear by comparing the angle of each of them to one point p.
public class FastCollinearPoints {

    private Stack<LineSegment> lineStack = new Stack<>();
    private WeightedQuickUnionUF pointNetwork;

    /**
     * finds all line segments containing 4 or more points
     * @param points
     */
    public FastCollinearPoints(Point[] points) {
        pointNetwork = new WeightedQuickUnionUF(points.length);

        for (int p = 0; p < points.length; p++) {

            Point origin = points[p];
            // check q
            for (int q = p + 1; q < points.length; q++) {

                // if q is already part of the same line segment as p, don't go further
                if (pointNetwork.connected(p, q)) {
                    continue;
                }

                // establish slope with p
                double qSlope = origin.slopeTo(points[q]);

                //check next point
                for (int r = q + 1; r < points.length; r++) {

                    // establish slope with p
                    double rSlope = origin.slopeTo(points[r]);

                    // if it is the start of a collinear segment:
                    if (rSlope == qSlope) {

                        // check next point
                        for (int s = r + 1; s < points.length; s++) {

                            // establish slope with p
                            double sSlope = origin.slopeTo(points[s]);

                            if (sSlope == qSlope) {
                                // collinear reached; start gathering all points
                                startCollinear(p, q, r, s, qSlope, points);
                                break;
                            }
                        }
                        break;
                    }
                }
            }
        }
    }

    private void startCollinear(int p, int q, int r, int s, double slope, Point[] points) {
        Stack<Point> collinear = new Stack<>();

        collinear.push(points[p]);
        collinear.push(points[q]);
        collinear.push(points[r]);
        collinear.push(points[s]);

        pointNetwork.union(p, q);
        pointNetwork.union(p, r);
        pointNetwork.union(p, s);

        // find any remaining points on the line
        collinear = continueLine(points, p, s + 1, slope, collinear);

        Point[] linePoints = new Point[collinear.size()];
        int i = 0;
        for (Point point : collinear) {
            linePoints[i++] = point;
        }
        lineStack.push(getLineSegment(linePoints));
    }

    /**
     *
     * @param points    The collection of points
     * @param index     Index of the next point to start checking slope equivalence from
     * @param qSlope    The slope to compare to
     */
    private Stack<Point> continueLine(Point[] points, int p, int index, double qSlope, Stack<Point> collinear) {
        for (int i = index; i < points.length; i++) {
            // find slope with p
            double iSlope = points[p].slopeTo(points[i]);

            if (iSlope == qSlope) {
                // gather point with collinear
                pointNetwork.union(p, i);
                collinear.push(points[i]);
                return continueLine(points, p, i + 1, qSlope, collinear);
            }
        }
        return collinear;
    }

    /**
     * Creates a line segment from the provided points, using the highest and lowest points as definers.
     * @param linePoints An array of collinear points
     * @return the line segment defined by the highest and lowest two points
     */
    private LineSegment getLineSegment(Point[] linePoints) {
        Arrays.sort(linePoints);
        return new LineSegment(linePoints[0], linePoints[linePoints.length - 1]);
    }

    /**
     * @return the number of collinear line segments found
     */
    public int numberOfSegments() {
        return lineStack.size();
    }

    /**
     * @return the collinear line segments
     */
    public LineSegment[] segments() {
        LineSegment[] segments = new LineSegment[numberOfSegments()];
        int i = 0;
        for (LineSegment segment : lineStack) {
            segments[i++] = segment;
        }
        return segments;
    }
}