import java.util.*;
import java.lang.*;

// OBJECT for records
class Record {
	int x;
	int y;
	// Constructor
	Record(int x, int y) {
		this.x = x;
		this.y = y;
	}
}

public class Filter {
	
	public static void main(String[] args) {
		// Get records
		int numRecords = StdIn.readInt();
		Record coords[] = new Record[numRecords];
		for (int i = 0; i < numRecords; i++) {
			coords[i] = new Record(StdIn.readInt(), StdIn.readInt());
		}

		// Sort array of records by their x coordinates in increasing order
		Arrays.sort(coords, new Comparator<Record>() {
			public int compare(Record a, Record b) {
				return Integer.compare(a.x, b.x);
			}
		});
		
		// Variables for copying into new array
		int lastindex = -1;
		
		// Goes through sorted array to efficiently eliminate doubly inferior records
		while (true) {
			int max = 0, index = -1;
			// Get max value of y
			for (int i = lastindex + 1; i < coords.length; i++) {
				if(Math.max(max, coords[i].y) != max){
					max = Math.max(max, coords[i].y);
					index = i;
				}
			}
			// if no more records left to get max
			if (index == -1) { break; }
			
			// Print output
			StdOut.print(coords[index].x + " ");
			StdOut.println(coords[index].y);
		
			// Update index in coords
			lastindex = index;
		}
	}
}
