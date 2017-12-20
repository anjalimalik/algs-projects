/* Quick 3 way sorting */

/*
*  Cutoff to insertion sort:  Switch to insertion sort for tiny arrays of length equal to or less than 8.
*
*  Median-of-three partitioning: Use the median of a small sample of items taken from the array as the partitioning item.
*  Doing so will give a slightly better partition, but at the cost of computing the median.
*  It turns out that most of the available improvement comes from choosing a sample
*  of size 3 (and then partitioning on the middle item). Only for elements <= 40.
*
*  Tukey ninther: Use the Tukey ninther technique when the previous optimizations do not apply. In the
*  Tukey ninether method, the partitioning element is selected as the median of 3 medians of three.
*  Specifically, if 9 evenly sampled values are: (v0, v1, v2, v3, v4, v5, v6, v7, v8)
*           m0 = median(v0, v1, v2)
*           m1 = median(v3, v4, v5)
*           m2 = median(v6, v7, v8)
*  median(m0, m1, m2) is then the partitioning element.
*/


import java.io.*;
import java.util.*;
public class Quick3WayBM{

	public static void sort(Comparable[] a) {	
		sort(a, 0, a.length - 1); 
	}
	public static void sort(Comparable[] a, int lo, int hi) {
		
		int check = 0;
		// edge case
		if(hi <= lo){ return; } 
	
		// Insertion sort
		if ((hi-lo) < 8){
			int arr[] = {lo, 0, 0, 0, 0,0, hi};
			output("Insertion Sort", a, arr);
			insertionSort(a, lo, hi);
			check--;
			return;
		}
		
		// Median of 3 
		if((hi-lo) <= 40){
			int me = medianOf3(a, lo, ((hi + lo)/2), hi);
			swap(a, lo, me);			//me is selected as the partitioning element
		}
		
		// Use Tukey ninther as partitioning element
		if((hi-lo) > 40){
			int par = tukeyNinther(a, lo, hi);
			swap(a, lo, par);			//par is selected as the partitioning element
			check++;
		}
		
		
        // BM 3-way partitioning
        int i, j, p, q;
        
        //Initialize
        i = p = lo;
        j = q = hi;
        Comparable v = a[lo]; // partition element

        j++;
        q++;
        
        while (i <= j) {
        	i++;
            j--;
            
            while (i < hi && comp(a[i], v) < 0){ i++; }
            while (j > lo && comp(a[j], v) > 0){ j--; }
               
            if (i == j){
            	if(comp(a[i], v) == 0){
            		p++;
            		swap(a, p, i);
            	}
            }
            

            if (i >= j) break;
            swap(a, i, j);
         
            if (comp(a[i], v) == 0){
            	p++;
            	swap(a, p, i);
            }
            if (comp(a[j], v) == 0){
            	q--;
            	swap(a, q, j);
            }
        }

        int part[] = {lo, Integer.parseInt(v.toString()), p, i, j, q, hi};
        i = j;

        for (int x = hi; x >= q; x--){
        	i++;
            swap(a, x, i);
        }
        for (int k = lo; k <= p; k++){
            swap(a, k, j);
            j--;
        }
 

		// PRINT OUTPUT before moving equal values
		if(check == 0){
			output("Median of 3", a, part);
		} else if (check > 0){
			output("Tukey Ninther", a, part);
		} 
		//StdOut.println(Arrays.toString(a));
        sort(a, lo, j);
        //StdOut.println(Arrays.toString(a));
        sort(a, i, hi);

    }

	// Swap function
	public static void swap(Comparable[] a, int i, int j){
		Comparable swap = a[i];
		a[i] = a[j];
		a[j] = swap;
	}
	
	// Custom compare function for integers only (part 1) because
	// compareTo is not functioning properly for this problem
	public static int comp(Comparable a, Comparable b){
		
		String x = (a.toString());
		String y = (b.toString());
		if(x.equals(y)){ return 0; } 
		else if(x.length() != y.length()){ 
		      if (x.length() < y.length()) { return -1; } 
		      else { return 1; }
		} else {
			int xx = Integer.parseInt(x);
			int yy = Integer.parseInt(y);
			if(xx > yy) { return 1; }
			else { return -1; }
		}
		//return 0;
	}
	
	// Method for printing output
	public static void output(String s, Comparable[] a, int[] par){
		if(s.equals("Insertion Sort")){
			StdOut.println("("+ s + ", " + par[0] + ", " + par[6] + ")");
		} else {
			StdOut.println("(" + s + ", " + par[0] + ", " + par[1] + ", " + par[2] + ", " + par[3] + ", " + par[4] + ", " + par[5] + ", " + par[6]+ ")");
		}
	}
	
	// Insertion Sort method
	public static void insertionSort(Comparable[] a, int lo, int hi){
        for (int i = lo; i <= hi; i++) {
            int j = i;
            while (j > 0){
                if(Integer.parseInt(a[j-1].toString()) <= Integer.parseInt(a[j].toString())) { break; } 
            	swap(a, j, j-1);
                j--;
            } 
        }
	}
	
	// Median of 3 method
	public static int medianOf3(Comparable[] a, int lo, int me, int hi){
		// swap so that leftmost element is smallest and rightmost is largest
		if(comp(a[lo], a[me]) > 0){ swap(a, lo, me); }
		if(comp(a[lo], a[hi]) > 0){ swap(a, lo, hi); } 
		if(comp(a[me], a[hi]) > 0){ swap(a, me, hi); }
		return me;
	}
	
	// Tukey Ninther Method
	public static int tukeyNinther(Comparable[] a, int lo, int hi){
		// dividing into 8 subarrays to find three medians of 3. 
		int range = (hi-lo);
		int v0 = lo;
		int v1 = lo + (range/8);
		int v2 = lo + (range/4);
		int v3 = lo + (3*(range/8));
		int v4 = lo + (range/2);
		int v5 = hi - (3*(range/8));
		int v6 = hi - (range/4);
		int v7 = hi - (range/8);
		int v8 = hi;
		
		int m0 = medianOf3(a, v0, v1, v2);
		int m1 = medianOf3(a, v3, v4, v5);
		int m2 = medianOf3(a, v6, v7, v8);
		
		int result = (medianOf3(a, m0, m1, m2));
		return result;
	}

	public static void main(String[] args) throws FileNotFoundException{
		
		int num = StdIn.readInt();
		String a[] = StdIn.readAllStrings();
		//File newFile = new File("examples/numbers1.txt");
		//Scanner scan = new Scanner(newFile);
		//int size = scan.nextInt();
		//String a[] = new String[size];
		//int i = 0;
		//while(scan.hasNextInt()) {
		//	Integer temp = scan.nextInt();
		//	a[i] = temp.toString();
		///	i++;
		//}
		//StdOut.println(Arrays.toString(a));
		sort(a);
		//StdOut.println(Arrays.toString(a));
		//scan.close();
	}
}
