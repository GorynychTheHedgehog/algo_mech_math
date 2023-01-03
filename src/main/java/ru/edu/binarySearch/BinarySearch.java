package ru.edu.binarySearch;

public class BinarySearch {
    public static int find(int[] array, int element, Type type) {
        var lowerBound = 0;
        var upperBound = array.length - 1;

        switch (type) {
            case ITERATIVE -> {
                return iterBinarySearch(array, element, lowerBound, upperBound);
            }
            case RECURSIVE -> {
                return recursiveBinarySearch(array, element, lowerBound, upperBound);
            }
            default -> throw new UnsupportedOperationException();
        }
    }

    //iterative binary search
    private static int iterBinarySearch(int[] array, int element, int lowerBound, int upperBound) {
        while (lowerBound <= upperBound) {
            int middle = lowerBound  + ((upperBound - lowerBound) / 2);
            if (element > array[middle]) {
                lowerBound = middle + 1;
            } else if (element < array[middle]) {
                upperBound = middle - 1;
            } else if (element == array[middle]) {
                return middle;
            }
        }

        return -1;
    }

    //recursive binary search
    private static int recursiveBinarySearch(int[] array, int element, int lowerBound, int upperBound) {
        if (upperBound < lowerBound) {
            return -1;
        }

        int middle = lowerBound  + (upperBound - lowerBound) / 2;

        if (element == array[middle]) {
            return middle;
        } else if (element < array[middle]) {
            return recursiveBinarySearch(array, element, lowerBound, middle - 1);
        } else {
            return recursiveBinarySearch(array, element, middle + 1, upperBound);
        }
    }

    public enum Type {
        ITERATIVE,
        RECURSIVE
    }
}
