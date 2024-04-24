package pe.edu.utp.csvsorterandsearcher.Algorithms.Search;

public interface SearchAlgorithm {
     <T extends Comparable<T>> Integer search(T[] arr, T itemToSearch);
}
