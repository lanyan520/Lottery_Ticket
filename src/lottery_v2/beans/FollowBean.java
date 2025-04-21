package lottery_v2.beans;

public class FollowBean {

    String cRed;
    String nRed;
    int count;

    public String getcRed() {
        return cRed;
    }

    public void setcRed(String cRed) {
        this.cRed = cRed;
    }

    public String getnRed() {
        return nRed;
    }

    public void setnRed(String nRed) {
        this.nRed = nRed;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public FollowBean(String cRed, String nRed, int count) {
        this.cRed = cRed;
        this.nRed = nRed;
        this.count = count;
    }

    public FollowBean(String cRed, String nRed) {
        this.cRed = cRed;
        this.nRed = nRed;
    }
    @Override
    public String toString() {
        return "FollowBean{" +
                "cRed='" + cRed + '\'' +
                ", nRed='" + nRed + '\'' +
                ", count='" + count + '\'' +
                '}';
    }
}
