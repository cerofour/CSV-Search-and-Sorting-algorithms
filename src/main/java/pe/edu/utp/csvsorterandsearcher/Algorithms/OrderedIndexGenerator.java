package pe.edu.utp.csvsorterandsearcher.Algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.IntStream;


/**
 * This class provides methods for generating indices
 * based on the order of elements in arrays.
 */
public class OrderedIndexGenerator {

    /**
     * What this method does is that based on the original array and the ordered array,
     * it generates an array of indexes where the index (position) of each element of
     * the ordered array is reflected based on the original array.
     * @param arr original arrangement
     * @param arr2 ordered arrangement
     * @return returns the array of indexes
     * @param <T> generic data type
     */
    public static <T> Integer[] generateIndices(T[] arr, T[]arr2){
        //the two arrangements must be of the same type (logically)
        if (arr.length != arr2.length)
            return null;

        Integer[] indices =  indexArrayGenerator(arr.length);

        HashMap<T, ArrayList<Integer>> indexMap = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            if(!indexMap.containsKey(arr[i]))
                indexMap.put(arr[i], new ArrayList<>());
            indexMap.get(arr[i]).add(i);
        }

        for (int i = 0; i < arr.length; i++)
            indices[i] = indexMap.get(arr2[i]).remove(0);

        indexMap.clear();
        return indices;
    }

    /**
     * This method reverts all elements of an array
     * @param arr array to apply the reverse
     */
    public static void reverseArr(int[] arr){
        int start = 0;
        int end = arr.length - 1;
        while (start < end) {
            Swap.swap(arr, start, end);
            start++;
            end--;
        }
    }

    /**
     * This method reverts all elements of an array
     * @param arr array to apply the reverse
     */
    public static <T> void reverseArr(T[] arr){
        int start = 0;
        int end = arr.length - 1;
        while (start < end) {
            Swap.swap(arr, start, end);
            start++;
            end--;
        }
    }

    /**
     * This class generates an array of indices,
     * from 0 to the specified length
     * @param maxLength -> maximum array length
     * @return -> returns the array of indexes
     */
    private static Integer[] indexArrayGenerator(int maxLength){
        return IntStream.range(0, maxLength).boxed().toArray(Integer[]::new);
    }

}
