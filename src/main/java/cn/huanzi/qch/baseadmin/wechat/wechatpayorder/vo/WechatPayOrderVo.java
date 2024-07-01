package cn.huanzi.qch.baseadmin.wechat.wechatpayorder.vo;

import cn.huanzi.qch.baseadmin. common.pojo.PageCondition;import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
public class WechatPayOrderVo extends PageCondition implements Serializable {
    private String id;//id

    private String orderNo;//order_no

    private String orderName;//订单名

    private Integer orderStatus;//是否验证通过，1：否，2：是

    private Integer payStatus;//是否支付成功，1：否，2：是

    private String payOrderNo;//商户支付订单编号

    private String payWxOrderNo;//微信支付订单编号

    private Double payWxOrderAmount;//支付金额

    private Integer separateStatus;//是否支付成功，1：否，2：是

    private String separateWxOrderNo;//微信分账订单编号

    private Date createTime;//创建时间

    private Date updateTime;//修改时间

    private Date payOrderTime;//支付时间

    private Date separateTime;//分账时间

}
