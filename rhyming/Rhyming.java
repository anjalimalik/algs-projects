/* Rhyming */
/*
 * Rhyming words are defined as a group of words sharing a rhyme, and a rhyme is defined as being
 * the longest possible suffix common to all words in the group.
 * Words are listed in increasing order of suffix length.
 * Among suffixes of the same length, words are listed in alphabetical suffix order.
 * Words with common suffix are listed in alphabetical order.
 */

import java.io.*;
import java.util.*;

public class Rhyming {

	
	public static void main(String[] args){
		
		// Get input using StdIn
    	ArrayList<String> input = new ArrayList<String>();

        while(StdIn.hasNextLine()){
        	input.add(StdIn.readLine());
        }
        String[] words = input.toArray(new String[input.size()]); // make array
		
        //String[] words = {"clinic", "ethnic", "electrics", "metrics","coughed","laughed"};

        // Reverse all words in the list 
		for (int i = 0; i < words.length; i++){
			words[i] = reverse(words[i]);
		}
		
		// Sort words array to place rhyming words adjacent to each other
		Arrays.sort(words);

		// Make new List for suffices
		ArrayList<String> suffices = new ArrayList<String>(); 
		String lastsuffix = "";
		String suffix = "";
		
		// Iterate through the words list to compare strings and add suffices to a separate list
		for(int j = 0; j < (words.length-1); j++) {
			suffix = "";
			// compare words and find common suffix
			for(int i = 0; (i < words[j].length()) && (i < words[j+1].length()); i++){
				if(words[j].charAt(i) != words[j+1].charAt(i)){ break; } 
				else { suffix += words[j].charAt(i); }
			}
			
			// add to suffices list
			if(suffix.equals("") == false){
				if(suffix.equals(lastsuffix) == true){ /*do nothing*/ } 
				else {
					suffices.add(suffix);
					lastsuffix = suffix;
				}
			}
		}
		
		// Reverse all suffices in the list 
		for (int i = 0; i < suffices.size(); i++) {
			suffices.set(i, reverse(suffices.get(i)));
		}
		
		// Reverse all words again in the list
		for (int i = 0; i < words.length; i++){
			words[i] = reverse(words[i]);
		}

		// Using hashing concepts to remove duplicates from the suffices list
		Set<String> hset = new HashSet<String>(suffices);
		ArrayList<String> sufflist = new ArrayList<String>(hset);
		
		// Sort again to order suffices based on length, or if same length, alphabetically
		Collections.sort(sufflist, new RhymingComparator());
		
		// New list that contains all groups (list of rhyming words)
		ArrayList<ArrayList<String>> groups = findGroups(words, sufflist);

		// Print Output in desired manner with visualization of unique suffix in each group
		for (int i = 0; i < groups.size(); i++) {
			String output = getOutput( sufflist.get(i), groups.get(i));
			StdOut.println(output);
		}
		
	}
	
	// reverse Method
	// Uses StringBuilder for its string reverse function
	private static String reverse(String s) {
		StringBuilder sb = new StringBuilder(s);
		String reversed = new String(sb.reverse());
		return reversed;
	}

	// findGroups Method
	// Returns list of list of rhyming words using suffices list
	private static ArrayList<ArrayList<String>> findGroups(String[] words, ArrayList<String> sufflist){

		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		
		for(int i = 0; i < sufflist.size(); i++){
			ArrayList<String> group = new ArrayList<String>();
			for(int j = 0; j < words.length; j++){
				if(words[j].endsWith(sufflist.get(i))){
					group.add(words[j]);
				} 
			}
			// Sort again for alphabetical ordering of the words inside of a group
			Collections.sort(group);
			result.add(group);
		}
		
		return result;
	}
	
	// getOutput Method
	// For getting output in desired manner as a single string
	private static String getOutput(String suff, ArrayList<String> group) {

		String result = "[ ";
		
		for(int i = 0; i < group.size(); i++) {
			result = result + group.get(i).substring(0, group.get(i).length() - suff.length());
			if (i != group.size()-1) {
				result = result + "|" + suff + ", ";
			}
			else {
				result = result + "|" + suff;
			}
		}
		
		result = result + " ]";
		
		return result;
	}
}
