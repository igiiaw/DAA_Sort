package cd;

public class InsertionSort {
    public static void sort(int[] a) {
        sort(a, 0, a.length - 1, null);
    }

    public static void sort(int[] a, int left, int right, Metrics m) {
        for (int i = left + 1; i <= right; i++) {
            int key = a[i];
            int j = i - 1;
            while (j >= left) {
                if (m != null) m.incComparisons(1);
                if (a[j] > key) {
                    a[j + 1] = a[j];
                    if (m != null) m.incSwaps(1);
                    j--;
                } else break;
            }
            a[j + 1] = key;
            if (m != null) m.incAllocations(1);
        }
    }
}