package lottery_v2.beans;

public class ThreeDBean {

    /**
     *开奖期号
     */
    String data;
    /**
     *开奖单选号
     */
    String singleNumber;

    @Override
    public String toString() {
        return "ThreeDBean{" +
                "data='" + data + '\'' +
                ", singleNumber='" + singleNumber + '\'' +
                ", groupNumber='" + groupNumber + '\'' +
                ", sum=" + sum +
                ", span=" + span +
                '}';
    }

    /**
     *开奖组选号
     */
    String groupNumber;
    /**
     *和值
     */
    int sum;
    /**
     *跨度
     */
    int span;
}
