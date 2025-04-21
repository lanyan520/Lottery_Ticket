package lottery_v2.mysql.eight;

import lottery_v2.mysql.MysqlBatchBase;
import lottery_v2.mysql.constant.BaseConstant;
import lottery_v2.mysql.constant.EightConstant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class EightSqlBatchBase extends MysqlBatchBase {

    private static volatile EightSqlBatchBase instance ;
    public static EightSqlBatchBase getInstance() {
        if (instance == null) { // 第一次检查，避免不必要的锁竞争
            synchronized (EightSqlBatchBase.class) {
                if (instance == null) { // 第二次检查，确保唯一性
                    instance = new EightSqlBatchBase();
                }
            }
        }
        return instance;
    }
    @Override
    public void batchUpdate(List<String> data, String tableName, String sql) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConnection();
            conn.setAutoCommit(false); // 关闭自动提交

            pstmt = conn.prepareStatement(sql);

            int counter = 0;
            for (int i = 0; i < data.size(); i++) {

                // 设置参数（需要处理可能的类型转换错误）
                pstmt.setString(1, data.get(i));
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
     * 批量插入数据库，选2-选10，根据selected决定
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
            pstmt = conn.prepareStatement(EightConstant.SQL.getInsertTableSQL(selected));

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

    //-------------------------------------忽略函数-----------------------------------------------

    /***
     * 生成排序后的组合键
     * @param numbers
     * @return
     */
    private static String generateSortedKey(List<String> numbers) {
        // 深拷贝并排序
        List<String> sorted = Arrays.asList(numbers.toArray(new String[0]));
        Collections.sort(sorted);
        return String.join(",", sorted);
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

    @Override
    public String getDBName() {
        return EightConstant.SQL.dbName;
    }
}
