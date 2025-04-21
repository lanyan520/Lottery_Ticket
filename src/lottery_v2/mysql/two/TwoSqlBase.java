package lottery_v2.mysql.two;

import lottery_v2.beans.TwoBean;
import lottery_v2.mysql.MysqlBase;
import lottery_v2.mysql.constant.ThreeConstant;
import lottery_v2.mysql.constant.TwoConstant;
import lottery_v2.utils.TwoFunctionHelper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TwoSqlBase extends MysqlBase {

    private static volatile TwoSqlBase instance ;
    public static TwoSqlBase getInstance() {
        if (instance == null) { // 第一次检查，避免不必要的锁竞争
            synchronized (TwoSqlBase.class) {
                if (instance == null) { // 第二次检查，确保唯一性
                    instance = new TwoSqlBase();
                }
            }
        }
        return instance;
    }
    @Override
    public String getDBName() {
        return TwoConstant.SQL.dbName;
    }

    /**
     * 查询所有的红球号
     * @return
     */
    public List<TwoBean> queryAllRedData(){

        List<TwoBean> result = new ArrayList<>();

        // 直接执行的查询语句
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(TwoConstant.SQL.query_two_base_data)) {
            // 遍历结果集
            while (rs.next()) {
                String red1 = rs.getString("red1");
                String red2 = rs.getString("red2");
                String red3 = rs.getString("red3");
                String red4 = rs.getString("red4");
                String red5 = rs.getString("red5");
                String red6 = rs.getString("red6");
                result.add(new TwoBean(red1,red2,red3,red4,red5,red6));
            }

        } catch (SQLException e) {
            System.err.println("数据库操作异常：" + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 查询所有的红球组合Numbers号
     * @return
     */
    public List<String> queryAllNumbersData(){

        List<String> result = new ArrayList<>();

        // 直接执行的查询语句
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(TwoConstant.SQL.query_two_numbers_data)) {
            // 遍历结果集
            while (rs.next()) {
                String numbers = rs.getString("numbers").replace(",,",",");
                result.add(numbers);
            }

        } catch (SQLException e) {
            System.err.println("数据库操作异常：" + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 查询所有的红球组合Numbers号
     * @param selected 2-6表
     * @param selectedFrequency 频率
     * @return
     */
    public List<String> queryRedNumbersWithCountSort(int selected,int selectedFrequency){

        List<String> result = new ArrayList<>();

        // 直接执行的查询语句
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(TwoConstant.SQL.getQueryNumberWithCountSort(selected,selectedFrequency))) {
            // 遍历结果集
            while (rs.next()) {
                String numbers = rs.getString("numbers");
                result.add(numbers);
            }

        } catch (SQLException e) {
            System.err.println("数据库操作异常：" + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
}
