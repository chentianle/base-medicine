package cn.huanzi.qch.baseadmin.wechat.wechatuser.pojo;


import lombok.Data;

@Data
public class WxPayProfitsharingaddreceiver {

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
    private String 	receiver;


}
