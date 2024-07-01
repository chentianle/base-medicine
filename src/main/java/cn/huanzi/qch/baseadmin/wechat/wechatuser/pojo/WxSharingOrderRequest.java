package cn.huanzi.qch.baseadmin.wechat.wechatuser.pojo;


import lombok.Data;

@Data
public class WxSharingOrderRequest {
    /**
     * 服务商商户号
     */
    private String mch_id;

    /**
     * 子商户号
     */
    private String sub_mch_id;

    /**
     * 服务商appid
     */
    private String appid;

    /**
     * 子商户appid
     */
    private String sub_appid;

    /**
     * 随机字符串
     */
    private String nonce_str;

    /**
     * 签名
     */
    private String sign;
    /**
     * 签名类型（只支持HMAC-SHA256）
     */
    private String sign_type;

    /**
     * 微信订单号
     */
    private String transaction_id;

    /**
     * 商家订单号
     */
    private String out_trade_no;

    /**
     * 商户分账单号（同一个单号多次提交只算一次）
     */
    private String out_order_no;

    /**
     * 商户分账金额（小于等于订单金额*（1-手续费）*最大分账比例）
     */
    private Integer amount;

    /**
     * 分账接收方列表（单次分账不能即是支付商户又是接收商户，多次分账没有限制）
     */
    private String receivers;
}
