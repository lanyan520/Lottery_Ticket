package lottery_v2.interfaces.two;

import lottery_v2.beans.TwoBean;

import java.sql.SQLException;
import java.util.List;

public interface TwoInterface {

    /**
     * 获取2003年到2024年数据
     * @return
     */
    abstract List<TwoBean> getAllData();

    /**
     * 查询数据库所有的红球数据，不含日期和蓝球
     * @return
     */
    abstract List<TwoBean> queryAllRedData();

    interface InsertInterface{

        /**
         * 往数据库插入双色球基础数据2003-2024年数据
         * @param data
         * @throws SQLException
         */
        abstract void insertAllData(List<TwoBean> data) throws SQLException;

    }

}
