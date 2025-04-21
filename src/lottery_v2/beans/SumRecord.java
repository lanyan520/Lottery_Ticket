package lottery_v2.beans;

public class SumRecord {
    private final int currentSum;
    private final int nextSum;
    private final int count;

    public int getCurrentSum() {
        return currentSum;
    }

    public int getNextSum() {
        return nextSum;
    }

    @Override
    public String toString() {
        return "SumRecord{" +
                "currentSum=" + currentSum +
                ", nextSum=" + nextSum +
                ", count=" + count +
                '}';
    }

    public int getCount() {
        return count;
    }

    public SumRecord(int currentSpan, int nextSpan, int count) {
        this.currentSum = currentSpan;
        this.nextSum = nextSpan;
        this.count = count;
    }
}
