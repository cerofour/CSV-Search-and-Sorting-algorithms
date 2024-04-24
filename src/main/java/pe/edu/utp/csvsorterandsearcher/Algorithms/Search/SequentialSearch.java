package pe.edu.utp.csvsorterandsearcher.Algorithms.Search;

public class SequentialSearch {

    /**
     * Searches for the specified item in the array using sequential search.
     * @param arr          the array to be searched
     * @param itemToSearch the item to search for
     * @param <T>          the type of elements in the array, must be comparable
     * @return the index of the first occurrence of the item in the array, or -1 if not found
     */
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
