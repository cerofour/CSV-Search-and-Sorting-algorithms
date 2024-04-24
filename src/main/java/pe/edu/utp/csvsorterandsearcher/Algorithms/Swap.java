package pe.edu.utp.csvsorterandsearcher.Algorithms;

public class Swap {
	public static <T> void swap(T[] arr, int x, int y) {
		T tmp = arr[x];
		arr[x] = arr[y];
		arr[y] = tmp;
	}
}
