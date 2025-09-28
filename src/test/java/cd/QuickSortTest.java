package cd;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;

public class QuickSortTest {
    @Test
    void testMixedNumbers() {
        int[] a = {33, 14, 27, -5, 18, 0, 42};
        int[] exp = a.clone(); Arrays.sort(exp);
        QuickSort.sort(a);
        assertArrayEquals(exp, a);
    }
}