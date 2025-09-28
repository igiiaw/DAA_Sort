package cd;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;

public class InsertionSortTest {
    @Test
    void testSortRandom() {
        int[] arr = {42, 17, 8, 99, 23, 56};
        int[] expected = arr.clone();
        Arrays.sort(expected);
        InsertionSort.sort(arr);
        assertArrayEquals(expected, arr);
    }
}