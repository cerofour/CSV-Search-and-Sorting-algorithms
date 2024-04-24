package pe.edu.utp.csvsorterandsearcher.Algorithms.Sort;

import pe.edu.utp.csvsorterandsearcher.Algorithms.Swap;

/**
 * Bubble sort algorithm.
 * Best suitable for small datasets.
 * @timeComplexity O(n^2)
 */
public class BubbleSort {

	/**
	 * Implementation based on: https://faculty.cs.niu.edu/~mcmahon/CS241/Notes/Sorting_Algorithms/bubble_sort.html
	 * @param arr Array to sort
	 * @param <T> Generic type. Must be comparable.
	 */
	public static <T extends Comparable<T>> void sort(T[] arr) {
		boolean swapped = false;

		do {
			swapped = false;
			for (int i = 1; i < arr.length; i++) {

				// TODO: modify this according to sorting order
				if (arr[i - 1].compareTo(arr[i]) > 0) {
					Swap.swap(arr, i - 1, i);
					swapped = true;
				}
			}
		} while (swapped);
	}
}
