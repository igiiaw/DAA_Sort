package cd;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.concurrent.ThreadLocalRandom;

public class ClosestPairTest {
    private double brute(Point[] pts){
        double best = Double.POSITIVE_INFINITY;
        for (int i=0;i<pts.length;i++) {
            for (int j=i+1;j<pts.length;j++) {
                double dx = pts[i].x - pts[j].x, dy = pts[i].y - pts[j].y;
                best = Math.min(best, Math.hypot(dx, dy));
            }
        }
        return best;
    }

    @Test
    void testSmallRandom() {
        for (int t=0;t<10;t++) {
            int n = 300;
            Point[] pts = new Point[n];
            for (int i=0;i<n;i++) pts[i] = new Point(ThreadLocalRandom.current().nextDouble(), ThreadLocalRandom.current().nextDouble());
            double a = ClosestPair.closest(pts);
            double b = brute(pts);
            assertEquals(b, a, 1e-9);
        }
    }
}