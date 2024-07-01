package cn.huanzi.qch.baseadmin.wechat.wechatpayorder.controller;

import cn.huanzi.qch.baseadmin.common.controller.*;
import cn.huanzi.qch.baseadmin.wechat.wechatpayorder.pojo.WechatPayOrder;
import cn.huanzi.qch.baseadmin.wechat.wechatpayorder.vo.WechatPayOrderVo;
import cn.huanzi.qch.baseadmin.wechat.wechatpayorder.service.WechatPayOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sys/wechatPayOrder/")
public class WechatPayOrderController extends CommonController<WechatPayOrderVo, WechatPayOrder, String> {
    @Autowired
    private WechatPayOrderService wechatPayOrderService;
}
