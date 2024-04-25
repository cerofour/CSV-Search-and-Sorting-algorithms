package pe.edu.utp.csvsorterandsearcher.Algorithms.Sort;

import pe.edu.utp.csvsorterandsearcher.Algorithms.Swap;

/**
 * The so-called fastest sorting algorithm.
 * Best suitable for larger datasets.
 * @timeComplexity O(n*log(n))
 */
public class QuickSort {

	/**
	 * Sorts the given array using the QuickSort algorithm.
	 * @param arr The array to be sorted.
	 * @param <T> The type of elements in the array. Must implement the Comparable interface.
	 */
	public static <T extends Comparable<T>> void sort(T[] arr) {
		quickSort(arr, 0, arr.length - 1);
	}

	/**
	 * Recursively performs QuickSort on the array.
	 * @param arr The array to be sorted.
	 * @param start The starting index of the subarray.
	 * @param end The ending index of the subarray.
	 * @param <T> The type of elements in the array. Must implement the Comparable interface.
	 */
	private static <T extends Comparable<T>> void quickSort(T[] arr, int start, int end) {
		if (start < end) {
			int pivotIdx = partition(arr, start, end);
			quickSort(arr, start, pivotIdx - 1);
			quickSort(arr, pivotIdx + 1, end);
		}
	}

	/**
	 * Partitions the array around a pivot element.
	 * @param arr The array to be partitioned.
	 * @param start The starting index of the subarray.
	 * @param end The ending index of the subarray.
	 * @param <T> The type of elements in the array. Must implement the Comparable interface.
	 * @return The index of the pivot element after partitioning.
	 */
	private static <T extends Comparable<T>> int partition(T[] arr, int start, int end) {
		int mid = (start + end) / 2;
		Swap.swap(arr, start, mid);
		int pivotIdx = start;
		T pivot = arr[pivotIdx];

		for (int scan = start + 1; scan <= end; scan++) {
			if (arr[scan].compareTo(pivot) < 0) {
				pivotIdx++;
				Swap.swap(arr, pivotIdx, scan);
			}
		}

		Swap.swap(arr, start, pivotIdx);
		return pivotIdx;
	}
}
