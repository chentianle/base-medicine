package cn.huanzi.qch.baseadmin.wechat.wechatpayorderuser.controller;

import cn.huanzi.qch.baseadmin.common.controller.*;
import cn.huanzi.qch.baseadmin.wechat.wechatpayorderuser.pojo.WechatPayOrderUser;
import cn.huanzi.qch.baseadmin.wechat.wechatpayorderuser.vo.WechatPayOrderUserVo;
import cn.huanzi.qch.baseadmin.wechat.wechatpayorderuser.service.WechatPayOrderUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/wechatPayOrderUser/")
public class WechatPayOrderUserController extends CommonController<WechatPayOrderUserVo, WechatPayOrderUser, String> {
    @Autowired
    private WechatPayOrderUserService wechatPayOrderUserService;



    @GetMapping("list")
    public ModelAndView list() {
        return new ModelAndView("wechat/order/list", "list", null);
    }
}
