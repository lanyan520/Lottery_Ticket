package lottery_v2.mysql.eight;

import lottery_v2.beans.EightBean;
import lottery_v2.mysql.MysqlBase;
import lottery_v2.mysql.constant.EightConstant;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EightSqlBase extends MysqlBase {


    private static volatile EightSqlBase instance ;
    public static EightSqlBase getInstance() {
        if (instance == null) { // 第一次检查，避免不必要的锁竞争
            synchronized (EightSqlBase.class) {
                if (instance == null) { // 第二次检查，确保唯一性
                    instance = new EightSqlBase();
                }
            }
        }
        return instance;
    }


    /***
     * 查询预测号对应的Count值，中奖次数>=selectedFrequency次数输出到控制台
     * 排序，限制打印输出
     * @param predictionNumbers
     * @param selectedFrequency
     * @param selected
     */
    public List<String> queryPredictionNumberWithCount(List<List<String>> predictionNumbers, int selectedFrequency, int selected){

        // 1. 转换并排序所有组合键
        List<String> sortedKeys = new ArrayList<>();
        for (List<String> data : predictionNumbers) {
            // 深拷贝避免修改原始数据
            List<String> temp = new ArrayList<>(data);
            // 按数值升序排序
            temp.sort(Comparator.comparingInt(Integer::parseInt));
            // 拼接成字符串
            sortedKeys.add(String.join(",", temp));
        }

        // 2. 查询数据库
        List<EightBean> results = queryCountsFromDatabase(sortedKeys,selected);

        // 3. 按count降序排序
        Collections.sort(results, (a, b) -> b.getCount() - a.getCount());

        // 4. 输出结果
        List<String> finalResult = new ArrayList<>();
        System.out.println("*****************************************************************************");
        System.out.println("快乐8选"+selected+"查询结果（按Count降序排列）：");
        for (int i = 0; i <results.size(); i++) {
            if(results.get(i).getCount()>=selectedFrequency){
                finalResult.add(results.get(i).getNumber());
                System.out.println(results.get(i));
            }
        }
        return finalResult;
    }

    /***
     * 查询历史记录，数据如果存在，查询对应Count值，即中奖次数
     * @param predictionNumbers
     * @param selected
     * @return
     */
    private List<EightBean> queryCountsFromDatabase(List<String> predictionNumbers, int selected) {
        List<EightBean> results = new ArrayList<>();

        // 使用try-with-resources自动关闭资源
        try (Connection conn = getConnection()) {

            // 使用预编译语句防止SQL注入
            try (PreparedStatement pstmt = conn.prepareStatement(EightConstant.SQL.getQueryNumberWithCountSQL(selected))) {

                for (String key : predictionNumbers) {
                    pstmt.setString(1, key);

                    try (ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next()) {
                            results.add(new EightBean(
                                    rs.getString("number"),
                                    rs.getInt("count")
                            ));
                        } else {
                            // 处理没有找到记录的情况
                            results.add(new EightBean(key, 0));
                        }
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }

    /***
     * 查询高频限制中奖次数>=selectedFrequency
     * @param selected
     * @param selectedFrequency
     * @return List<String>
     */
    public  List<String> queryNumbersWithCount(int selected, int selectedFrequency){
        // 处理结果集
        List<String> results = new ArrayList<>();
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            // 执行查询
            ResultSet rs = stmt.executeQuery(EightConstant.SQL.getQueryNumberWithCountSort(selected,selectedFrequency));

            while (rs.next()) {
                String key = rs.getString("number");
                results.add(key);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    @Override
    public String getDBName() {
        return EightConstant.SQL.dbName;
    }
}
