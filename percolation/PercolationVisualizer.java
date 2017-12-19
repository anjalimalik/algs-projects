//import java.io.*;

public class PercolationVisualizer {

	public static void visualize(Percolation system, int n){
		
		
		// Setting up window without cells
		StdDraw.clear();
		StdDraw.setCanvasSize(600,600);
		StdDraw.filledSquare((n/2.0), (n/2.0), (n/2.0));
		
		// iterating through entire grid and drawing them on the window
		for(int i = 0; i < n; i++){
			for(int j = 0; j < n; j++){
				
				if(system.isOpen(i,j)){
					StdDraw.setPenColor(StdDraw.WHITE);
				}
				if(system.isFull(i,j)){
					StdDraw.setPenColor(StdDraw.BLUE);
				}
				if(!system.isOpen(i,j)){
					StdDraw.setPenColor(StdDraw.BLACK);
				}
				StdDraw.filledSquare((j + 0.5), (n - i - 0.5), 0.45);
			}
		}
	}
	public static void main(String[] args) {
		try{
			int n = StdIn.readInt();
			Percolation system = new Percolation(n);
			int x = 0, y = 0;
			
			// Base case
			StdDraw.show(0);
			visualize(system, n);
			StdDraw.show(100);

			// Animate until entirely visualized
			while(StdIn.hasNextLine()){
				x = StdIn.readInt();
				y = StdIn.readInt();
				system.open(x, y);
				visualize(system, n);
				StdDraw.show(100);
			}
			
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
