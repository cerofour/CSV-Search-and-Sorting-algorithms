package pe.edu.utp.csvsorterandsearcher.Algorithms.Search;

import java.util.Arrays;

public class InterpolationSearcher {


    public static <T extends Comparable<T>> int search(T[] arr, T itemToSearch){
        T[] arr1 = arr.clone();

        Arrays.sort(arr1);
        System.out.println(Arrays.toString(arr1));
        int Lo = 0;
        //int pos = - 1;
        int pos;
        int Hi = arr1.length - 1;

        // see if it uses an algorithm that is programmed

        while (Lo <= Hi){

            pos = Lo + (((Hi - Lo) * (itemToSearch.compareTo(arr1[Lo]))) / (arr1[Hi].compareTo(arr1[Lo])));

            if(arr1[pos].compareTo(itemToSearch) == 0){
                return pos;
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
