package cn.huanzi.qch.baseadmin.wechat.wechatpayorderuser.service;

import cn.huanzi.qch.baseadmin.common.service.*;
import cn.huanzi.qch.baseadmin.wechat.wechatpayorderuser.pojo.WechatPayOrderUser;
import cn.huanzi.qch.baseadmin.wechat.wechatpayorderuser.vo.WechatPayOrderUserVo;
import cn.huanzi.qch.baseadmin.wechat.wechatpayorderuser.repository.WechatPayOrderUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
@Transactional
public class WechatPayOrderUserServiceImpl extends CommonServiceImpl<WechatPayOrderUserVo, WechatPayOrderUser, String> implements WechatPayOrderUserService{

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private WechatPayOrderUserRepository wechatPayOrderUserRepository;
}
