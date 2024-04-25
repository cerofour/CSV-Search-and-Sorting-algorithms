package pe.edu.utp.csvsorterandsearcher.Algorithms.Sort;

import pe.edu.utp.csvsorterandsearcher.Algorithms.Swap;

import java.util.Random;

public class BogoSort {
	public static <T extends Comparable<T>> void sort(T[] arr) {
		while (!isSorted(arr)) {
			shuffle(arr);
		}
	}

	private static <T extends Comparable<T>> boolean isSorted(T[] arr) {
		for (int i = 0; i < arr.length - 1; i++) {
			if (arr[i].compareTo(arr[i + 1]) > 0) {
				return false;
			}
		}
		return true;
	}

	private static <T extends Comparable<T>> void shuffle(T[] arr) {
		Random rand = new Random();
		for (int i = arr.length - 1; i > 0; i--) {
			int j = rand.nextInt(i + 1);
			Swap.swap(arr, i, j);
		}
	}
}
