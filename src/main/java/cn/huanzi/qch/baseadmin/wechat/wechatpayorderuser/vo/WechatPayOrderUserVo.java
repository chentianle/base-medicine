package cn.huanzi.qch.baseadmin.wechat.wechatpayorderuser.vo;

import cn.huanzi.qch.baseadmin. common.pojo.PageCondition;import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
public class WechatPayOrderUserVo extends PageCondition implements Serializable {
    private String id;//id

    private String payId;//主表id

    private String openId;//openId

    private String userName;//用户名称

    private String telphone;//手机号

    private String idCard;//身份证

    private Integer bindStatus;//是否绑定，1：否，2：是

    private Integer receiveStatus;//是否为分账接收方，1：否，2：是

    private Double separateAmount;//分账金额

    private Date createTime;//创建时间

    private Date updateTime;//修改时间

}
