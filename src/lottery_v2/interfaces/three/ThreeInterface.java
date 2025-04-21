package lottery_v2.interfaces.three;

import lottery_v2.beans.GallbladderCodeBean;
import lottery_v2.beans.MyFilterSumSpan;

import java.sql.SQLException;
import java.util.List;

public interface ThreeInterface {

    /**
     * 获取3D所有开奖数据
     * @return
     */
    abstract List<String> getAllData();
    /**
     * 2004-2024年3D开奖数据插入数据库
     * @throws SQLException
     */
    abstract void insertAllData() throws SQLException;
    /**
     * 批量插入单选统计表
     * @throws SQLException
     */
    abstract void insertSingleTable() throws SQLException;
    /**
     * 批量插入组选统计表
     * @throws SQLException
     */
    abstract void insertGroupTable() throws SQLException;

    /**
     * 批量插入和值统计表
     * @throws SQLException
     */
    abstract void batchInsert3DSumTable() throws SQLException;

    /**
     * 批量插入跨度统计表
     * @throws SQLException
     */
    abstract void batchInsert3DSpanTable() throws SQLException;


    /**
     * 查询所有的组选号
     * 号码按照count频率排序的
     * 总共220注
     */
    abstract List<String> queryGroupNumberWithCount();

    /**
     * 匹配3D批量投注号与开奖号，进行金额计算、中奖次数统计
     */
    abstract void calculateWith3D(List<String> winNumbers, String[] own);

    /**
     * 查询数据库的组选号
     * 根据maxSize 截取数组，maxSize取值范围1-220，建议不要大于200
     * //1-200取值范围，值越大，误差就越小，判断就越准确，过滤掉的低频号就少，过滤的号少效果就不明显了
     * @param ownNumbers 自己选择的一堆组选号
     * @param maxSize 1-220
     * @return
     */
    abstract void checkOwnGroupNumbers(String[] ownNumbers, int maxSize);

    /**
     * 预测下期跨度高频值
     * @param cspan
     * @return
     */
    abstract List<Integer> queryNextSpans(int cspan);

    /**
     * 预测下期和值高频值
     * @param csum
     * @return
     */
    abstract List<Integer> queryNextSums(int csum);

    /***
     * 判断当前跨度是否在高频跨度范围内
     * @param nextSpans
     * @param span
     * @return
     */
    abstract boolean hasNextSpan(List<Integer> nextSpans, int span);

    /***
     * 判断当前和值是否在高频和值范围内
     * @param nextSums
     * @param sum
     * @return
     */
    abstract boolean hasNextSum(List<Integer> nextSums, int sum);

    /**
     * 是否是组选3或者组选6
     * @param number
     * @return
     */
    abstract boolean is3DGroup3Or6 (String number);

    /**
     * 是否是组选6
     * @param number
     * @return
     */
    abstract boolean is3DGroup6 (String number);

    /**
     * 是否是组选3
     * @param number
     * @return
     */
    boolean is3DGroup3 (String number);

    /**
     * 是否是豹子
     * @param number
     * @return
     */
    boolean is3DGroupLeopard (String number);

    /**
     * 判断号码类型是否匹配
     * @param number
     * @param numberType : 0豹子，1组选3，2组选6，3，组6或者组3
     * @return
     */
    abstract boolean check3DGroupNumberType (String number, int numberType);

    /**
     * 计算是否符合匹配胆码范围值
     * @param number
     * @param array
     * @return
     */
    abstract boolean countMatches(String number, String[] array, int min, int max);

    /**
     * 胆码转换成数组
     * @param number
     * @return
     */
    abstract String[] formatNumberWithGallbladderCode (String number);

    /***
     * 删除开奖中个多次的号
     * @param winnerNumbers
     * @param groupNumbersWithCodes
     * @param maxWinnerCount
     * @return
     */
    abstract List<String> deleteWinnerNumbers(List<String> winnerNumbers, List<String> groupNumbersWithCodes,int maxWinnerCount);

    /***
     * 缩水过滤函数
     * 流程设计：
     * 1.计算出所有组选
     * 2.预测下期跨度，去掉预测的低频跨度值排序最后的几个
     * 3.将计算出的所有组选匹配跨度值，不在范围内的删除
     * 4.预测下期和值，取最高频的10个和值
     * 5.将上面计算出的结果再次与和值匹配，不在范围内的删除
     * 6.号码类型输入匹配，不在号码类型范围内的删除
     * 7.确定胆码出几个，最少出0个，最多出3个，进行匹配（可以设定多组，默认支持自动化），删除不在范围的号
     * 8.与今年的历史开奖号进行匹配，中奖>=2次的直接删除
     * 9.没中过奖的号单独输出
     *
     * 执行到第8步，机率高
     * 执行到第9步，机率会下降，但是缩水后的号码会变少
     */
    abstract void next3DNumber(String c3DNumber, int removeSpanSize, int selectMaxSums, int numberType, List<GallbladderCodeBean> codes, int maxWinnerCount, List<String> winnerNumbers, MyFilterSumSpan sumSpanBean);

    /**
     * 标准预测缩水过滤
     * @param powerNumber
     * @param testNumber
     * @param c3DNumber
     */
    abstract void defaultNext3DNumber(String powerNumber, String testNumber, String c3DNumber);

    /**
     * 个人预测缩水过滤
     * @param powerNumber
     * @param testNumber
     * @param c3DNumber
     */
    abstract void myNext3DNumber(String powerNumber, String testNumber, String c3DNumber);
}
