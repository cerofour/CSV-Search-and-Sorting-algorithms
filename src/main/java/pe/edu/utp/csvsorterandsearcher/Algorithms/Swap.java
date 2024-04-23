package pe.edu.utp.csvsorterandsearcher.Algorithms;

public class Swap {

	// horrible language design that forces me to create a whole class for something
	// that could be a standalone procedure.
	public static <T> void swap(T[] arr, int x, int y) {
		T tmp = arr[x];
		arr[x] = arr[y];
		arr[y] = tmp;
	}
}
