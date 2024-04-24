package pe.edu.utp.csvsorterandsearcher.Algorithms;

/**
 * This class provides static methods for swapping
 * elements in arrays of different types.
 */
public class Swap {

	/**
	 * Swaps two elements in a generic array.
	 * @param arr generic arrangement
	 * @param x index of the first element to exchange
	 * @param y index of the second element to be exchanged
	 * @param <T> generic data type
	 */
	public static <T> void swap(T[] arr, int x, int y) {
		T tmp = arr[x];
		arr[x] = arr[y];
		arr[y] = tmp;
	}

	/**
	 * Swaps two elements in a generic array.
	 * @param arr generic arrangement
	 * @param x index of the first element to exchange
	 * @param y index of the second element to be exchanged
	 * @param <T> generic data type
	 */
	public static  void swap(Integer[] arr, int x, int y) {
		int tmp = arr[x];
		arr[x] = arr[y];
		arr[y] = tmp;
	}

	/**
	 * Swaps two elements in a generic array.
	 * @param arr generic arrangement
	 * @param x index of the first element to exchange
	 * @param y index of the second element to be exchanged
	 * @param <T> generic data type
	 */
	public static  void swap(int[] arr, int x, int y) {
		int tmp = arr[x];
		arr[x] = arr[y];
		arr[y] = tmp;
	}
}
