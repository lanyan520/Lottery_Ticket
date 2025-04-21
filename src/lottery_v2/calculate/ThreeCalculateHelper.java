package lottery_v2.calculate;

import lottery_v2.interfaces.calculate.ThreeCalculateInterface;
import lottery_v2.mysql.constant.ThreeConstant;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public class ThreeCalculateHelper implements ThreeCalculateInterface {
    private static volatile ThreeCalculateHelper instance ;
    public static ThreeCalculateHelper getInstance() {
        if (instance == null) { // 第一次检查，避免不必要的锁竞争
            synchronized (ThreeCalculateHelper.class) {
                if (instance == null) { // 第二次检查，确保唯一性
                    instance = new ThreeCalculateHelper();
                }
            }
        }
        return instance;
    }
    @Override
    public boolean isWinPrizeWith3DSingle(String openNumbers, String ownNumbers) {
        return openNumbers.equals(ownNumbers);
    }

    @Override
    public boolean isWinPrizeWith3DGroup(String openNumbers, String ownNumbers) {
        return getGroupNumber(ownNumbers).equals(getGroupNumber(openNumbers));
    }

    @Override
    public int getNumberType(String number) {
        Integer data = Integer.valueOf(number);
        int result = 0;
        // 分解各位数字
        int hundreds = data / 100;
        int tens = (data % 100) / 10;
        int units = data % 10;

        // 判断重复情况
        if (hundreds == tens && tens == units) {
            result = 0;
        } else if (hundreds == tens || hundreds == units || tens == units) {
            result = 1;
        } else {
            result = 2;
        }
        return result;
    }

    @Override
    public int getSum(String number) {
        return (number.charAt(0) - '0') + (number.charAt(1) - '0') + (number.charAt(2) - '0');
    }

    @Override
    public int getSpan(String number) {
        // 参数校验
        if (number == null || number.length() != 3) {
            throw new IllegalArgumentException("输入必须为3位数字字符串");
        }

        // 字符转数字
        int[] digits = new int[3];
        for (int i = 0; i < 3; i++) {
            char c = number.charAt(i);
            if (!Character.isDigit(c)) {
                throw new IllegalArgumentException("包含非数字字符: " + c);
            }
            digits[i] = c - '0'; // ASCII转换技巧[6](@ref)
        }

        // 计算极值
        int max = Math.max(digits[0], Math.max(digits[1], digits[2]));
        int min = Math.min(digits[0], Math.min(digits[1], digits[2]));

        int result = max - min;
        return result;
    }

    @Override
    public String getGroupNumber(String number) {
        int[] digits = new int[3];
        // 分解每一位数字
        for (int i = 0; i < 3; i++) {
            digits[i] = Character.getNumericValue(number.charAt(i));
        }
        // 升序排序
        Arrays.sort(digits);
        // 组合成三位数字符串
        return String.format("%d%d%d", digits[0], digits[1], digits[2]);
    }

    @Override
    public int getSingleMoney() {
        return ThreeConstant.Money.singleMoney;
    }

    @Override
    public int getGroupMoneyWithType1() {
        return ThreeConstant.Money.groupMoneyWithType1;
    }


    @Override
    public int getGroupMoneyWithType2() {
        return ThreeConstant.Money.groupMoneyWithType2;
    }

    @Override
    public int getLeopardMoney() {
        return ThreeConstant.Money.leopardMoney;
    }

    @Override
    public void calculateWith3DGroup(List<String> winNumbers, String[] ownNumbers) {
        System.out.println("---------------------------累计"+winNumbers.size()+"期，累计投入金额："+ownNumbers.length*2*winNumbers.size()+"元-------------------------------");
        int totalMoney = 0;
        LinkedHashMap<String,Integer> map = new LinkedHashMap<>();

        for (int i = 0; i < ownNumbers.length; i++) {
            map.put(ownNumbers[i],0);
        }

        for (int i = 0; i < winNumbers.size(); i++) {
            String[] result = winNumbers.get(i).replaceAll("\\s", "").split(",");
            String number = getInstance().getGroupNumber(result[1]);
            boolean flag = containsElement3D(ownNumbers,number);
            if(flag){
                //中奖次数统计
                int count = map.get(number);
                if(count>0){
                    count = count+1;
                }else {
                    count = 1;
                }
                map.put(number,count);

                int money = 0;
                int type = getInstance().getNumberType(number);
                if(type ==0){
                    money = getInstance().getLeopardMoney();
                }else if(type ==1){
                    money = getInstance().getGroupMoneyWithType1();
                }else if(type==2){
                    money = getInstance().getGroupMoneyWithType2();
                }
                totalMoney=totalMoney+money;
                System.out.println("------------------------第"+(i+1)+"期中奖金额："+money+"----------------------------------");
            }else{
                System.out.println("---------------------------第"+(i+1)+"期未中奖-------------------------------");
            }
        }
        System.out.println("---------------------------累计"+winNumbers.size()+"期，累计投入金额："+ownNumbers.length*2*winNumbers.size()+"元,累计中奖："+totalMoney+"元，净利润："+(totalMoney-(ownNumbers.length*2*winNumbers.size()))+"元-----------------------------");

        for (String key : map.keySet()) {
            Integer value = map.get(key);
            System.out.println("开奖号: " + key + ", 中奖次数: " + value);
        }
    }

    @Override
    public void checkOwnGroupNumbers(List<String> queryGroupNumbers, String[] ownNumbers, int maxSize) {

        System.out.println("以下号码是本期推荐打的号码：");
        String[] array = queryGroupNumbers.stream().toArray(String[]::new);
        for (int i = 0; i < ownNumbers.length; i++) {
            String number = ownNumbers[i];
            boolean flag = containsElement3D(Arrays.copyOf(array, Math.min(array.length, maxSize)),number);
            if(flag){
                System.out.println(number);
            }
        }
    }

    @Override
    public boolean containsElement3D(String[] ownNumbers, String winNumber) {
        if (ownNumbers == null || winNumber == null) return false;
        List<String> list = Arrays.asList(ownNumbers);
        return list.contains(winNumber);
    }

}
