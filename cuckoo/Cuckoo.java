
import java.util.ArrayList;

public class Cuckoo {
    
    private int r;
    private int h1;
    private int h2;
    private long a1;
    private long a2;
    private double e;
    private static int n;                    // Number of Keys
    private static int count;                // Count of evictions
    private static int[] hashKeys;            // Hash Keys
    private static int[] hashValues;            // Hash Values
    private static int popH;
    private static int once = 0;
    private static boolean verbose = false;
    
    public static int getH(long a, int r, int x) {
        long num = (long)a*(long)x;
        long den = (long) Math.pow(2, 16);
        long result1 = num/den;
        long result2 = result1 % r;
        return (int) result2;
    }
    
    public static int getLMax(double e, int r) {
        return (int) Math.ceil(((3*Math.log(r)/Math.log(((double)1+e))) + 1));
    }
    
    public static double getR(double e, int n) {
        double a = 1 + e;
        double b = n*a;
        double c = 2*b;
        return (c);
    }
    
    public static void initializeTable(long a1, long a2, int size) {
        count = 0;
        n = 0;
        hashKeys = new int[size];
        hashValues = new int[size];
        for(int i = 0; i < size; i++) {
            hashKeys[i] = -1;
            hashValues[i] = -1;
        }
        StdOut.println("(hash " + a1 + " " + a2 + " " + size + ")");
    }
    
    public Cuckoo(long a1, long a2) {
        this.e = 0.05;
        this.a1 = a1;
        this.a2 = a2;
        this.r = 256;
        initializeTable(this.a1, this.a2, this.r);
    }
    
    public void reHash() {
        ArrayList<ArrayList<Integer>> insertions = new ArrayList<ArrayList<Integer>>();    // List of Insertions (to store for rehashing)
        for(int i = 0; i < hashKeys.length; i++) {
            if (hashKeys[i] != -1) {
                ArrayList<Integer> in = new ArrayList<Integer>();
                in.add(hashKeys[i]);
                in.add(hashValues[i]);
                insertions.add(in);
            }
        }
        initializeTable(this.a1, this.a2, this.r);
        for(int i = 0; i < insertions.size(); i++) {
            ArrayList<Integer> out = insertions.get(i);
            put(out.get(0), out.get(1));
            
        }
    }
    
    public void put(int key, int value) {
        // Check if maximum limit for sequence reached
        int LMax = getLMax(this.e, this.r);
        if (count >= LMax) {
            this.r = 2*(this.r);
            reHash();
        }
        
        // Check if table size inequality is met
        n++;
        double newR = getR(this.e, n);
        n--;
        if (this.r < newR) {
            double diff = (double)newR - (double)this.r;
            if (diff <= 0.11 && once <= 1) {
                once++;
            }
            else {
                once = 0;
                this.r = 2*(this.r);
                reHash();
            }
        }
        
        // Calculate hash functions
        h1 = getH(this.a1,this.r,key);
        h2 = getH(this.a2,this.r,key);
        
        // Check if key exists. If yes, update the value
        if (hashKeys[h1] == key) {
            hashValues[h1] = value;
            if (verbose) {
                StdOut.println("(" + h1 + " " + hashKeys[h1] + " " + hashValues[h1] + ")");
            }
            return;
        }
        else if (hashKeys[h2] == key) {
            hashValues[h2] = value;
            if (verbose) {
                StdOut.println("(" + h2 + " " + hashKeys[h2] + " " + hashValues[h2] + ")");
            }
            return;
        }
        
        // Check if h1 or h2 are empty
        if (hashKeys[h1] == -1) {        // h1 is empty
            hashKeys[h1] = key;
            hashValues[h1] = value;
            n++;
            count = 0;
            once = 0;
            if (verbose) {
                StdOut.println("(" + h1 + " " + hashKeys[h1] + " " + hashValues[h1] + ")");
            }
            return;
        }
        else if (hashKeys[h2] == -1) {    // h2 is empty
            hashKeys[h2] = key;
            hashValues[h2] = value;
            n++;
            count = 0;
            once = 0;
            if (verbose) {
                StdOut.println("(" + h2 + " " + hashKeys[h2] + " " + hashValues[h2] + ")");
            }
            return;
        }
        else {        // Dislodge
            // Check if being added for first time. Use h1 in this case, otherwise, use "the other h"
            if (count == 0) {
                popH = h1;
            }
            once = 0;
            // Save (pop) the Key and Value which existed in the index
            int popKey = hashKeys[popH];
            int popValue = hashValues[popH];
            int popH1 = getH(this.a1, this.r, popKey);
            int popH2 = getH(this.a2, this.r, popKey);
            // Store the one being inserted, in place of popped key
            hashKeys[popH] = key;
            hashValues[popH] = value;
            if (verbose) {
                StdOut.println("(" + popH + " " + hashKeys[popH] + " " + hashValues[popH] + ")");
            }
            // Put the popped key back in the HashTable
            if (popH == popH1) {
                popH = popH2;
                count++;
                put(popKey, popValue);
            }
            else if (popH == popH2) {
                popH = popH1;
                count++;
                put(popKey, popValue);
            }
        }
        
    }
    
    public void get(int key) {
        // Print Value. "none" if not contains
        h1 = getH(this.a1,this.r,key);
        h2 = getH(this.a2,this.r,key);
        if (hashKeys[h1] == key) {
            StdOut.println(hashValues[h1]);
        }
        else if (hashKeys[h2] == key){
            StdOut.println(hashValues[h2]);
        }
        else {
            StdOut.println("none");
        }
    }
    
    
    public void delete(int key) {
        h1 = getH(this.a1,this.r,key);
        h2 = getH(this.a2,this.r,key);
        if (hashKeys[h1] == key) {
            hashKeys[h1] = -1;
            hashValues[h1] = -1;
            n--;
        }
        else if (hashKeys[h2] == key) {
            hashKeys[h2] = -1;
            hashValues[h2] = -1;
            n--;
        }
        
    }
    
    public boolean contains(int key) {
        // Print "yes" or "no"
        h1 = getH(this.a1,this.r,key);
        h2 = getH(this.a2,this.r,key);
        if (hashKeys[h1] == key || hashKeys[h2] == key) {
            StdOut.println("yes");
            return true;
        }
        StdOut.println("no");
        return false;
    }
    
    public int size() {
        StdOut.println(n);
        return n;
    }
    
    public static void setVerbose(boolean set) {
        verbose = set;
    }
    
    public static void main(String[] args) {
        // Verbose
        setVerbose(true);
        
        // Read a1 and a1
        long a1 = StdIn.readLong();
        long a2 = StdIn.readLong();
        // Instantiate Cuckoo class
        Cuckoo test = new Cuckoo(a1, a2);
        // Read "number of commands"
        StdIn.readInt();
        // Read commands
        while(!StdIn.isEmpty()) {
            String[] strs = StdIn.readLine().split(" ");
            if (strs[0].equals("put")) {
                test.put(Integer.parseInt(strs[1]), Integer.parseInt(strs[2]));
            }
            if (strs[0].equals("get")) {
                test.get(Integer.parseInt(strs[1]));
            }
            if (strs[0].equals("size")) {
                test.size();
            }
            if (strs[0].equals("contains")) {
                test.contains(Integer.parseInt(strs[1]));
            }
            if (strs[0].equals("delete")) {
                test.delete(Integer.parseInt(strs[1]));
            }
            
        }
        
    }
}
