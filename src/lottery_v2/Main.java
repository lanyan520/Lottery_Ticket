package lottery_v2;

import lottery_v2.beans.GallbladderCodeBean;
import lottery_v2.beans.TwoBean;
import lottery_v2.calculate.ThreeCalculateHelper;
import lottery_v2.data.eight.Lottery_2025;
import lottery_v2.interfaces.two.TwoInterface;
import lottery_v2.mysql.MysqlBase;
import lottery_v2.mysql.constant.BaseConstant;
import lottery_v2.mysql.constant.EightConstant;
import lottery_v2.mysql.three.ThreeSqlBase;
import lottery_v2.mysql.two.TwoSqlBatchBase;
import lottery_v2.utils.EightFunctionHelper;
import lottery_v2.utils.ThreeFunctionHelper;
import lottery_v2.utils.TwoFunctionHelper;

import java.sql.SQLException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws SQLException {

//        String powerNumber = "173";
//        //试机号
//        String testNumber = "071";
//        //上期中奖号
//        String c3DNumber = "385";
//        ThreeFunctionHelper.getInstance().myNext3DNumber(powerNumber,testNumber,c3DNumber);

        //initEight();
       // initThree();
         // initTwo();

        //上期开奖号
//        String[] data = Lottery_2025.DATA_TEST;
//        //今日看好胆码
//        String[] codes = {"13","27","61","64"};
//        autoPredictionNumbers(data,codes);

         // calculate()


        //TwoFunctionHelper.getInstance().batchInsertFollowRed();

       // TwoFunctionHelper.getInstance().batchInsertFollowBlue();

        //initTwoGroup();

        //TwoFunctionHelper.getInstance().checkNumberWithSelected(2, 110);
        //TwoFunctionHelper.getInstance().checkNumberWithSelected(3, 22);

    }

    /**
     * 数据库3D表初始化，执行一次
     * @throws SQLException
     */
    public static void initThree() throws SQLException {
        ThreeFunctionHelper.getInstance().insertAllData();
        ThreeFunctionHelper.getInstance().insertGroupTable();
        ThreeFunctionHelper.getInstance().insertSingleTable();
        ThreeFunctionHelper.getInstance().batchInsert3DSumTable();
        ThreeFunctionHelper.getInstance().batchInsert3DSpanTable();
    }

    /**
     * 数据库快乐8表初始化，执行一次
     * @throws SQLException
     */
    public static void initEight() throws SQLException {
        EightFunctionHelper.getInstance().insertAllData();
        //插入选2-选8，选7-选10非常耗时间，选10没多大参考价值，个人值分析道选8即可
        for (int i = 2; i < 9; i++) {
            EightFunctionHelper.getInstance().batchInsertWithSelected(EightFunctionHelper.getInstance().getAllData(), i);
        }
    }

    /**
     * 数据库快乐8表初始化，执行一次
     * @throws SQLException
     */
    public static void initTwoGroup() throws SQLException {
        //插入选2-选6
        for (int i = 2; i < 7; i++) {
            TwoFunctionHelper.getInstance().batchInsertWithSelected(i);
        }
    }

    public static void initTwo() throws SQLException {
        TwoFunctionHelper.getInstance().insertAllData(TwoFunctionHelper.getInstance().getAllData());
    }

    /**
     * 自动预测快乐8选2-选5
     */
    public static void autoPredictionNumbers(String[] data, String[] codes){

        //过滤胆码必须含有几个例如1-2
        GallbladderCodeBean bean2 = new GallbladderCodeBean(codes,1,2);
        //例如选2，出现次数 要大于107次才符合要求，selectedFrequency值越小，过滤后的号就越多，不能超过理论范围90-118
        EightFunctionHelper.getInstance().autoPredictionNumbers(data,2,107, bean2);

        GallbladderCodeBean bean3 = new GallbladderCodeBean(codes,2,3);
        EightFunctionHelper.getInstance().autoPredictionNumbers(data,3,32,bean3);
        GallbladderCodeBean bean4 = new GallbladderCodeBean(codes,3,4);
        EightFunctionHelper.getInstance().autoPredictionNumbers(data,4,11,bean4);
        GallbladderCodeBean bean5 = new GallbladderCodeBean(codes,3,5);
        EightFunctionHelper.getInstance().autoPredictionNumbers(data,5,5,bean5);

        GallbladderCodeBean bean6 = new GallbladderCodeBean(codes,4,4);
        EightFunctionHelper.getInstance().autoPredictionNumbers(data,6,2,bean6);

        GallbladderCodeBean bean7 = new GallbladderCodeBean(codes,4,4);
        EightFunctionHelper.getInstance().autoPredictionNumbers(data,7,2,bean7);
    }

    public static void calculate(){
        int selected = 7;
        int selectedFrequency = 3;
        int maxSize = 400;
        EightFunctionHelper.getInstance().calculate(EightFunctionHelper.getInstance().queryNumbersWithCount(selected,selectedFrequency),Lottery_2025.DATA,selected,selectedFrequency,maxSize);
    }

}
