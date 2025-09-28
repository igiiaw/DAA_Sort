package cd;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.Random;

public class DeterministicSelectTest {
    @Test
    void testSelectMaxManyRandom() {
        Random rnd = new Random(456); // другой seed
        for (int t = 0; t < 100; t++) {
            int n = 100;
            int[] a = rnd.ints(n, -500, 500).toArray(); // другой диапазон чисел
            int[] b = a.clone();
            Arrays.sort(b);
            int want = b[n - 1]; // берем максимум
            int got = DeterministicSelect.select(a.clone(), n - 1);
            assertEquals(want, got);
        }
    }
}