import java.util.*;

class TreeNode {
	int x;
	int y;
	TreeNode[] subs;

	/* Constructor */
	TreeNode(int x, int y) {
		this.x = x;
		this.y = y;
		subs = null;
	}
}

class myComparator implements Comparator<TreeNode> {

	@Override
	public int compare(TreeNode a, TreeNode b) {
		return Integer.compare(a.x, b.x);
	}

}

public class Query {
	private static int numRecords;
	private static int numQueries;

	public static void main(String[] args) {
		numRecords = StdIn.readInt();
		TreeNode coords[] = new TreeNode[numRecords];
		for (int i = 0; i < numRecords; i++) {
			coords[i] = new TreeNode(StdIn.readInt(), StdIn.readInt());

		}
		Arrays.sort(coords, new Comparator<TreeNode>() {
			public int compare(TreeNode a, TreeNode b) {
				return Integer.compare(a.x, b.x);
			}
		});

		for (int i = 0; i < numRecords; i++) {
			makeLvs(coords[i], coords, i);
		}

		// Get queries
		numQueries = StdIn.readInt();
		for (int i = 0; i < numQueries; i++) {
			int[] query = new int[] { StdIn.readInt(), StdIn.readInt() };
			// int[] query = new int[] { 5, 3 };
			processQueries(coords, query);
		}
	}
	
	// PRE-QUERY METHOD
	// This method is makes L[v]s.
	static void makeLvs(TreeNode n, TreeNode[] coords, int index) {
		int a = 0;
		n.subs = new TreeNode[numRecords - index];

		for (int i = index; i < (coords.length); i++) {
			n.subs[a] = coords[i];
			a++;
		}

		Arrays.sort(n.subs, new Comparator<TreeNode>() {
			public int compare(TreeNode a, TreeNode b) {
				return Integer.compare(a.y, b.y);
			}
		});
		/*
		 * for(int i = 0; i < a; i++){ System.out.print(n.subs[i].x + " " +
		 * n.subs[i].y + ", "); } System.out.println();
		 */
	}
	
	// QUERY METHOD
	// For each query, the time complexity is set by the method below
	// Complexity is O(logN) + O(mlogm)
	static void processQueries(TreeNode[] arr, int[] query) {

		// Binary search
		// Code below (until this while loop) is O(logN)
		int l = 0;
		int h = arr.length;

		while (l != h) {
			int mid = (l + h) / 2;
			if (arr[mid].x > query[0]) {
				h = mid;
			} else {
				l = (mid + 1);
			}
		}
		// System.out.println(h);

		// If no x is bigger than a, return none.
		if (h >= arr.length) {
			StdOut.println("none");
			return;
		}

		// Binary Search
		// Code below (until this while loop) is O(log(N-m))
		// This is less than O(logN) so we can just assume code till here is
		// O(logN)
		int l2 = 0;
		int h2 = arr[h].subs.length;
		while (l2 != h2) {
			int mid = (l2 + h2) / 2;
			if (arr[h].subs[mid].y > query[1]) {
				h2 = mid;
			} else {
				l2 = (mid + 1);
			}
		}

		// System.out.println(h2);
		// If no y is bigger than a, return none.
		if (h2 >= arr[h].subs.length) {
			StdOut.println("none");
			return;
		}

		// Rest of the code is O(m log m)
		TreeSet<TreeNode> tset = new TreeSet<TreeNode>(new myComparator());

		// adding + sorting m records in treeset
		for (int i = h2; i < arr[h].subs.length; i++) {
			tset.add(arr[h].subs[i]);
			// System.out.println("here");
		}

		// PRINT OUTPUT
		// Code below takes 0(m log m) which is less than O(m log m)
		Iterator<TreeNode> it = tset.iterator();
		while (it.hasNext()) {
			TreeNode result = it.next();
			StdOut.println(result.x + " " + result.y);
		}

	}

}
