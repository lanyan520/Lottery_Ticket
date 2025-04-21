package lottery_v2.mysql;

import lottery_v2.interfaces.mysql.MysqlBaseInterface;
import lottery_v2.mysql.constant.BaseConstant;

import java.sql.*;

public abstract class MysqlBase implements MysqlBaseInterface {

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC Driver not found", e);
        }
    }

    @Override
    public void ensureDatabaseExists(String dbName) {

        try (Connection conn = DriverManager.getConnection(
                BaseConstant.URL_BASE, BaseConstant.USER, BaseConstant.PASSWORD)) {

            Statement stmt = conn.createStatement();
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS "+dbName);
        } catch (SQLException e) {
            System.err.println("数据库创建失败: " + e.getMessage());
        }
    }

    @Override
    public void initializeTable(String createTableSql) throws SQLException {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSql);
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(BaseConstant.URL+getDBName(), BaseConstant.USER, BaseConstant.PASSWORD);
    }

    @Override
    public void rollback(Connection conn) {
        try {
            if (conn != null) conn.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
