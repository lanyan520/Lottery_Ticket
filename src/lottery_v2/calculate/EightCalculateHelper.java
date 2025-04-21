package lottery_v2.calculate;

import lottery_v2.data.eight.Lottery_2025;

import java.util.*;
import java.util.stream.Collectors;

public class EightCalculateHelper {

    private static volatile EightCalculateHelper instance ;
    public static EightCalculateHelper getInstance() {
        if (instance == null) { // 第一次检查，避免不必要的锁竞争
            synchronized (EightCalculateHelper.class) {
                if (instance == null) { // 第二次检查，确保唯一性
                    instance = new EightCalculateHelper();
                }
            }
        }
        return instance;
    }

    /***
     * 计算选2-选10中奖金额
     * @param ownNumbers
     * @param winnerNumbers
     * @param selected
     */
    public void calculateMoney(List<String> ownNumbers, List<String> winnerNumbers, int selected){

        switch (selected){
            case 2:
                select2WithMoney(ownNumbers,winnerNumbers);
                break;
            case 3:
                select3WithMoney(ownNumbers,winnerNumbers);
                break;
            case 4:
                select4WithMoney(ownNumbers,winnerNumbers);
                break;
            case 5:
                select5WithMoney(ownNumbers,winnerNumbers);
                break;
            case 6:
                 select6WithMoney(ownNumbers,winnerNumbers);
                 break;
            case 7:
                 select7WithMoney(ownNumbers,winnerNumbers);
                 break;
            case 8:
                 select8WithMoney(ownNumbers,winnerNumbers);
                 break;
            case 9:
                 select9WithMoney(ownNumbers,winnerNumbers);
                 break;
            case 10:
                 select10WithMoney(ownNumbers,winnerNumbers);
                 break;
            default:
                break;
        }
    }

    private void select2WithMoney(List<String> ownNumbers, List<String> winnerNumbers){

        List<Set<String>> planSets = convertToSets(ownNumbers);
        List<Set<String>> bestSets = convertToSets(winnerNumbers);

        // 计算每行的匹配数量
        List<List<Integer>> result = compareMatches(planSets, bestSets);

        System.out.println("---------------------开始计算选2中奖情况-------------------------------------");
        int number_2 = 0;
        for (int i = 0; i < result.size(); i++) {
            List<Integer> row = result.get(i);
            System.out.println("第"+(i+1)+"注号码中奖情况：");
            for (int j = 0; j<row.size(); j++){
                switch (row.get(j)){
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        number_2=number_2+1;
                        break;
                    default:
                        break;
                }
            }
            System.out.println("选2中2: "+number_2+"次，中奖金额："+(number_2*19)+"元");
            System.out.println("********************************");
            System.out.println("累计中奖金额："+(number_2*19)+"元");
            System.out.println("********************************");
            System.out.println();
            number_2 = 0;
        }
    }

    private void select3WithMoney(List<String> ownNumbers, List<String> winnerNumbers){

        List<Set<String>> planSets = convertToSets(ownNumbers);
        List<Set<String>> bestSets = convertToSets(winnerNumbers);

        // 计算每行的匹配数量
        List<List<Integer>> result = compareMatches(planSets, bestSets);

        System.out.println("---------------------开始计算选3中奖情况-------------------------------------");
        int number_2 = 0;
        int number_3 = 0;
        for (int i = 0; i < result.size(); i++) {
            List<Integer> row = result.get(i);
            System.out.println("第"+(i+1)+"注号码中奖情况：");
            for (int j = 0; j<row.size(); j++){
                switch (row.get(j)){
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        number_2=number_2+1;
                        break;
                    case 3:
                        number_3=number_3+1;
                        break;
                    default:
                        break;
                }
            }
            System.out.println("选3中2: "+number_2+"次，中奖金额："+(number_2*3)+"元");
            System.out.println("选3中3: "+number_3+"次，中奖金额："+(number_3*53)+"元");
            System.out.println("********************************");
            System.out.println("累计中奖金额："+(number_2*3+number_3*53)+"元");
            System.out.println("********************************");
            System.out.println();
            number_2 = 0;
            number_3 = 0;
        }
    }

    private void select4WithMoney(List<String> ownNumbers, List<String> winnerNumbers){

        List<Set<String>> planSets = convertToSets(ownNumbers);
        List<Set<String>> bestSets = convertToSets(winnerNumbers);

        // 计算每行的匹配数量
        List<List<Integer>> result = compareMatches(planSets, bestSets);

        System.out.println("---------------------开始计算选4中奖情况-------------------------------------");
        int number_2 = 0;
        int number_3 = 0;
        int number_4 = 0;
        for (int i = 0; i < result.size(); i++) {
            List<Integer> row = result.get(i);
            System.out.println("第"+(i+1)+"注号码中奖情况：");
            for (int j = 0; j<row.size(); j++){
                switch (row.get(j)){
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        number_2=number_2+1;
                        break;
                    case 3:
                        number_3=number_3+1;
                        break;
                    case 4:
                        number_4=number_4+1;
                        break;
                    default:
                        break;
                }
            }
            System.out.println("选4中2: "+number_2+"次，中奖金额："+(number_2*3)+"元");
            System.out.println("选4中3: "+number_3+"次，中奖金额："+(number_3*5)+"元");
            System.out.println("选4中4: "+number_4+"次，中奖金额："+(number_4*100)+"元");
            System.out.println("********************************");
            System.out.println("累计中奖金额："+(number_2*3+number_3*5+number_4*100)+"元");
            System.out.println("********************************");
            System.out.println();
            number_2 = 0;
            number_3 = 0;
            number_4 = 0;
        }
    }

    /***
     * 选5中奖金额计算
     * @param ownNumbers
     * @param winnerNumbers
     */
    private void select5WithMoney(List<String> ownNumbers, List<String> winnerNumbers){

        int totalCount = 0;
        int totalMoney = 0;

        List<Set<String>> planSets = convertToSets(ownNumbers);
        List<Set<String>> bestSets = convertToSets(winnerNumbers);

        // 计算每行的匹配数量
        List<List<Integer>> result = compareMatches(planSets, bestSets);

        System.out.println("-----------------------开始计算选5中奖情况-----------------------------------");
        int number_3 = 0;
        int number_4 = 0;
        int number_5 = 0;
        for (int i = 0; i < result.size(); i++) {
            List<Integer> row = result.get(i);
            System.out.println("第"+(i+1)+"注号码中奖情况：");
            for (int j = 0; j<row.size(); j++){
                switch (row.get(j)){
                    case 3:
                        number_3=number_3+1;
                        break;
                    case 4:
                        number_4=number_4+1;
                        break;
                    case 5:
                        number_5=number_5+1;
                        break;
                    default:
                        break;
                }
            }
            totalMoney = totalMoney + (number_3*3+number_4*21+number_5*1000);
            totalCount = totalCount + number_5;

            System.out.println("选5中3: "+number_3+"次，中奖金额："+(number_3*3)+"元");
            System.out.println("选5中4: "+number_4+"次，中奖金额："+(number_4*21)+"元");
            System.out.println("选5中5: "+number_5+"次，中奖金额："+(number_5*1000)+"元");
            System.out.println("********************************");
            System.out.println("累计中奖金额："+(number_3*3+number_4*21+number_5*1000)+"元");
            System.out.println("********************************");
            System.out.println();
            number_3 = 0;
            number_4 = 0;
            number_5 = 0;
        }
        System.out.println("*************************************************");
        System.out.println("累计投入期数："+winnerNumbers.size());
        System.out.println("累计投入注数："+ownNumbers.size());
        System.out.println("累计投入金额："+ownNumbers.size()*2*winnerNumbers.size());
        System.out.println("累计中奖金额："+totalMoney);
        System.out.println("累计选5中5次数："+totalCount);
        System.out.println("*************************************************");
    }

    /***
     * 选6中奖金额计算
     * @param ownNumbers
     * @param winnerNumbers
     */
    private void select6WithMoney(List<String> ownNumbers, List<String> winnerNumbers){

        int totalCount = 0;
        int totalMoney = 0;

        List<Set<String>> planSets = convertToSets(ownNumbers);
        List<Set<String>> bestSets = convertToSets(winnerNumbers);

        // 计算每行的匹配数量
        List<List<Integer>> result = compareMatches(planSets, bestSets);

        System.out.println("--------------------------开始计算选6中奖情况--------------------------------");
        int number_3 = 0;
        int number_4 = 0;
        int number_5 = 0;
        int number_6 = 0;
        for (int i = 0; i < result.size(); i++) {
            List<Integer> row = result.get(i);
            System.out.println("第"+(i+1)+"注号码中奖情况：");
            for (int j = 0; j<row.size(); j++){
                switch (row.get(j)){
                    case 3:
                        number_3=number_3+1;
                        break;
                    case 4:
                        number_4=number_4+1;
                        break;
                    case 5:
                        number_5=number_5+1;
                        break;
                    case 6:
                        number_6=number_6+1;
                        break;
                    default:
                        break;
                }
            }

            totalMoney = totalMoney + (number_3*3+number_4*10+number_5*30+number_6*3000);
            totalCount = totalCount + number_6;

            System.out.println("选6中3: "+number_3+"次，中奖金额："+(number_3*3)+"元");
            System.out.println("选6中4: "+number_4+"次，中奖金额："+(number_4*10)+"元");
            System.out.println("选6中5: "+number_5+"次，中奖金额："+(number_5*30)+"元");
            System.out.println("选6中6: "+number_6+"次，中奖金额："+(number_6*3000)+"元");
            System.out.println("********************************");
            System.out.println("累计中奖金额："+(number_3*3+number_4*10+number_5*30+number_6*3000)+"元");
            System.out.println("********************************");
            System.out.println();
            number_3 = 0;
            number_4 = 0;
            number_5 = 0;
            number_6 = 0;
        }

        System.out.println("*************************************************");
        System.out.println("累计投入期数："+winnerNumbers.size());
        System.out.println("累计投入注数："+ownNumbers.size());
        System.out.println("累计投入金额："+ownNumbers.size()*2*winnerNumbers.size());
        System.out.println("累计中奖金额："+totalMoney);
        System.out.println("累计选6中6次数："+totalCount);
        System.out.println("*************************************************");
    }

    /***
     * 选7中奖金额计算
     * @param ownNumbers
     * @param winnerNumbers
     */
    private void select7WithMoney(List<String> ownNumbers, List<String> winnerNumbers){

        int totalCount = 0;
        int totalMoney = 0;

        List<Set<String>> planSets = convertToSets(ownNumbers);
        List<Set<String>> bestSets = convertToSets(winnerNumbers);

        // 计算每行的匹配数量
        List<List<Integer>> result = compareMatches(planSets, bestSets);

        System.out.println("--------------------------开始计算选7中奖情况--------------------------------");
        int number_0 = 0;
        int number_4 = 0;
        int number_5 = 0;
        int number_6 = 0;
        int number_7 = 0;
        for (int i = 0; i < result.size(); i++) {
            List<Integer> row = result.get(i);
            System.out.println("第"+(i+1)+"注号码中奖情况：");
            for (int j = 0; j<row.size(); j++){
                switch (row.get(j)){
                    case 0:
                        number_0 = number_0+1;
                        break;
                    case 4:
                        number_4=number_4+1;
                        break;
                    case 5:
                        number_5=number_5+1;
                        break;
                    case 6:
                        number_6=number_6+1;
                        break;
                    case 7:
                        number_7=number_7+1;
                        break;
                    default:
                        break;
                }
            }

            totalMoney = totalMoney + (number_0*2+number_4*4+number_5*28+number_6*288+number_7*10000);
            totalCount = totalCount + number_7;

            System.out.println("选7中0: "+number_0+"次，中奖金额："+(number_0*2)+"元");
            System.out.println("选7中4: "+number_4+"次，中奖金额："+(number_4*4)+"元");
            System.out.println("选7中5: "+number_5+"次，中奖金额："+(number_5*28)+"元");
            System.out.println("选7中6: "+number_6+"次，中奖金额："+(number_6*288)+"元");
            System.out.println("选7中7: "+number_7+"次，中奖金额："+(number_7*10000)+"元");
            System.out.println("********************************");
            System.out.println("累计中奖金额："+(number_0*2+number_4*4+number_5*28+number_6*288+number_7*10000)+"元");
            System.out.println("********************************");
            System.out.println();
            number_0 = 0;
            number_4 = 0;
            number_5 = 0;
            number_6 = 0;
            number_7 = 0;
        }

        System.out.println("*************************************************");
        System.out.println("累计投入期数："+winnerNumbers.size());
        System.out.println("累计投入注数："+ownNumbers.size());
        System.out.println("累计投入金额："+ownNumbers.size()*2*winnerNumbers.size());
        System.out.println("累计中奖金额："+totalMoney);
        System.out.println("累计选7中7次数："+totalCount);
        System.out.println("*************************************************");
    }

    /***
     * 选8中奖金额计算
     * @param ownNumbers
     * @param winnerNumbers
     */
    private void select8WithMoney(List<String> ownNumbers, List<String> winnerNumbers){

        int totalCount = 0;
        double totalMoney = 0;

        List<Set<String>> planSets = convertToSets(ownNumbers);
        List<Set<String>> bestSets = convertToSets(winnerNumbers);

        // 计算每行的匹配数量
        List<List<Integer>> result = compareMatches(planSets, bestSets);

        System.out.println("-------------------------开始计算选8中奖情况---------------------------------");
        int number_0 = 0;
        int number_4 = 0;
        int number_5 = 0;
        int number_6 = 0;
        int number_7 = 0;
        int number_8 = 0;
        for (int i = 0; i < result.size(); i++) {
            List<Integer> row = result.get(i);
            System.out.println("第"+(i+1)+"注号码中奖情况：");
            for (int j = 0; j<row.size(); j++){
                switch (row.get(j)){
                    case 0:
                        number_0 = number_0+1;
                        break;
                    case 4:
                        number_4=number_4+1;
                        break;
                    case 5:
                        number_5=number_5+1;
                        break;
                    case 6:
                        number_6=number_6+1;
                        break;
                    case 7:
                        number_7=number_7+1;
                        break;
                    case 8:
                        number_8=number_8+1;
                        break;
                    default:
                        break;
                }
            }
            totalMoney = totalMoney + (number_0*2+number_4*3+number_5*10+number_6*88+number_7*800+number_8*50000*0.8);
            totalCount = totalCount + number_8;
            System.out.println("选8中0: "+number_0+"次，中奖金额："+(number_0*2)+"元");
            System.out.println("选8中4: "+number_4+"次，中奖金额："+(number_4*3)+"元");
            System.out.println("选8中5: "+number_5+"次，中奖金额："+(number_5*10)+"元");
            System.out.println("选8中6: "+number_6+"次，中奖金额："+(number_6*88)+"元");
            System.out.println("选8中7: "+number_7+"次，中奖金额："+(number_7*800)+"元");
            System.out.println("选8中8: "+number_8+"次，中奖金额："+(number_8*50000*0.8)+"元");
            System.out.println("********************************");
            System.out.println("累计中奖金额："+(number_0*2+number_4*3+number_5*10+number_6*88+number_7*800+number_8*50000*0.8)+"元");
            System.out.println("********************************");
            System.out.println();
            number_0 = 0;
            number_4 = 0;
            number_5 = 0;
            number_6 = 0;
            number_7 = 0;
            number_8 = 0;
        }

        System.out.println("*************************************************");
        System.out.println("累计投入期数："+winnerNumbers.size());
        System.out.println("累计投入注数："+ownNumbers.size());
        System.out.println("累计投入金额："+ownNumbers.size()*2*winnerNumbers.size());
        System.out.println("累计中奖金额："+totalMoney);
        System.out.println("累计选8中8次数："+totalCount);
        System.out.println("*************************************************");
    }

    /***
     * 选9中奖金额计算
     * @param ownNumbers
     * @param winnerNumbers
     */
    private void select9WithMoney(List<String> ownNumbers, List<String> winnerNumbers){

        int totalCount = 0;
        double totalMoney = 0;

        List<Set<String>> planSets = convertToSets(ownNumbers);
        List<Set<String>> bestSets = convertToSets(winnerNumbers);

        // 计算每行的匹配数量
        List<List<Integer>> result = compareMatches(planSets, bestSets);

        System.out.println("--------------------------开始计算选9中奖情况---------------------------------");
        int number_0 = 0;
        int number_4 = 0;
        int number_5 = 0;
        int number_6 = 0;
        int number_7 = 0;
        int number_8 = 0;
        int number_9 = 0;
        for (int i = 0; i < result.size(); i++) {
            List<Integer> row = result.get(i);
            System.out.println("第"+(i+1)+"注号码中奖情况：");
            for (int j = 0; j<row.size(); j++){
                switch (row.get(j)){
                    case 0:
                        number_0 = number_0+1;
                        break;
                    case 4:
                        number_4=number_4+1;
                        break;
                    case 5:
                        number_5=number_5+1;
                        break;
                    case 6:
                        number_6=number_6+1;
                        break;
                    case 7:
                        number_7=number_7+1;
                        break;
                    case 8:
                        number_8=number_8+1;
                        break;
                    case 9:
                        number_9=number_9+1;
                        break;
                    default:
                        break;
                }
            }

            totalMoney = totalMoney + (number_0*2+number_4*3+number_5*5+number_6*20+number_7*200+number_8*2000+number_9*300000*0.8);
            totalCount = totalCount + number_9;
            System.out.println("选9中0: "+number_0+"次，中奖金额："+(number_0*2)+"元");
            System.out.println("选9中4: "+number_4+"次，中奖金额："+(number_4*3)+"元");
            System.out.println("选9中5: "+number_5+"次，中奖金额："+(number_5*5)+"元");
            System.out.println("选9中6: "+number_6+"次，中奖金额："+(number_6*20)+"元");
            System.out.println("选9中7: "+number_7+"次，中奖金额："+(number_7*200)+"元");
            System.out.println("选9中8: "+number_8+"次，中奖金额："+(number_8*2000)+"元");
            System.out.println("选9中9: "+number_9+"次，中奖金额："+(number_9*300000*0.8)+"元");
            System.out.println("********************************");
            System.out.println("累计中奖金额："+(number_0*2+number_4*3+number_5*5+number_6*20+number_7*200+number_8*2000+number_9*300000*0.8)+"元");
            System.out.println("********************************");
            System.out.println();
            number_0 = 0;
            number_4 = 0;
            number_5 = 0;
            number_6 = 0;
            number_7 = 0;
            number_8 = 0;
            number_9 = 0;
        }

        System.out.println("*************************************************");
        System.out.println("累计投入期数："+winnerNumbers.size());
        System.out.println("累计投入注数："+ownNumbers.size());
        System.out.println("累计投入金额："+ownNumbers.size()*2*winnerNumbers.size());
        System.out.println("累计中奖金额："+totalMoney);
        System.out.println("累计选8中8次数："+totalCount);
        System.out.println("*************************************************");
    }

    /***
     * 选10中奖金额计算
     * @param ownNumbers
     * @param winnerNumbers
     */
    private void select10WithMoney(List<String> ownNumbers, List<String> winnerNumbers){

        int totalCount = 0;
        double totalMoney = 0;

        List<Set<String>> planSets = convertToSets(ownNumbers);
        List<Set<String>> bestSets = convertToSets(winnerNumbers);

        // 计算每行的匹配数量
        List<List<Integer>> result = compareMatches(planSets, bestSets);

        System.out.println("--------------------------开始计算选10中奖情况---------------------------------");
        int number_0 = 0;
        int number_5 = 0;
        int number_6 = 0;
        int number_7 = 0;
        int number_8 = 0;
        int number_9 = 0;
        int number_10 = 0;

        for (int i = 0; i < result.size(); i++) {
            List<Integer> row = result.get(i);
            System.out.println("第"+(i+1)+"注号码中奖情况：");
            for (int j = 0; j<row.size(); j++){
                switch (row.get(j)){
                    case 0:
                        number_0 = number_0+1;
                        break;
                    case 5:
                        number_5=number_5+1;
                        break;
                    case 6:
                        number_6=number_6+1;
                        break;
                    case 7:
                        number_7=number_7+1;
                        break;
                    case 8:
                        number_8=number_8+1;
                        break;
                    case 9:
                        number_9=number_9+1;
                        break;
                    case 10:
                        number_10=number_10+1;
                        break;
                    default:
                        break;
                }
            }

            totalMoney = totalMoney + (number_0*2+number_5*3+number_6*5+number_7*80+number_8*800+number_9*8000+number_10*5000000*0.8);
            totalCount = totalCount + number_10;

            System.out.println("选10中0: "+number_0+"次，中奖金额："+(number_0*2)+"元");
            System.out.println("选10中5: "+number_5+"次，中奖金额："+(number_5*3)+"元");
            System.out.println("选10中6: "+number_6+"次，中奖金额："+(number_6*5)+"元");
            System.out.println("选10中7: "+number_7+"次，中奖金额："+(number_7*80)+"元");
            System.out.println("选10中8: "+number_8+"次，中奖金额："+(number_8*800)+"元");
            System.out.println("选10中9: "+number_9+"次，中奖金额："+(number_9*8000)+"元");
            System.out.println("选10中10: "+number_10+"次，中奖金额："+(number_10*5000000*0.8)+"元");
            System.out.println("********************************");
            System.out.println("累计中奖金额："+(number_0*2+number_5*3+number_6*5+number_7*80+number_8*800+number_9*8000+number_10*5000000*0.8)+"元");
            System.out.println("********************************");
            System.out.println();
            number_0 = 0;
            number_5 = 0;
            number_6 = 0;
            number_7 = 0;
            number_8 = 0;
            number_9 = 0;
            number_10 = 0;
        }

        System.out.println("*************************************************");
        System.out.println("累计投入期数："+winnerNumbers.size());
        System.out.println("累计投入注数："+ownNumbers.size());
        System.out.println("累计投入金额："+ownNumbers.size()*2*winnerNumbers.size());
        System.out.println("累计中奖金额："+totalMoney);
        System.out.println("累计选8中8次数："+totalCount);
        System.out.println("*************************************************");
    }

    /**
     * 将字符串列表转换为Set集合列表
     */
    private List<Set<String>> convertToSets(List<String> list) {
        return list.stream()
                .map(line -> Arrays.stream(line.split(",")).collect(Collectors.toSet()))
                .collect(Collectors.toList());
    }

    /**
     * 对比两组集合的匹配数量
     */
    private List<List<Integer>> compareMatches(List<Set<String>> planSets, List<Set<String>> bestSets) {
        List<List<Integer>> matches = new ArrayList<>();

        for (Set<String> planSet : planSets) {
            List<Integer> rowMatches = new ArrayList<>();
            for (Set<String> bestSet : bestSets) {
                // 计算交集数量
                int count = (int) planSet.stream()
                        .filter(bestSet::contains)
                        .count();
                rowMatches.add(count);
            }
            matches.add(rowMatches);
        }

        return matches;
    }

}
