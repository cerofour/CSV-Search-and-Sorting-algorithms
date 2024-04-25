package pe.edu.utp.csvsorterandsearcher.Algorithms.Sort;

/**
 * Interface defining a sorting algorithm.
 */
public interface SortingAlgorithm {

	/**
	 * Sorts the given array in place.
	 * @param arr The array to be sorted.
	 * @param <T> The type of elements in the array.
	 *            Must implement the Comparable interface.
	 */
	<T extends Comparable<T>> void sort(T[] arr);
}
