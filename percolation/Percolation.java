//import java.io.*;
//import java.util.*;
//import java.lang.*;

public class Percolation {
	
	// Global variables
	private WeightedQuickUnionUF uf = null;
	private boolean[][] grid;
	private int n = 0;
	private int numofsites = 0;
    
	// Constructor
	public  Percolation(int n){	
		this.grid = new boolean[n][n];
		
		// Initializing the elements in grid as all false
		for(int i = 0; i < this.grid.length; i++){
		    for(int j = 0; j < this.grid[i].length; j++) {
		        this.grid[i][j] = false;
		    }
		}
		
		this.n = n;
		this.numofsites = (n*n);
		// Initializing new object
		this.uf = new WeightedQuickUnionUF(this.numofsites + 2);
	}
	
	
	public void open(int x, int y){
		
		// Handling bad input - (0,0) to (n-1,n-1) allowed
		if(x < 0 || y < 0){
			return;
		} else if (x > (n-1) || y > (n-1)) {
			return;
		}
		// Opened by changing to true
		grid[x][y] = true;

		int site1, site2;
		site1 = encode(x,y);
		
        // Cases to check if neighboring sites are open or not
        
        if (isOpen(x-1, y) && (x != 0)) {
            uf.union(encode(x-1, y), site1); // Below
        }
        if (isOpen(x+1, y) && (x != n-1)) {
            uf.union(encode(x+1, y), site1); // Above
        }
        if (isOpen(x, y-1) && (y != 0)) {
            uf.union(encode(x, y-1), site1); // Left
        }
        if (isOpen(x, y+1) && (y != n-1)) {
            uf.union(encode(x, y+1), site1); // Right
        }
        if (x == n-1){
            uf.union(numofsites+1, site1); // Top
        }
        if (x == 0){
            uf.union(0, site1); // Bottom
        }
		
	}
	public boolean isOpen(int x, int y){
		// Handling bad input - (0,0) to (n-1,n-1) allowed
		if(x < 0 || y < 0){
			return false;
		} else if (x > (n-1) || y > (n-1)) {
			return false;
		}
		
		if(grid[x][y]){
			return true;
		}
		return false;
	}
	
	// Helper function to get site ID;
	public int encode(int x, int y){
		return (n * x+y+1);
	}
	
	public boolean isFull(int x, int y){
		if(isOpen(x, y)){
			int site = n * x + y + 1;
			if(uf.connected(this.numofsites + 1, site)){
				return true;
			}
		}
		return false;	
	}
	public boolean percolates(){
		// check if top and bottom are connected, true if percolates
		if(uf.connected( this.numofsites + 1, 0)){
			return true;
		}
		return false;
	}
	
	
	public static void main(String[] args) {
		//StdIn in = new StdIn();
		int n = StdIn.readInt();
        
		Percolation system = new Percolation(n);
		
		int x = 0; 
		int y = 0;
		
		while(StdIn.hasNextLine()){
			x = StdIn.readInt();
			y = StdIn.readInt();
			// Edge case
			if(!system.isOpen(x,y)){
				system.open(x, y);
			}	
		}
		
		if(system.percolates()){
			StdOut.println("Yes");
		} else {
			StdOut.println("No");
		}
		
	}

}
