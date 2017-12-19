// Overriding the compare method in Comparator
// to sort suffices in desired way. 

import java.util.Comparator;

public class RhymingComparator implements Comparator<String>{
    @Override
    public int compare(String x, String y) { 
    	// Sort suffices based on lengths
      if (x.length() < y.length()) { return -1; } 
      else if (x.length() > y.length()) { return 1; }
      
      // otherwise, sort alphabetically
      int result = x.compareTo(y);
      return result;
    }
}
