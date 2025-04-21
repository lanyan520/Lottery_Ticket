package lottery_v2.utils;

import lottery_v2.beans.FollowBean;
import lottery_v2.beans.TwoBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FollowNumberHelper {

    private static volatile FollowNumberHelper instance ;
    public static FollowNumberHelper getInstance() {
        if (instance == null) { // 第一次检查，避免不必要的锁竞争
            synchronized (FollowNumberHelper.class) {
                if (instance == null) { // 第二次检查，确保唯一性
                    instance = new FollowNumberHelper();
                }
            }
        }
        return instance;
    }

    /**
     * 双色球红球跟随号统计使用
     * @param data
     * @return
     */
    private HashMap<String,HashMap<String,Integer>> getFollowNumberHashMap(List<TwoBean> data){

        HashMap<String,HashMap<String,Integer>> result = new HashMap<>();
        for (int i = 1; i < 34; i++) {

            HashMap<String,Integer> map = new HashMap<>();
            for (int j = 1; j < 34; j++) {
                if(j<10){
                    map.put("0"+j,0);
                }else{
                    map.put(String.valueOf(j),0);
                }
            }

            if(i<10){
                result.put("0"+i,map);
            }else{
                result.put(String.valueOf(i),map);
            }
        }
        for (int i = 0; i < data.size()-1; i++) {
            TwoBean cTwoBean = data.get(i);
            TwoBean nTwoBean = data.get(i+1);
            HashMap<String,Integer> childMap1 = result.get(cTwoBean.getRed1());
            childMap1.put(nTwoBean.getRed1(),childMap1.get(nTwoBean.getRed1())+1);
            childMap1.put(nTwoBean.getRed2(),childMap1.get(nTwoBean.getRed2())+1);
            childMap1.put(nTwoBean.getRed3(),childMap1.get(nTwoBean.getRed3())+1);
            childMap1.put(nTwoBean.getRed4(),childMap1.get(nTwoBean.getRed4())+1);
            childMap1.put(nTwoBean.getRed5(),childMap1.get(nTwoBean.getRed5())+1);
            childMap1.put(nTwoBean.getRed6(),childMap1.get(nTwoBean.getRed6())+1);
            
            HashMap<String,Integer> childMap2 = result.get(cTwoBean.getRed2());
            childMap2.put(nTwoBean.getRed1(),childMap2.get(nTwoBean.getRed1())+1);
            childMap2.put(nTwoBean.getRed2(),childMap2.get(nTwoBean.getRed2())+1);
            childMap2.put(nTwoBean.getRed3(),childMap2.get(nTwoBean.getRed3())+1);
            childMap2.put(nTwoBean.getRed4(),childMap2.get(nTwoBean.getRed4())+1);
            childMap2.put(nTwoBean.getRed5(),childMap2.get(nTwoBean.getRed5())+1);
            childMap2.put(nTwoBean.getRed6(),childMap2.get(nTwoBean.getRed6())+1);
            
            HashMap<String,Integer> childMap3 = result.get(cTwoBean.getRed3());
            childMap3.put(nTwoBean.getRed1(),childMap3.get(nTwoBean.getRed1())+1);
            childMap3.put(nTwoBean.getRed2(),childMap3.get(nTwoBean.getRed2())+1);
            childMap3.put(nTwoBean.getRed3(),childMap3.get(nTwoBean.getRed3())+1);
            childMap3.put(nTwoBean.getRed4(),childMap3.get(nTwoBean.getRed4())+1);
            childMap3.put(nTwoBean.getRed5(),childMap3.get(nTwoBean.getRed5())+1);
            childMap3.put(nTwoBean.getRed6(),childMap3.get(nTwoBean.getRed6())+1);
            
            HashMap<String,Integer> childMap4 = result.get(cTwoBean.getRed4());
            childMap4.put(nTwoBean.getRed1(),childMap4.get(nTwoBean.getRed1())+1);
            childMap4.put(nTwoBean.getRed2(),childMap4.get(nTwoBean.getRed2())+1);
            childMap4.put(nTwoBean.getRed3(),childMap4.get(nTwoBean.getRed3())+1);
            childMap4.put(nTwoBean.getRed4(),childMap4.get(nTwoBean.getRed4())+1);
            childMap4.put(nTwoBean.getRed5(),childMap4.get(nTwoBean.getRed5())+1);
            childMap4.put(nTwoBean.getRed6(),childMap4.get(nTwoBean.getRed6())+1);
            
            HashMap<String,Integer> childMap5 = result.get(cTwoBean.getRed5());
            childMap5.put(nTwoBean.getRed1(),childMap5.get(nTwoBean.getRed1())+1);
            childMap5.put(nTwoBean.getRed2(),childMap5.get(nTwoBean.getRed2())+1);
            childMap5.put(nTwoBean.getRed3(),childMap5.get(nTwoBean.getRed3())+1);
            childMap5.put(nTwoBean.getRed4(),childMap5.get(nTwoBean.getRed4())+1);
            childMap5.put(nTwoBean.getRed5(),childMap5.get(nTwoBean.getRed5())+1);
            childMap5.put(nTwoBean.getRed6(),childMap5.get(nTwoBean.getRed6())+1);
            
            HashMap<String,Integer> childMap6 = result.get(cTwoBean.getRed6());
            childMap6.put(nTwoBean.getRed1(),childMap6.get(nTwoBean.getRed1())+1);
            childMap6.put(nTwoBean.getRed2(),childMap6.get(nTwoBean.getRed2())+1);
            childMap6.put(nTwoBean.getRed3(),childMap6.get(nTwoBean.getRed3())+1);
            childMap6.put(nTwoBean.getRed4(),childMap6.get(nTwoBean.getRed4())+1);
            childMap6.put(nTwoBean.getRed5(),childMap6.get(nTwoBean.getRed5())+1);
            childMap6.put(nTwoBean.getRed6(),childMap6.get(nTwoBean.getRed6())+1);
            
        }
        return result;
    }

    /**
     * 双色球红球下期跟随号统计使用
     * @param data
     * @return
     */
    public List<FollowBean> getFollowNumberList(List<TwoBean> data){
        List<FollowBean> result = new ArrayList<>();
        HashMap<String,HashMap<String,Integer>> maps = getFollowNumberHashMap(data);
        for (int i = 1; i < 34; i++) {
            String cRed = null;
            if(i<10){
                cRed = "0"+i;
            }else{
                cRed = String.valueOf(i);
            }
            HashMap<String,Integer> map = maps.get(cRed);
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                String nRed = entry.getKey();
                int count = entry.getValue();
                result.add(new FollowBean(cRed,nRed,count));
            }

        }
        return result;
    }
}
