
import java.util.*;

class sum{
	int val;
	int i;
	int j;
	sum (int val, int i, int j){
		this.val = val;
		this.i = i;
		this.j = j;
	}
}

//O(n^2 logn) time complexity
public class SumSort {
	
	private static ArrayList<sum> allsums = new ArrayList<sum>();
	private static ArrayList<ArrayList<Integer>> pairs = new ArrayList<ArrayList<Integer>>();
	private static int total = 0;
	
	static void get4indices (int[] nums, int len){

		// store all possible sums
		for(int x = 0; x < nums.length-1; x++){
			for(int y = x+1; y < nums.length; y++){
				sum s = new sum((nums[x] + nums[y]), x, y);
				allsums.add(s);
			}
		}
		
		// FIRST SORT
        // sort the array so that all same sums are together
		Collections.sort(allsums, new Comparator<sum>() {
			public int compare(sum s1, sum s2){
				if(s1.val > s2.val){
					return 1;
				} else if (s1.val < s2.val){
					return -1;
				}
		        return 0;
		    }
		});
		
		ArrayList<ArrayList<sum>> sameSums = new ArrayList<ArrayList<sum>>();
		ArrayList<sum> l = new ArrayList<sum>();
		
		// group indices with same sum
		for(int x = 1; x < allsums.size(); x++){
			if(allsums.get(x).val == allsums.get(x-1).val){
				l.add(allsums.get(x-1));
			} else {
				if(l != null){
					l.add(allsums.get(x-1));
					sameSums.add(l);
					l = new ArrayList<sum>();
				}
			}
		}
		
		// to get all possible combinations of given sum
		for(int x = 0; x < sameSums.size();x++){
			ArrayList<sum> list = sameSums.get(x);
			
			for (int i = 0; i < list.size(); i++){
				for (int j = i + 1; j < list.size(); j++){
							
					if((list.get(i).i != list.get(j).i) && (list.get(i).j != list.get(j).j) && ((list.get(i).j != list.get(j).i) && (list.get(i).i != list.get(j).j))){
						int i0 = Math.min(list.get(i).i, list.get(i).j);
						int i2 = Math.min(list.get(j).i, list.get(j).j);
						int i1 = Math.max(list.get(i).i, list.get(i).j);
						int i3 = Math.max(list.get(j).i, list.get(j).j);
					
						ArrayList<Integer> l1 = new ArrayList<Integer>();
						l1.add(i0);
						l1.add(i1);
						l1.add(i2);
						l1.add(i3);
						pairs.add(l1);
						total++;
					}
				}
			}
		}
		
		// SECOND SORT
	    // SORT LEXOGRAPHICALLY
		sortListofpairs(pairs);
			
		//PRINT
		StdOut.println(total);
		for (int i1 = 0; i1 < pairs.size(); i1++){
			StdOut.println(pairs.get(i1).get(0) + " " + pairs.get(i1).get(1) + " "+ pairs.get(i1).get(2) + " " + pairs.get(i1).get(3));
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
		//int nums[] = {3, 4, 9, 8};
		//int n = 4;
		
		// CALL getPairs TO PRINT OUTPUT
		get4indices(nums, n);
	}	

}
