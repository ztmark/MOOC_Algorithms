import java.awt.Color;

/**
 * Author: Mark
 * Date  : 2015/4/8
 * Time  : 15:53
 */
public class SeamCarver {

    private Color[][] imageColors;
    private int width;
    private int height;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        width = picture.width();
        height = picture.height();
        imageColors = new Color[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                imageColors[i][j] = picture.get(j, i);
            }
        }
    }

    // current picture
    public Picture picture() {
        Picture picture = new Picture(width, height);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                picture.set(j, i, imageColors[i][j]);
            }
        }
        return picture;
    }

    // width of current picture
    public int width() {
        return width;
    }

    // height of current picture
    public int height() {
        return height;
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            throw new IndexOutOfBoundsException();
        }
        // not border pixels
        if (0 < x && x < width - 1 && 0 < y && y < height - 1) {
            return xPart(x, y) + yPart(x, y);
        }
        return 195075.0;
    }

    private double xPart(int x, int y) {
        double r = Math.abs(imageColors[y][x-1].getRed() - imageColors[y][x+1].getRed());
        double g = Math.abs(imageColors[y][x-1].getGreen() - imageColors[y][x+1].getGreen());
        double b = Math.abs(imageColors[y][x-1].getBlue() - imageColors[y][x+1].getBlue());
        return r * r + g * g + b * b;
    }

    private double yPart(int x, int y) {
        double r = Math.abs(imageColors[y - 1][x].getRed() - imageColors[y + 1][x].getRed());
        double g = Math.abs(imageColors[y - 1][x].getGreen() - imageColors[y + 1][x].getGreen());
        double b = Math.abs(imageColors[y - 1][x].getBlue() - imageColors[y + 1][x].getBlue());
        return r * r + g * g + b * b;
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        Color[][] old = imageColors;
        int oldWidth = width;
        int oldHeight = height;
        width = oldHeight;
        height = oldWidth;
        imageColors = new Color[height][width];
        for (int i = 0; i < oldHeight; i++) {
            for (int j = 0; j < oldWidth; j++) {
                imageColors[j][i] = old[i][j];
            }
        }
        int[] ans = findVerticalSeam();
        imageColors = old;
        width = oldWidth;
        height = oldHeight;
        return ans;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        double[][] energies = new double[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                energies[i][j] = energy(j, i);
            }
        }
        double[][] path = new double[height][width];
        int[][] edge = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                path[i][j] = Double.MAX_VALUE;
            }
        }
        for (int i = 0; i < width; i++) {
            path[0][i] = energies[0][i];
            edge[0][i] = i;
        }
        for (int i = 0; i < height - 1; i++) {
            for (int j = 0; j < width; j++) {
                if (path[i][j] + energies[i+1][j] < path[i+1][j]) {
                    path[i+1][j] = path[i][j] + energies[i+1][j];
                    edge[i+1][j] = j;
                }
                if (j != 0) {
                    if (path[i][j] + energies[i+1][j-1] < path[i+1][j-1]) {
                        path[i+1][j-1] = path[i][j] + energies[i+1][j-1];
                        edge[i+1][j-1] = j;
                    }
                }
                if (j != width - 1) {
                    if (path[i][j] + energies[i+1][j+1] < path[i+1][j+1]) {
                        path[i+1][j+1] = path[i][j] + energies[i+1][j+1];
                        edge[i+1][j+1] = j;
                    }
                }
            }
        }
        int[] ans = new int[height];
        double min = Double.MAX_VALUE;
        int minIndex = -1;
        for (int i = 0; i < width; i++) {
            if (path[height-1][i] < min) {
                min = path[height-1][i];
                minIndex = i;
            }
        }
        if (minIndex != -1) {
            ans[height-1] = minIndex;
            for (int i = height-2; i >= 0; i--) {
                ans[i] = edge[i+1][ans[i+1]];
            }
        }
        return ans;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam == null) {
            throw new NullPointerException();
        }
        if (seam.length != width) {
            throw new IllegalArgumentException();
        }
        check(seam);
        Color[][] old = imageColors;
        int oldWidth = width;
        int oldHeight = height;
        width = oldHeight;
        height = oldWidth;
        imageColors = new Color[height][width];
        for (int i = 0; i < oldHeight; i++) {
            for (int j = 0; j < oldWidth; j++) {
                imageColors[j][i] = old[i][j];
            }
        }
        removeVerticalSeam(seam);
        width = oldWidth;
        height = --oldHeight;
        Color[][] tmp = new Color[height][width];
        for (int i = 0; i < oldWidth; i++) {
            for (int j = 0; j < oldHeight; j++) {
                tmp[j][i] = imageColors[i][j];
            }
        }
        imageColors = tmp;
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (seam == null) {
            throw new NullPointerException();
        }
        if (seam.length != height) {
            throw new IllegalArgumentException();
        }
        check(seam);
        Color[][] colors = new Color[height][width-1];
        for (int i = 0; i < seam.length; i++) {
            System.arraycopy(imageColors[i], 0, colors[i], 0, seam[i]);
            System.arraycopy(imageColors[i], seam[i]+1, colors[i], seam[i], width-seam[i]-1);
        }
        imageColors = colors;
        --width;
    }

    private void check(int[] seam) {
        for (int i = 1; i < seam.length; i++) {
            if (Math.abs(seam[i] - seam[i-1]) > 1) {
                throw new IllegalArgumentException();
            }
        }
    }
}
