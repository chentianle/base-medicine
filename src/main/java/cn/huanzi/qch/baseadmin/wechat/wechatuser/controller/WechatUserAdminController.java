package cn.huanzi.qch.baseadmin.wechat.wechatuser.controller;

import cn.huanzi.qch.baseadmin.common.controller.CommonController;
import cn.huanzi.qch.baseadmin.common.pojo.Result;
import cn.huanzi.qch.baseadmin.sys.sysuser.service.SysUserService;
import cn.huanzi.qch.baseadmin.sys.sysuser.vo.SysUserVo;
import cn.huanzi.qch.baseadmin.util.SecurityUtil;
import cn.huanzi.qch.baseadmin.wechat.wechatuser.pojo.*;
import cn.huanzi.qch.baseadmin.wechat.wechatuser.service.WechatPayService;
import cn.huanzi.qch.baseadmin.wechat.wechatuser.service.WechatUserService;
import cn.huanzi.qch.baseadmin.wechat.wechatuser.vo.WechatUserVo;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/wechartAdmin/")
public class WechatUserAdminController extends CommonController<WechatUserVo, WechatUser, String> {

    private static final Logger logger = LoggerFactory.getLogger(WechatUserAdminController.class);


    @Autowired
    private WechatUserService wechatUserService;

    @Autowired
    private WechatPayService wechatPayService;

    @Autowired
    private SysUserService sysUserService;

    @GetMapping("list")
    public ModelAndView list() {
        SysUserVo sysUserVo = sysUserService.findByLoginName(SecurityUtil.getLoginUser().getUsername()).getData();
        sysUserVo.setPassword(null);
        return new ModelAndView("wechat/user/list", "list", sysUserVo);
    }

    /**
     * @Description 支付接口
     * @Author  索超超
     * @Date   2021/5/8 11:03
     * @Param
     * @Return
     * @Exception
     */
    @PostMapping("payUnifiedorder")
    public Result<WxZhifuResp> payUnifiedorder(@RequestBody Double total_fee) throws Exception{
        WxZhifuRequest wxZhifuRequest = new WxZhifuRequest();
        Integer total_fee_integer = Integer.parseInt(String.valueOf(total_fee * 100));
        wxZhifuRequest.setTotal_fee(total_fee_integer);
        WxZhifuResp resp = wechatPayService.payUnifiedorder(wxZhifuRequest);
        logger.info("支付返回"+JSONObject.toJSONString(resp));
        return Result.of(resp, true, "请求后台接口成功");
    }


    /**
    * @Description 分账接口
    * @Author  索超超
    * @Date   2021/5/8 11:03
    * @Param
    * @Return
    * @Exception
    */
    @PostMapping("oncePaySharing")
    public Result<WxSharingOrderResp> oncePaySharing(@RequestBody WxSharingOrderWebRequest wxSharingOrderRequest) throws Exception{
//        WxSharingOrderRequest wxSharingOrderRequest = new WxSharingOrderRequest();
//        wxSharingOrderRequest.setTransaction_id("4200001045202105088260224341");
//        wxSharingOrderRequest.setOut_order_no("20210508121223237");
        WxSharingOrderResp resp = wechatPayService.oncePaySharing(wxSharingOrderRequest);
        logger.info("分账返回"+JSONObject.toJSONString(resp));
        return Result.of(resp, true, "请求后台接口成功");
    }


    /**
    * @Description 添加分账接收方
    * @Author  索超超
    * @Date   2021/5/8 11:03
    * @Param
    * @Return
    * @Exception
    */
    @PostMapping("profitsharingaddreceiver")
    public void profitsharingaddreceiver() throws Exception{
        WechatUserVo wechartUserVo = new WechatUserVo();
        wechatPayService.profitsharingaddreceiver(wechartUserVo);
    }

}
