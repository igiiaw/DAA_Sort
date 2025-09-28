package cd;

public class MergeSort {
    private static final int CUTOFF = 16;

    public static void sort(int[] a) {
        sort(a, null, null);
    }
    public static void sort(int[] a, Metrics m, DepthTracker d) {
        if (a == null || a.length < 2) return;
        int[] aux = new int[a.length];
        if (m != null) m.incAllocations(a.length);
        mergeSort(a, aux, 0, a.length - 1, m, d);
    }
    private static void mergeSort(int[] a, int[] aux, int left, int right, Metrics m, DepthTracker d) {
        if (d != null) d.enter();
        try {
            if (right - left + 1 <= CUTOFF) {
                InsertionSort.sort(a, left, right, m);
                return;
            }
            int mid = (left + right) >>> 1;
            mergeSort(a, aux, left, mid, m, d);
            mergeSort(a, aux, mid + 1, right, m, d);
            merge(a, aux, left, mid, right, m);
        } finally {
            if (d != null) d.exit();
        }
    }
    private static void merge(int[] a, int[] aux, int left, int mid, int right, Metrics m) {
        System.arraycopy(a, left, aux, left, right - left + 1);
        if (m != null) m.incAllocations(right - left + 1);

        int i = left, j = mid + 1;
        for (int k = left; k <= right; k++) {
            if (i > mid) {
                a[k] = aux[j++];
                if (m != null) m.incSwaps(1);
            } else if (j > right) {
                a[k] = aux[i++];
                if (m != null) m.incSwaps(1);
            } else {
                if (m != null) m.incComparisons(1);
                if (aux[i] <= aux[j]) {
                    a[k] = aux[i++];
                    if (m != null) m.incSwaps(1);
                } else {
                    a[k] = aux[j++];
                    if (m != null) m.incSwaps(1);
                }
            }
        }
    }
}