package lottery_v2.mysql.three;

import lottery_v2.mysql.MysqlBase;
import lottery_v2.mysql.constant.ThreeConstant;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ThreeSqlBase extends MysqlBase {

    private static volatile ThreeSqlBase instance ;
    public static ThreeSqlBase getInstance() {
        if (instance == null) { // 第一次检查，避免不必要的锁竞争
            synchronized (ThreeSqlBase.class) {
                if (instance == null) { // 第二次检查，确保唯一性
                    instance = new ThreeSqlBase();
                }
            }
        }
        return instance;
    }


    /**
     * 查询所有的组选号
     * 号码按照count频率排序的
     * @return
     */
    public List<String> queryGroupNumberWithCount(){

        List<String> result = new ArrayList<>();

        // 直接执行的查询语句
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(ThreeConstant.SQL.query_groupNumberWithCount)) {
            // 遍历结果集
            while (rs.next()) {
                String groupNumber = rs.getString("groupNumber");
                int count = rs.getInt("count");
                result.add(groupNumber);
            }

        } catch (SQLException e) {
            System.err.println("数据库操作异常：" + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 预测跨度查询
     * @param cspan
     * @return
     */
    public List<Integer> queryNextSpans(int cspan){
        // 直接执行的查询语句
        List<Integer> data = new ArrayList<>();
        try (Connection conn = getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(ThreeConstant.SQL.getQuerySpanWithNextSQL(cspan))) {
            // 遍历结果集
            while (rs.next()) {
                data.add(rs.getInt("nspan"));
            }
        } catch (SQLException e) {
            System.err.println("数据库操作异常：" + e.getMessage());
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 预测和值查询
     * @param csum
     * @return
     */
    public static List<Integer> queryNextSums(int csum){
        // 直接执行的查询语句
        List<Integer> data = new ArrayList<>();
        try (Connection conn = getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(ThreeConstant.SQL.getQuerySumWithNextSQL(csum))) {
            // 遍历结果集
            while (rs.next()) {
                data.add(rs.getInt("nsum"));
            }
        } catch (SQLException e) {
            System.err.println("数据库操作异常：" + e.getMessage());
            e.printStackTrace();
        }
        return data;
    }

    @Override
    public String getDBName() {
        return ThreeConstant.SQL.dbName;
    }
}
