import edu.princeton.cs.algs4.Stack;
import java.util.Arrays;

// Finds whether points are collinear by testing if all slopes between four connected points are the same.
public class BruteCollinearPoints {

    private Stack<LineSegment> lineStack = new Stack<>();

    /**
     * Finds all line segments containing four points
     * @param points
     */
    public BruteCollinearPoints(Point[] points) {

        if (points == null) {
            throw new java.lang.IllegalArgumentException();
        }

        for (int p = 0; p < points.length; p++) {
            if (points[p] == null) {
                throw new java.lang.IllegalArgumentException();
            }

            int pointsFound = 0;

            // gather each collinear point after itself until 4 is reached\
            for (int q = p + 1; q < points.length; q++) {
                if (points[q] == null) {
                    throw new java.lang.IllegalArgumentException();
                }

                // find slope between i and j
                double compareSlope = points[p].slopeTo(points[q]);

                // check the rest of the array for third point
                for (int r = q + 1; r < points.length; r++) {
                    if (points[r] == null) {
                        throw new java.lang.IllegalArgumentException();
                    }

                    double rqSlope = points[q].slopeTo(points[r]);
                    if (compareSlope == rqSlope) {
                        pointsFound++;

                        // check for fourth point
                        for (int s = r + 1; s < points.length; s++) {
                            if (points[s] == null) {
                                throw new java.lang.IllegalArgumentException();
                            }

                            double srSlope = points[r].slopeTo(points[s]);
                            if (srSlope == compareSlope) {
                                pointsFound++;
                                lineStack.push(getLineSegment(points[p], points[q], points[r], points[s]));
                                break;
                            }
                        }
                    }

                    if (pointsFound == 2) {
                        break;
                    }
                }
            }
        }
    }

    /**
     * Creates a line segment from the provided collinear points, using the highest and lowest points as definers.
     * @param p collinear point
     * @param q collinear point
     * @param r collinear point
     * @param s collinear point
     * @return the line segment defined by the highest and lowest two points
     */
    private LineSegment getLineSegment(Point p, Point q, Point r, Point s) {
        Point[] linePts = new Point[] {p, q, r, s};
        Arrays.sort(linePts);
        return new LineSegment(linePts[0], linePts[3]);
    }

    /**
     * Returns the number of collinear line segments found
     * @return
     */
    public int numberOfSegments() {
        return lineStack.size();
    }

    /**
     * @return an array of collinear line segments
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