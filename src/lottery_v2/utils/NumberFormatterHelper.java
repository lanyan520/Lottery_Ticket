package lottery_v2.utils;

import lottery_v2.beans.TwoBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/***
 * 用于爬去数据格式不正确清洗
 * 比如爬数据：1，3，10，20-》"01，03，10，20"，
 */
public class NumberFormatterHelper {

    /**
     * 格式化单个数字项：
     * - 如果是单数字且为1-9 → 补零为两位（如 "1" → "01"）
     * - 否则保持原样（如 "10", "a", "" → 直接返回）
     */
    private static String numberDigit(String numStr) {
        if (numStr != null
                && numStr.length() == 1
                && numStr.matches("[1-9]")) { // 仅处理单数字且为1-9的情况
            return String.format("%02d", Integer.parseInt(numStr));
        }
        return numStr; // 其他情况原样返回
    }

    /**
     * 格式化字符串
     * 1,3,10,20->"01,03,10,20",
     * @param data
     */
    public static void numberForMatter(List<String> data) {

        for (int i = 0; i < data.size(); i++) {
            String input = data.get(i);
            if (input == null || input.isEmpty()) {
                return; // 处理空输入
            }

            String result = Arrays.stream(input.split(","))
                    .map(NumberFormatterHelper::numberDigit)
                    .collect(Collectors.joining(","));


            System.out.println("\""+result+"\",");
        }
    }

    /**
     * 处理多行文本：
     * 1. 去掉每行开头的第一个逗号（仅当首字符是逗号时）
     * 2. 添加前缀后缀，逗号 "每一行最后一个字符添加"，
     */
    public static String cleanData(String number) {
        return number.lines()
                .map(line -> {
                    // 处理空行
                    if (line.isEmpty()) {
                        return "\"\""; // 空行返回空双引号
                    }

                    // 去掉首字符如果是逗号
                    char firstChar = line.charAt(0);
                    String cleanedLine = (firstChar == '，') ? line.substring(1) : line;

                    // 去掉末尾逗号
                    cleanedLine = cleanedLine.isEmpty()
                            ? ""
                            : cleanedLine.charAt(cleanedLine.length() - 1) == '，'
                            ? cleanedLine.substring(0, cleanedLine.length() - 1)
                            : cleanedLine;

                    // 添加前缀并包裹双引号
                    String formattedLine = "\"" + cleanedLine.trim() + "\",";

                    return formattedLine;
                })
                .collect(Collectors.joining("\n")); // 保留原始换行符
    }


    /**
     * 清洗双色球数据
     * @param winnerData
     * @param DATA_TAG
     */
    public static List<TwoBean> clearTwoBallData(List<String> winnerData, String DATA_TAG){
        List<TwoBean> result = new ArrayList<>();

        for (int i = 0; i < winnerData.size(); i++) {
            TwoBean bean = new TwoBean();
            String[] oneWinnerData = winnerData.get(i).split(",");
            bean.setData(DATA_TAG+oneWinnerData[0]);
            bean.setRed1(oneWinnerData[1].length()==1?"0"+oneWinnerData[1]:oneWinnerData[1]);
            bean.setRed2(oneWinnerData[2].length()==1?"0"+oneWinnerData[2]:oneWinnerData[2]);
            bean.setRed3(oneWinnerData[3].length()==1?"0"+oneWinnerData[3]:oneWinnerData[3]);
            bean.setRed4(oneWinnerData[4].length()==1?"0"+oneWinnerData[4]:oneWinnerData[4]);
            bean.setRed5(oneWinnerData[5].length()==1?"0"+oneWinnerData[5]:oneWinnerData[5]);
            bean.setRed6(oneWinnerData[6].length()==1?"0"+oneWinnerData[6]:oneWinnerData[6]);
            bean.setBlue(oneWinnerData[7].length()==1?"0"+oneWinnerData[7]:oneWinnerData[7]);
            result.add(bean);
        }
        return result;
    }
}
