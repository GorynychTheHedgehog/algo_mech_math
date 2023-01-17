package ru.edu.quickSort;

import java.util.Comparator;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) {
        var quickSort = new QuickSort(-1, true, true, false);
        var array = generateIntArray(1000);
        var cmp = new ComparatorWithCounter<Integer>();

        quickSort.sort(array, cmp);

        System.out.println(cmp.getTotalComparisons());

    }

    static class ComparatorWithCounter<T extends Comparable<T>> implements Comparator<T> {
        private final AtomicInteger counter = new AtomicInteger();

        @Override
        public int compare(T o1, T o2) {
            counter.incrementAndGet();
            return o1.compareTo(o2);
        }

        public int getTotalComparisons() {
            return counter.get();
        }
    }

    private static Integer[] generateIntArray(int size) {
        var array = new Integer[size];
        for (int i = 0; i < array.length; i++) {
            array[i] = ThreadLocalRandom.current().nextInt();
        }
        return array;
    }
}
