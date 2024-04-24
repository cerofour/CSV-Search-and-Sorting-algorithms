package pe.edu.utp.csvsorterandsearcher.Algorithms.Search;

import java.util.ArrayList;

public class SequentialSearcher {

    public static <T extends Comparable<T>> Integer search(T[] arr, T itemToSearch){

        //ArrayList<Integer> arrList = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].compareTo(itemToSearch) == 0){
                //arrList.add(i);
                return i;
            }
        }
        //return arrList.toArray(Integer[]::new);
        return -1;
    }

}
