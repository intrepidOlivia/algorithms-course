import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Picture;

import java.awt.Color;

public class Energy {
    /**
     * Retrieving the energy of a specified pixel.
     * NOTE: Currently avoiding the square root, since it is an expensive function. Trying instead to boost everything else up by a square of itself.
     * @param p Picture
     * @param x column pixel
     * @param y row pixel
     * @return
     */
    public static int getEnergySquared(Picture p, int x, int y) {
        if (x == 0 || y == 0) {
            return 1000 * 1000;
        }

        if (x == p.width() -1 || y == p.height() - 1) {
            return 1000 * 1000;
        }

        return xGradient(p, x, y) + yGradient(p, x, y);
    }

    public static double getEnergy(Picture p, int x, int y) {
        return  Math.sqrt(getEnergySquared(p, x, y));
    }

    /**
     * Rx(x,y)2+Gx(x,y)2+Bx(x,y)2
     * @param p Picture
     * @param x pixel column
     * @param y pixel row
     * @return
     */
    private static int xGradient(Picture p, int x, int y) {
        Color xNext = p.get(x + 1, y);
        Color xPrev = p.get(x -1, y);

        return getGradient(xNext, xPrev);
    }

    /**
     * Ry(x,y)2+Gy(x,y)2+By(x,y)2
     * @param p Picture
     * @param x pixel column 1-indexed
     * @param y pixel row   1-indexed
     * @return
     */
    private static int yGradient(Picture p, int x, int y) {
        Color yNext = p.get(x, y + 1);
        Color yPrev = p.get(x, y - 1);

        return getGradient(yNext, yPrev);
    }

    private static int getGradient(Color next, Color prev) {
        int rDiff = next.getRed() - prev.getRed();
        int gDiff = next.getGreen() - prev.getGreen();
        int bDiff = next.getBlue() - prev.getBlue();

        return (rDiff * rDiff) + (gDiff * gDiff) + (bDiff * bDiff);
    }

    public static void main(String[] args) {
    }
}
