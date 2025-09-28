package cd;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;

public class MergeSortTest {
    @Test
    void testWithNegatives() {
        int[] a = {15, -7, 23, 0, -42, 5, 8};
        int[] exp = a.clone(); Arrays.sort(exp);
        MergeSort.sort(a);
        assertArrayEquals(exp, a);
    }
}