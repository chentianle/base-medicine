package cn.huanzi.qch.baseadmin.wechat.wechatuser.service;

import cn.huanzi.qch.baseadmin.common.pojo.Result;
import cn.huanzi.qch.baseadmin.common.service.*;
import cn.huanzi.qch.baseadmin.wechat.wechatuser.pojo.WechatUser;
import cn.huanzi.qch.baseadmin.wechat.wechatuser.vo.WechatUserVo;

import java.util.List;

public interface WechatUserService extends CommonService<WechatUserVo, WechatUser, String> {

    void saveOrUpdate(WechatUserVo wechartUserVo);


    Result<List<WechatUserVo>> getUserInfo(WechatUserVo query);
}
