import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final WeightedQuickUnionUF uf;
    private final WeightedQuickUnionUF bw;     // backwash UF
    private boolean[] blockedGrid;
    private final int gridDim;
    private int openSites;

    public Percolation(int n) {                // create n-by-n grid, with all sites blocked
    
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
    
        this.uf = new WeightedQuickUnionUF((n * n) + 2);
        // Do not want virtual bottom site for BW check so we only add 1 extra
        // element for the virtual top site
        this.bw = new WeightedQuickUnionUF((n * n) + 1);
        this.blockedGrid = new boolean[(n * n) + 2];
        this.gridDim = n;
        this.openSites = 0;
        // Open both virtual sites
        this.blockedGrid[0] = true;
        this.blockedGrid[(gridDim*gridDim) + 1] = true;
    }
    
    public void open(int row, int col) {       // open site (row, col) if it is not open already
    
        if (row <= 0 || row > gridDim || col <= 0 || col > gridDim) {
            throw new java.lang.IllegalArgumentException();
        }
    
        if (!this.isOpen(row, col)) {
            // convert 2d to 1d location
            int loc = xyTo1D(row, col);
            
            // check top
            if (row == 1) { 
                uf.union(0, loc);
                bw.union(0, loc);
            } else {
                if (isOpen(row - 1, col)) {
                    uf.union(loc, loc - gridDim);
                    bw.union(loc, loc - gridDim);
                }
            }
            
            // check bot
            if (row == gridDim) {
                uf.union((gridDim * gridDim) + 1, loc);
            } else {
                if (isOpen(row + 1, col)) {
                    uf.union(loc, loc + gridDim);
                    bw.union(loc, loc + gridDim);
                }
            }
            
            // check left
            if (col != 1) {
               if (isOpen(row, col - 1)) {
                    uf.union(loc, loc - 1);
                    bw.union(loc, loc - 1);
                } 
            }
            
            // check right
            if (col != gridDim) {
               if (isOpen(row, col + 1)) {
                    uf.union(loc, loc + 1);
                    bw.union(loc, loc + 1);
                } 
            }
    
            this.blockedGrid[loc] = true;
            this.openSites++;
        }
    }
    
    public boolean isOpen(int row, int col) {  // is site (row, col) open?
        
        if (row <= 0 || row > gridDim || col <= 0 || col > gridDim) {
            throw new java.lang.IllegalArgumentException();
        }
        
        return this.blockedGrid[xyTo1D(row, col)];
    }
    
    public boolean isFull(int row, int col) {  // is site (row, col) full?
        
        if (row <= 0 || row > gridDim || col <= 0 || col > gridDim) {
            throw new java.lang.IllegalArgumentException();
        }
        
        return bw.connected(xyTo1D(row, col), 0);
    }
    
    public int numberOfOpenSites() {           // number of open sites
        return this.openSites;
    }
    
    public boolean percolates() {              // does the system percolate?
        return uf.connected(0, (gridDim * gridDim) + 1);
    }

    private int xyTo1D(int row, int col) {
        return ((row - 1) * gridDim) + col;
    }

    public static void main(String[] args) {
       Percolation test = new Percolation(5);
       System.out.println(test.numberOfOpenSites());
       test.open(1, 3);
       test.open(2, 3);
       System.out.println(test.percolates());
       test.open(3, 3);
       test.open(4, 3);
       test.open(5, 3);
       System.out.println(test.numberOfOpenSites());
       System.out.println(test.percolates());
    }
}