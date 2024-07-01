package cn.huanzi.qch.baseadmin.wechat.wechatuser.pojo;

import lombok.Data;

@Data
public class WxSharingReceiversJsVO {

    /**
     * 分账接收方类型
     */
    private String type;

    /**
     * 分账接收方帐号
     */
    private String account;

    /**
     * 名字
     */
    private String name;
    /**
     * 与分账方的关系类型 STAFF
     */
    private String relation_type;
}
