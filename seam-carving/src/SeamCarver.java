import edu.princeton.cs.algs4.Picture;

public class SeamCarver {

    private int currWidth, currHeight;
    private Picture currPic;

    /**
     * Creates a seam carver object based on the provided picture.
     * @param picture
     */
    public SeamCarver(Picture picture) {
        if (picture == null) {
            throw new IllegalArgumentException("Null argument to constructor.");
        }

        currPic = new Picture(picture);
        currWidth = picture.width();
        currHeight = picture.height();
    }

    /**
     * @return the current picture
     */
    public Picture picture() {
        return new Picture(currPic);
    }

    /**
     * @return the width of the current picture
     */
    public     int width() {
        return currPic.width();
    }

    /**
     * @return the height of the current picture
     */
    public     int height() {
        return currPic.height();
    }

    /**
     * Determines the energy of a given pixel at column x and row y
     * @param x col of pixel
     * @param y row of pixel
     * @return pixel's energy
     */
    public  double energy(int x, int y) {
        if (x < 0 || x >= currWidth || y < 0 || y >= currHeight) {
            throw new IllegalArgumentException("Coordinates provided are out of bounds.");
        }

        /*
        * The energy of pixel (x,y) is sqrt( Δ2x(x,y)+Δ2y(x,y) )
        * */
        return Energy.getEnergy(currPic, x, y);
    }

    /**
     * @return the sequence of indices that compose a horizontal seam in the image.
     * NOTE: index of array is row. value at index is column. (CLEVER!)
     */
    public   int[] findHorizontalSeam() {
        return new PixelMap(currPic).getHorizontalSeam();
    }

    /**
     * @return the sequence of indices that compose a vertical seam in the image
     */
    public   int[] findVerticalSeam() {
        return new PixelMap(currPic).getVerticalSeam();
    }

    /**
     * Removes a horizontal seam from the current image
     * @param seam A sequence of indices that compose a horizontal seam
     */
    public    void removeHorizontalSeam(int[] seam) {   // index: column, value: row
        if (seam == null) {
            throw new IllegalArgumentException("Null argument to removeHorizontalSeam.");
        }

        if (seam.length < width()) {
            throw new IllegalArgumentException("Horizontal seam provided is the wrong length.");
        }

        if (currHeight <= 1) {
            throw new IllegalArgumentException("Image can no longer be shrunk vertically.");
        }

        // Create a new picture with height of one less than before
        Picture edited = new Picture(currPic.width(), currPic.height() - 1);

        int prevRow = seam[0];
        for (int i = 0; i < edited.width(); i++) { // columns
            int thisRow = seam[i];
            if (thisRow < prevRow - 1 || thisRow > prevRow + 1) {
                throw new IllegalArgumentException("Seam row " + thisRow + " is not valid");
            }

            prevRow = thisRow;

            for (int j = 0; j < edited.height(); j++) { // rows
                int seamRow = seam[i];

                if (j < seamRow) {  // if have not hit the seam while moving downward
                    edited.setRGB(i, j, currPic.getRGB(i, j));
                } else {
                    edited.setRGB(i, j, currPic.getRGB(i, j + 1));
                }
            }
        }

        currPic = edited;
    }

    /**
     * Removes a vertical seam from the current image
     * @param seam A sequence of indices that compose a vertical seam
     */
    public    void removeVerticalSeam(int[] seam) { // index: row, value: column
        if (seam == null) {
            throw new IllegalArgumentException("Null argument to removeVerticalSeam.");
        }

        if (seam.length < height()) {
            throw new IllegalArgumentException("Seam provided is the wrong length.");
        }

        if (currWidth <= 1) {
            throw new IllegalArgumentException("Image can no longer be shrunk on this axis.");
        }

        // Create a new picture with width of one less than before
        Picture edited = new Picture(currPic.width() - 1, currPic.height());

        // Double-for loop through each currPic vertex and assign to new image.
        // When we hit a seam, jump one column over for getting the color.
        int prevColumn = seam[0];
        for (int i = 0; i < edited.height(); i++) {    // rows
            int thisCol = seam[i];
            // check to see if column is +/- 1 than the previous column
            if (thisCol < prevColumn - 1 || thisCol > prevColumn + 1) {
                throw new IllegalArgumentException("Seam column " + thisCol + " is not valid");
            }

            prevColumn = thisCol;

            for (int j = 0; j < edited.width(); j++) { // columns
                int seamCol = seam[i];  // sets to column on current row


                if (j >= seamCol) {
                    edited.setRGB(j, i, currPic.getRGB(j + 1, i));
                } else {
                    edited.setRGB(j, i, currPic.getRGB(j, i));
                }
            }
        }

        currPic = edited;
    }

    public static void main(String[] args) {

    }
}