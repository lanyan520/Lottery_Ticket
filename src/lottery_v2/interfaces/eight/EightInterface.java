package lottery_v2.interfaces.eight;

import lottery_v2.beans.GallbladderCodeBean;

import java.sql.SQLException;
import java.util.List;

public interface EightInterface {
    /**
     * 获取2020年到2024年数据
     * @return
     */
    abstract List<String> getAllData();

    interface InsertInterface {

        /**
         * 把所有数据插入数据库表
         *
         * @throws SQLException
         */
        abstract void insertAllData() throws SQLException;

        /**
         * 把所有数据插入数据库表
         * 选2-选10
         *
         * @throws SQLException
         */
        abstract void batchInsertWithSelected(List<String> winnerNumbers, int selected) throws SQLException;
    }

    interface QueryInterface{

        /**
         * 自动预测开奖号
         * 功能偏向于预测选2-选5(选五的查询相比较选2-4更耗时间)
         * 扩展号码大概有中奖号10-14个
         * @param lastWinnerNumbers 上期开奖号
         * @param selectType 选2-选7
         * @param selectedFrequency 中过奖的次数，用于过滤
         * @return
         */
        abstract List<String> autoPredictionNumbers(String[] lastWinnerNumbers, int selectType, int selectedFrequency);

        /**
         * 查询高频选号，过滤掉频率<selectedFrequency
         * @param selected
         * @param selectedFrequency
         * @return
         */
        abstract List<String> queryNumbersWithCount(int selected, int selectedFrequency);

        /**
         * 自动预测开奖号(GallbladderCodeBean ！=null ，就需要过滤胆码，不含胆码的直接踢出)
         * 功能偏向于预测选2-选5(选五的查询相比较选2-4更耗时间)
         * 扩展号码大概有中奖号10-14个
         * @param lastWinnerNumbers 上期开奖号
         * @param selectType 选2-选7
         * @param selectedFrequency 中过奖的次数，用于过滤
         * @param bean 过滤条件，例如 01 05 08 要含有1-2个才能通过
         * @return
         */
        abstract List<String> autoPredictionNumbers(String[] lastWinnerNumbers, int selectType, int selectedFrequency, GallbladderCodeBean bean);
    }

    interface CalculateInterface{
        /***
         * 计算投注号码对应历史数据的中奖金额情况
         * @param ownNumbers 自己投注的多注号码
         * @param winnerNumbers 开奖的多期历史号码数据
         * @param selected 选2-选10，对应值2-10（本地数据库不支持选 8 选9 选10，太占用存储空间）
         * @param selectedFrequency 用于过滤号码出现过多少次，低于这个频率值就不要
         * @param maxSize 查询数据库后得到的数据是庞大的，用maxsize截取0-maxsize注数用于投注 ，选7最佳不要超过400，选8不要超过700
         */
        abstract void calculate(List<String> ownNumbers, List<String> winnerNumbers, int selected,int selectedFrequency,int maxSize);
    }
}
