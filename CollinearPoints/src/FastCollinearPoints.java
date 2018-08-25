import java.util.Arrays;
import edu.princeton.cs.algs4.Stack;

// Finds whether four or more points are collinear by comparing the angle of each of them to one point p.
public class FastCollinearPoints {

    Stack<LineSegment> lineStack = new Stack<>();

    /**
     * finds all line segments containing 4 or more points
     * @param points
     */
    public FastCollinearPoints(Point[] points) {
        for (int p = 0; p < points.length; p++) {
            // find slope of each other point in the array
            Point thisPoint = points[p];

            Point[] otherPoints = new Point[points.length - 1];
            int j = 0;
            for (int i = 0; i < otherPoints.length; i++) {
                if (i != p) {
                    otherPoints[i] = points[j];
                } else {
                    otherPoints[i] = points[++j];
                }
                j++;
            }

            // sort points by slopes with p (use Comparator?)
            Arrays.sort(otherPoints, points[p].slopeOrder());

            // navigate sorted array by checking to see if adjacent slopes are fidelitous with each other
            for (int n = 0; n < otherPoints.length - 2;) {
                System.out.println("checking at n = " + n);
                Stack<Point> collinear = new Stack<>();

                // check next two points for equality
                double qSlope = thisPoint.slopeTo(otherPoints[n]);
                double rSlope = thisPoint.slopeTo(otherPoints[n + 1]);
                double sSlope = thisPoint.slopeTo(otherPoints[n + 2]);

                if (qSlope == rSlope && rSlope == sSlope) {
                    System.out.println("Found start of collinear line segment.");
                    collinear.push(thisPoint);
                    collinear.push(otherPoints[n]);
                    collinear.push(otherPoints[n + 1]);
                    collinear.push(otherPoints[n + 2]);

                    boolean adjacent = true;
                    int m = n + 3;
                    while (adjacent && m < otherPoints.length) {
                        double slope = thisPoint.slopeTo(otherPoints[m]);
                        if (slope == qSlope) {
                            collinear.push(otherPoints[m]);
                        } else {
                            adjacent = false;
                        }
                        m++;
                    }

                    n = m;
                    System.out.println("Collinear segment ended, n now equals " + n);

                    // get line segment from collinear points
                    Point[] linePoints = new Point[collinear.size()];
                    int i = 0;
                    for (Point pt : collinear) {
                        linePoints[i++] = pt;
                    }
                    lineStack.push(getLineSegment(linePoints));
                    System.out.println("Collinear line segment found with " + linePoints.length + " points.");
                } else {
                    n++;
                }
            }
        }
    }

    /**
     * Creates a line segment from the provided points, using the highest and lowest points as definers.
     * @param points An array of collinear points
     * @return the line segment defined by the highest and lowest two points
     */
    LineSegment getLineSegment(Point[] points) {
        Arrays.sort(points);
        return new LineSegment(points[0], points[points.length - 1]);
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

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}