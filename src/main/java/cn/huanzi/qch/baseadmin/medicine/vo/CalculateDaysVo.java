package cn.huanzi.qch.baseadmin.medicine.vo;

import cn.huanzi.qch.baseadmin.common.pojo.PageCondition;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CalculateDaysVo extends PageCondition implements Serializable {

    private String drug_name;//药品名称

    private String unit;//药品单位

    private String specification;//药品规格

    private String dosage;//药品用法用量

    private Integer quantity;//药品购买数量



}
