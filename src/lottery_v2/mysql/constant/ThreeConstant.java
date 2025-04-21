package lottery_v2.mysql.constant;

public class ThreeConstant {

    public static class Money{

        public static int leopardMoney = 1040;

        public static int singleMoney = 1040;

        public static int groupMoneyWithType2 = 173;

        public static int groupMoneyWithType1 = 346;
    }

    public static class SQL{
        //-----------------------------表名称----------------------------------

        public static String dbName = "Lottery_Three";
        public static String tableName_Base = "Lottery_Three_Base";

        public static String tableName_single = "Lottery_Three_Single";
        public static String tableName_Group = "Lottery_Three_Group";

        public static String tableName_Sum = "Lottery_Three_Sum";

        public static String tableName_Span = "Lottery_Three_Span";

        //-----------------------------以下是创建表单-----------------------------
        public static String createTable_Base =
                "CREATE TABLE IF NOT EXISTS "+tableName_Base+" (" +
                        "data VARCHAR(50) PRIMARY KEY," +
                        "singleNumber VARCHAR(50) NOT NULL," +
                        "groupNumber VARCHAR(50) NOT NULL," +
                        "sum INT NOT NULL," +
                        "span INT NOT NULL" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4";

        public static String createTable_Single =
                "CREATE TABLE IF NOT EXISTS "+tableName_single+" (" +
                        "singleNumber VARCHAR(50) PRIMARY KEY," +
                        "count INT NOT NULL" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4";

        public static String createTable_Group =
                "CREATE TABLE IF NOT EXISTS "+tableName_Group+" (" +
                        "groupNumber VARCHAR(50) PRIMARY KEY," +
                        "count INT NOT NULL" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4";

        public static String createTable_Sum =
                "CREATE TABLE IF NOT EXISTS "+tableName_Sum+" (" +
                        "csum INT NOT NULL," +
                        "nsum INT NOT NULL," +
                        "count INT NOT NULL" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4";

        public static String createTable_Span =
                "CREATE TABLE IF NOT EXISTS "+tableName_Span+" (" +
                        "cspan INT NOT NULL," +
                        "nspan INT NOT NULL," +
                        "count INT NOT NULL" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4";

        //--------------------------------以下是插入数据库表单----------------------------------

        public static String insert_base_data = "INSERT INTO "+tableName_Base+" (data, singleNumber, groupNumber, sum, span) "
                + "VALUES (?, ?, ?, ?, ?)";

        public static String insert_single_data = "INSERT INTO "+tableName_single+" (singleNumber, count) VALUES (?, ?)";
        public static String insert_group_data = "INSERT INTO "+tableName_Group+" (groupNumber, count) VALUES (?, ?)";

        public static String insert_sum_data = "INSERT INTO "+tableName_Sum+" (csum, nsum, count) VALUES (?, ?, ?)";
        public static String insert_span_data = "INSERT INTO "+tableName_Span+" (cspan, nspan, count) VALUES (?, ?, ?)";

        //-------------------------------以下是查询语句---------------------------------------

        public static String query_base_singleNumberWithCount = "SELECT singleNumber, COUNT(*) AS cnt FROM "+tableName_Base+" GROUP BY singleNumber";

        public static String query_base_groupNumberWithCount = "SELECT groupNumber, COUNT(*) AS cnt FROM "+tableName_Base+" GROUP BY groupNumber";

        // 核心统计SQL（使用用户变量获取下一行的span值）
        public static String query_sumWithNextSumCount = "SELECT csum, nsum, COUNT(*) AS count FROM ("
                + "    SELECT "
                + "        sum AS csum, "
                + "        @next_sum AS nsum, "
                + "        @next_sum := sum "
                + "    FROM "+tableName_Base+" "
                + "    CROSS JOIN (SELECT @next_sum := NULL) vars "
                + "    ORDER BY data ASC" // 必须按期号排序
                + ") tmp "
                + "WHERE nsum IS NOT NULL "
                + "GROUP BY csum, nsum";

        public static String query_spanWithNextSpanCount = "SELECT cspan, nspan, COUNT(*) AS count FROM ("
                + "    SELECT "
                + "        span AS cspan, "
                + "        @next_span AS nspan, "
                + "        @next_span := span "
                + "    FROM "+tableName_Base+" "
                + "    CROSS JOIN (SELECT @next_span := NULL) vars "
                + "    ORDER BY data ASC" // 必须按期号排序
                + ") tmp "
                + "WHERE nspan IS NOT NULL "
                + "GROUP BY cspan, nspan";

        // 直接执行的查询语句
        public static String query_groupNumberWithCount = "SELECT groupNumber, count FROM "+tableName_Group+" ORDER BY count DESC LIMIT 300";

        public static String getQuerySpanWithNextSQL(int cspan){
            return "SELECT cspan, nspan, count FROM "+tableName_Span+" WHERE cspan = "+cspan+" ORDER BY count DESC";
        }

        public static String getQuerySumWithNextSQL(int csum){
            return "SELECT csum, nsum, count FROM "+tableName_Sum+" WHERE csum = "+csum+" ORDER BY count DESC";
        }

        //------------------------------批量清空数据表-----------------------------
        public static String clearTable_Single = "DELETE FROM "+tableName_single;

        public static String clearTable_Group = "DELETE FROM "+tableName_Group;

        public static String clearTable_Sum = "DELETE FROM "+tableName_Sum;

        public static String clearTable_Span = "DELETE FROM "+tableName_Span;


    }

}
