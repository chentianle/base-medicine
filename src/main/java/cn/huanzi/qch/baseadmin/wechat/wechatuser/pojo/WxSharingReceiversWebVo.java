package cn.huanzi.qch.baseadmin.wechat.wechatuser.pojo;

import lombok.Data;

@Data
public class WxSharingReceiversWebVo {

    /**
     * 分账接收方帐号
     */
    private String account;

    /**
     * 分账金额
     */
    private Double amount;
}
