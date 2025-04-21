package lottery_v2.mysql;

import lottery_v2.interfaces.mysql.MysqlBaseInterface;
import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class MysqlBatchBase extends MysqlBase implements MysqlBaseInterface.BatchInterface {
    @Override
    public void handleBatchException(BatchUpdateException e) {
        System.err.println("批量操作部分失败，错误代码：" + e.getErrorCode());
        System.err.println("错误信息：" + e.getMessage());
    }

    @Override
    public void analyzeBatchResults(int[] results) {
        int success = 0;
        for (int result : results) {
            if (result >= 0) success++;
        }
        System.out.printf("批量处理成功%d/%d条记录%n", success, results.length);
    }

    @Override
    public void executeBatch(Connection conn, PreparedStatement pstmt) throws SQLException {
        try {
            int[] updateCounts = pstmt.executeBatch();
            analyzeBatchResults(updateCounts);
            pstmt.clearBatch();
            conn.commit(); // 提交当前批次
        } catch (BatchUpdateException e) {
            handleBatchException(e);
            throw e;
        }
    }

}
