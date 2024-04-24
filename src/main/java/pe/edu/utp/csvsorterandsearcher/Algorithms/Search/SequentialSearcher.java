package pe.edu.utp.csvsorterandsearcher.Algorithms.Search;

public class SequentialSearcher {

    public static <T extends Comparable<T>> int search(T[] arr, T itemToSearch){
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].compareTo(itemToSearch) == 0){
                return i;
            }
        }
        return -1;
    }

}
