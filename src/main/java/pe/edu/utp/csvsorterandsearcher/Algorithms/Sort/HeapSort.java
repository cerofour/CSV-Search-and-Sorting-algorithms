package pe.edu.utp.csvsorterandsearcher.Algorithms.Sort;

import pe.edu.utp.csvsorterandsearcher.Algorithms.Swap;

public class HeapSort {
	public static <T extends Comparable<T>>  void sort(T[] arr) {
		if (arr == null || arr.length <= 1) {
			return;
		}
		int n = arr.length;

		for (int i = n / 2 - 1; i >= 0; i--) {
			heapify(arr, n, i);
		}

		for (int i = n - 1; i > 0; i--) {
			Swap.swap(arr, 0, i);

			heapify(arr, i, 0);
		}
	}

	private static <T extends Comparable<T>> void heapify(T[] arr, int n, int i) {
		int largest = i;
		int left = 2 * i + 1;
		int right = 2 * i + 2;

		if (left < n && arr[left].compareTo(arr[largest]) > 0) {
			largest = left;
		}

		if (right < n && arr[right].compareTo(arr[largest]) > 0) {
			largest = right;
		}

		if (largest != i) {
			Swap.swap(arr, i, largest);

			heapify(arr, n, largest);
		}
	}
}
