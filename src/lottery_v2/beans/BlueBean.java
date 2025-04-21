package lottery_v2.beans;

public class BlueBean {
    String cBlue;
    String nBlue;
    int count;

    public String getcBlue() {
        return cBlue;
    }

    public void setcBlue(String cBlue) {
        this.cBlue = cBlue;
    }

    public String getnBlue() {
        return nBlue;
    }

    public void setnBlue(String nBlue) {
        this.nBlue = nBlue;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public BlueBean(String cBlue, String nBlue, int count) {
        this.cBlue = cBlue;
        this.nBlue = nBlue;
        this.count = count;
    }

    @Override
    public String toString() {
        return "BlueBean{" +
                "cBlue='" + cBlue + '\'' +
                ", nBlue='" + nBlue + '\'' +
                ", count=" + count +
                '}';
    }
}
