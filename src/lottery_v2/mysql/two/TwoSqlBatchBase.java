package lottery_v2.mysql.two;

import lottery_v2.beans.BlueBean;
import lottery_v2.beans.FollowBean;
import lottery_v2.beans.SpanRecord;
import lottery_v2.beans.TwoBean;
import lottery_v2.mysql.MysqlBatchBase;
import lottery_v2.mysql.constant.BaseConstant;
import lottery_v2.mysql.constant.EightConstant;
import lottery_v2.mysql.constant.ThreeConstant;
import lottery_v2.mysql.constant.TwoConstant;

import java.sql.*;
import java.util.*;

public class TwoSqlBatchBase extends MysqlBatchBase {

    private static volatile TwoSqlBatchBase instance ;
    public static TwoSqlBatchBase getInstance() {
        if (instance == null) { // 第一次检查，避免不必要的锁竞争
            synchronized (TwoSqlBatchBase.class) {
                if (instance == null) { // 第二次检查，确保唯一性
                    instance = new TwoSqlBatchBase();
                }
            }
        }
        return instance;
    }

    @Override
    public String getDBName() {
        return TwoConstant.SQL.dbName;
    }

    @Override
    public void batchUpdate(List<String> data, String tableName, String sql) throws SQLException {
        //TODO 废弃不用
    }

    /**
     * 往数据库插入双色球基础数据
     * @param data
     * @throws SQLException
     */
    public void insertAllData(List<TwoBean> data) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConnection();
            conn.setAutoCommit(false); // 关闭自动提交

            pstmt = conn.prepareStatement(TwoConstant.SQL.insert_base_data);

            int counter = 0;
            for (int i = 0; i < data.size(); i++) {
                TwoBean bean = data.get(i);
                // 设置参数（需要处理可能的类型转换错误）
                pstmt.setString(1, bean.getData());
                pstmt.setString(2, bean.getRed1());
                pstmt.setString(3, bean.getRed2());
                pstmt.setString(4, bean.getRed3());
                pstmt.setString(5, bean.getRed4());
                pstmt.setString(6, bean.getRed5());
                pstmt.setString(7, bean.getRed6());
                pstmt.setString(8, bean.getBlue());
                pstmt.setString(9, bean.getNumbers());
                pstmt.addBatch();
                // 分批提交
                if (++counter % BaseConstant.BATCH_SIZE == 0) {
                    executeBatch(conn, pstmt);
                }
            }

            // 提交剩余批次
            executeBatch(conn, pstmt);
            conn.commit();

        } catch (SQLException e) {
            rollback(conn);
            throw e;
        } finally {
            close(conn, pstmt, null);
        }
    }

    /**
     * 往数据库插入双色球基础数据
     * @param data
     * @throws SQLException
     */
    public void insertFollowData(List<FollowBean> data) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConnection();
            conn.setAutoCommit(false); // 关闭自动提交

            pstmt = conn.prepareStatement(TwoConstant.SQL.insert_follow_red);

            int counter = 0;
            for (int i = 0; i < data.size(); i++) {
                FollowBean bean = data.get(i);
                // 设置参数（需要处理可能的类型转换错误）
                pstmt.setString(1, bean.getcRed());
                pstmt.setString(2, bean.getnRed());
                pstmt.setInt(3, bean.getCount());
                pstmt.addBatch();
                // 分批提交
                if (++counter % BaseConstant.BATCH_SIZE == 0) {
                    executeBatch(conn, pstmt);
                }
            }

            // 提交剩余批次
            executeBatch(conn, pstmt);
            conn.commit();

        } catch (SQLException e) {
            rollback(conn);
            throw e;
        } finally {
            close(conn, pstmt, null);
        }
    }


    /**
     * 批量插入蓝球预测表
     */
    public void batchInsertFollowBlue() throws SQLException {
        Connection conn = null;
        try {
            // 1. 获取数据库连接
            conn = getInstance().getConnection();
            conn.setAutoCommit(false); // 关闭自动提交

            // 2. 执行统计查询
            List<BlueBean> blueRecord = new ArrayList<>();

            // 核心统计SQL（使用用户变量获取下一行的span值）

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(TwoConstant.SQL.query_blueWithNextBlueCount);

            while (rs.next()) {
                blueRecord.add(new BlueBean(
                        rs.getString("cBlue"),
                        rs.getString("nBlue"),
                        rs.getInt("count")
                ));
            }
            // 3. 清空目标表
            stmt.executeUpdate(TwoConstant.SQL.clearTable_Follow_Blue);
            // 4. 插入统计结果
            if (blueRecord.isEmpty()) return;

            PreparedStatement pstmt = conn.prepareStatement(TwoConstant.SQL.insert_follow_blue);
            // 遍历查询结果
            int counter = 0;
            for (BlueBean record : blueRecord) {
                pstmt.setString(1, record.getcBlue());
                pstmt.setString(2, record.getnBlue());
                pstmt.setInt(3, record.getCount());
                pstmt.addBatch();
                // 批量提交
                if (++counter % BaseConstant.BATCH_SIZE == 0) {
                    pstmt.executeBatch();
                }
            }

            // 提交剩余的批量操作
            pstmt.executeBatch();
            // 5. 提交事务
            conn.commit();

        } catch (SQLException e) {
            rollback(conn);
            throw e;
        } finally {
            close(conn, null, null);
        }
    }

    /**
     * 批量插入数据库，红球组合2-6，根据selected决定
     * @param winnerNumbers
     * @param selected
     * @throws SQLException
     */
    public void batchInsertWithSelected(List<String> winnerNumbers, int selected) throws SQLException {

        List<List<String>> generateNumbers = generateNumbers(winnerNumbers,selected);
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConnection();
            conn.setAutoCommit(false); // 关闭自动提交
            pstmt = conn.prepareStatement(TwoConstant.SQL.getInsertTableSQL(selected));

            int counter = 0;
            for (List<String> numbers : generateNumbers) {
                String sortedKey = generateSortedKey(numbers);
                pstmt.setString(1, sortedKey);
                pstmt.addBatch();

                // 分批提交
                if (++counter % BaseConstant.BATCH_SIZE == 0) {
                    executeBatch(conn, pstmt);
                }
            }

            // 提交剩余批次
            executeBatch(conn, pstmt);
            conn.commit();

        } catch (SQLException e) {
            rollback(conn);
            throw e;
        } finally {
            close(conn, pstmt, null);
        }
    }

    /***
     * 生成排序后的组合键
     * @param numbers
     * @return
     */
    public static String generateSortedKey(List<String> numbers) {
        // 深拷贝并排序
        List<String> sorted = Arrays.asList(numbers.toArray(new String[0]));
        Collections.sort(sorted);
        return String.join(",", sorted);
    }

    /**
     * 生成选2-选10
     * @param numbers
     * @param selected  2-》选2
     * @return
     */
    public List<List<String>> generateNumbers(List<String> numbers, int selected) {
        // 输入验证
        checkInput(numbers);

        // 直接使用输入数字（已保证无重复）
        List<String> sortedNumbers = new ArrayList<>(numbers);
        Collections.sort(sortedNumbers);

        // 生成组合（C(20,5)=15504）
        return generateCombinations(sortedNumbers, selected);
    }

    /***
     * 严格输入验证
     * @param numbers
     */
    private void checkInput(List<String> numbers) {
        // 基础验证
        if (numbers == null ) {
            throw new IllegalArgumentException("数字不能为空");
        }

        Set<String> seen = new HashSet<>();
        for (String num : numbers) {

            // 重复验证
            if (!seen.add(num)) {
                throw new IllegalArgumentException("存在重复数字: " + num);
            }
        }
    }


    private List<List<String>> generateCombinations(List<String> nums, int k) {
        List<List<String>> result = new ArrayList<>();
        backtrack(nums, k, 0, new ArrayList<>(), result);
        return result;
    }


    private void backtrack(List<String> nums, int k, int start,
                           List<String> path, List<List<String>> result) {
        if (path.size() == k) {
            result.add(new ArrayList<>(path));
            return;
        }

        for (int i = start; i < nums.size(); i++) {
            path.add(nums.get(i));
            backtrack(nums, k, i + 1, path, result);
            path.remove(path.size() - 1);
        }
    }
}
