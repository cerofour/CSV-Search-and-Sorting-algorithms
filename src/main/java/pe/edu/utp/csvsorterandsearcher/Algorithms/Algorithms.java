package pe.edu.utp.csvsorterandsearcher.Algorithms;

import pe.edu.utp.csvsorterandsearcher.CSV.FieldType;
import pe.edu.utp.csvsorterandsearcher.Controllers.CSVSorterAndSearcherController;

import java.time.LocalDate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Algorithms {

    public static String[] algorithmsNames = {
            "QuickSort"
    };
    private static Algorithm[] algorithms = {
            Algorithms::QuickSort
    };

    public static int[] sort(String[] columnToSort, FieldType dataType){
        int columnIndex = CSVSorterAndSearcherController.columnIndexToSelectedInSort;
        Object[] convertedData = Algorithms.dataTypeConversion(columnToSort, dataType);
        // The timer should be applied here.
        int[] indices = switch (dataType){
            case String -> algorithms[columnIndex].sort((String[]) convertedData);
            case Boolean -> algorithms[columnIndex].sort((Boolean[]) convertedData);
            case Double -> algorithms[columnIndex].sort((Double[]) convertedData);
            case Date -> algorithms[columnIndex].sort((LocalDate[]) convertedData);
        };
        // End timer
        return indices;
    }

    private static Object[] dataTypeConversion(String[] items, FieldType dataType){
        /*
            - How do we do with boolean data?
            - Remove Integer
         */
        return switch (dataType){
            case String -> items;
            case Boolean -> Stream.of(items).map(Boolean::parseBoolean).toArray();
            case Double -> Stream.of(items).map(Double::parseDouble).toArray();
            case Date -> Stream.of(items).map(LocalDate::parse).toArray();
        };
    }

    private static <T extends Comparable<T>> int[] QuickSort(T[] columns){


        int initialIndex = 0;
        int finalIndex = columns.length-1;
        int pivoteIndex = (initialIndex + finalIndex)/2;

        return new int[1];
    }


    private static int[] IndexArrayGenerator(int maxLength){
        return IntStream.range(0, maxLength).toArray();
    }


}
