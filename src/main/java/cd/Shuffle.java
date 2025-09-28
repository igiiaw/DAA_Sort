package cd;

import java.util.concurrent.ThreadLocalRandom;

public final class Shuffle {
    private Shuffle(){}
    public static void shuffle(int[] a){
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        for (int i = a.length - 1; i > 0; i--) {
            int j = rnd.nextInt(i + 1);
            int t = a[i]; a[i] = a[j]; a[j] = t;
        }
    }
}