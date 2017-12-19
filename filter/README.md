# filtering records
The input consists of a set S of N two-dimensional records of integers, each of which consists of a pair of coordinates (x[i],y[i]) for 0 ≤ i ≤ N-1, where the x[i] are distinct (i.e., no two are equal) and the y[i] are also distinct.
This program eliminates from the set every record (x[i],y[i]) for which there exists another record (x[j],y[j]) having both x[j] > x[i] and y[j] > y[i]. Program eliminates all records that are doubly inferior to at least one other record. Output the filtered records in the set are sorted according to increasing order of their x coordinates.



