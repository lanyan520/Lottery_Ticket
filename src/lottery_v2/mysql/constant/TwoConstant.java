package lottery_v2.mysql.constant;

public class TwoConstant {

    public static class SQL{
        //-----------------------------表名称----------------------------------

        public static String dbName = "Lottery_Two";

        public static String tableName_Base = "Lottery_Two_Base";

        public static String tableName_follow_blue = "Lottery_Follow_Blue";

        public static String tableName_follow_red = "Lottery_Follow_Red";

        public static String tableName_Selected = "Lottery_Two_";

        public static String getInsertTabName(int selected){
            return tableName_Selected+selected;
        }

        public static String clearTable_Follow_Blue = "DELETE FROM "+tableName_follow_blue;


        //-----------------------------以下是创建表单-----------------------------
        public static String createTable_Two_Base = "CREATE TABLE IF NOT EXISTS "+tableName_Base+" (" +
                "data VARCHAR(10) PRIMARY KEY," +
                "red1 VARCHAR(10) NOT NULL," +
                "red2 VARCHAR(10) NOT NULL," +
                "red3 VARCHAR(10) NOT NULL," +
                "red4 VARCHAR(10) NOT NULL," +
                "red5 VARCHAR(10) NOT NULL," +
                "red6 VARCHAR(10) NOT NULL," +
                "blue VARCHAR(10) NOT NULL," +
                "numbers VARCHAR(50) NOT NULL" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4";

        public static String createTable_Follow_Blue = "CREATE TABLE IF NOT EXISTS "+tableName_follow_blue+" (" +
                "cBlue VARCHAR(10) NOT NULL," +
                "nBlue VARCHAR(10) NOT NULL," +
                "count INT NOT NULL" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4";;

        public static String createTable_Two_2 = getCreateTableSQL(2);

        public static String createTable_Two_3 = getCreateTableSQL(3);

        public static String createTable_Two_4 = getCreateTableSQL(4);

        public static String createTable_Two_5 = getCreateTableSQL(5);

        public static String createTable_Two_6 = getCreateTableSQL(6);


        public static String getCreateTableSQL(int selected){
            return "CREATE TABLE IF NOT EXISTS "+getInsertTabName(selected)+" (" +
                    "numbers VARCHAR(20) PRIMARY KEY," +
                    "count INT NOT NULL" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4";
        }


        public static String getCreateTableFollowSQL(){
            return "CREATE TABLE IF NOT EXISTS "+tableName_follow_red+" (" +
                    "cRed VARCHAR(10) NOT NULL," +
                    "nRed VARCHAR(10) NOT NULL," +
                    "count INT NOT NULL" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4";
        }
        //--------------------------------以下是插入数据库表单----------------------------------

        public static String insert_base_data = "INSERT INTO "+tableName_Base+" (data,red1,red2,red3,red4,red5,red6,blue,numbers) VALUES (?,?,?,?,?,?,?,?,?)";

        public static String insert_follow_red = "INSERT INTO "+tableName_follow_red+" (cRed,nRed,count) VALUES (?,?,?)";

        public static String insert_follow_blue = "INSERT INTO "+tableName_follow_blue+" (cBlue,nBlue,count) VALUES (?,?,?)";

        public static String getInsertTableSQL(int selected){
            return "INSERT INTO "+getInsertTabName(selected)+" (numbers, count) "
                    + "VALUES (?, 1) "
                    + "ON DUPLICATE KEY UPDATE count = count + 1";
        }

        public static String getQueryNumberWithCountSQL(int selected){
            return "SELECT numbers, count FROM "+getInsertTabName(selected)+" WHERE numbers = ?";
        }

        public static String getQueryNumberWithCountSort(int selected,int selectedFrequency){
            return "SELECT numbers, count FROM "+getInsertTabName(selected)+" WHERE count >= "+selectedFrequency+" ORDER BY count DESC";
        }


        public static String query_two_base_data = "SELECT red1, red2, red3, red4, red5, red6 FROM " + tableName_Base;

        public static String query_two_numbers_data = "SELECT numbers FROM " + tableName_Base;


        public static String query_blueWithNextBlueCount = "SELECT cBlue, nBlue, COUNT(*) AS count FROM ("
                + "    SELECT "
                + "        blue AS cBlue, "
                + "        @next_blue AS nBlue, "
                + "        @next_blue := blue "
                + "    FROM "+tableName_Base+" "
                + "    CROSS JOIN (SELECT @next_blue := NULL) vars "
                + "    ORDER BY data ASC" // 必须按期号排序
                + ") tmp "
                + "WHERE nBlue IS NOT NULL "
                + "GROUP BY cBlue, nBlue";

    }

}
