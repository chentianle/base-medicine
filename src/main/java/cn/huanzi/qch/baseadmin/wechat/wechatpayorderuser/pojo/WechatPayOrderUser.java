package cn.huanzi.qch.baseadmin.wechat.wechatpayorderuser.pojo;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "wechat_pay_order_user")
@Data
public class WechatPayOrderUser implements Serializable {
    @Id
    private String id;//id

    private String payId;//主表id

    private String openId;//openId

    private String userName;//用户名称

    private String telphone;//手机号

    private String idCard;//身份证

    private Integer bindStatus;//是否绑定，1：否，2：是

    private Integer receiveStatus;//是否为分账接收方，1：否，2：是

    private Double separateAmount;//分账金额

    private Date createTime;//创建时间

    private Date updateTime;//修改时间

}
