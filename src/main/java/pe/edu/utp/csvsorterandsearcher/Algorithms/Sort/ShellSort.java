package pe.edu.utp.csvsorterandsearcher.Algorithms.Sort;


public class ShellSort {
	public static <T extends Comparable<T>> void sort(T[] arr) {
		int n = arr.length;

		// Start with a big gap, then reduce the gap
		for (int gap = n / 2; gap > 0; gap /= 2) {
			for (int i = gap; i < n; i++) {
				T temp = arr[i];

				int j;
				for (j = i; j >= gap && arr[j - gap].compareTo(temp) > 0; j -= gap) {
					arr[j] = arr[j - gap];
				}

				arr[j] = temp;
			}
		}
	}
}
