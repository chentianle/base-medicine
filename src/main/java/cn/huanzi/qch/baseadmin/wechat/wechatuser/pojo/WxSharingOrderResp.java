package cn.huanzi.qch.baseadmin.wechat.wechatuser.pojo;


import lombok.Data;

@Data
public class WxSharingOrderResp {

    //返回状态码，通信标识，SUCCESS/FAIL
    private String return_code;
    //返回信息,通信标识OK
    private String return_msg;
    //业务结果，交易标识，SUCCESS/FAIL
    private String result_code;
    //错误代码
    private String err_code;
    //错误代码描述
    private String err_code_des;
    //商户号
    private String mch_id;
    //子商户号
    private String sub_mch_id;
    //公众账号id
    private String appid;

    private String sub_appid;

    //随机字符串
    private String nonce_str;
    //签名
    private String sign;
    //微信支付订单号
    private String transaction_id;
    //商户分账单号（商户订单号）
    private String out_order_no;
    //商户分账单号
    private String order_id;
}
