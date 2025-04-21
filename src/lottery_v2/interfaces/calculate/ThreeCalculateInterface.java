package lottery_v2.interfaces.calculate;

import java.util.Arrays;
import java.util.List;

public interface ThreeCalculateInterface {

    /**
     * 单选判断开奖号和自己的号码是否相同
     * @param openNumbers
     * @param ownNumbers
     * @return
     */
    boolean isWinPrizeWith3DSingle(String openNumbers,String ownNumbers);

    /**
     * 组选判断开奖号和自己的号码是否相同
     * @param openNumbers
     * @param ownNumbers
     * @return
     */
    boolean isWinPrizeWith3DGroup(String openNumbers,String ownNumbers);

    /**
     * 判断号码类型，0=豹子，1=组3，2组6
     * @param number
     * @return
     */
    int getNumberType(String number);

    /**
     * 计算当前开奖号的和值
     * @param number
     * @return
     */
    int getSum(String number);

    /**
     * 计算当前开奖号的跨度值
     * @param number
     * @return
     */
    int getSpan(String number);

    /**
     * 开奖号单选转换组选
     * @param number
     * @return
     */
    String getGroupNumber(String number);

    /**
     * 单选奖金
     * @return
     */
    int getSingleMoney();

    /**
     * 组选组3奖金
     * @return
     */
    int getGroupMoneyWithType1();

    /**
     * 组选组6奖金
     * @return
     */
    int getGroupMoneyWithType2();


    /**
     * 豹子奖金
     * @return
     */
    int getLeopardMoney();

    /**
     * 计算组选中奖金额、次数
     * @param winNumbers 开奖数据
     * @param ownNumbers 自己的投注数据
     */
    abstract void calculateWith3DGroup(List<String> winNumbers, String[] ownNumbers);

    /**
     * 判断3D组选是否中奖（或者用于判断选号是否是在高频组选号里面）
     * @param ownNumbers
     * @param winNumber
     * @return
     */
    abstract boolean containsElement3D(String[] ownNumbers, String winNumber);

    /**
     * 查询数据库的组选号
     * 根据maxSize 截取数组，maxSize取值范围1-220，建议不要大于200
     * //1-200取值范围，值越大，误差就越小，判断就越准确，过滤掉的低频号就少，过滤的号少效果就不明显了
     * @param queryGroupNumbers 查询到的开奖号
     * @param ownNumbers 自己选择的一堆组选号
     * @param maxSize 1-220
     * @return
     */
    abstract void checkOwnGroupNumbers(List<String> queryGroupNumbers, String[] ownNumbers, int maxSize);

}
