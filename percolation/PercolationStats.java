//import java.util.*;

public class PercolationStats {
	
	private static int n = 0;
	private static int t = 0;
	
	// Constructor
	public PercolationStats(int N, int trials){
		n = N;
		t = trials;
	}
	
	// Method calculates and returns number of open cells for each type
	public int numOpenCells(String type){
		
		int numcells = 0;
		int x = 0, y = 0;
		
		if(type.equals("fast")){
			Percolation system = new Percolation(n);
			while(true){
				x = (int) StdRandom.uniform(0, n);
				y = (int) StdRandom.uniform(0, n);
				
				if((system.percolates() == false) && (system.isOpen(x, y) == true)){
					x = (int) StdRandom.uniform(0, n);
					y = (int) StdRandom.uniform(0, n);
				} else if((system.percolates() == false) && (system.isOpen(x, y) == false)){
					system.open(x, y);
					numcells++;
				} else {
					return numcells; // get out if it percolates
				}
			}
			
		} else {
			PercolationSlow system = new PercolationSlow(n);
			while(true){
				x = (int) StdRandom.uniform(0, n);
				y = (int) StdRandom.uniform(0, n);
				
				if((system.percolates() == false) && (system.isOpen(x, y) == true)){
					x = (int) StdRandom.uniform(0, n);
					y = (int) StdRandom.uniform(0, n);
				} else if((system.percolates() == false) && (system.isOpen(x, y) == false)){
					system.open(x, y);
					numcells++;
				} else {
					return numcells; // get out if it percolates
				}
			}
		}	
	}
	
	public static void main(String[] args) {
		
		// Using Stopwatch.java to calculate total time
		Stopwatch timer1 = new Stopwatch();
		
		// Parsing given arguments
		PercolationStats sim = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
		String type = args[2];
		
		// Variables used in fast and slow types
		double times[] = new double[t];
		double pthres[] = new double[t];
		//Percolation fast = new Percolation();
		//Percolation slow = new Percolation();
		int numcells = 0; // number of times sites have to be opened
		int numofsites = (n*n);
		
		// Iterate through all trials	
		for(int i = 0; i < t; i++){
			Stopwatch timer2 = new Stopwatch();
			numcells = sim.numOpenCells(type);
			
			// fill arrays with times & thresholds for each trial 
			times[i] = (timer2.elapsedTime())*0.001; // in seconds
			pthres[i] = (double) numcells /numofsites;
		}
		
		// Calculate everything and print
		double meanthres = StdStats.mean(pthres);
		double stdthres = StdStats.stddev(pthres);
		System.out.println("mean threshold=" + meanthres);
		System.out.println("std dev=" + stdthres);

		double totaltime = timer1.elapsedTime(); 
		double meantime = StdStats.mean(times);
		double stdtime = StdStats.stddev(times);
		System.out.println("time: " + totaltime);
		System.out.println("mean time=" + meantime);
		System.out.println("stddev time=" + stdtime);	
		
	}
}
