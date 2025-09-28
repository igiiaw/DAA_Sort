package cd;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Runner {
    public static void main(String[] args) {
        CsvWriter csv = new CsvWriter("results.csv");
        csv.writeLine("algorithm,n,time_ns,depth,comparisons,allocations,swaps");

        int[] ns = new int[] {1000, 2000, 5000, 10000};

        for (int n : ns) {
            int[] a = randomArray(n);

            // MERGESORT
            Metrics m = new Metrics(); DepthTracker d = new DepthTracker();
            int[] copy = Arrays.copyOf(a, a.length);
            long t0 = System.nanoTime();
            MergeSort.sort(copy, m, d);
            long t1 = System.nanoTime();
            csv.writeLine(String.format("mergesort,%d,%d,%d,%d,%d,%d", n, (t1 - t0), d.max(), m.comparisons.sum(), m.allocations.sum(), m.swaps.sum()));

            // QUICKSORT
            m.reset(); d.reset();
            copy = Arrays.copyOf(a, a.length);
            t0 = System.nanoTime();
            QuickSort.sort(copy, m, d);
            t1 = System.nanoTime();
            csv.writeLine(String.format("quicksort,%d,%d,%d,%d,%d,%d", n, (t1 - t0), d.max(), m.comparisons.sum(), m.allocations.sum(), m.swaps.sum()));

            // SELECT (median)
            m.reset(); d.reset();
            copy = Arrays.copyOf(a, a.length);
            t0 = System.nanoTime();
            int median = DeterministicSelect.select(copy, copy.length/2, m, d);
            t1 = System.nanoTime();
            csv.writeLine(String.format("select,%d,%d,%d,%d,%d,%d", n, (t1 - t0), d.max(), m.comparisons.sum(), m.allocations.sum(), m.swaps.sum()));

            // CLOSEST PAIR (random points)
            Point[] pts = new Point[n];
            for (int i = 0; i < n; i++) pts[i] = new Point(ThreadLocalRandom.current().nextDouble(), ThreadLocalRandom.current().nextDouble());
            t0 = System.nanoTime();
            double dmin = ClosestPair.closest(pts);
            t1 = System.nanoTime();
            csv.writeLine(String.format("closest,%d,%d,0,0,0,0", n, (t1 - t0)));

            System.out.println("n=" + n + " median=" + median + " closest=" + dmin);
        }
        System.out.println("Results written to results.csv");
    }

    private static int[] randomArray(int n){
        int[] a = new int[n];
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        for (int i = 0; i < n; i++) a[i] = rnd.nextInt(0, n*10);
        return a;
    }
}