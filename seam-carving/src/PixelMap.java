import edu.princeton.cs.algs4.Bag;

import java.util.Iterator;
import edu.princeton.cs.algs4.Picture;

public class PixelMap {

    private Picture p;

    private int[][] edgeToCol; // [columns][rows]   // backwards to column of previous row
    private int[][] edgeToRow;  // [columns][rows]  // backwards to row of previous column
    private double[][] distToCol; // [columns][rows] for finding horizontal seam
    private double[][] distToRow; // [columns][rows] for finding vertical seams
    private double energy[][];   // [columns][rows]

    public PixelMap(Picture p) {
        // Every pixel in a row has a directed path to the three pixels beneath it.
        this.p = p;
        int cols = p.width();
        int rows = p.height();

        edgeToCol = new int[cols][rows];
        edgeToRow = new int[cols][rows];
        distToCol = new double[cols][rows];
        distToRow = new double[cols][rows];
        energy = new double[cols][rows];

        for (int i = 0; i < p.height(); i++) {   // rows

            for (int j = 0; j < p.width(); j++) {   // columns
                if (j == 0) {
                    distToCol[j][i] = 0;   // All nodes at left column
                } else {
                    distToCol[j][i] = Double.POSITIVE_INFINITY;
                }

                if (i == 0) {
                    distToRow[j][i] = 0;    // All nodes on top row
                } else {
                    distToRow[j][i] = Double.POSITIVE_INFINITY;
                }

                energy[j][i] = Energy.getEnergy(p, j, i);
//
//                // Print energy
//                DecimalFormat df = new DecimalFormat("#.##");

                edgeToCol[j][i] = j;
                edgeToRow[j][i] = i;
            }
        }
    }

    public int[] getVerticalSeam() {
        int[] seam = new int[p.height()];
        for (int i = 0; i < p.height() - 1; i++) {  // rows
            for (int j = 0; j < p.width(); j++) {   // columns
            Iterator<Integer> linked = getLinkedCols(j, i).iterator();
            while (linked.hasNext()) {
                // relax edges
                int nextCol = linked.next();
                int nextRow = i + 1;

                if (energy[nextCol][nextRow] + distToRow[j][i] < distToRow[nextCol][nextRow]) {
                    distToRow[nextCol][nextRow] = energy[nextCol][nextRow] + distToRow[j][i];
                    edgeToRow[nextCol][nextRow] = j;    // assigns edgeTo backwards from the relaxed node.
                }
            }
        }
    }

        // Process backwards from final row
        double leastEnergy = Double.POSITIVE_INFINITY;
        int leastCol = 0;
        int lastRow = p.height() - 1;
        for (int i = 0; i < p.width(); i++) {   // cols
            // check all distTos on last row
            if (distToRow[i][lastRow] < leastEnergy) {
                leastEnergy = distToRow[i][lastRow];
                leastCol = i;
            }
        }

        int row = lastRow;
        int col = leastCol;
        while (row >= 0) {
            seam[row] = col;
            col = edgeToRow[col][row];
            row--;
        }

        return seam;
    }

    public int[] getHorizontalSeam() {
        int[] seam = new int[p.width()];
        for (int i = 0; i < p.width() - 1; i++) {   // cols
            for (int j = 0; j < p.height(); j++) {  // rows
                Iterator<Integer> linked = getLinkedRows(j, i).iterator();
                while (linked.hasNext()) {
                    // relax edges
                    int nextRow = linked.next();
                    int nextCol = i + 1;

                    // distToCol is the total amount of energy from col 0 to current col
                    if (energy[nextCol][nextRow] + distToCol[i][j] < distToCol[nextCol][nextRow]) {
                        distToCol[nextCol][nextRow] = energy[nextCol][nextRow] + distToCol[i][j];
                        edgeToCol[nextCol][nextRow] = j;    // assigns edgeTo backwards from the relaxed node.
                    }
                }
            }
        }

        // Process backwards from final column
        double leastEnergy = Double.POSITIVE_INFINITY;
        int leastRow = 0;
        int lastCol = p.width() - 1;
        for (int i = 0; i < p.height(); i++) {  // rows
            // Check all distTos on last column
            if (distToCol[lastCol][i] < leastEnergy) {
                leastEnergy = distToCol[lastCol][i];
                leastRow = i;
            }
        }

        int col = lastCol;
        int row = leastRow;
        while (col >= 0) {
            seam[col] = row;
            row = edgeToCol[col][row];
            col--;
        }

        return seam;
    }


    public Bag<Integer> getLinkedCols(int col, int row) {
        if (row >= p.height()) {
            throw new IllegalArgumentException("Row " + row + " is not in the picture.");
        }

        int linkedRow = row + 1;
        if (linkedRow >= p.height()) {
            throw new IllegalArgumentException("Row " + row + " is the last row in the picture.");
        }

        Bag<Integer> colBag = new Bag<>();
        for (int i = col - 1; i <= col + 1; i++) {
            if (i < 0 || i >= p.width()) {
                continue;
            }

            colBag.add(i);
        }
        return colBag;
    }

    /**
     * For finding the linked rows in the column to the right.
     * @param row
     * @param col
     * @return
     */
    public Bag<Integer> getLinkedRows(int row, int col) {
        if (col >= p.width()) {
            throw new IllegalArgumentException("Column " + col + " is not in the picture.");
        }

        int linkedCol = col + 1;
        if (linkedCol >= p.width()) {
            throw new IllegalArgumentException("Column " + col + " is the last column in the picture.");
        }

        Bag<Integer> rowBag = new Bag<>();
        for (int i = row - 1; i <= row + 1; i++) {
            if (i < 0 || i >= p.height()) {
                continue;
            }

            rowBag.add(i);
        }
        return rowBag;
    }

    public void removeHorizontalSeam(int[] seam, Picture newPic) { // index: column, value: row
        // adjust distTo and edgeTo so that they no longer point to the seam.
        // They should point to the one immediately prior. Also their energy should be recalculated?
        // also make sure all indices point to new indices
        p = newPic;

        for (int i = 1; i < seam.length; i++) { // columns
            int row = seam[i];

            edgeToCol[i][row] = edgeToCol[i - 1][row];
            distToCol[i][row] = Double.POSITIVE_INFINITY;
        }
    }

    public void removeVerticalSeam(int[] seam, Picture newPic) { // index: row, value: column
        p = newPic;

        // arrays to change:
        // edgeToRow, distToRow, energy,
        double[][] newEnergy = new double[p.width()][p.height()];
        int[][] newEdgeToRow = new int[p.width()][p.height()];
        int[][] newDistToRow = new int[p.width()][p.height()];

        for (int i = 1; i < seam.length; i++) { // rows
            int col = seam[i];

            System.arraycopy(energy[col], 0, newEnergy[col], 0, col);
            System.arraycopy(energy[col], col + 1, newEnergy[col], col, seam.length - col);
            energy = newEnergy;

            // TODO: do the same with newEdge and newDist

            edgeToRow[col][i] = edgeToRow[col][i - 1];
            distToRow[col][i] = Double.POSITIVE_INFINITY;
        }

        // find new energy values for new col and new col - 1
    }

    public static void main(String[] args) {
        Picture p = new Picture(args[0]);
        PixelMap pMap = new PixelMap(p);
        pMap.getVerticalSeam();
        pMap.getHorizontalSeam();
    }
}
