package lottery_v2.beans;

import java.util.Arrays;

public class GallbladderCodeBean {

    public GallbladderCodeBean(String[] codes, int min, int max) {
        this.codes = codes;
        this.min = min;
        this.max = max;
    }

    String[] codes;
    int min;
    int max;

    public void setCodes(String[] codes) {
        this.codes = codes;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public String[] getCodes() {
        return codes;
    }

    @Override
    public String toString() {
        return "GallbladderCodeBean{" +
                "codes=" + Arrays.toString(codes) +
                ", min=" + min +
                ", max=" + max +
                '}';
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }
}
