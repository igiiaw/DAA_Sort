package cd;

import java.util.Arrays;
import java.util.Comparator;

public class ClosestPair {
    public static double closest(Point[] pts) {
        if (pts == null || pts.length < 2) return Double.POSITIVE_INFINITY;
        Point[] px = pts.clone();
        Arrays.sort(px, Comparator.comparingDouble(p -> p.x));
        Point[] aux = new Point[px.length];
        return closestRec(px, aux, 0, px.length);
    }

    private static double distance(Point a, Point b){
        double dx = a.x - b.x;
        double dy = a.y - b.y;
        return Math.hypot(dx, dy);
    }

    private static double closestRec(Point[] ptsByX, Point[] aux, int lo, int hi){
        int n = hi - lo;
        if (n <= 3) {
            double best = Double.POSITIVE_INFINITY;
            for (int i = lo; i < hi; i++)
                for (int j = i + 1; j < hi; j++)
                    best = Math.min(best, distance(ptsByX[i], ptsByX[j]));
            Arrays.sort(ptsByX, lo, hi, Comparator.comparingDouble(p -> p.y));
            return best;
        }
        int mid = lo + n/2;
        double midx = ptsByX[mid].x;
        double dl = closestRec(ptsByX, aux, lo, mid);
        double dr = closestRec(ptsByX, aux, mid, hi);
        double d = Math.min(dl, dr);

        int i = lo, j = mid, k = lo;
        while (i < mid || j < hi) {
            if (j >= hi || (i < mid && ptsByX[i].y <= ptsByX[j].y)) aux[k++] = ptsByX[i++];
            else aux[k++] = ptsByX[j++];
        }
        System.arraycopy(aux, lo, ptsByX, lo, n);

        int stripSize = 0;
        for (int p = lo; p < hi; p++) {
            if (Math.abs(ptsByX[p].x - midx) < d) aux[stripSize++] = ptsByX[p];
        }
        for (int u = 0; u < stripSize; u++) {
            for (int v = u + 1; v < stripSize && (aux[v].y - aux[u].y) < d && v <= u + 7; v++) {
                d = Math.min(d, distance(aux[u], aux[v]));
            }
        }
        return d;
    }
}