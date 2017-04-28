import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
  Author: Coy Sanders
  Written: 4/24/2017
  Last Updated: 4/27/2017

  Compilation: javac-algs4 Percolation.java
  Execution: N/A, used in PercolationClass.java

  Implement a class

  TODO https://www.coursera.org/learn/algorithms-part1/discussions/all?q=incorrect+algs4.jar

*/
public class Percolation {

    /*
        Notes: row and column indicies are between 1 and n, (1,1) upper left site.
        Throw java.lang.IndexOutOfBoundsException if any arg to open(), isOpen(), isFull() is outside    the prescribed range.
        Constructor should throw a java.lang.IllegalArgumentException if n < 0
        
    */

    private boolean[] cells;
    private int numOpenSites;
    private int gridDimension;
    private WeightedQuickUnionUF uf;
    private int virtualTopSite, virtualBottomSite;


    // False means the site is full, true means it open
    public Percolation(int n) {
        
        if (n <= 0) throw new java.lang.IllegalArgumentException("n must be greater than 0");

        numOpenSites = 0;
        gridDimension = n;
        cells = new boolean[n*n];
        virtualTopSite = n*n;
        virtualBottomSite = n*n+1;

        uf = new WeightedQuickUnionUF((n*n)+2);
        for (int i = 1; i <= n; i++) {
            uf.union(virtualTopSite, getCellIndex(1, i)); // row 1 is the top row, connect all nodes on that row to top site
            uf.union(virtualBottomSite, getCellIndex(gridDimension, i)); // gridDimension is the bottom row, connect all nodes on that row to the bottom site

        }
    }

    /**
			row and col are 1-indexed
		*/
    private int getCellIndex(int row, int col) {
        return (row-1)*gridDimension + (col-1);
    }


    /**
      Checks if the cell requested is within the grid. row and col start at index 1.
    */
    private void checkIfInBounds(int row, int col) {
        if (row < 0 || row > gridDimension) {
            throw new java.lang.IndexOutOfBoundsException("row index " + row + " is out of bounds");
        }
        if (col < 0 || col > gridDimension) {
            throw new java.lang.IndexOutOfBoundsException("col index " + col + " is out of bounds");
        }
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        checkIfInBounds(row, col);
        int cellNum = getCellIndex(row, col);

        if (cells[cellNum]) return; // already opened
        cells[cellNum] = true; // marks it open
        numOpenSites++;

//System.out.println("open("+row+","+col+")");
        // Now add connections
        // East
        if (row + 1 <= gridDimension && this.isOpen(row+1, col)) {
            uf.union(cellNum, getCellIndex(row+1, col));
        }    
        // West
        if (row - 1 > 0 && this.isOpen(row-1, col)) {
            uf.union(cellNum, getCellIndex(row-1, col));
        }
        // North
        if (col + 1 <= gridDimension && this.isOpen(row, col+1)) { 
            uf.union(cellNum, getCellIndex(row, col+1));
        }
        // South
        if (col - 1 > 0 && this.isOpen(row, col-1)) {
            uf.union(cellNum, getCellIndex(row, col-1));
        }
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
//System.out.println("isOpen("+row+","+col+")");
        checkIfInBounds(row, col);
        return cells[getCellIndex(row, col)];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        checkIfInBounds(row, col);
        return !cells[getCellIndex(row, col)];
    }

    // number of open sites
    public int numberOfOpenSites() {
        return numOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.connected(virtualBottomSite, virtualTopSite);
    }

}
