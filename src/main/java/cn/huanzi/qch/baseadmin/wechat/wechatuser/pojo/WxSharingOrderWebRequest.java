package cn.huanzi.qch.baseadmin.wechat.wechatuser.pojo;


import lombok.Data;

import java.util.List;

@Data
public class WxSharingOrderWebRequest {


    /**
     * 微信订单号
     */
    private String transaction_id;

    /**
     * 商家订单号
     */
    private String out_order_no;

    /**
     * 分账接收方列表（单次分账不能即是支付商户又是接收商户，多次分账没有限制）
     */
    private List<WxSharingReceiversWebVo> wxSharingReceiversWebVo;
}
