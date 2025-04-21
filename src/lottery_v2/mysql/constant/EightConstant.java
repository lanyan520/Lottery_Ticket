package lottery_v2.mysql.constant;

public class EightConstant {


    public static class SQL{
        //-----------------------------表名称----------------------------------

        public static String dbName = "Lottery_Eight";

        public static String tableName_Base = "Lottery_Eight_Base";

        public static String tableName_Selected = "Lottery_Eight_";

        public static String getInsertTabName(int selected){
            return tableName_Selected+selected;
        }


        //-----------------------------以下是创建表单-----------------------------
        public static String createTable_Eight_Base = "CREATE TABLE IF NOT EXISTS "+tableName_Base+" (" +
                "number VARCHAR(100) NOT NULL" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4";;

        public static String createTable_Eight_2 = getCreateTableSQL(2);

        public static String createTable_Eight_3 = getCreateTableSQL(3);

        public static String createTable_Eight_4 = getCreateTableSQL(4);

        public static String createTable_Eight_5 = getCreateTableSQL(5);

        public static String createTable_Eight_6 = getCreateTableSQL(6);

        public static String createTable_Eight_7 = getCreateTableSQL(7);

        public static String createTable_Eight_8 = getCreateTableSQL(8);

        public static String createTable_Eight_9 = getCreateTableSQL(9);

        public static String createTable_Eight_10 = getCreateTableSQL(10);

        public static String getCreateTableSQL(int selected){
            return "CREATE TABLE IF NOT EXISTS "+getInsertTabName(selected)+" (" +
                    "number VARCHAR(50) PRIMARY KEY," +
                    "count INT NOT NULL" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4";
        }
        //--------------------------------以下是插入数据库表单----------------------------------

        public static String insert_base_data = "INSERT INTO "+tableName_Base+" (number) VALUES (?)";;

        public static String getInsertTableSQL(int selected){
            return "INSERT INTO "+getInsertTabName(selected)+" (number, count) "
                    + "VALUES (?, 1) "
                    + "ON DUPLICATE KEY UPDATE count = count + 1";
        }

        public static String getQueryNumberWithCountSQL(int selected){
            return "SELECT number, count FROM "+getInsertTabName(selected)+" WHERE number = ?";
        }

        public static String getQueryNumberWithCountSort(int selected,int selectedFrequency){
            return "SELECT number, count FROM "+getInsertTabName(selected)+" WHERE count >= "+selectedFrequency+" ORDER BY count DESC";
        }

    }
}
