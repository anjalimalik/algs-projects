/* Program: A command-line program that performs plagiarism detection 
 * 			using a N-tuple comparison algorithm allowing for synonyms 
 * 			in the text.
 * File: PlagiarismDetection
 * 
 * Author: Anjali Malik
 * 
 */

package tripAdvisor_CodingAssignment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class PlagiarismDetection {
	
	// Constant variables
	private static final int DEFAULT_N = 3;
	
	// Instance variables 
	private String syns;
	private String file1;
	private String file2;
	private int tupleSize;
	private HashMap<String, HashSet<String>> synonyms;
	
	// Constructor
	public PlagiarismDetection(String syns, String file1, String file2, int tupleSize) {
		this.syns = syns;
		this.file1 = file1;
		this.file2 = file2;
		this.tupleSize = tupleSize;
		this.synonyms = new HashMap<String, HashSet<String>>();
	}
	
	/* Method: This method checks if the .txt filename is valid or not
	 * Parameters: Filename of input file
	 * Return: boolean; true if filename is valid, false otherwise
	 */
	public static boolean validateInput(String filename){
		
		/* Assumption: 	Only .txt files are allowed.
		 * 				Otherwise, if needed, regex could be altered to include more extensions
		 */
		
		if(filename.matches("[-_. A-Za-z0-9]+\\.(txt)")){ 
			return true;
		} 
		return false;
	}
	
	/* Method: This method is used to print usage instructions as required by problem statement
	 * 			in case of insufficient provided arguments
	 * Parameters: none
	 * Return: void
	 */
	public static void printUsageInstructions(){
		System.out.println("\n3 required arguments and 1 optional argument are needed for Plagiarism Detection.");
		System.out.println("\t\t1. file name for a list of synonyms, containing a group of synonyms per line");
		System.out.println("\t\t2. filename for input file 1");
		System.out.println("\t\t3. filename for input file 2");
		System.out.println("\t\t4. (optional) Integer value of N, the tuple size\n");
	}
	

	/* Method: This method call methods in getInput file to populate list of tuples
	 *  		from both input files as well as extract synonyms from syns file.
	 *  		It iterates through all the tuples in each line in both the files, and increments
	 *  		number of same or similar tuples as they are compared to check for equality and 
	 *  		synonyms using the list of synonyms in the hashmap.
	 * Parameters: none
	 * Return: double; result percentage to be printed
	 */
	public double computePercentage() throws Exception{
		
		
		// Populate synonyms hashmap and arraylists of tuples from given input files to compare
		getInput in = new getInput();
		synonyms = in.getSynonyms(syns);
		ArrayList<ArrayList<String>> tuples1 = in.getTuples(file1, tupleSize);
		ArrayList<ArrayList<String>> tuples2 = in.getTuples(file2, tupleSize);
		
		
		boolean check = true; 			// Used to check if two strings are same or synonyms of each other
		int numofDetectedTuples = 0;	// Used to keep track of number of similar/same tuples
		
		int i = 0, j; // counters
		 
		// First while loop iterates through the list of tuples from first input file
		while(i < tuples1.size()){
			
			ArrayList<String> t1 = tuples1.get(i);
			j = 0;
			
			// Second while loop iterates through the list of tuples from second input file
			while(j < tuples2.size()){
				
				ArrayList<String> t2 = tuples2.get(j);
				
				/* Assumption: 	For the simplicity of the program, this method only compares
				 * 				strings at the same index in both lists of tuples
				 * 				Example: "go for a run" and "for a run go" will not yield
				 * `			a 100% score because of discrepancy in the placement of the 
				 * 				words. 
				 */
				
				// Comparing strings in both lists of tuples of size N
				for (int idx = 0; idx < tupleSize; idx++) {
					
					String s1 = t1.get(idx);
					String s2 = t2.get(idx);
					
					if(s1.equals(s2)){
						check = true; 		// if strings are equal, let check be true
					} else {
						// if two strings are not equal, they must be in the
						// list of synonyms, otherwise check becomes false and loop breaks.
						if (synonyms.containsKey(s1)) {
							if(!synonyms.get(s1).contains(s2)){ // Not synonyms
								check = false;
								break;
							}
						} else {
							check = false;
							break;
						}
					}
				}
				
				// Only if check if still true, increment the number of detected tuples and then break (for increased efficiency)
				if(check){
					numofDetectedTuples++;
					break;
				}
				j++;
			}
			i++;
		}
		
		// Calculate percentage by dividing number of same/similar tuples found with total number of tuples in either tuples1 or tuples2
		double percentage = (double) numofDetectedTuples/ (double) tuples1.size();
		
		return (percentage * 100);
	}
	
	/* Method: Driver Main Function
	 * Parameters: String array of arguments
	 * Return: void
	 */
	public static void main(String[] args) throws Exception {
		
		if (args.length < 3){
			printUsageInstructions();
			return;
		}
		
		String synonymsFilename = args[0];
		String filename1 = args[1];
		String filename2 = args[2];
		int N = DEFAULT_N; //default 
		
		// Check if optional argument is provided and Validate the given tuple size
		if(args.length == 4){
			try{
				N = Integer.parseInt(args[3]);
				if(N < 1){
					N = DEFAULT_N; //continue with default size
					System.out.println("\nInvalid Tuple Size: Tuple Size must be an integer greater than or equal to 1. Default Tuple Size is used.\n");
				}
			} catch (NumberFormatException e){
				N = DEFAULT_N; //continue with default size
				System.out.println("\nInvalid Tuple Size: Tuple Size must be an integer greater than or equal to 1. Default Tuple Size is used.\n");
			}
		}
		
		// Instance of PlagiarismDetection
		PlagiarismDetection detect;
		
		// Filename validation and intialisation of object detect if input is valid
		if(validateInput(synonymsFilename) && validateInput(filename1) && validateInput(filename2)){
			detect = new PlagiarismDetection(synonymsFilename, filename1, filename2, N);
		} else {
			throw new IllegalArgumentException("Invalid Input");
		}
		
		// get result percentage and print output
		double output = detect.computePercentage();
		System.out.println("\nPercentage of Plagiarism Detected: " + output + "%\n");
	}

}
