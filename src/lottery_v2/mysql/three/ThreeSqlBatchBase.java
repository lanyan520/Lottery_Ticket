package lottery_v2.mysql.three;

import lottery_v2.beans.SpanRecord;
import lottery_v2.beans.SumRecord;
import lottery_v2.calculate.ThreeCalculateHelper;
import lottery_v2.mysql.MysqlBatchBase;
import lottery_v2.mysql.constant.BaseConstant;
import lottery_v2.mysql.constant.ThreeConstant;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThreeSqlBatchBase extends MysqlBatchBase {

    private static volatile ThreeSqlBatchBase instance ;
    public static ThreeSqlBatchBase getInstance() {
        if (instance == null) { // 第一次检查，避免不必要的锁竞争
            synchronized (ThreeSqlBatchBase.class) {
                if (instance == null) { // 第二次检查，确保唯一性
                    instance = new ThreeSqlBatchBase();
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
                // 分割数据字符串
                List<String> parts = Arrays.asList(data.get(i).replaceAll("\\s", "").split(","));
                if (parts.size() != 4) {
                    throw new IllegalArgumentException("输入数据格式错误，需包含5个字段");
                }

                // 设置参数（需要处理可能的类型转换错误）
                pstmt.setString(1, parts.get(0));
                pstmt.setString(2, parts.get(1));
                String groupNumber = ThreeCalculateHelper.getInstance().getGroupNumber((parts.get(1)));
                pstmt.setString(3, groupNumber);
                pstmt.setInt(4, Integer.parseInt(parts.get(2)));
                pstmt.setInt(5, Integer.parseInt(parts.get(3)));
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
     * 批量插入单选统计表
     * @throws SQLException
     */
    public void batchInsertWithSingleTable() throws SQLException {
        Connection conn = null;

        try {
            conn = getInstance().getConnection();
            conn.setAutoCommit(false); // 关闭自动提交

            try (Statement clearStmt = conn.createStatement();
                 Statement countStmt = conn.createStatement();
                 ResultSet rs = countStmt.executeQuery(ThreeConstant.SQL.query_base_singleNumberWithCount);
                 PreparedStatement insertStmt = conn.prepareStatement(ThreeConstant.SQL.insert_single_data)) {

                // 清空目标表
                clearStmt.executeUpdate(ThreeConstant.SQL.clearTable_Single);

                // 遍历查询结果
                int counter = 0;
                while (rs.next()) {
                    String singleNumber = rs.getString("singleNumber");
                    int count = rs.getInt("cnt");
                    // 设置插入参数
                    insertStmt.setString(1, singleNumber);
                    insertStmt.setInt(2, count);
                    insertStmt.addBatch();

                    // 批量提交
                    if (++counter % BaseConstant.BATCH_SIZE == 0) {
                        insertStmt.executeBatch();
                    }
                }

                // 提交剩余的批量操作
                insertStmt.executeBatch();

                // 提交事务
                conn.commit();

            } catch (SQLException e) {
                getInstance().rollback(conn);
                System.err.println("操作失败，已回滚事务：" + e.getMessage());
                throw e;
            } finally {
                getInstance().close(conn, null, null);
            }
        }catch (SQLException e) {
            System.err.println("数据库连接失败：" + e.getMessage());
        }
    }


    /**
     * 批量插入组选统计表
     * @throws SQLException
     */
    public void batchInsertWithGroupTable() throws SQLException {

        Connection conn = null;

        try {
            conn = getInstance().getConnection();
            conn.setAutoCommit(false); // 关闭自动提交

            try (Statement clearStmt = conn.createStatement();
                 Statement countStmt = conn.createStatement();
                 ResultSet rs = countStmt.executeQuery(ThreeConstant.SQL.query_base_groupNumberWithCount);
                 PreparedStatement insertStmt = conn.prepareStatement(ThreeConstant.SQL.insert_group_data)) {

                // 清空目标表
                clearStmt.executeUpdate(ThreeConstant.SQL.clearTable_Group);

                // 遍历查询结果
                int counter = 0;
                while (rs.next()) {
                    String groupNumber = rs.getString("groupNumber");
                    int count = rs.getInt("cnt");
                    // 设置插入参数
                    insertStmt.setString(1, groupNumber);
                    insertStmt.setInt(2, count);
                    insertStmt.addBatch();

                    // 批量提交
                    if (++counter % BaseConstant.BATCH_SIZE == 0) {
                        insertStmt.executeBatch();
                    }
                }

                // 提交剩余的批量操作
                insertStmt.executeBatch();

                // 提交事务
                conn.commit();

                System.out.println("数据统计迁移完成！");

            } catch (SQLException e) {
                getInstance().rollback(conn);
                System.err.println("操作失败，已回滚事务：" + e.getMessage());
                throw e;
            } finally {
                getInstance().close(conn, null, null);
            }
        }catch (SQLException e) {
            System.err.println("数据库连接失败：" + e.getMessage());
        }
    }

    /**
     * 批量插入跨度预测表
     */
    public void batchInsert3DSpanTable() throws SQLException {
        Connection conn = null;
        try {
            // 1. 获取数据库连接
            conn = getInstance().getConnection();
            conn.setAutoCommit(false); // 关闭自动提交

            // 2. 执行统计查询
            List<SpanRecord> spanRecord = new ArrayList<>();

            // 核心统计SQL（使用用户变量获取下一行的span值）

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(ThreeConstant.SQL.query_spanWithNextSpanCount);

            while (rs.next()) {
                spanRecord.add(new SpanRecord(
                        rs.getInt("cspan"),
                        rs.getInt("nspan"),
                        rs.getInt("count")
                ));
            }
            // 3. 清空目标表
            stmt.executeUpdate(ThreeConstant.SQL.clearTable_Span);
            // 4. 插入统计结果
            if (spanRecord.isEmpty()) return;

            PreparedStatement pstmt = conn.prepareStatement(ThreeConstant.SQL.insert_span_data);
            // 遍历查询结果
            int counter = 0;
            for (SpanRecord record : spanRecord) {
                pstmt.setInt(1, record.getCurrentSpan());
                pstmt.setInt(2, record.getNextSpan());
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
     * 批量插入和值预测表
     */
    public void batchInsert3DSumTable() throws SQLException {
        Connection conn = null;
        try {
            // 1. 获取数据库连接
            conn = getInstance().getConnection();
            conn.setAutoCommit(false); // 关闭自动提交

            List<SumRecord> sumRecord = new ArrayList<>();

            // 核心统计SQL（使用用户变量获取下一行的span值）
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(ThreeConstant.SQL.query_sumWithNextSumCount);

            while (rs.next()) {
                    sumRecord.add(new SumRecord(
                            rs.getInt("csum"),
                            rs.getInt("nsum"),
                            rs.getInt("count")
                    ));
            }

            // 3. 清空目标表
            stmt.executeUpdate(ThreeConstant.SQL.clearTable_Sum);

            // 4. 插入统计结果
            if (sumRecord.isEmpty()) return;
            PreparedStatement pstmt = conn.prepareStatement(ThreeConstant.SQL.insert_sum_data);
            // 遍历查询结果
            int counter = 0;
            // 批量插入参数设置
            for (SumRecord record : sumRecord) {
                pstmt.setInt(1, record.getCurrentSum());
                pstmt.setInt(2, record.getNextSum());
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

    @Override
    public String getDBName() {
        return ThreeConstant.SQL.dbName;
    }
}
