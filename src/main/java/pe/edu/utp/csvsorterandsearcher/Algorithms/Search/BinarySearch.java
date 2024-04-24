package pe.edu.utp.csvsorterandsearcher.Algorithms.Search;

import pe.edu.utp.csvsorterandsearcher.Algorithms.OrderedIndexGenerator;

import java.util.Arrays;
import java.util.Objects;

public class BinarySearch {

    /**
     * Implementation based on: https://faculty.cs.niu.edu/~mcmahon/CS241/Notes/Search_Algorithms/binary_search.html
     * Binary search
     * @param arr array where to search
     * @param itemToSearch element to search within the given array
     * @param <T> Generic type. Must be comparable.
     * @return an index where that element is located
     */
    public static <T extends Comparable<T>> Integer search(T[] arr, T itemToSearch){
        T[] arr2 = arr.clone();
        Arrays.sort(arr2);

        int low = 0;
        int high = arr.length - 1;
        int pos;

        while (low <= high){
            pos = (low + high) / 2;
            int comparation = itemToSearch.compareTo(arr2[pos]);
            if (comparation == 0){
                //return pos;
                return Objects.requireNonNull(OrderedIndexGenerator.generateIndices(arr, arr2))[pos];
            }
            if (comparation < 0) { // search_key < array[pos]
                high = pos - 1;
            }else{ // search_key > array[pos]
                low = pos + 1;
            }

        }
        return  -1;
    }


}
