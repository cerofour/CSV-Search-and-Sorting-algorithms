package pe.edu.utp.csvsorterandsearcher.Algorithms.Sort;

import java.util.Arrays;

public class MergeSort {
		public static <T extends Comparable<T>> void sort(T[] arr) {
			if (arr == null || arr.length <= 1) {
				return;
			}
			mergeSort(arr, 0, arr.length - 1);
		}

		private static <T extends Comparable<T>> void mergeSort(T[] arr, int left, int right) {
			if (left < right) {
				int mid = left + (right - left) / 2;
				mergeSort(arr, left, mid);
				mergeSort(arr, mid + 1, right);
				merge(arr, left, mid, right);
			}
		}

		private static <T extends Comparable<T>> void merge(T[] arr, int left, int mid, int right) {
			int n1 = mid - left + 1;
			int n2 = right - mid;

			T[] L = Arrays.copyOfRange(arr, left, mid + 1);
			T[] R = Arrays.copyOfRange(arr, mid + 1, right + 1);

			int i = 0, j = 0, k = left;

			while (i < n1 && j < n2) {
				if (L[i].compareTo(R[j]) <= 0) {
					arr[k++] = L[i++];
				} else {
					arr[k++] = R[j++];
				}
			}

			while (i < n1) {
				arr[k++] = L[i++];
			}

			while (j < n2) {
				arr[k++] = R[j++];
			}
		}
}
