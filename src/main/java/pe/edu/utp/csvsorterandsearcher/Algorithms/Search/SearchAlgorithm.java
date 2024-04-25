package pe.edu.utp.csvsorterandsearcher.Algorithms.Search;

/**
 * Interface for search algorithms.
 */
public interface SearchAlgorithm {

     /**
      * Searches for the specified item in the given array.
      * @param arr The array to search within.
      * @param itemToSearch The item to search for.
      * @param <T> The type of elements in the array. Must implement the Comparable interface.
      * @return The index of the item if found, or null if not found.
      */
     <T extends Comparable<T>> Integer search(T[] arr, T itemToSearch);
}
