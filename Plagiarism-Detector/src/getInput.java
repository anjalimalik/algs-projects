/* Program: A command-line program that performs plagiarism detection 
 * 			using a N-tuple comparison algorithm allowing for synonyms 
 * 			in the text.
 * File: getInput
 * 
 * Author: Anjali Malik
 * 
 */

import java.util.List;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class getInput {
	
	// Instance variables 
	private HashMap<String, HashSet<String>> syns;
	private ArrayList<ArrayList<String>> tuples;
	
	/* Method: This method reads from given file and extracts the synonyms from each line 
	 * 			into one group represented by a key in the hashmap.
	 * Parameters: Filename of the file containing groups of synonyms
	 * Return: HashMap<String, List<String>>; hashmap containing synonyms 
	 * 
	 * Assumptions: Given synonyms are unique in their meaning. 
	 * 				This implies that same words with multiple meaning are not handled for now.
	 * Design choices: 	- BufferedReader is safer and can be more efficient 
	 * 				 	 than using Scanner class from reading files
	 * 					- HashMap is used because it is needed by the solution to compare keys 
	 * 						and that groups are represented by keys, because of this 
	 * 						simply lists weren't enough. Rest of the other data structures 
	 * 						made less sense for this problem than these.
	 * 					- HashSet is chosen instead of list to store synonyms to ensure no
	 * 						duplicates are entered and lookup efficiency of a hashset is faster.
	 */
	
	public HashMap<String, HashSet<String>> getSynonyms(String filename) throws Exception{
	
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(new FileReader(filename));
			
			syns = new HashMap<String, HashSet<String>>(); 		// initialize
			List<String> listofsyns = new ArrayList<String>(); 	//helper variable
			
		    String line = br.readLine();
		    
		    // fill hashmap line by line
		    while (line != null) {
		    	
		    	// split line using delimiter of spaces and make all strings lower case 
		    	// for easy handling
		    	listofsyns = Arrays.asList(line.toLowerCase().split("\\s+"));
		    	
		    	HashSet<String> hs = new HashSet<String>();
		    	for(String word: listofsyns){
		    		hs.add(word);
		    	}
		    	
		    	// iterate over the entire hashset to make sure all synonyms are keys
		    	// this is done to make sure that when looking for a synonym, 
		    	// it is not found because the key for its group is another 
		    	for(String word: hs){
		    		if(!syns.containsKey(word)){ 	// check if already present or if lines are repeated or multiple words are present
		    			syns.put(word, hs);
		    		}
		    	}
		    	
		        line = br.readLine(); //iterate
		    }
		    
		} catch (FileNotFoundException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		} finally {
		    br.close();
		}
		
		if(syns == null){
			throw new IllegalArgumentException("No synonyms found");
		}
		
		return syns;
	}
	
	/* Method: This method reads from given file and extracts tuples of given size.
	 * Parameters: Filename of the input file containing tuples, Tuple Size (N)
	 * Return: ArrayList<ArrayList<String>>; list of lists of tuples
	 */
	
	public ArrayList<ArrayList<String>> getTuples(String filename, int N) throws Exception{
		
		tuples = new ArrayList<ArrayList<String>>();
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(new FileReader(filename));
			List<String> wordsinline = new ArrayList<String>(); //helper variable 
			ArrayList<String> tuple; //helper variable
			
		    String line = br.readLine();
		    
		    // get tuples from each line
		    while (line != null) {
		    	
		    	// split line using delimiter of spaces and make all strings lower case 
		    	// for easy handling
		    	wordsinline = Arrays.asList(line.toLowerCase().split("\\s+"));
		    	
		    	// get tuples of size N
		    	if(wordsinline.size() >= N){
		    		for(int i = 0; i <= (wordsinline.size() - N); i++){
			    		tuple = new ArrayList<String>();
			    		int j = i;
			    		while(j < i+N){
			    			tuple.add(wordsinline.get(j));
			    			j++;
			    		}
			    		tuples.add(tuple);
			    	}
		    	}
		    	
		        line = br.readLine(); //iterate
		    }
		    
		} catch (FileNotFoundException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		} finally {
		    br.close();
		}
		
		return tuples;
	}

}
