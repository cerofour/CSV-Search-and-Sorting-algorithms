package pe.edu.utp.csvsorterandsearcher.Algorithms;

/**
 * The optimized Bubble sort algorithm.
 * Best suitable for small datasets.
 * One of the advantages of this algorithm is that it can detect if the array is sorted in only
 * one pass. So the best case complexity is O(n).
 * @timeComplexity O(n^2)
 */
public class OptimizedBubbleSort implements SortingAlgorithm {

	/**
	 * Implementation based on: https://faculty.cs.niu.edu/~mcmahon/CS241/Notes/Sorting_Algorithms/bubble_sort.html
	 * Optimized bubble sort that saves some comparisons. Still not so fast.
	 * @param arr Array to sort
	 * @param <T> Generic type. Must be comparable.
	 */
	public static <T extends Comparable<T>> void sort(T[] arr) {
		int limit = arr.length;
		int lastSwap = 0;

		do {
			lastSwap = 0;
			for (int i = 1; i < limit; i++) {

				// TODO: modify this according to sorting order
				if (arr[i - 1].compareTo(arr[i]) > 0) {
					Swap.swap(arr, i - 1, i);
					lastSwap = i;
				}
			}
			limit = lastSwap;
		} while (limit > 1);
	}
}