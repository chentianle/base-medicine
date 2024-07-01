package cn.huanzi.qch.baseadmin.medicine.vo;

import cn.huanzi.qch.baseadmin.common.pojo.PageCondition;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UtilDrugResultVo implements Serializable {

    private Integer days;//可以服用的天数

    private String message;//描述（计算成功）

    private String type;//是否为慢病药品（慢病药品可以开具90天 普通药品7天）

    private CalculateDaysVo calculateDaysVo;

}
