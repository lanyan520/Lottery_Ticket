package lottery_v2.utils;

import lottery_v2.beans.FollowBean;
import lottery_v2.beans.TwoBean;
import lottery_v2.data.two.Lottery_2003_2009;
import lottery_v2.data.two.Lottery_2010_2024;
import lottery_v2.interfaces.two.TwoInterface;
import lottery_v2.mysql.constant.EightConstant;
import lottery_v2.mysql.constant.ThreeConstant;
import lottery_v2.mysql.constant.TwoConstant;
import lottery_v2.mysql.eight.EightSqlBatchBase;
import lottery_v2.mysql.three.ThreeSqlBase;
import lottery_v2.mysql.three.ThreeSqlBatchBase;
import lottery_v2.mysql.two.TwoSqlBase;
import lottery_v2.mysql.two.TwoSqlBatchBase;

import java.sql.SQLException;
import java.util.*;

public class TwoFunctionHelper implements TwoInterface,TwoInterface.InsertInterface {

    private static volatile TwoFunctionHelper instance ;
    public static TwoFunctionHelper getInstance() {
        if (instance == null) { // 第一次检查，避免不必要的锁竞争
            synchronized (TwoFunctionHelper.class) {
                if (instance == null) { // 第二次检查，确保唯一性
                    instance = new TwoFunctionHelper();
                }
            }
        }
        return instance;
    }


    @Override
    public List<TwoBean> getAllData() {
        List<TwoBean> allData = new ArrayList<>();
        allData.addAll(Lottery_2003_2009.DATA);
        allData.addAll(Lottery_2010_2024.DATA);
        return allData;
    }

    @Override
    public List<TwoBean> queryAllRedData() {
        return TwoSqlBase.getInstance().queryAllRedData();
    }

    @Override
    public void insertAllData(List<TwoBean> data) throws SQLException {
        /**
         * 先创建数据库
         * 在创建表
         * 插入数据
         */
        TwoSqlBatchBase.getInstance().ensureDatabaseExists(TwoConstant.SQL.dbName);
        TwoSqlBatchBase.getInstance().initializeTable(TwoConstant.SQL.createTable_Two_Base);
        TwoSqlBatchBase.getInstance().insertAllData(data);
    }

    /**
     * 红球跟随号插入数据库
     * 1.确定创建表
     * 2.查询所有红球数据
     * 3.重新统计跟随号
     * 4.插入数据库
     * @throws SQLException
     */
    public void batchInsertFollowRed() throws SQLException {
        TwoSqlBatchBase.getInstance().initializeTable(TwoConstant.SQL.getCreateTableFollowSQL());
        List<FollowBean> data = FollowNumberHelper.getInstance().getFollowNumberList(TwoSqlBase.getInstance().queryAllRedData());
        TwoSqlBatchBase.getInstance().insertFollowData(data);
    }

    public void batchInsertFollowBlue() throws SQLException {
        //自动创建基础蓝球数据跟随表
        TwoSqlBatchBase.getInstance().initializeTable(TwoConstant.SQL.createTable_Follow_Blue);
        TwoSqlBatchBase.getInstance().batchInsertFollowBlue();
    }


    public void batchInsertWithSelected(int selected) throws SQLException {
        //自动创建基础3D数据库表
        List<String> numbers = TwoSqlBase.getInstance().queryAllNumbersData();
        switch (selected){
            case 2:
                TwoSqlBatchBase.getInstance().initializeTable(TwoConstant.SQL.createTable_Two_2);
                break;
            case 3:
                TwoSqlBatchBase.getInstance().initializeTable(TwoConstant.SQL.createTable_Two_3);
                break;
            case 4:
                TwoSqlBatchBase.getInstance().initializeTable(TwoConstant.SQL.createTable_Two_4);
                break;
            case 5:
                TwoSqlBatchBase.getInstance().initializeTable(TwoConstant.SQL.createTable_Two_5);
                break;
            case 6:
                TwoSqlBatchBase.getInstance().initializeTable(TwoConstant.SQL.createTable_Two_6);
                break;
        }

        for (int i = 0; i < numbers.size(); i++) {
            List<String> data = Arrays.asList(numbers.get(i).split(","));
            System.out.println("第"+(i+1)+"期数据: " + data);
            TwoSqlBatchBase.getInstance().batchInsertWithSelected(data,selected);
            System.out.println("第"+(i+1)+"期数据插入成功" );
            System.out.println("--------------------------------------------");
        }
    }

    /**
     * 查询所有的红球组合Numbers号
     * @param selected 2-5
     * @param selectedFrequency 2-110,平均一年5次； 3-22，平均一年一次； 4-6高频； 5-2高频
     * @return
     */
    public List<String> queryRedNumbersWithCountSort(int selected,int selectedFrequency){
        return TwoSqlBase.getInstance().queryRedNumbersWithCountSort(selected,selectedFrequency);
    }

    public static void checkNumberWithSelected(int selected,int selectedFrequency){
        //查询2003年到2024年所有的红球组合号出现的高频率号，例如selected=2,selectedFrequency=110--->意思是两个红球组合出现次数>110次的号，22年数据意味着每年最少出现5次
        List<String> data =TwoFunctionHelper.getInstance().queryRedNumbersWithCountSort(selected,selectedFrequency);
        //获取2025年开奖数据
        List<TwoBean> data2025 = lottery_v2.data.two.Lottery_2025.DATA;
        List<String> dataString2025 = new ArrayList<>();
        for (int i = 0; i < data2025.size(); i++) {
            dataString2025.add(data2025.get(i).getNumbers());
        }

        //根据2025年开奖数据生成所有红球组合号，例如selected=2----》所有红球两个号的组合号，生成后排序
        List<String> set = new ArrayList<>();
        for (int i = 0; i < dataString2025.size(); i++) {
            List<List<String>> generateNumbers = TwoSqlBatchBase.getInstance().generateNumbers(Arrays.asList(dataString2025.get(i).split(",")),selected);
            for (List<String> numbers : generateNumbers) {
                String sortedKey = TwoSqlBatchBase.getInstance().generateSortedKey(numbers);
                set.add(sortedKey);
            }
        }
        //22年查询到数据与2025年开奖数据匹配，计算出这些高频组合号在2025年出现过的次数
        HashMap<String,Integer> result = new HashMap<>();
        for (int i = 0; i < data.size(); i++) {
            String key = data.get(i);
            int count = 0;
            for (int j = 0; j < set.size(); j++) {
                String kj = set.get(j);
                if(key.contains(kj)){
                    count++;
                }
            }
            result.put(key,count);
        }

        //最后输出，如果2025年43期，高频号一次都没出现过直接输出
        System.out.println("----------------------------------------");
        for (Map.Entry<String, Integer> entry : result.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            if(value==0){
                System.out.println(key);
            }
        }
        System.out.println("----------------------------------------");
    }
}
