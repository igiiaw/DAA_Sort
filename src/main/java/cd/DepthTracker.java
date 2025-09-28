package cd;

public class DepthTracker {
    private final ThreadLocal<Integer> depth = ThreadLocal.withInitial(() -> 0);
    private final ThreadLocal<Integer> maxDepth = ThreadLocal.withInitial(() -> 0);

    public void enter(){
        int d = depth.get() + 1;
        depth.set(d);
        if (d > maxDepth.get()) maxDepth.set(d);
    }
    public void exit(){
        depth.set(depth.get() - 1);
    }
    public int current(){ return depth.get(); }
    public int max(){ return maxDepth.get(); }
    public void reset(){ depth.set(0); maxDepth.set(0); }
}