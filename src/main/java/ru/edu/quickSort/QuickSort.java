package ru.edu.quickSort;

import java.util.Arrays;

public class QuickSort {

    public static void main(String[] args) {
        var array = new Integer[]{3, 1, 4, 2, 5, 0, 6};
        sort(array);

        System.out.println(Arrays.deepToString(array));
    }


    public static <T extends Comparable<T>> void sort(T[] array) {
        applyQuickSort(array, 0, array.length - 1);
    }


    /**
     * @param array array of comparable elements to sort
     * @param start index of the first element (incl.)
     * @param end index of the last element (incl.)
     */
    private static <T extends Comparable<T>> void applyQuickSort(T[] array, int start, int end) {
        if (start < end) {

            //TODO add insertionSort if end - start < ?
            var partitionIndex = partition(array, start, end);

            //TODO only one recursion
            applyQuickSort(array, start, partitionIndex - 1);
            applyQuickSort(array, partitionIndex + 1, end);
        }
    }

    private static <T extends Comparable<T>> int partition(T[] array, int start, int end) {
        var pivot = getPivot(array, end);

        //TODO 3-way partition
        var cursor = start;

        for (int i = start; i < end + 1; i++) {
            if (array[i].compareTo(pivot) <= 0) {
                swap(array, i, cursor);
                cursor++;
            }
        }

        return cursor - 1;
    }

    private static <T extends Comparable<T>> T getPivot(T[] array, int end) {
        //TODO rewrite to median-of-3
        return array[end];
    }

    private static void swap(Object[] array, int i, int j) {
       var tmp = array[i];
       array[i] = array[j];
       array[j] = tmp;
    }
}