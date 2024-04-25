package pe.edu.utp.csvsorterandsearcher.Algorithms.Sort;

import pe.edu.utp.csvsorterandsearcher.Algorithms.Swap;

public class ShakerSort {

	public static <T extends Comparable<T>> void sort(T[] arr) {
		int left = 0;
		int right = arr.length - 1;
		boolean swapped = true;

		while (left < right && swapped) {
			swapped = false;

			// Move the largest element to the right
			for (int i = left; i < right; i++) {
				if (arr[i].compareTo(arr[i + 1]) > 0) {
					Swap.swap(arr, i, i + 1);
					swapped = true;
				}
			}
			right--;

			// Move the smallest element to the left
			for (int i = right; i > left; i--) {
				if (arr[i].compareTo(arr[i - 1]) < 0) {
					Swap.swap(arr, i, i - 1);
					swapped = true;
				}
			}
			left++;
		}
	}
}