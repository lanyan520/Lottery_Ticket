package lottery_v2.beans;

public class SpanRecord {

    private final int currentSpan;
    private final int nextSpan;
    private final int count;

    public int getCurrentSpan() {
        return currentSpan;
    }

    @Override
    public String toString() {
        return "SpanRecord{" +
                "currentSpan=" + currentSpan +
                ", nextSpan=" + nextSpan +
                ", count=" + count +
                '}';
    }

    public int getNextSpan() {
        return nextSpan;
    }

    public int getCount() {
        return count;
    }

    public SpanRecord(int currentSpan, int nextSpan, int count) {
        this.currentSpan = currentSpan;
        this.nextSpan = nextSpan;
        this.count = count;
    }
}
