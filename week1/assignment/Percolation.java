import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
  @author: Coy Sanders
  @version: 4/28/2017
  Compilation: javac-algs4 Percolation.java
  Execution: N/A, used in PercolationClass.java

  Implement a class that creates a n-by-n grid of sites that are blocked by default.
  Sites can be opened and the grid can percolate if a site on the bottom row can connect to a site on the top row via a chain
   of neighboring (north, south, west, or east) sites.

  Source: http://coursera.cs.princeton.edu/algs4/assignments/percolation.html

*/
public class Percolation {

    private boolean[] sites;
    private int numOpenSites, gridDimension; 
    private int virtualTopSite, virtualBottomSite;
    private WeightedQuickUnionUF ufPercolationCheck, ufFullCheck;

    /**
        Creates the n-by-n grid of sites that are blocked by default.
        It also creates two WeightedQuickUnionUF objects;
          one is used to check if the system percolates,
          the other will be used to assess whether or not a site is full.
        <p>
        Note: The second WeightedQuickUnionUF object is needed because we can't use the first one to check if a site is full.

        Take the following example of a 3x3 grid (X means site is blocked, O means open):

          T <-- virtual top site, connected to all sites on top row
        X X O
        X X O
        O X O
          B <-- virtual bottom site, connected to all sites on the bottom row

        Site at (3,1) (bottom left corner) would be considered full because it can connect to the
         top row via the virtual bottom site:

          (3,1) --> B --> (3,3) --> (2,3) --> (1,3) --> T

        However it should not be considered full because B doesn't really exist, it is just used to more efficiently check for
         Percolation. The second union-find object will just be used to check if a site is full (i.e. don't create any
         connections with virtual bottom site.

      @param n number of sites on one side of the square grid
      @throws IllegalArgumentException if n is a non positive value
    */
    public Percolation(int n) {
        
        if (n <= 0) throw new java.lang.IllegalArgumentException("n must be greater than 0");

        numOpenSites = 0;
        gridDimension = n;
        sites = new boolean[n*n];
        virtualTopSite = n*n;
        virtualBottomSite = n*n+1;

        ufPercolationCheck = new WeightedQuickUnionUF((n*n)+2); // Needs two extra virtual sites
        ufFullCheck = new WeightedQuickUnionUF((n*n)+1); // Only needs one extra virtual site
    }



    /**
      Opens site (row, col) if it is not open already. 
      @param row row of the site to open
      @param col column of the site to open
    */
    public void open(int row, int col) {

        checkIfInBounds(row, col);
        int cellNum = getSiteIndex(row, col);
        if (sites[cellNum]) return; // already opened
        sites[cellNum] = true; // marks it open
        numOpenSites++;

        // Now add connections between any adjacent open sites
        // East
        if (row + 1 <= gridDimension && this.isOpen(row+1, col)) {
            ufPercolationCheck.union(cellNum, getSiteIndex(row+1, col));
            ufFullCheck.union(cellNum, getSiteIndex(row+1, col));
        }    
        // West
        if (row - 1 > 0 && this.isOpen(row-1, col)) {
            ufPercolationCheck.union(cellNum, getSiteIndex(row-1, col));
            ufFullCheck.union(cellNum, getSiteIndex(row-1, col));
        }
        // North
        if (col + 1 <= gridDimension && this.isOpen(row, col+1)) { 
            ufPercolationCheck.union(cellNum, getSiteIndex(row, col+1));
            ufFullCheck.union(cellNum, getSiteIndex(row, col+1));
        }
        // South
        if (col - 1 > 0 && this.isOpen(row, col-1)) {
            ufPercolationCheck.union(cellNum, getSiteIndex(row, col-1));
            ufFullCheck.union(cellNum, getSiteIndex(row, col-1));
        }

        // Check if the site needs to be connected to any virtual sites.

        // If the site is on the top row, connect to virtual top on both union-find objects.
        if (row == 1) {
            ufPercolationCheck.union(virtualTopSite, cellNum);
            ufFullCheck.union(virtualTopSite, cellNum);
        }

        // If the site is on the bottom row, connect to virtual bottom on the union-find object used to check for percolation.
        if (row == gridDimension) {
            ufPercolationCheck.union(virtualBottomSite, cellNum);
        }
    }

    /**
      Checks if a site at a given coordinate is open.
      @param row row of the site to check (range 1-n)
      @param col column of the site to check (range 1-n)
      @return returns true if the site is open, false if otherwise
    */
    public boolean isOpen(int row, int col) {
        checkIfInBounds(row, col);
        return sites[getSiteIndex(row, col)];
    }

    /**
      Checks if a site at a given coordinate is full.
      @param row row of the site to check (range 1-n)
      @param col column of the site to check (range 1-n)
      @return returns true if the site is full, false if otherwise
    */
    public boolean isFull(int row, int col) {
        return isOpen(row, col) && ufFullCheck.connected(getSiteIndex(row, col), virtualTopSite);
    }

    /**
      Returns the number of sites that are open on the grid.
      @return number of sites that are open on the grid.
    */
    public int numberOfOpenSites() {
        return numOpenSites;
    }

    /**
      Checks whether or not the system can percolate.
      @return returns true if the system percolates, false if otherwise.
    */
    public boolean percolates() {
        return ufPercolationCheck.connected(virtualBottomSite, virtualTopSite);
    }

    /**
      Helper method that grabs the site index of sites object.
      @param row row of the site (range 1-n)
      @param col column of the site (range 1-n)
      @return returns the index of the appropriate site, adjusted for zero-index
    */
    private int getSiteIndex(int row, int col) {
        return (row-1)*gridDimension + (col-1);
    }


    /**
      Helper method that checks if the location requested is within the Percolation grid dimensions.
      @param row row of the site (range 1-n)
      @param col column of the site (range 1-n)
      @throws IndexOutOfBoundsException if there is no site at the coordinate requested.
    */
    private void checkIfInBounds(int row, int col) {
        if (row <= 0 || row > gridDimension) {
            throw new java.lang.IndexOutOfBoundsException("row index " + row + " is out of bounds");
        }
        if (col <= 0 || col > gridDimension) {
            throw new java.lang.IndexOutOfBoundsException("col index " + col + " is out of bounds");
        }
    }


    /**
      Helper method that prints out the grid. An X indicates an open site, a dash means it's blocked.
    */
    private void printGrid() {
      for (int i = 1; i <= gridDimension; i++) {
        for (int j = 1; j <= gridDimension; j++) {
          if (this.isOpen(i, j)) {
            System.out.print(" X");
          } else {
            System.out.print(" -");
          }
        }
        System.out.println();
      }
    }


    /**
      Helper method that prints out the grid. An F indicates a full site, a dash means not full.
    */
    private void printFullSites() {
      for (int i = 1; i <= gridDimension; i++) {
        for (int j = 1; j <= gridDimension; j++) {
          if (this.isFull(i, j)) {
            System.out.print(" F");
          } else {
            System.out.print(" -");
          }
        }
        System.out.println();
      }
    }
}
