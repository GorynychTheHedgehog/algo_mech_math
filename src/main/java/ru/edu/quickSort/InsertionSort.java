package ru.edu.quickSort;

import java.util.Comparator;

public class InsertionSort {
    public static <T> void sort(T[] array, int start, int end, Comparator<T> cmp) {
        for (int i = start + 1; i < end + 1; i++) {
            var element = array[i];
            var cursor = i - 1;
            while (cursor > start - 1 && cmp.compare(array[cursor], element) > 0) {
                array[cursor + 1] = array[cursor];
                cursor--;
            }
            array[cursor + 1] = element;
        }
    }
}
