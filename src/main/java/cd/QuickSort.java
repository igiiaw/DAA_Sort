    package cd;

    import java.util.concurrent.ThreadLocalRandom;

    public class QuickSort {
        private static final int CUTOFF = 16;

        public static void sort(int[] a) {
            sort(a, null, null);
        }

        public static void sort(int[] a, Metrics m, DepthTracker d) {
            if (a == null || a.length < 2) return;
            quickSort(a, 0, a.length - 1, m, d);
        }

        private static void quickSort(int[] a, int lo, int hi, Metrics m, DepthTracker d) {
            if (d != null) d.enter();
            try {
                while (lo < hi) {
                    int len = hi - lo + 1;
                    if (len <= CUTOFF) {
                        InsertionSort.sort(a, lo, hi, m);
                        return;
                    }
                    int pivotIndex = ThreadLocalRandom.current().nextInt(lo, hi + 1);
                    int pivot = a[pivotIndex];
                    swap(a, pivotIndex, hi, m); // move pivot to end
                    int p = partition(a, lo, hi, pivot, m);

                    int leftLen = p - 1 - lo;
                    int rightLen = hi - (p + 1) + 1;
                    if (leftLen < rightLen) {
                        quickSort(a, lo, p - 1, m, d);
                        lo = p + 1;
                    } else {
                        quickSort(a, p + 1, hi, m, d);
                        hi = p - 1;
                    }
                }
            } finally {
                if (d != null) d.exit();
            }
        }

        private static int partition(int[] a, int lo, int hi, int pivot, Metrics m) {
            int i = lo;
            for (int j = lo; j < hi; j++) {
                if (m != null) m.incComparisons(1);
                if (a[j] < pivot) {
                    swap(a, i, j, m);
                    i++;
                }
            }
            swap(a, i, hi, m); // place pivot at i
            return i;
        }

        private static void swap(int[] a, int i, int j, Metrics m) {
            if (i == j) return;
            int t = a[i]; a[i] = a[j]; a[j] = t;
            if (m != null) m.incSwaps(1);
        }
    }