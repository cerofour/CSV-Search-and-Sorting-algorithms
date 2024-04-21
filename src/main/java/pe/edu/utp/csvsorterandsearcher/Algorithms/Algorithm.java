package pe.edu.utp.csvsorterandsearcher.Algorithms;

@FunctionalInterface
interface Algorithm {
    <T extends Comparable<T>> int[] sort(T[] columns);
}
