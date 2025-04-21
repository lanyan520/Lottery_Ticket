package lottery_v2.beans;

import java.util.Arrays;

public class MyFilterSumSpan {

        Integer[] sums;
        Integer[] spans;

        public void setSums(Integer[] sums) {
            this.sums = sums;
        }

        public void setSpans(Integer[] spans) {
            this.spans = spans;
        }

        public Integer[] getSums() {
            return sums;
        }

        public Integer[] getSpans() {
            return spans;
        }

    @Override
    public String toString() {
        return "MyFilterSumSpan{" +
                "sums=" + Arrays.toString(sums) +
                ", spans=" + Arrays.toString(spans) +
                '}';
    }

    public MyFilterSumSpan(Integer[] sums, Integer[] spans) {
            this.sums = sums;
            this.spans = spans;
        }

}
