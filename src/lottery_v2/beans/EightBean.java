package lottery_v2.beans;

public class EightBean {
    String number;
    int count;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public EightBean(String number, int count) {
        this.number = number;
        this.count = count;
    }

    @Override
    public String toString() {
        return "EightBean{" +
                "number='" + number + '\'' +
                ", count=" + count +
                '}';
    }
}
