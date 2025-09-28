package cd;

import java.util.concurrent.atomic.LongAdder;

public class Metrics {
    public final LongAdder comparisons = new LongAdder();
    public final LongAdder allocations = new LongAdder();
    public final LongAdder swaps = new LongAdder();

    public void incComparisons(long n){ comparisons.add(n); }
    public void incAllocations(long n){ allocations.add(n); }
    public void incSwaps(long n){ swaps.add(n); }

    public void reset(){
        comparisons.reset();
        allocations.reset();
        swaps.reset();
    }

    @Override
    public String toString(){
        return "cmp=" + comparisons.sum() + " alloc=" + allocations.sum() + " swaps=" + swaps.sum();
    }
}
