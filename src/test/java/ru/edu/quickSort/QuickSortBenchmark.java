package ru.edu.quickSort;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Group;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;


@Fork(1)
@Warmup(iterations = 1, timeUnit = TimeUnit.NANOSECONDS)
@Measurement(iterations = 5, timeUnit = TimeUnit.NANOSECONDS)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@BenchmarkMode(Mode.AverageTime)
@State(Scope.Benchmark)
public class QuickSortBenchmark {
    private QuickSort basicQuickSort;
    private QuickSort insertionQuickSort;
    private QuickSort medianQuickSort;
    private QuickSort dutchFlagQuickSort;

    private Integer[][] arrays;

    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder()
                .include(".*" + QuickSortBenchmark.class.getSimpleName() + ".*")
                .build();

        new Runner(options).run();
    }

    @Setup(Level.Trial)
    public void benchmarkSetup() {
        basicQuickSort = new QuickSort(-1, false, false, false);
        insertionQuickSort = new QuickSort(50, false, false, false);
        medianQuickSort = new QuickSort(-1, true, false, false);
        dutchFlagQuickSort = new QuickSort(-1, false, false, true);
    }

    @Setup(Level.Iteration)
    public void up() {
        var array = generateIntArray(15_000);
        arrays = new Integer[4][];
        Arrays.fill(arrays, array);
    }

    @Benchmark
    @Group("randomArray")
    public void basicQuickSort() {
        basicQuickSort.sort(arrays[0]);
    }

    @Benchmark
    @Group("randomArray")
    public void insertionQuickSort() {
        insertionQuickSort.sort(arrays[1]);
    }

    @Benchmark
    @Group("randomArray")
    public void medianQuickSort() {
        medianQuickSort.sort(arrays[2]);
    }

    @Benchmark
    @Group("randomArray")
    public void dutchFlagQuickSort() {
        dutchFlagQuickSort.sort(arrays[3]);
    }

    private static Integer[] generateIntArray(int size) {
        var array = new Integer[size];
        for (int i = 0; i < array.length; i++) {
            array[i] = ThreadLocalRandom.current().nextInt();
        }
        return array;
    }
}
