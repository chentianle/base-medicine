package cn.huanzi.qch.baseadmin.wechat.wechatpayorder.service;

import cn.huanzi.qch.baseadmin.common.service.*;
import cn.huanzi.qch.baseadmin.wechat.wechatpayorder.pojo.WechatPayOrder;
import cn.huanzi.qch.baseadmin.wechat.wechatpayorder.vo.WechatPayOrderVo;
import cn.huanzi.qch.baseadmin.wechat.wechatpayorder.repository.WechatPayOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
@Transactional
public class WechatPayOrderServiceImpl extends CommonServiceImpl<WechatPayOrderVo, WechatPayOrder, String> implements WechatPayOrderService{

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private WechatPayOrderRepository wechatPayOrderRepository;
}
