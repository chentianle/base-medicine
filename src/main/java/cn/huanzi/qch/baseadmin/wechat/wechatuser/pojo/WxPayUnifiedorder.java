package cn.huanzi.qch.baseadmin.wechat.wechatuser.pojo;


import lombok.Data;

@Data
public class WxPayUnifiedorder {

    /**
     * 公众号appid
     */
    private String appid;
    /**
     * 服务商商户号
     */
    private String mch_id;

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
     * 商品描述
     */
    private String 	body;

    /**
     * 商家订单号
     */
    private String out_trade_no;

    /**
     * 标价金额
     */
    private Integer total_fee;

    /**
     * 终端IP
     */
    private String spbill_create_ip;


    /**
     * 通知地址
     */
    private String notify_url;

    /**
     * 交易类型  JSAPI
     */
    private String trade_type;
    /**
     * 支付用户openid
     */
    private String openid;
    /**
     * 	Y-是，需要分账
     */
    private String profit_sharing;




}
