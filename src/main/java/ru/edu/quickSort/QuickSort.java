package ru.edu.quickSort;

import com.google.common.annotations.VisibleForTesting;
import java.util.Comparator;


import lombok.AllArgsConstructor;

@AllArgsConstructor
public class QuickSort {
    private final int insertionThreshold;
    private final boolean useMedianOfThree;
    private final boolean useOneRecursiveCall;
    private final boolean useDutchFlagPartition;

    public <T extends Comparable<T>> void sort(T[] array) {
        sort(array, Comparable::compareTo);
    }

    public <T> void sort(T[] array, Comparator<T> cmp) {
        if (useOneRecursiveCall){
            applyQuickSortWithOneRecursiveCall(array, 0, array.length - 1, cmp);
        } else {
            applyQuickSort(array, 0, array.length - 1, cmp);
        }
    }

    /**
     * @param array array of comparable elements to sort
     * @param start index of the first element (incl.)
     * @param end   index of the last element (incl.)
     */
    private <T> void applyQuickSort(T[] array, int start, int end, Comparator<T> cmp) {
        if (start < end) {
            if (end - start < insertionThreshold) {
                InsertionSort.sort(array, start, end, cmp);
            } else {
                var partitionIndex = getPartitionIndex(array, start, end, cmp);

                applyQuickSort(array, start, partitionIndex.left() - 1, cmp);
                applyQuickSort(array, partitionIndex.right() + 1, end, cmp);
            }
        }
    }

    /**
     * @param array array of comparable elements to sort
     * @param start index of the first element (incl.)
     * @param end   index of the last element (incl.)
     */
    private <T> void applyQuickSortWithOneRecursiveCall(T[] array, int start, int end, Comparator<T> cmp) {
        while (start < end) {
            if (end - start < insertionThreshold) {
                InsertionSort.sort(array, start, end, cmp);
                break;
            } else {
                var partitionIndex = getPartitionIndex(array, start, end, cmp);

                if (partitionIndex.left() - start > end - partitionIndex.right()) {
                    applyQuickSortWithOneRecursiveCall(array, partitionIndex.right() + 1, end, cmp);
                    end = partitionIndex.left() - 1;
                } else {
                    applyQuickSortWithOneRecursiveCall(array, start, partitionIndex.left() - 1, cmp);
                    start = partitionIndex.right() + 1;
                }
            }
        }
    }

    private <T> IntTuple getPartitionIndex(T[] array, int start, int end, Comparator<T> cmp) {
        if (useMedianOfThree) {
            var middle = start + (end - start) / 2;
            medianOfThree(array, start, middle, end, cmp);
            swap(array, middle, end - 1);
        }

        return useDutchFlagPartition ? dutchFlagPartition(array, start, end, cmp) : partition(array, start, end, cmp);
    }

    private <T> IntTuple partition(T[] array, int start, int end, Comparator<T> cmp) {
        var cursor = start;
        var pivot = array[end];
        for (int i = start; i < end + 1; i++) {
            if (cmp.compare(array[i], pivot) <= 0) {
                swap(array, i, cursor);
                cursor++;
            }
        }

        return new IntTuple(cursor - 1, cursor - 1);
    }

    @VisibleForTesting
    static <T> IntTuple dutchFlagPartition(T[] array, int start, int end, Comparator<T> cmp) {
        var pivot = array[end];

        var startCursor = start - 1;
        var endCursor = end;

        var startOccurrences = 0;
        var endOccurrences = 0;

        while (true) {

            while (cmp.compare(array[++startCursor], pivot) < 0);

            while (cmp.compare(array[--endCursor], pivot) > 0) {
                if (endCursor == start) {
                    break;
                }
            }

            if (startCursor >= endCursor) {
                break;
            }

            swap(array, startCursor, endCursor);

            if (cmp.compare(array[startCursor], pivot) == 0) {
                swap(array, startCursor, start + startOccurrences);
                startOccurrences++;
            }

            if (cmp.compare(array[endCursor], pivot) == 0) {
                swap(array, endCursor, end - 1 - endOccurrences);
                endOccurrences++;
            }
        }

        swap(array, end, startCursor);

        swap(array, startCursor, start, startOccurrences, end - 1, endOccurrences);

        return new IntTuple(startCursor - startOccurrences, startCursor + endOccurrences);
    }

        // find median of the first, middle and last element and sort them in array
    @VisibleForTesting
    static <T> void medianOfThree(T[] array, int start, int middle, int end, Comparator<T> cmp) {
        if (cmp.compare(array[start], array[middle]) < 0) {
            if (cmp.compare(array[middle], array[end]) >= 0) {
                if (cmp.compare(array[start], array[end]) < 0) {
                    swap(array, middle, end);
                } else {
                    swap(array, start, middle, end);
                }
            }
        } else {
            if (cmp.compare(array[middle], array[end]) < 0) {
                if (cmp.compare(array[start], array[end]) < 0) {
                    swap(array, start, middle);
                } else {
                    swap(array, middle, start, end);
                }
            } else {
                swap(array, start, end);
            }
        }
    }

    private static void swap(Object[] array, int i, int j) {
        var tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

    // ->
    private static void swap(Object[] array, int i, int j, int k) {
        var tmp = array[j];
        array[j] = array[i];
        array[i] = array[k];
        array[k] = tmp;
    }

    @VisibleForTesting
   static void swap(Object[] array, int center, int start, int startOccurrences, int end, int endOccurrences) {
        var leftCenterShift = center - start - startOccurrences;
        if (startOccurrences > 0 && leftCenterShift > 0) {
            System.arraycopy(array, center - leftCenterShift, array, start, leftCenterShift);
            for (int i = 0; i < startOccurrences; i++) {
                array[center - 1 - i] = array[center];
            }
        }

        var leftEndShift = end - center - endOccurrences;
        if (endOccurrences > 0 && leftEndShift > 0) {
            System.arraycopy(array, center + 1, array, end - leftEndShift + 1, leftEndShift);
            for (int i = 0; i < endOccurrences; i++) {
                array[center + 1 + i] = array[center];
            }
        }
    }

    @Override
    public String toString() {
        var stringBuilder = new StringBuilder("QuickSort: ");
        if (insertionThreshold > 0) {
            stringBuilder.append("insertion ");
        }
        if (useMedianOfThree) {
            stringBuilder.append("median ");
        }
        if (useOneRecursiveCall) {
            stringBuilder.append("oneRecurCall ");
        }
        if (useDutchFlagPartition) {
            stringBuilder.append("dutchFlag ");
        }
        return stringBuilder.toString();
    }

    record IntTuple(
            int _1,
            int _2
    ) {
        int left() {
            return _1;
        }

        int right() {
            return _2;
        }
    }
}