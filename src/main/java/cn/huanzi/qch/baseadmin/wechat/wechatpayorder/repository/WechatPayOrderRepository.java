package cn.huanzi.qch.baseadmin.wechat.wechatpayorder.repository;

import cn.huanzi.qch.baseadmin.common.repository.*;
import cn.huanzi.qch.baseadmin.wechat.wechatpayorder.pojo.WechatPayOrder;
import org.springframework.stereotype.Repository;

@Repository
public interface WechatPayOrderRepository extends CommonRepository<WechatPayOrder, String> {
}
