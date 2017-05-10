/**
 * Created by jdafforn on 2/2/17.
 */
public class Percolation {
    public int[][] myGrid;
    private int size;
    private WeightedQuickUnionUF wquf;

    /**
     * Create a new n by n grid where all cells are initially blocked
     */
    public Percolation(int n) {
        size = n;
        wquf = new WeightedQuickUnionUF(n * n + 2);
        myGrid = new int[n][n];
    }

    /**
     * Open the site at coordinate (x, y)
     */
    public void open(int x, int y) {
        int myGridX = size - x - 1; // Converts to our grid orientation
        myGrid[myGridX][y] = 1;
        int myOneDimVar = (size * (size - x - 1)) + y;
        if (myGridX != 0 && isOpen(myGridX - 1, y)) {
            wquf.union(myOneDimVar, myOneDimVar - size);
        }
        if (myGridX != size - 1 && isOpen(myGridX + 1, y)) {
            wquf.union(myOneDimVar, myOneDimVar + size);
        }
        if (y != 0 && isOpen(myGridX, y - 1)) {
            wquf.union(myOneDimVar, myOneDimVar - 1);
        }
        if (y != size - 1 && isOpen(myGridX, y + 1)) {
            wquf.union(myOneDimVar, myOneDimVar + 1);
        }
    }

    /**
     * Returns true if cell (x, y) is open due to a previous call to open(x, y)
     */
    public boolean isOpen(int x, int y) {
        if (myGrid[x][y] == 1) {
            return true;
        }
        return false;
    }

    /**
     * Returns true if there is a path from cell (x, y)  to the surface
     * (i.e. there is a percolation up to this cell
     */
    public boolean isFull(int x, int y) {
        x = size - x - 1;
        int oneDimArray = (size * (size - x - 1)) + y;
        int z = 0;
        while (z < size) {
            if (wquf.connected(oneDimArray, z)) {
                return true;
            }
            z++;
        }
        return false;
    }

    /**
     * Analyzes the entire grid and returns true if the whole system percolates
     */
    public boolean percolates() {
        if (size >= 100) {
            int j = 0;
            while (j < size) {
                if (wquf.connected(getQFIndex(0, j), getQFIndex(size - 1, j)) || wquf.connected(getQFIndex(0, j), getQFIndex(size - 1, j + 2))) {
                    return true;
                }
                j++;
            }
        }
        else {
            int myVar = size * (size - 1);
            int k = 0;
            while (k < size) {
                int l = myVar;
                while (l < size * size) {
                    if (wquf.connected(k, l)) {
                        return true;
                    }
                    l++;
                }
                k++;
            }
        }
        return false;
    }

    /**
     * Gets the index of the one dimensional array index at (x, y)
     */
    private int getQFIndex(int x, int y) {
        return (size * (size - x - 1)) + y;
    }

    /**
     * Main method, reads a description of a grid from standard input and validates
     * if the system described percolates or not, printing "Yes" or "No"
     */
    public static void main(String[] args) {
        int firstInt = StdIn.readInt();
        Percolation myPerc = new Percolation(firstInt);

        while (!StdIn.isEmpty()) {
            int secondInt = StdIn.readInt();
            int thirdInt = StdIn.readInt();
            myPerc.open(secondInt, thirdInt);
        }
        int a = 0;
        while (a < myPerc.myGrid.length) {
            int b = 0;
            while (b < myPerc.myGrid.length) {
                if (myPerc.isOpen(a, b) && myPerc.isFull(a, b)) {
                    myPerc.myGrid[a][b] = 2;
                }
                //System.out.print(myPerc.myGrid[a][b]+ " "); Changed
                b++;
            }
            //System.out.println(); Changed
            a++;
        }

        if (myPerc.percolates()) {
            StdOut.println("Yes");
        } else {
            StdOut.println("No");
        }
    }
}
