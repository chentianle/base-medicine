package cn.huanzi.qch.baseadmin.medicine.unit;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DosageParser {

    private static final Map<String, Integer> CHINESE_NUMBER_MAP = new HashMap<>();

    static {
        CHINESE_NUMBER_MAP.put("零", 0);
        CHINESE_NUMBER_MAP.put("一", 1);
        CHINESE_NUMBER_MAP.put("二", 2);
        CHINESE_NUMBER_MAP.put("三", 3);
        CHINESE_NUMBER_MAP.put("四", 4);
        CHINESE_NUMBER_MAP.put("五", 5);
        CHINESE_NUMBER_MAP.put("六", 6);
        CHINESE_NUMBER_MAP.put("七", 7);
        CHINESE_NUMBER_MAP.put("八", 8);
        CHINESE_NUMBER_MAP.put("九", 9);
        CHINESE_NUMBER_MAP.put("两", 2);
    }



    public DosageSpec dosageChuli(String input) throws Exception{
        String pattern = "\\s*(\\d+)次[\\s\\S]*次(\\d+(\\.\\d+)?)(ml|g|cl|mg|片|瓶|支|板|粒|片/盒|S|袋|IU|iu|包|克|喷|丸|毫克|毫升|丸|枚|s|贴|揿|ug|单位|钦|吸|张|U|μg|万|PNA|泡|块)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(input);
        if (m.find()) {
            Integer timesPerDay = Integer.parseInt(m.group(1));
            double pillsPerTime = Double.parseDouble(m.group(2));
            String unit = m.group(4);
            System.out.println("用法用量=》频次：" + timesPerDay+"，数量："+pillsPerTime+"，单位："+ unit);
            return new DosageSpec(timesPerDay,pillsPerTime,unit);
        }
        String patternV1 = "\\s*次(\\d+(\\.\\d+)?)(ml|g|cl|mg|片|瓶|支|板|粒|片/盒|S|袋|IU|iu|包|克|喷|丸|毫克|毫升|丸|枚|s|贴|揿|ug|单位|钦|吸|张|U|μg|万|PNA|泡|块)[\\s\\S]*(\\d+)次";
        Pattern rV1 = Pattern.compile(patternV1);
        Matcher mV1 = rV1.matcher(input);
        if (mV1.find()) {
            Integer timesPerDay = Integer.parseInt(mV1.group(4));
            double pillsPerTime = Double.parseDouble(mV1.group(1));
            String unit = mV1.group(3);
            System.out.println("用法用量=》频次：" + timesPerDay+"，数量："+pillsPerTime+"，单位："+ unit);
            return new DosageSpec(timesPerDay,pillsPerTime,unit);
        }
        String patternV2 = "\\s*(\\d+)次[\\s\\S]*次(一|二|三|四|五|六|七|八|九|两)(ml|g|cl|mg|片|瓶|支|板|粒|片/盒|S|袋|IU|iu|包|克|喷|丸|毫克|毫升|丸|枚|s|贴|揿|ug|单位|钦|吸|张|U|μg|万|PNA|泡|块)";
        Pattern rV2 = Pattern.compile(patternV2);
        Matcher mV2 = rV2.matcher(input);
        if (mV2.find()) {
            String timesPerDayStr = mV2.group(2);
            Integer timesPerDay = CHINESE_NUMBER_MAP.get(timesPerDayStr);
            double pillsPerTime = Double.parseDouble(mV2.group(1));
            String unit = mV2.group(3);
            System.out.println("用法用量=》频次：" + timesPerDay+"，数量："+pillsPerTime+"，单位："+ unit);
            return new DosageSpec(timesPerDay,pillsPerTime,unit);
        }

        throw new Exception("药品用法用量格式无效！");
    }

}


