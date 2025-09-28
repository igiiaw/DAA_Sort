package cd;

import java.util.Arrays;

public class DeterministicSelect {
    public static int select(int[] a, int k) {
        return select(a, k, null, null);
    }

    public static int select(int[] a, int k, Metrics m, DepthTracker d) {
        if (k < 0 || k >= a.length) throw new IllegalArgumentException("k out of range");
        return selectInPlace(a, 0, a.length - 1, k, m, d);
    }

    private static int selectInPlace(int[] a, int lo, int hi, int k, Metrics m, DepthTracker d) {
        if (d != null) d.enter();
        try {
            while (lo <= hi) {
                if (hi - lo + 1 <= 16) {
                    Arrays.sort(a, lo, hi + 1);
                    if (m != null) m.incAllocations(hi - lo + 1);
                    return a[lo + k];
                }

                int pivot = medianOfMedians(a, lo, hi, m);
                int pivotIndex = partitionAroundValue(a, lo, hi, pivot, m);
                int rank = pivotIndex - lo;
                if (k == rank) return a[pivotIndex];
                if (k < rank) {
                    hi = pivotIndex - 1;
                } else {
                    k = k - rank - 1;
                    lo = pivotIndex + 1;
                }
            }
            throw new RuntimeException("shouldn't reach here");
        } finally {
            if (d != null) d.exit();
        }
    }

    private static int partitionAroundValue(int[] a, int lo, int hi, int pivot, Metrics m) {
        int store = lo;
        for (int i = lo; i <= hi; i++) {
            if (m != null) m.incComparisons(1);
            if (a[i] < pivot) {
                int t = a[store]; a[store] = a[i]; a[i] = t;
                if (m != null) m.incSwaps(1);
                store++;
            }
        }
        int pivotIndex = store;
        for (int i = store; i <= hi; i++) {
            if (a[i] == pivot) { pivotIndex = i; break; }
        }
        int t = a[pivotIndex]; a[pivotIndex] = a[store]; a[store] = t;
        if (m != null) m.incSwaps(1);
        return store;
    }

    private static int medianOfMedians(int[] a, int lo, int hi, Metrics m) {
        int n = hi - lo + 1;
        int numGroups = (n + 4) / 5;
        for (int g = 0; g < numGroups; g++) {
            int gs = lo + g * 5;
            int ge = Math.min(gs + 5, hi + 1);
            Arrays.sort(a, gs, ge);
            if (m != null) m.incAllocations(ge - gs);
            int medianIndex = gs + (ge - gs - 1) / 2;
            int target = lo + g;
            int tmp = a[target]; a[target] = a[medianIndex]; a[medianIndex] = tmp;
            if (m != null) m.incSwaps(1);
        }
        int mlen = numGroups;
        if (mlen == 1) return a[lo];
        return selectInPlace(a, lo, lo + mlen - 1, mlen / 2, m, new DepthTracker());
    }
}