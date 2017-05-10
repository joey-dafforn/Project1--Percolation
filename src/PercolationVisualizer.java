import java.io.*;
import java.util.Random;

/**
 * Created by jdafforn on 2/4/17.
 */
public class PercolationVisualizer {
    public int[][] myGrid;
    private int size;
    private WeightedQuickUnionUF wquf;

    /** Create a new n by n grid where all cells are initially blocked */
    public PercolationVisualizer(int n) {
        size = n;
        wquf = new WeightedQuickUnionUF(n * n + 2);
        myGrid = new int[n][n];
    }

    /** Open the site at coordinate (x, y) */
    public void open(int x, int y) {
        int myGridX = size - x - 1; // Converts to our grid orientation
        myGrid[myGridX][y] = 1;
        int oneDimArray = (size * (size - x - 1)) + y;
        if (myGridX != 0 && isOpen(myGridX - 1, y)) {
            wquf.union(oneDimArray, oneDimArray - size);
        }
        if (myGridX != size - 1 && isOpen(myGridX + 1, y)) {
            wquf.union(oneDimArray, oneDimArray + size);
        }
        if (y != 0 && isOpen(myGridX, y - 1)) {
            wquf.union(oneDimArray, oneDimArray - 1);
        }
        if (y != size - 1 && isOpen(myGridX, y + 1)) {
            wquf.union(oneDimArray, oneDimArray + 1);
        }
        System.out.println();
    }

    /** Returns true if cell (x, y) is open due to a previous call to open(x, y) */
    public boolean isOpen(int x, int y) {
        return myGrid[x][y] == 1;
    }

    /** Returns true if there is a path from cell (x, y)  to the surface
     * (i.e. there is a percolation up to this cell */
    public boolean isFull(int x, int y) {
        x = size - x - 1;
        int oneDimArray = (size * (size - x - 1)) + y;
        int c = 0;
        while (c < size) {
            if (wquf.connected(oneDimArray, c)) {
                return true;
            }
            c++;
        }
        return false;
    }

    /** Analyzes the entire grid and returns true if the whole system percolates */
    public boolean percolates() {
        if (size >= 100) {
            int m = 0;
            while (m < size) {
                if (wquf.connected(getQFIndex(0, m), getQFIndex(size - 1, m)) || wquf.connected(getQFIndex(0, m), getQFIndex(size - 1, m + 2))) {
                    return true;
                }
                m++;
            }
        } else {
            int myVar = size * (size - 1);
            int d = 0;
            while (d < size) {
                int f = myVar;
                while (f < size * size) {
                    if (wquf.connected(d, f)) {
                        return true;
                    }
                    f++;
                }
                d++;
            }
        }
        return false;
    }

    /** Gets the index */
    private int getQFIndex(int x, int y) {
        return (size * (size - x - 1)) + y;
    }

    /** Main method, reads a description of a grid from standard input and validates
     * if the system described percolates or not, printing "Yes" or "No" */
    public static void main(String[] args) throws IOException {
        int firstInt = StdIn.readInt();
        PercolationVisualizer myVisualizer = new PercolationVisualizer(firstInt);
        PrintWriter myWriter = new PrintWriter(new FileWriter("visualMatrix.txt"));
        myWriter.print(firstInt + " ");
        myWriter.flush();
        myWriter.print("\n");
        myWriter.flush();
       // myWriter.print("\n");
        //myWriter.flush();
        while (!StdIn.isEmpty()) {
            int x = StdIn.readInt();
            int y = StdIn.readInt();
            myVisualizer.open(x, y);
            for (int i = 0; i < myVisualizer.myGrid.length; i++) {
                for (int j = 0; j < myVisualizer.myGrid.length; j++) {
                    if (myVisualizer.isOpen(i, j) && myVisualizer.isFull(i, j)) {
                        myVisualizer.myGrid[i][j] = 2;
                    }
                    myWriter.print(myVisualizer.myGrid[i][j] + " ");
                    myWriter.flush();
                    StdOut.print(myVisualizer.myGrid[i][j] + " ");
                }
                myWriter.flush();
                StdOut.println();
                myWriter.println();
            }
            myWriter.flush();
            StdOut.println();
            myWriter.println();
        }

        myWriter.close();
        System.out.println(myVisualizer.percolates());
    }
}

