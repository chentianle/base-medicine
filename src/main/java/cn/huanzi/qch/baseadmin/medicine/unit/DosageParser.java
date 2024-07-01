package cn.huanzi.qch.baseadmin.medicine.unit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DosageParser {
    public DosageSpec dosageChuli(String input) throws Exception{
        String pattern = "\\s*(\\d+)次[\\s\\S]*次(\\d+(\\.\\d+)?)(ml|g|cl|mg|片|瓶|支|板|粒|片/盒|S|袋|IU|包|克|喷|丸|毫克|丸|枚|s|贴|揿|ug|单位|钦)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(input);
        if (m.find()) {
            Integer timesPerDay = Integer.parseInt(m.group(1));
            double pillsPerTime = Double.parseDouble(m.group(2));
            String unit = m.group(4);
            System.out.println("用法用量=》频次：" + timesPerDay+"，数量："+pillsPerTime+"，单位："+ unit);
            return new DosageSpec(timesPerDay,pillsPerTime,unit);
        }
        String patternV1 = "\\s*次(\\d+(\\.\\d+)?)(ml|g|cl|mg|片|瓶|支|板|粒|片/盒|S|袋|IU|包|克|喷|丸|毫克|丸|枚|s|贴|揿|ug|单位|钦)[\\s\\S]*(\\d+)次";
        Pattern rV1 = Pattern.compile(patternV1);
        Matcher mV1 = rV1.matcher(input);
        if (mV1.find()) {
            Integer timesPerDay = Integer.parseInt(mV1.group(4));
            double pillsPerTime = Double.parseDouble(mV1.group(1));
            String unit = mV1.group(3);
            System.out.println("用法用量=》频次：" + timesPerDay+"，数量："+pillsPerTime+"，单位："+ unit);
            return new DosageSpec(timesPerDay,pillsPerTime,unit);
        }
//        String patternV2 = "\\s*日(\\d+)\\s*(，| |,|)\\s*次(\\d+(\\.\\d+)?)(ml|g|cl|mg|片|瓶|支|板|粒|片/盒|S|袋|IU|包|克|喷)";
//        Pattern rV2 = Pattern.compile(patternV2);
//        Matcher mV2 = rV2.matcher(input);
//        if (mV2.find()) {
//            Integer timesPerDay = Integer.parseInt(mV2.group(1));
//            double pillsPerTime = Double.parseDouble(mV2.group(3));
//            String unit = mV2.group(5);
//            System.out.println("次数：" + timesPerDay);
//            System.out.println("片数：" + pillsPerTime);
//            System.out.println("单位：" + unit);
//            return new DosageSpec(timesPerDay,pillsPerTime,unit);
//        }
        throw new Exception("药品用法用量格式无效！");
    }

    public static void main(String[] args) throws Exception{
        String input= "口服 每次10IU 一日2次";
        String patternV2 = "\\s*次(\\d+(\\.\\d+)?)(ml|g|cl|mg|片|瓶|支|板|粒|片/盒|S|袋|IU|包|克|喷|毫克|丸|枚|s|贴|揿|ug|单位|钦)[\\s\\S]*(\\d+)次";
        Pattern rV2 = Pattern.compile(patternV2);
        Matcher mV2 = rV2.matcher(input);
        if (mV2.find()) {
            Integer timesPerDay = Integer.parseInt(mV2.group(4));
            double pillsPerTime = Double.parseDouble(mV2.group(1));
            String unit = mV2.group(3);
            System.out.println("次数：" + timesPerDay);
            System.out.println("片数：" + pillsPerTime);
            System.out.println("单位：" + unit);
        } else {
            throw new Exception("药品用法用量格式无效！");
        }
    }


}


