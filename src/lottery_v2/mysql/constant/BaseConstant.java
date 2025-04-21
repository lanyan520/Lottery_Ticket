package lottery_v2.mysql.constant;

public class BaseConstant {
    /**
     * 用于创建数据库
     */
    public static final String URL_BASE = "jdbc:mysql://localhost:3306/?useSSL=false";
    /**
     * JDBC注册驱动URL
     */
    public static final String URL = "jdbc:mysql://localhost:3306/";
    /**
     * Mysql用户名
     */
    public static final String USER = "root";
    /**
     * Mysql密码
     */
    public static final String PASSWORD = "fallinlove20141314";

    /***
     * 批次提交大小
     */
    public static final int BATCH_SIZE = 10000; // 批量提交大小
}
