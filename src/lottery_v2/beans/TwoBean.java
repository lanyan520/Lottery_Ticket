package lottery_v2.beans;

public class TwoBean {

    String data;
    String red1;
    String red2;
    String red3;
    String red4;
    String red5;
    String red6;
    String blue;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getRed1() {
        return red1;
    }

    public void setRed1(String red1) {
        this.red1 = red1;
    }

    public String getRed2() {
        return red2;
    }

    public void setRed2(String red2) {
        this.red2 = red2;
    }

    public String getRed3() {
        return red3;
    }

    public void setRed3(String red3) {
        this.red3 = red3;
    }

    public String getRed4() {
        return red4;
    }

    public void setRed4(String red4) {
        this.red4 = red4;
    }

    public String getRed5() {
        return red5;
    }

    public void setRed5(String red5) {
        this.red5 = red5;
    }

    public String getRed6() {
        return red6;
    }

    public void setRed6(String red6) {
        this.red6 = red6;
    }

    public String getBlue() {
        return blue;
    }

    public void setBlue(String blue) {
        this.blue = blue;
    }

    public String getNumbers(){
        return red1+","+red2+","+red3+","+red4+","+red5+","+red6;
    }

    public TwoBean() {

    }

    public TwoBean(String data, String red1, String red2, String red3, String red4, String red5, String red6, String blue) {
        this.data = data;
        this.red1 = red1;
        this.red2 = red2;
        this.red3 = red3;
        this.red4 = red4;
        this.red5 = red5;
        this.red6 = red6;
        this.blue = blue;
    }

    public TwoBean(String red1, String red2, String red3, String red4, String red5, String red6) {
        this.red1 = red1;
        this.red2 = red2;
        this.red3 = red3;
        this.red4 = red4;
        this.red5 = red5;
        this.red6 = red6;
    }

    @Override
    public String toString() {
        return "TwoBean{" +
                "data='" + data + '\'' +
                ", red1='" + red1 + '\'' +
                ", red2='" + red2 + '\'' +
                ", red3='" + red3 + '\'' +
                ", red4='" + red4 + '\'' +
                ", red5='" + red5 + '\'' +
                ", red6='" + red6 + '\'' +
                ", blue='" + blue + '\'' +
                '}';
    }
}
