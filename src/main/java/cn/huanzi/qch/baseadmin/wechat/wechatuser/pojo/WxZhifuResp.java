package cn.huanzi.qch.baseadmin.wechat.wechatuser.pojo;


import lombok.Data;

@Data
public class WxZhifuResp {

    //返回状态码，通信标识，SUCCESS/FAIL
    private String return_code;
    //返回信息,通信标识OK
    private String return_msg;
    //业务结果，交易标识，SUCCESS/FAIL
    private String result_code;
    //商户号
    private String mch_id;
    //公众账号id
    private String appid;
    //随机字符串
    private String nonce_str;
    //签名
    private String sign;

    private String prepay_id;

    private String trade_type;

    private String code_url;
    //错误代码
    private String err_code;
    //错误代码描述
    private String err_code_des;

    private String out_trade_no;
}
