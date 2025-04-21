package lottery_v2.utils;

import lottery_v2.beans.GallbladderCodeBean;
import lottery_v2.calculate.EightCalculateHelper;
import lottery_v2.data.eight.*;
import lottery_v2.interfaces.eight.EightInterface;
import lottery_v2.mysql.constant.EightConstant;
import lottery_v2.mysql.eight.EightSqlBase;
import lottery_v2.mysql.eight.EightSqlBatchBase;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class EightFunctionHelper implements EightInterface , EightInterface.InsertInterface,EightInterface.QueryInterface,EightInterface.CalculateInterface {

    private static volatile EightFunctionHelper instance ;
    public static EightFunctionHelper getInstance() {
        if (instance == null) { // 第一次检查，避免不必要的锁竞争
            synchronized (EightFunctionHelper.class) {
                if (instance == null) { // 第二次检查，确保唯一性
                    instance = new EightFunctionHelper();
                }
            }
        }
        return instance;
    }

    @Override
    public List<String> getAllData() {
        List<String> allData = new ArrayList<>();
        allData.addAll(Lottery_2020.DATA);
        allData.addAll(Lottery_2021.DATA);
        allData.addAll(Lottery_2022.DATA);
        allData.addAll(Lottery_2023.DATA);
        allData.addAll(Lottery_2024.DATA);
        return allData;
    }

    @Override
    public void insertAllData() throws SQLException {
        //自动创建数据库
        EightSqlBatchBase.getInstance().ensureDatabaseExists(EightConstant.SQL.dbName);
        //自动创建基础3D数据库表
        EightSqlBatchBase.getInstance().initializeTable(EightConstant.SQL.createTable_Eight_Base);
        //3D基础数据批量插入数据库
        EightSqlBatchBase.getInstance().batchUpdate(getAllData(),EightConstant.SQL.tableName_Base,EightConstant.SQL.insert_base_data);
        System.out.println("------------------------3D数据已经成功插入数据库------------------------");
    }

    @Override
    public void batchInsertWithSelected(List<String> winnerNumbers, int selected) throws SQLException {
        //自动创建基础3D数据库表
        switch (selected){
            case 2:
                EightSqlBatchBase.getInstance().initializeTable(EightConstant.SQL.createTable_Eight_2);
                break;
            case 3:
                EightSqlBatchBase.getInstance().initializeTable(EightConstant.SQL.createTable_Eight_3);
                break;
            case 4:
                EightSqlBatchBase.getInstance().initializeTable(EightConstant.SQL.createTable_Eight_4);
                break;
            case 5:
                EightSqlBatchBase.getInstance().initializeTable(EightConstant.SQL.createTable_Eight_5);
                break;
            case 6:
                EightSqlBatchBase.getInstance().initializeTable(EightConstant.SQL.createTable_Eight_6);
                break;
            case 7:
                EightSqlBatchBase.getInstance().initializeTable(EightConstant.SQL.createTable_Eight_7);
                break;
            case 8:
                EightSqlBatchBase.getInstance().initializeTable(EightConstant.SQL.createTable_Eight_8);
                break;
            case 9:
                EightSqlBatchBase.getInstance().initializeTable(EightConstant.SQL.createTable_Eight_9);
                break;
            case 10:
                EightSqlBatchBase.getInstance().initializeTable(EightConstant.SQL.createTable_Eight_10);
                break;
        }

        for (int i = 0; i < winnerNumbers.size(); i++) {
            List<String> data = Arrays.asList(winnerNumbers.get(i).split(","));
            System.out.println("第"+(i+1)+"期数据: " + data);
            EightSqlBatchBase.getInstance().batchInsertWithSelected(data,selected);
            System.out.println("第"+(i+1)+"期数据插入成功" );
            System.out.println("--------------------------------------------");
        }
    }


    @Override
    public List<String> autoPredictionNumbers(String[] lastWinnerNumbers, int selectType, int selectedFrequency) {
       return autoPredictionNumbers(lastWinnerNumbers,selectType,selectedFrequency,null);
    }

    @Override
    public List<String> queryNumbersWithCount(int selected, int selectedFrequency) {
        return EightSqlBase.getInstance().queryNumbersWithCount(selected,selectedFrequency);
    }

    @Override
    public List<String> autoPredictionNumbers(String[] lastWinnerNumbers, int selectType, int selectedFrequency, GallbladderCodeBean bean) {

        List<String> result = null;
        if(selectType>=8){
            System.out.println("查询时间太长，功能暂时禁用！");
            return null;
        }
        List<List<String>> numbers = EightSqlBatchBase.getInstance().generateNumbers(extendNumbers(lastWinnerNumbers),selectType);
        if(bean!=null){
            List<List<String>> numbersWithCodes =  new ArrayList<>();
            for (int i = 0; i < numbers.size(); i++) {
                String number = numbers.get(i).stream()
                        .filter(Objects::nonNull)  // 过滤 null
                        .map(String::trim)         // 清理空格
                        .collect(Collectors.joining(","));
                boolean flag = countMatches(number,bean.getCodes(),bean.getMin(),bean.getMax());
                if(flag){
                    numbersWithCodes.add(numbers.get(i));
                }

            }
            result = EightSqlBase.getInstance().queryPredictionNumberWithCount(numbersWithCodes,selectedFrequency,selectType);
        }else{
            result = EightSqlBase.getInstance().queryPredictionNumberWithCount(numbers,selectedFrequency,selectType);
        }

        return result;
    }

    @Override
    public void calculate(List<String> ownNumbers, List<String> winnerNumbers, int selected,int selectedFrequency,int maxSize) {
        EightCalculateHelper.getInstance().calculateMoney(ownNumbers.subList(0,Math.min(ownNumbers.size(),maxSize)), winnerNumbers,selected);
    }

    /**
     * 自动扩展上期中奖号，一般会含有10-14个中奖号
     * @param data
     * @return
     */
    public List<String> extendNumbers(String[] data) {
        Set<String> resultSet = new HashSet<>();

        for (String numStr : data) {
            int num = Integer.parseInt(numStr);
            // 尝试 +1、-1、+0 三种操作
            for (int delta : new int[]{-1, 0, 1}) {
                int newNum = num + delta;
                if (newNum >= 1 && newNum <= 80) { // 检查范围
                    String newStr = String.format("%02d", newNum); // 转换为两位字符串
                    resultSet.add(newStr); // 自动去重
                }
            }
        }

        return new ArrayList<>(resultSet);
    }

    public boolean countMatches(String number, String[] array, int min, int max) {
        if (number == null || array == null) return false;

        int count = 0;

        for (int i = 0; i < array.length; i++) {
            boolean flag = number.contains(array[i]);
            if(flag){
                count++;
            }
        }
        return count>=min&&count<=max;
    }
}
