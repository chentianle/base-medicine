package cn.huanzi.qch.baseadmin.wechat.wechatuser.vo;

import cn.huanzi.qch.baseadmin.annotation.Like;
import cn.huanzi.qch.baseadmin. common.pojo.PageCondition;import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
public class WechatUserVo extends PageCondition implements Serializable {
    private String id;//id


    private String openId;//openId

    @Like
    private String userName;//用户名称
    @Like
    private String telphone;//手机号
    @Like
    private String idCard;//身份证

    private Date createTime;//创建时间

    private Date updateTime;//修改时间

    private Integer isAddfzjsf;//是否添加至接收方，1：没有，2：已添加

}
