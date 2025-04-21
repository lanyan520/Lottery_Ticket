package lottery_v2.utils;

import lottery_v2.beans.GallbladderCodeBean;
import lottery_v2.beans.MyFilterSumSpan;
import lottery_v2.calculate.ThreeCalculateHelper;
import lottery_v2.data.three.*;
import lottery_v2.interfaces.three.ThreeInterface;
import lottery_v2.mysql.constant.ThreeConstant;
import lottery_v2.mysql.three.ThreeSqlBase;
import lottery_v2.mysql.three.ThreeSqlBatchBase;

import java.sql.SQLException;
import java.util.*;

public class ThreeFunctionHelper implements ThreeInterface {

    private static volatile ThreeFunctionHelper instance ;
    public static ThreeFunctionHelper getInstance() {
        if (instance == null) { // 第一次检查，避免不必要的锁竞争
            synchronized (ThreeFunctionHelper.class) {
                if (instance == null) { // 第二次检查，确保唯一性
                    instance = new ThreeFunctionHelper();
                }
            }
        }
        return instance;
    }

    @Override
    public List<String> getAllData() {
        List<String> allData = new ArrayList<>();
        allData.addAll(Lottery_2004.DATA);
        allData.addAll(Lottery_2005.DATA);
        allData.addAll(Lottery_2006.DATA);
        allData.addAll(Lottery_2007.DATA);
        allData.addAll(Lottery_2008.DATA);
        allData.addAll(Lottery_2009.DATA);
        allData.addAll(Lottery_2010.DATA);
        allData.addAll(Lottery_2011.DATA);
        allData.addAll(Lottery_2012.DATA);
        allData.addAll(Lottery_2013.DATA);
        allData.addAll(Lottery_2014.DATA);
        allData.addAll(Lottery_2015.DATA);
        allData.addAll(Lottery_2016.DATA);
        allData.addAll(Lottery_2017.DATA);
        allData.addAll(Lottery_2018.DATA);
        allData.addAll(Lottery_2019.DATA);
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
        ThreeSqlBatchBase.getInstance().ensureDatabaseExists(ThreeConstant.SQL.dbName);
        //自动创建基础3D数据库表
        ThreeSqlBase.getInstance().initializeTable(ThreeConstant.SQL.createTable_Base);
        //3D基础数据批量插入数据库
        ThreeSqlBatchBase.getInstance().batchUpdate(getAllData(),ThreeConstant.SQL.tableName_Base,ThreeConstant.SQL.insert_base_data);
        System.out.println("------------------------3D数据已经成功插入数据库------------------------");
    }

    @Override
    public void insertSingleTable() throws SQLException {
        //自动创建基础3D数据库表
        ThreeSqlBase.getInstance().initializeTable(ThreeConstant.SQL.createTable_Single);
        ThreeSqlBatchBase.getInstance().batchInsertWithSingleTable();
        System.out.println("------------------------3D单选统计数据已经成功插入数据库------------------------");
    }

    @Override
    public void insertGroupTable() throws SQLException {
        //自动创建基础3D数据库表
        ThreeSqlBase.getInstance().initializeTable(ThreeConstant.SQL.createTable_Group);
        ThreeSqlBatchBase.getInstance().batchInsertWithGroupTable();
        System.out.println("------------------------3D组选统计数据已经成功插入数据库------------------------");
    }

    @Override
    public void batchInsert3DSumTable() throws SQLException {
        //自动创建基础3D数据库表
        ThreeSqlBase.getInstance().initializeTable(ThreeConstant.SQL.createTable_Sum);
        ThreeSqlBatchBase.getInstance().batchInsert3DSumTable();
    }

    @Override
    public void batchInsert3DSpanTable() throws SQLException {
        //自动创建基础3D数据库表
        ThreeSqlBase.getInstance().initializeTable(ThreeConstant.SQL.createTable_Span);
        ThreeSqlBatchBase.getInstance().batchInsert3DSpanTable();
    }

    @Override
    public List<String> queryGroupNumberWithCount() {
        return ThreeSqlBase.getInstance().queryGroupNumberWithCount();
    }

    @Override
    public void calculateWith3D(List<String> winNumbers, String[] own) {
        ThreeCalculateHelper.getInstance().calculateWith3DGroup(winNumbers,own);
    }

    @Override
    public void checkOwnGroupNumbers(String[] ownNumbers, int maxSize) {
        ThreeCalculateHelper.getInstance().checkOwnGroupNumbers(queryGroupNumberWithCount(),ownNumbers,maxSize);
    }

    @Override
    public List<Integer> queryNextSpans(int cspan) {
        return ThreeSqlBase.getInstance().queryNextSpans(cspan);
    }

    @Override
    public List<Integer> queryNextSums(int csum) {
        return ThreeSqlBase.getInstance().queryNextSums(csum);
    }

    @Override
    public boolean hasNextSpan(List<Integer> nextSpans, int span) {
        return nextSpans.contains(span);
    }

    @Override
    public boolean hasNextSum(List<Integer> nextSums, int sum) {
        return nextSums.contains(sum);
    }

    @Override
    public boolean is3DGroup3Or6(String number) {
        return ThreeCalculateHelper.getInstance().getNumberType(number)==1 || ThreeCalculateHelper.getInstance().getNumberType(number)==2 ;
    }

    @Override
    public boolean is3DGroup6(String number) {
        return ThreeCalculateHelper.getInstance().getNumberType(number)==2;
    }

    @Override
    public boolean is3DGroup3(String number) {
        return ThreeCalculateHelper.getInstance().getNumberType(number)==1;
    }

    @Override
    public boolean is3DGroupLeopard(String number) {
        return ThreeCalculateHelper.getInstance().getNumberType(number)==0;
    }

    @Override
    public boolean check3DGroupNumberType(String number, int numberType) {
        boolean flag = false;
        if(numberType==0){
            flag = is3DGroupLeopard(number);
        }else if(numberType==1){
            flag = is3DGroup3(number);
        }else if(numberType==2){
            flag = is3DGroup6(number);
        }else if(numberType==3){
            flag = is3DGroup3Or6(number);
        }
        return flag;
    }

    @Override
    public boolean countMatches(String number, String[] array, int min, int max) {
        if (number == null || array == null) return false;

        // 将数组转换为集合以优化查询性能（O(1)时间复杂度）[1,3](@ref)
        Set<String> targetSet = new HashSet<>(Arrays.asList(array));
        int count = 0;

        // 遍历字符串中的每个字符
        for (char c : number.toCharArray()) {
            String charStr = String.valueOf(c);
            if (targetSet.contains(charStr)) {
                count++; // 每次匹配都计数
            }
        }
        return count>=min&&count<=max;
    }

    @Override
    public String[] formatNumberWithGallbladderCode(String number) {
        String[] result = new String[number.length()];
        for (int i = 0; i < number.length(); i++) {
            result[i] = String.valueOf(number.charAt(i)); // 逐个字符转换为字符串
        }
        return result;
    }

    @Override
    public List<String> deleteWinnerNumbers(List<String> winnerNumbers, List<String> groupNumbersWithCodes, int maxWinnerCount) {
        LinkedHashMap<String,Integer> map = new LinkedHashMap<>();

        for (int i = 0; i < groupNumbersWithCodes.size(); i++) {
            map.put(groupNumbersWithCodes.get(i),0);
        }

        for (int i = 0; i < winnerNumbers.size(); i++) {
            String[] result = winnerNumbers.get(i).replaceAll("\\s", "").split(",");
            String number = ThreeCalculateHelper.getInstance().getGroupNumber(result[1]);
            boolean flag = groupNumbersWithCodes.contains(number);
            if(flag){
                //中奖次数统计
                int count = map.get(number);
                if(count>0){
                    count = count+1;
                }else {
                    count = 1;
                }
                map.put(number,count);
            }
        }

        List<String> printWinnerNumbers = new ArrayList<>();
        for (String key : map.keySet()) {
            Integer value = map.get(key);
            if(value<maxWinnerCount){
                printWinnerNumbers.add(key);
            }
        }

        return printWinnerNumbers;
    }

    @Override
    public void next3DNumber(String c3DNumber, int removeSpanSize, int selectMaxSums, int numberType, List<GallbladderCodeBean> codes, int maxWinnerCount, List<String> winnerNumbers, MyFilterSumSpan sumSpanBean) {
        //1计算出所有组选
        List<String> groupNumbers = getInstance().queryGroupNumberWithCount();
        System.out.println("------已完成所有组选计算-------");
        //2预测下期跨度，去掉预测的低频跨度值排序最后的几个
        int cspan = ThreeCalculateHelper.getInstance().getSpan(c3DNumber);
        List<Integer> nextSpans = queryNextSpans(cspan);
        nextSpans = nextSpans.subList(0, nextSpans.size()-removeSpanSize);
        System.out.println("------已完成所有跨度预测-------");
        //3将计算出的所有组选匹配跨度值，不在范围内的删除
        List<String> groupNumbersWithSpan = new ArrayList<>();
        for (int i = 0; i < groupNumbers.size(); i++) {
            String number = groupNumbers.get(i);
            int span = ThreeCalculateHelper.getInstance().getSpan(number);
            boolean spanFlag = hasNextSpan(nextSpans,span);
            if(spanFlag){
                groupNumbersWithSpan.add(number);
            }
        }
        System.out.println("------已完成踢出低频跨度-------");

        //4预测下期和值，取最高频的10个和值

        int csum = ThreeCalculateHelper.getInstance().getSum(c3DNumber);
        List<Integer> nextSums = queryNextSums(csum);
        nextSpans = nextSums.subList(0, selectMaxSums);
        System.out.println("------已完成所有和值预测-------");
        //5将上面计算出的结果再次与和值匹配，不在范围内的删除
        List<String> groupNumbersWithSum =  new ArrayList<>();
        for (int i = 0; i < groupNumbersWithSpan.size(); i++) {
            String number = groupNumbersWithSpan.get(i);
            int sum = ThreeCalculateHelper.getInstance().getSum(number);
            boolean spanFlag = hasNextSum(nextSpans,sum);
            if(spanFlag){
                groupNumbersWithSum.add(number);
            }
        }
        System.out.println("------已完成踢出低频和值-------");

        //6号码类型输入匹配，不在号码类型范围内的删除
        List<String> groupNumbersWithType =  new ArrayList<>();
        for (int i = 0; i < groupNumbersWithSum.size(); i++) {
            String number = groupNumbersWithSum.get(i);
            boolean typeFlag = check3DGroupNumberType(number,numberType);
            if(typeFlag){
                groupNumbersWithType.add(number);
            }
        }
        System.out.println("------已完成和值匹配筛选-------");

        //7确定胆码出几个，最少出0个，最多出3个，进行匹配（可以设定多组，默认支持自动化），删除不在范围的号
        List<String> groupNumbersWithCodes =  new ArrayList<>();
        for (int i = 0; i < groupNumbersWithType.size(); i++) {
            String number = groupNumbersWithType.get(i);
            boolean result = true;
            for (int j = 0; j <codes.size(); j++) {
                GallbladderCodeBean bean = codes.get(j);
                boolean flag = countMatches(number,bean.getCodes(),bean.getMin(),bean.getMax());
                if(flag==false){
                    result = false;
                    break;
                }
            }
            if(result){
                groupNumbersWithCodes.add(number);
            }
        }
        System.out.println("------已完成胆码匹配筛选-------");

        //8与今年的历史开奖号进行匹配，中奖>=2次的直接删除
        List<String> groupNumberWithWinnerDeletes = deleteWinnerNumbers(winnerNumbers,groupNumbersWithCodes,maxWinnerCount);
        System.out.println("------已完成中奖次数匹配筛选-------");
        System.out.println("标准过滤结果共计 "+groupNumberWithWinnerDeletes.size()+" 注");
        System.out.println(groupNumberWithWinnerDeletes.subList(0,groupNumberWithWinnerDeletes.size()/2));
        System.out.println(groupNumberWithWinnerDeletes.subList(groupNumberWithWinnerDeletes.size()/2,groupNumberWithWinnerDeletes.size()));


        //9删除中过奖的号
        List<String> groupNumberWithAllWinnerDeletes = deleteWinnerNumbers(winnerNumbers,groupNumbersWithCodes,1);
        System.out.println("------已完成中过奖的匹配筛选去除-------");
        System.out.println("二次过滤结果共计 "+groupNumberWithAllWinnerDeletes.size()+" 注，二次过滤容易漏掉中过奖的号");
        System.out.println(groupNumberWithAllWinnerDeletes.subList(0,groupNumberWithAllWinnerDeletes.size()/2));
        System.out.println(groupNumberWithAllWinnerDeletes.subList(groupNumberWithAllWinnerDeletes.size()/2,groupNumberWithAllWinnerDeletes.size()));
    }

    @Override
    public void defaultNext3DNumber(String powerNumber, String testNumber, String c3DNumber) {
        //缩水过滤流程设计
        //默认移除跨度低频最后三个
        int removeSpanSize = 3;
        //默认选择高频预测和值前10个
        int selectMaxSums = 10;
        //默认组选3 组选6排除豹子
        int numberType = 3;
        //默认移除今年中奖2次及以上号码
        int maxWinnerCount = 2;
        //默认试机号、开机号、上期中奖好出0-2个
        List<String> winnerNumbers = Lottery_2025.DATA;

        List<GallbladderCodeBean> codes = new ArrayList<>();
        String[] powerCodes = formatNumberWithGallbladderCode(powerNumber);
        String[] testCodes = formatNumberWithGallbladderCode(testNumber);
        String[] c3DCodes = formatNumberWithGallbladderCode(c3DNumber);
        codes.add(new GallbladderCodeBean(powerCodes,0,2));
        codes.add(new GallbladderCodeBean(testCodes,0,2));
        codes.add(new GallbladderCodeBean(c3DCodes,0,2));

        Integer[] sumFilters = {6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22};
        Integer[] spanFilters = {2,3,4,5,6,7,8};
        MyFilterSumSpan sumSpanBean = new MyFilterSumSpan(sumFilters,spanFilters);

        next3DNumber(c3DNumber,removeSpanSize,selectMaxSums,numberType,codes,maxWinnerCount, winnerNumbers,sumSpanBean);
    }

    @Override
    public void myNext3DNumber(String powerNumber, String testNumber, String c3DNumber) {
        //缩水过滤流程设计
        //默认移除跨度低频最后三个
        int removeSpanSize = 4;
        //默认选择高频预测和值前10个
        int selectMaxSums = 5;
        //默认组选3 组选6排除豹子
        int numberType = 2;
        //默认移除今年中奖2次及以上号码
        int maxWinnerCount = 2;
        //默认试机号、开机号、上期中奖好出0-2个
        List<String> winnerNumbers = Lottery_2025.DATA;

        List<GallbladderCodeBean> codes = new ArrayList<>();
        String[] powerCodes = formatNumberWithGallbladderCode(powerNumber);
        String[] testCodes = formatNumberWithGallbladderCode(testNumber);
        String[] c3DCodes = formatNumberWithGallbladderCode(c3DNumber);
        codes.add(new GallbladderCodeBean(powerCodes,0,1));
        codes.add(new GallbladderCodeBean(testCodes,0,1));
        codes.add(new GallbladderCodeBean(c3DCodes,0,1));

        Integer[] sumFilters = {10,12,13,15,16,17};
        Integer[] spanFilters = {2,3,4,5,8};
        MyFilterSumSpan sumSpanBean = new MyFilterSumSpan(sumFilters,spanFilters);

        next3DNumber(c3DNumber,removeSpanSize,selectMaxSums,numberType,codes,maxWinnerCount, winnerNumbers,sumSpanBean);
    }
}
