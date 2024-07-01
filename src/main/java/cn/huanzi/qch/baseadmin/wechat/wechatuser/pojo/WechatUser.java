package cn.huanzi.qch.baseadmin.wechat.wechatuser.pojo;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "wechat_user")
@Data
public class WechatUser implements Serializable {
    @Id
    private String id;//id

    private String openId;//openId

    private String userName;//用户名称

    private String telphone;//手机号

    private String idCard;//身份证

    private Date createTime;//创建时间

    private Date updateTime;//修改时间

    private Integer isAddfzjsf;//是否添加至接收方，1：没有，2：已添加
}
