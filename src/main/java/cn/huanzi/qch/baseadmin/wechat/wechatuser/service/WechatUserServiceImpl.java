package cn.huanzi.qch.baseadmin.wechat.wechatuser.service;

import cn.huanzi.qch.baseadmin.common.pojo.PageInfo;
import cn.huanzi.qch.baseadmin.common.pojo.Result;
import cn.huanzi.qch.baseadmin.common.service.*;
import cn.huanzi.qch.baseadmin.sys.sysuser.pojo.SysUser;
import cn.huanzi.qch.baseadmin.sys.sysuser.vo.SysUserVo;
import cn.huanzi.qch.baseadmin.util.SqlUtil;
import cn.huanzi.qch.baseadmin.wechat.wechatuser.pojo.WechatUser;
import cn.huanzi.qch.baseadmin.wechat.wechatuser.vo.WechatUserVo;
import cn.huanzi.qch.baseadmin.wechat.wechatuser.repository.WechatUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Service
@Transactional
public class WechatUserServiceImpl extends CommonServiceImpl<WechatUserVo, WechatUser, String> implements WechatUserService{

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private WechatUserRepository wechatUserRepository;


    @Override
    public void saveOrUpdate(WechatUserVo wechartUserVo) {
        //查询openid是否存在
        WechatUserVo queryVo = new WechatUserVo();
        queryVo.setOpenId(wechartUserVo.getOpenId());
        Result<List<WechatUserVo>> userList = super.list(queryVo);
        if(userList.getData()!=null && userList.getData().size()>0){
            wechartUserVo.setId(userList.getData().get(0).getId());
        }
        super.save(wechartUserVo);
    }

    @Override
    public Result<List<WechatUserVo>> getUserInfo(WechatUserVo wechatUserVo) {
        return super.list(wechatUserVo);
    }


    @Override
    public Result<PageInfo<WechatUserVo>> page(WechatUserVo entityVo) {
        //根据实体、Vo直接拼接全部SQL
        StringBuilder sql = SqlUtil.joinSqlByEntityAndVo(WechatUser.class,entityVo);

        //设置SQL、映射实体，以及设置值，返回一个Query对象
        Query query = em.createNativeQuery(sql.toString(), WechatUser.class);

        //分页设置，page从0开始
        PageRequest pageRequest = PageRequest.of(entityVo.getPage() - 1, entityVo.getRows());

        //获取最终分页结果
        Result<PageInfo<WechatUserVo>> result = Result.of(PageInfo.of(PageInfo.getJPAPage(query,pageRequest,em), WechatUserVo.class));
        return result;
    }





}
