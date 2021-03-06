/**
 * Created by jdafforn on 2/2/17.
 */
public class PercolationQuick {
    public int[][] myGrid;
    private int size;
    private QuickUnionUF wquf;

    /** Create a new n by n grid where all cells are initially blocked */
    public PercolationQuick(int n) {
        size = n;
        wquf = new QuickUnionUF(n * n + 2);
        myGrid = new int[n][n];
    }

    /** Open the site at coordinate (x, y) */
    public void open(int x, int y) {
        int myGridX = size - x - 1; // Converts to our grid orientation
        myGrid[myGridX][y] = 1;
        int myOneDVar = (size * (size - x - 1)) + y;
        if (myGridX != size - 1 && isOpen(myGridX + 1, y)) {
            wquf.union(myOneDVar, myOneDVar + size);
        }
        if (myGridX != 0 && isOpen(myGridX - 1, y)) {
            wquf.union(myOneDVar, myOneDVar - size);
        }
        if (y != size - 1 && isOpen(myGridX, y + 1)) {
            wquf.union(myOneDVar, myOneDVar + 1);
        }
        if (y != 0 && isOpen(myGridX, y - 1)) {
            wquf.union(myOneDVar, myOneDVar - 1);
        }
    }

    /** Returns true if cell (x, y) is open due to a previous call to open(x, y) */
    public boolean isOpen(int x, int y) {
        if (myGrid[x][y] == 1) {
            return true;
        }
        return false;
    }

    /** Returns true if there is a path from cell (x, y)  to the surface
     * (i.e. there is a percolation up to this cell */
    public boolean isFull(int x, int y) {
        x = size - x - 1;
        int oneDimArray = (size * (size - x - 1)) + y;
        int i = 0;
        while (i < size) {
            if (wquf.connected(oneDimArray, i)) {
                return true;
            }
            i++;
        }
        return false;
    }

    /** Analyzes the entire grid and returns true if the whole system percolates */
    public boolean percolates() {
        if (size >= 100) {
            int i = 0;
            while (i < size) {
                if (wquf.connected(getQFIndex(0, i), getQFIndex(size - 1, i)) || wquf.connected(getQFIndex(0, i), getQFIndex(size - 1, i + 2))) {
                    return true;
                }
                i++;
            }
        }
        else {
            int myVar = size * (size - 1);
            int i = 0;
            while (i < size) {
                int j = myVar;
                while (j < size * size) {
                    if (wquf.connected(i, j)) {
                        return true;
                    }
                    j++;
                }
                i++;
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
    public static void main(String[] args) {
        int firstInt = StdIn.readInt();
        PercolationQuick p = new PercolationQuick(firstInt);

        while (!StdIn.isEmpty()) {
            int secondInt = StdIn.readInt();
            int thirdInt = StdIn.readInt();
            p.open(secondInt, thirdInt);
        }
        int i = 0;
        while (i < p.myGrid.length) {
            int j = 0;
            while (j < p.myGrid.length) {
                if (p.isOpen(i, j) && p.isFull(i, j)) {
                    p.myGrid[i][j] = 2;
                }
                //System.out.print(p.myGrid[i][j]+ " "); Changed
                j++;
            }
            //System.out.println(); Changed
            i++;
        }

        if (p.percolates()) {
            StdOut.println("Yes");
        } else {
            StdOut.println("No");
        }
    }
}

