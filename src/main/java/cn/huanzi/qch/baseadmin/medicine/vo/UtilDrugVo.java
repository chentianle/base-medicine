package cn.huanzi.qch.baseadmin.medicine.vo;
import cn.huanzi.qch.baseadmin.common.pojo.PageCondition;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
public class UtilDrugVo extends PageCondition implements Serializable {
    private Integer drugId;//药品编码

    private Integer isSpecialDrugs;//是否特殊药品不给修改诊断 0-否 1是

    private String brandName;//品牌名

    private String drugName;//药品名称

    private String drugNameComm;//药品通用名称

    private String standardCode;//药品本位码

    private String pyCode;//拼音码

    private String drugSpecification;//药品规格

    private Integer drugType;//药品类型 1西药  2中成药  3中草药  0 未知

    private String packSize;//包装量

    private String packUnit;//包装单位

    private String factory;//生产厂家

    private String approvalNumber;//批准文号

    private String dosaNum;//剂量

    private String dosaUnit;//剂量单位

    private Integer instructionsUseful;//说明书是否有用 0有用 1没用

    private String instructions;//说明书

    private Integer status;//是否有效(1有效0无效)

    private String usageName;//药品用法

    private String freqTimes;//使用频次数量

    private String freqUnit;//使用频次单位

    private Date createTime;//创建时间

    private String createBy;//创建人

    private Date updateTime;//修改时间

    private String updateBy;//修改人

}
