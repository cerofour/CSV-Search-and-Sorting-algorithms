package pe.edu.utp.csvsorterandsearcher.Algorithms;

/**
 * The so-called fastest sorting algorithm.
 * Best suitable for larger datasets.
 * @timeComplexity O(n*log(n))
 */
public class QuickSort implements SortingAlgorithm {
	public static <T extends Comparable<T>> void sort(T[] arr) {
		quickSort(arr, 0, arr.length - 1);
	}

	private static <T extends Comparable<T>> void quickSort(T[] arr, int start, int end) {
		if (start < end) {
			int pivotIdx = partition(arr, start, end);
			quickSort(arr, start, pivotIdx - 1);
			quickSort(arr, pivotIdx + 1, end);
		}
	}

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
