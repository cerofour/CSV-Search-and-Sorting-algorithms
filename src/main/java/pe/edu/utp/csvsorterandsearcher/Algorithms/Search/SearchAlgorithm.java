package pe.edu.utp.csvsorterandsearcher.Algorithms.Search;

public interface SearchAlgorithm {
     <T extends Comparable<T>> int search(T[] arr, T itemToSearch);
}
