package cn.huanzi.qch.baseadmin.wechat.wechatuser.controller;

import cn.huanzi.qch.baseadmin.common.controller.*;
import cn.huanzi.qch.baseadmin.common.pojo.Result;
import cn.huanzi.qch.baseadmin.util.HttpService;
import cn.huanzi.qch.baseadmin.wechat.wechatuser.pojo.WechatUser;
import cn.huanzi.qch.baseadmin.wechat.wechatuser.pojo.WxSharingOrderResp;
import cn.huanzi.qch.baseadmin.wechat.wechatuser.service.WechatPayService;
import cn.huanzi.qch.baseadmin.wechat.wechatuser.vo.WechatUserVo;
import cn.huanzi.qch.baseadmin.wechat.wechatuser.service.WechatUserService;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wechart/")
public class WechatUserController extends CommonController<WechatUserVo, WechatUser, String> {

    private static final Logger logger = LoggerFactory.getLogger(WechatUserController.class);


    @Autowired
    private WechatUserService wechatUserService;

    @Autowired
    private WechatPayService wechatPayService;

    @PostMapping("getOpenId")
    public Result<String> shortcutMenuDelete(@RequestBody String code) {
        System.out.println(code);
        Map<String,Object> codeMap = JSONObject.parseObject(code);
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx157e60fb00148cff&secret=97022ad09000b7528a32e94e76d21a71&" +
                "&code="+codeMap.get("code").toString()+"&grant_type=authorization_code";
        Map<String, String> map = new HashMap<>();
        String resultString = HttpService.doGet(url);
        System.out.println("获取微信用户openId="+resultString);
        Map<String,Object> resultMap = JSONObject.parseObject(resultString);
        if(resultMap.get("openid") !=null){
            return Result.of(resultMap.get("openid").toString(),true,"获取成功");
        }else{
            return Result.of(resultString,false,"获取失败");
        }
    }



    @PostMapping("saveUserInfo")
    public Result<String> saveUserInfo(@RequestBody WechatUserVo wechartUserVo) throws Exception{
        logger.info("保存用户信息："+JSONObject.toJSONString(wechartUserVo));
        if(wechartUserVo.getOpenId()!=null && !wechartUserVo.getOpenId().equals("")) {
            wechatUserService.saveOrUpdate(wechartUserVo);
            //添加分账接收方
            //查询是否已添加
            WechatUserVo query = new WechatUserVo();
            query.setOpenId(wechartUserVo.getOpenId());
            Result<List<WechatUserVo>> wechatUserVo = wechatUserService.getUserInfo(query);
            if(wechatUserVo!=null && wechatUserVo.getData()!=null && wechatUserVo.getData().size()>0) {
                if(wechatUserVo.getData().get(0).getIsAddfzjsf() == 1) {
                    wechatPayService.profitsharingaddreceiver(wechartUserVo);
                    wechartUserVo.setIsAddfzjsf(2);
                    wechatUserService.saveOrUpdate(wechartUserVo);
                }
            }
            return Result.of("",true,"保存成功");
        }else{
            return Result.of("",false,"openId不能为空");
        }
    }

    @PostMapping("wxNotice")
    public Result<String> wxNotice(@RequestBody String wechatPayString) {
        logger.info("微信通知："+JSONObject.toJSONString(wechatPayString));
        return Result.of("",true,"获取成功");
    }




    @PostMapping("getUserInfo")
    public Result<WechatUserVo> getUserInfo(@RequestBody String openId) {
        logger.info("获取用户信息："+JSONObject.toJSONString(openId));
        Map<String,Object> codeMap = JSONObject.parseObject(openId);
        WechatUserVo query = new WechatUserVo();
        query.setOpenId(codeMap.get("openId")+"");
        Result<List<WechatUserVo>> wechatUserVo = wechatUserService.getUserInfo(query);

        if(wechatUserVo!=null && wechatUserVo.getData()!=null && wechatUserVo.getData().size()>0) {
            return Result.of(wechatUserVo.getData().get(0), true, "获取成功");
        }else{
            return Result.of(null, false, "获取成功");
        }
    }

}
