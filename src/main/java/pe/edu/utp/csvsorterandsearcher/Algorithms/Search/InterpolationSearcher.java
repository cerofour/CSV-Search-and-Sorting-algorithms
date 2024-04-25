package pe.edu.utp.csvsorterandsearcher.Algorithms.Search;

import pe.edu.utp.csvsorterandsearcher.Algorithms.OrderedIndexGenerator;

import java.util.Arrays;
import java.util.Objects;

public class InterpolationSearcher {

    /**
     * Searches for the specified item in the given array using interpolation search.
     * TODO: When searching for certain data it stays in an infinite loop
     *       (the case in which it fails is undetermined).
     * @param arr The array to search within.
     * @param itemToSearch The item to search for.
     * @param <T> The type of elements in the array. Must implement the Comparable interface.
     * @return The index of the item if found, or -1 if not found.
     */
    public static <T extends Comparable<T>> Integer search(T[] arr, T itemToSearch){
        T[] arr1 = arr.clone();

        Arrays.sort(arr1);

        int Lo = 0;
        //int pos = - 1;
        int pos;
        int Hi = arr1.length - 1;

        // see if it uses an algorithm that is programmed

        while (Lo <= Hi){

            pos = Lo + (((Hi - Lo) * (itemToSearch.compareTo(arr1[Lo]))) / (arr1[Hi].compareTo(arr1[Lo])));

            if(arr1[pos].compareTo(itemToSearch) == 0){
                return Objects.requireNonNull(OrderedIndexGenerator.generateIndices(arr, arr1))[pos];
            }else {
                if (arr1[pos].compareTo(itemToSearch) < 0) {
                    Lo = pos + 1;
                } else {
                    Hi = pos - 1;
                }
            }

        }
        return -1;
    }

}
