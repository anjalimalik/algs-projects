# querying records

Query is a program that creates a complete binary tree T from an array of N records, sorted by their x coordinates. For every node v of T, an array L[v] is created that contains the records in the subtree of v in T, sorted according to their y coordinates.
The tree T and the L[v] lists are then used to efficiently process queries of the type Q(a,b) that outputs the records whose x coordinate is greater than a and y coordinate is greater than b. The records output from each query are sorted by their x coordinates in increasing order.
