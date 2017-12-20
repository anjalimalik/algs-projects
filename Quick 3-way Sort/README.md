# Optimized 3-way Quick Sort 
Sorts an array using an optimized version of quicksort which uses Bentley-McIlroy 3-way partitioning, Tukey's ninther, and cutoff to insertion sort methods.
- Cutoff to insertion sort when elements equal to or less than 8.
- Median-of-three partitioning, Use median of a small sample of items taken from the array as the partitioning item. Used when
  number of elements to sort is ≤ 40.
- Tukey ninther. Tukey ninther technique is used when the previous optimizations do not apply. In the
  Tukey ninether method, the partitioning element is selected as the median of 3 medians of three.
  Specifically, if 9 evenly sampled values are: (v0, v1, v2, v3, v4, v5, v6, v7, v8)
    m0 = median(v0, v1, v2)
    m1 = median(v3, v4, v5)
    m2 = median(v6, v7, v8)
  And median(m0, m1, m2) is chosen as the partitioning element.
