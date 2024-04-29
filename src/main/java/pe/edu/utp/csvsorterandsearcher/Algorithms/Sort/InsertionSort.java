package pe.edu.utp.csvsorterandsearcher.Algorithms.Sort;

import java.util.Arrays;

public class InsertionSort {

    public static <T extends Comparable<T>> void sort(T[] arr) {
        T aux;
        int k;
        for (int i = 1; i < arr.length; i++) {
            aux = arr[i];
            k = i - 1;
            while ((k >= 0) && (aux.compareTo(arr[k]) < 0)) {
                arr[k + 1] = arr[k];
                k--;
            }
            arr[k + 1]= aux;
        }
    }
}
