
import java.util.*;

class SumPair {
	int i;
	int j;
	
	SumPair(int i, int j) {
		this.i = i;
		this.j = j;
	}
}

public class SumHash {
	
	private static HashMap<Integer, ArrayList<SumPair>> map = new HashMap<Integer, ArrayList<SumPair>>();
	private static ArrayList<ArrayList<Integer>> listofpairs = new ArrayList<ArrayList<Integer>>();
	private static int total = 0;
	
	// USING HASHING TO GET PAIRS WITH SAME SUM
	// O(n^2) time complexity
	public static void getPairs(int nums[]) {
		
		
		for(int i = 0; i < nums.length; i++) {
			for(int j = i + 1; j < nums.length; j++) {
				
				int sum = nums[i] + nums[j];
				
				if(!map.containsKey(sum)) {
					ArrayList<SumPair> values = new ArrayList<SumPair>();
					
					values.add(new SumPair(i,j));
					// new entry
					map.put(sum, values);
				} else {
					// add to existing values list
					map.get(sum).add(new SumPair(i,j));
				}
			}
		}
		
		// Using iterator to go through keyset 
		Iterator<Integer> it = map.keySet().iterator();
		
		
		while(it.hasNext()) {
			
			Integer key = it.next();
			ArrayList<SumPair> list = map.get(key);
			
			// to get all possible combinations of given sum with more than 2 pairs 
			for (int i = 0; i < list.size(); i++){
				for (int j = i + 1; j < list.size(); j++){
					
					if((list.get(i).i != list.get(j).i) && (list.get(i).j != list.get(j).j) && ((list.get(i).j != list.get(j).i) && (list.get(i).i != list.get(j).j))){
						
							int i0 = Math.min(list.get(i).i, list.get(i).j);
							int i2 = Math.min(list.get(j).i, list.get(j).j);
							int i1 = Math.max(list.get(i).i, list.get(i).j);
							int i3 = Math.max(list.get(j).i, list.get(j).j);
							ArrayList<Integer> l = new ArrayList<Integer>();
							l.add(i0);
							l.add(i1);
							l.add(i2);
							l.add(i3);
							listofpairs.add(l);
							total++;
					}
				}
			}
		}
		// SORT LEXOGRAPHICALLY
		sortListofpairs(listofpairs);
		
		//PRINT
		StdOut.println(total);
		for (int i = 0; i < listofpairs.size(); i++){
			StdOut.println(listofpairs.get(i).get(0) + " " + listofpairs.get(i).get(1) + " "+ listofpairs.get(i).get(2) + " " + listofpairs.get(i).get(3));
		}
	}
	
	// METHOD TO SORT LIST OF PAIRS 
	public static void sortListofpairs(ArrayList<ArrayList<Integer>> list){
		Collections.sort(list, new Comparator<ArrayList<Integer>>() {
			public int compare(ArrayList<Integer> list1, ArrayList<Integer> list2){
		        int ret = 0;
		        for (int i = 0; i <= list1.size() - 1 && ret == 0; i++){
		            ret = list1.get(i).compareTo(list2.get(i));
		        }
		        return ret;
		    }
		});
	}
	
	public static void main(String args[]) {
		// GET INPUT
		int n = StdIn.readInt();
		int i = 0;
		int[] nums = new int[n];
		
		while(!StdIn.isEmpty()){
		  nums[i] = StdIn.readInt();
		  i++;
		}
		
		// CALL getPairs TO PRINT OUTPUT
		getPairs(nums);
	}	
}
