package cn.huanzi.qch.baseadmin.wechat.wechatuser.repository;

import cn.huanzi.qch.baseadmin.common.repository.*;
import cn.huanzi.qch.baseadmin.wechat.wechatuser.pojo.WechatUser;
import org.springframework.stereotype.Repository;

@Repository
public interface WechatUserRepository extends CommonRepository<WechatUser, String> {
}
