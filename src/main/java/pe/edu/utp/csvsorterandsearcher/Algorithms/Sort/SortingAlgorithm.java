package pe.edu.utp.csvsorterandsearcher.Algorithms.Sort;

public interface SortingAlgorithm {
	<T extends Comparable<T>> void sort(T[] arr);
}
