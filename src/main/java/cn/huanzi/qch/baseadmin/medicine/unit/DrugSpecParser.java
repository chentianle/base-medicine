package cn.huanzi.qch.baseadmin.medicine.unit;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DrugSpecParser {
    private static final Pattern DRUG_SPEC_PATTERN =
            Pattern.compile("^[\\u4e00-\\u9fa5a-zA-Z]*(\\d+(\\.\\d+)?)\\s*(ml|g|cl|mg|片|瓶|支|板|粒|片/盒|S|袋|IU|iu|克|丸|毫克|毫升|丸|枚|s|包|贴|揿|ug|单位|喷|钦|揿|吸|张|U|μg|万|PNA|泡|块|盒)?[\\s\\S]*");

    public static DrugSpec parse(String spec) {
        Matcher matcher = DRUG_SPEC_PATTERN.matcher(spec.trim());
        if (matcher.matches()) {
            double quantity = Double.parseDouble(matcher.group(1));
            String unit = matcher.group(3); // Group 3 corresponds to the unit capture group
            return new DrugSpec(quantity, unit);
        }
        return null;
    }

    public List<DrugSpec> drugSpecList(String drug_specification) {
        try {
//            String drug_specification = "5mg*15片*2板";
//            String drug_specification = "6.25mg*30片";
            List<DrugSpec> resultList = new ArrayList<>();
            String[] specifications = drug_specification.split("\\*");
            for(String spec : specifications) {
                if(spec.length()>1) {
                    DrugSpec drugSpec = parse(spec);
                    if(drugSpec!=null) {
                        System.out.println("药品规格=>数量: " + drugSpec.getQuantity() + ", 单位: " + drugSpec.getUnit());
                        resultList.add(drugSpec);
                    }
                }
            }
            if(resultList.size()>0) {
                return resultList;
            }else{
                throw new IllegalArgumentException("药品规格格式无效!");
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("药品规格格式无效!");
        }
    }



    public static void main(String[] args) throws Exception{
//        String input= "60mg24片";
//        Pattern sccc =
//                Pattern.compile("^[\\u4e00-\\u9fa5a-zA-Z]*(\\d+(\\.\\d+)?)(ml|g|cl|mg|片|瓶|支|板|粒|片/盒|S|袋|IU|iu|克|丸|毫克|毫升|丸|枚|s|包|贴|揿|ug|单位|吸|张|U|μg|万|PNA|泡)(\\d+(\\.\\d+)?)(ml|g|cl|mg|片|瓶|支|板|粒|片/盒|S|袋|IU|iu|克|丸|毫克|毫升|丸|枚|s|包|贴|揿|ug|单位|吸|张|U|μg|万|PNA|泡)?[\\s\\S]*");
//
//        Matcher matcher = sccc.matcher(input.trim());
//        if (matcher.matches()) {
//            System.out.println("数量：" + matcher.group(1));
//            System.out.println("数量：" + matcher.group(3));
//            System.out.println("数量：" + matcher.group(4));
//            System.out.println("数量：" + matcher.group(6));
//            double quantity = Double.parseDouble(matcher.group(1));
//            String unit = matcher.group(3); // Group 3 corresponds to the unit capture group
//            System.out.println("数量：" + quantity);
//            System.out.println("单位：" + unit);
//        } else {
//            throw new Exception("药品规格格式无效！");
//        }
        String input= "(8+4)片";
//        String input= "his is a sample text (123) with numbers in brack";

        Pattern sccc =
                Pattern.compile("\\((.*?)\\)");
        Matcher matcher = sccc.matcher(input.trim());
        while (matcher.find()){
            System.out.println("算式：" + matcher.group(1));
            String regex = "^[0-9+*\\s]+$";
            boolean isValid = matcher.group(1).matches(regex);
            if (isValid) {
                System.out.println("公式正确");
                ScriptEngineManager manager = new ScriptEngineManager();
                ScriptEngine engine = manager.getEngineByName("JavaScript");
                Object result = engine.eval(matcher.group(1));
                System.out.println("计算结果：" + result);
            } else {
                System.out.println("公式不正确");
            }
        }
    }


    public String guigezhuanhuan(String input){
        Pattern sccc =
                Pattern.compile("^[\\u4e00-\\u9fa5a-zA-Z]*(\\d+(\\.\\d+)?)(ml|g|cl|mg|片|瓶|支|板|粒|片/盒|S|袋|IU|iu|克|丸|毫克|毫升|丸|枚|s|包|贴|揿|ug|单位|吸|张|U|μg|万|PNA|泡)(\\d+(\\.\\d+)?)(ml|g|cl|mg|片|瓶|支|板|粒|片/盒|S|袋|IU|iu|克|丸|毫克|毫升|丸|枚|s|包|贴|揿|ug|单位|吸|张|U|μg|万|PNA|泡|盒)?[\\s\\S]*");
        Matcher matcher = sccc.matcher(input.trim());
        if (matcher.matches()) {
            System.out.println("数量：" + matcher.group(1));
            System.out.println("数量：" + matcher.group(3));
            System.out.println("数量：" + matcher.group(4));
            System.out.println("数量：" + matcher.group(6));
            return  matcher.group(1)+matcher.group(3) +"*" +matcher.group(4)+matcher.group(6);
        } else {
            return input;
        }
    }




    public String kuohaoContent(String input) throws Exception{
        Pattern sccc =
                Pattern.compile("\\((.*?)\\)");
        Matcher matcher = sccc.matcher(input.trim());
        while (matcher.find()){
            System.out.println("算式：" + matcher.group(1));
            String regex = "^[0-9+*\\s]+$";
            boolean isValid = matcher.group(1).matches(regex);
            if (isValid) {
                System.out.println("公式正确");
                ScriptEngineManager manager = new ScriptEngineManager();
                ScriptEngine engine = manager.getEngineByName("JavaScript");
                Object result = engine.eval(matcher.group(1));
                System.out.println("计算结果：" + result);
                return result + input;
            } else {
                System.out.println("公式不正确");
            }
        }
        return input;
    }
}

