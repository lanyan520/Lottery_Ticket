package lottery_v2.interfaces.mysql;

import java.sql.*;
import java.util.List;

public interface MysqlBaseInterface {
    /**
     * 获取数据库名
     */
    abstract String getDBName();

    /**
     * 自动创建数据库
     * @param dbName
     */
    abstract void ensureDatabaseExists(String dbName);

    /**
     * 初始化一张表
     * @throws SQLException
     */
    abstract void initializeTable(String createTableSql) throws SQLException;

    /**
     * 打开链接
     * @return
     */
    abstract Connection getConnection() throws SQLException;

    /**
     * 事务回滚
     * @param conn
     */
    abstract void rollback(Connection conn);

    /**
     * 关闭资源
     * @param conn
     * @param stmt
     * @param rs
     */
    abstract void close(Connection conn, Statement stmt, ResultSet rs);

    interface BatchInterface{
        /**
         * 批量操作异常处理
         * @param e
         */
        abstract void handleBatchException(BatchUpdateException e);

        /**
         * 批量处理结果反馈分析
         * @param results
         */
        abstract void analyzeBatchResults(int[] results);

        /**
         * 批量提交
         * @param conn
         * @param pstmt
         */
        abstract void executeBatch(Connection conn, PreparedStatement pstmt) throws SQLException;

        /**
         * 批量更新
         * @param data
         * @param tableName
         * @param sql
         */
        abstract void batchUpdate(List<String> data, String tableName,String sql)throws SQLException;
    }

}
