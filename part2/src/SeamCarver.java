import java.awt.Color;

/**
 * Author: Mark
 * Date  : 2015/4/8
 * Time  : 15:53
 */
public class SeamCarver {

    private int[][] rgbs;
    private int width;
    private int height;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        width = picture.width();
        height = picture.height();
        rgbs = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                rgbs[i][j] = picture.get(j, i).getRGB();
            }
        }
    }

    // current picture
    public Picture picture() {
        Picture picture = new Picture(width, height);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                picture.set(j, i, new Color(rgbs[i][j]));
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
        double r = Math.abs(getRed(rgbs[y][x-1]) - getRed(rgbs[y][x + 1]));
        double g = Math.abs(getGreen(rgbs[y][x-1]) - getGreen(rgbs[y][x + 1]));
        double b = Math.abs(getBlue(rgbs[y][x - 1]) - getBlue(rgbs[y][x + 1]));
        return r * r + g * g + b * b;
    }

    private double yPart(int x, int y) {
        double r = Math.abs(getRed(rgbs[y - 1][x]) - getRed(rgbs[y + 1][x]));
        double g = Math.abs(getGreen(rgbs[y - 1][x]) - getGreen(rgbs[y + 1][x]));
        double b = Math.abs(getBlue(rgbs[y - 1][x]) - getBlue(rgbs[y + 1][x]));
        return r * r + g * g + b * b;
    }

    private int getRed(int rgb) {
        return (rgb >> 16) & 0xFF;
    }

    private int getGreen(int rgb) {
        return (rgb >> 8) & 0xFF;
    }

    private int getBlue(int rgb) {
        return (rgb >> 0) & 0xFF;
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        int[][] old = rgbs;
        int oldWidth = width;
        int oldHeight = height;
        width = oldHeight;
        height = oldWidth;
        rgbs = new int[height][width];
        for (int i = 0; i < oldHeight; i++) {
            for (int j = 0; j < oldWidth; j++) {
                rgbs[j][i] = old[i][j];
            }
        }
        int[] ans = findVerticalSeam();
        rgbs = old;
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
        int[][] old = rgbs;
        int oldWidth = width;
        int oldHeight = height;
        width = oldHeight;
        height = oldWidth;
        rgbs = new int[height][width];
        for (int i = 0; i < oldHeight; i++) {
            for (int j = 0; j < oldWidth; j++) {
                rgbs[j][i] = old[i][j];
            }
        }
        removeVerticalSeam(seam);
        width = oldWidth;
        height = --oldHeight;
        int[][] tmp = new int[height][width];
        for (int i = 0; i < oldWidth; i++) {
            for (int j = 0; j < oldHeight; j++) {
                tmp[j][i] = rgbs[i][j];
            }
        }
        rgbs = tmp;
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
        int[][] colors = new int[height][width-1];
        for (int i = 0; i < seam.length; i++) {
            System.arraycopy(rgbs[i], 0, colors[i], 0, seam[i]);
            System.arraycopy(rgbs[i], seam[i]+1, colors[i], seam[i], width-seam[i]-1);
        }
        rgbs = colors;
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
