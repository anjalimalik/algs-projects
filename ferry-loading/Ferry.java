
import java.util.*;

class conf{
	int k;
	int l;
	conf(int k, int l){
		this.k = k;
		this.l = l;
	}
}
public class Ferry {
	
	public static void maxCars(int L, int n, int[] lens){
		
		// Using hashset because don't want to visit 
		// the same configuration again and hashing ensures no duplicates
		HashSet<conf> configs = new HashSet<conf>(); // STORE CONFIGURATIONS (k, len) & KEEPS TRACK OF STATES
		int lmax = 0, rmax = 0;
		int len = 0;
		int i = 0;
		int rlen = 0;
		
		while(i < n){
			// Check if configuration is allowed
			if(len + lens[i] <= L){
				// left configuration
				lmax += 1;
				len = len + lens[i];
				configs.add(new conf(lmax, len));
				
			} else if (rlen + lens[i] <= L) {
				// right configuration
				rmax += 1;
				rlen = rlen + lens[i];
				conf c = new conf(rmax, rlen);
				configs.add(c);
			} else {
				break; //optimizes 
			}
			
			i++;
		}
		
		StdOut.println(configs.size()); // Print out number of states
	}
			
	public static void main(String[] args){
		
		//CHANGE THIS ARRAY AS NEEDED FOR TEST CASE
		int[] L = {100, 200, 500, 1000, 2000, 5000, 10000, 20000, 50000};
		
		int n = StdIn.readInt();
		int i = 0;
		int[] arr = new int[n];
		
		while(!StdIn.isEmpty()){
			arr[i] = StdIn.readInt();
			i++;
		}
		
		for(i = 0; i < L.length; i++){
			maxCars(L[i], n, arr);
		}
		
	}
}
