
import java.util.Random;
import static java.lang.System.currentTimeMillis;

/**
 * Created by jdafforn on 2/6/17.
 */
public class PercolationStats {
    public static void main(String[] args) {
        //Deleted a few lines
        int gridSize = Integer.parseInt(args[0]); //Changed
        int numSimulations = Integer.parseInt(args[1]);//Changed
        String speed = args[2];//Changed
        double[] myArray = new double[numSimulations];
        double[] myTimeArray = new double[numSimulations];
        if (speed.equals("fast")) {
            int i = 0;
            while (i < numSimulations) {
                double start = System.currentTimeMillis();
                Percolation myPercolation = new Percolation(gridSize);
                int myRandomX;
                int myRandomY;
                int myRatio = 0;
                while (!myPercolation.percolates()) {
                    myRandomX = (int) (Math.random() * gridSize);
                    myRandomY = (int) (Math.random() * gridSize);
                    if (myPercolation.myGrid[gridSize - myRandomX - 1][myRandomY] == 0) {
                        myPercolation.open(myRandomX, myRandomY);
                        myRatio = myRatio + 1;
                    }
                }
                myArray[i] = myRatio / (double)(gridSize * gridSize);
                double end = System.currentTimeMillis();
                myTimeArray[i] = (end - start) / 1000.0;
                i++;
            }
        }
        else if (speed.equals("slow")) {
            int i = 0;
            while (i < numSimulations) {
                double start = System.currentTimeMillis();
                PercolationQuick myQuickPercolation = new PercolationQuick(gridSize);
                int myRandomX;
                int myRandomY;
                int myRatio = 0;
                while (!myQuickPercolation.percolates()) {
                    myRandomX = (int) (Math.random() * gridSize);
                    myRandomY = (int) (Math.random() * gridSize);
                    if (myQuickPercolation.myGrid[gridSize - myRandomX - 1][myRandomY] == 0) {
                        myRatio = myRatio + 1;
                        myQuickPercolation.open(myRandomX, myRandomY);
                    }
                }
                myArray[i] = myRatio / (double)(gridSize * gridSize);
                double end = System.currentTimeMillis();
                myTimeArray[i] = (end - start) / 1000.0;
                i++;
            }
        }
        System.out.println("mean threshold=" + StdStats.mean(myArray));
        System.out.println("std dev=" + StdStats.stddev(myArray));
        System.out.println("time=" + StdStats.sum(myTimeArray));
        System.out.println("mean time=" + StdStats.mean(myTimeArray));
        System.out.println("stddev time=" + StdStats.stddev(myTimeArray));
    }
}
