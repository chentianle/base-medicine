package cn.huanzi.qch.baseadmin.wechat.wechatuser.pojo;

import lombok.Data;

@Data
public class WxSharingReceiversVO {

    /**
     * 分账接收方类型
     */
    private String type;

    /**
     * 分账接收方帐号
     */
    private String account;

    /**
     * 分账金额
     */
    private Integer amount;

    /**
     * 分账描述
     */
    private String description;
}
