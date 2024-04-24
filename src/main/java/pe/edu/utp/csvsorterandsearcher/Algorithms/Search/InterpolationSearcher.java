package pe.edu.utp.csvsorterandsearcher.Algorithms.Search;

import pe.edu.utp.csvsorterandsearcher.Algorithms.OrderedIndexGenerator;

import java.util.Arrays;
import java.util.Objects;

public class InterpolationSearcher {


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
