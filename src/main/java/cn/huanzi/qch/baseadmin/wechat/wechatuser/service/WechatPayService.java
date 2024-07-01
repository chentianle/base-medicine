package cn.huanzi.qch.baseadmin.wechat.wechatuser.service;

import cn.huanzi.qch.baseadmin.util.wx.WxCertHttpUtil;
import cn.huanzi.qch.baseadmin.util.wx.WxUtils;
import cn.huanzi.qch.baseadmin.wechat.wechatuser.controller.WechatUserController;
import cn.huanzi.qch.baseadmin.wechat.wechatuser.pojo.*;
import cn.huanzi.qch.baseadmin.wechat.wechatuser.vo.WechatUserVo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.binarywang.wxpay.service.WxPayService;
import com.google.common.collect.Lists;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class WechatPayService {


    @Resource(name = "wxBusMiniPayService")
    private WxPayService wxBusMiniPayService;

    private static final Logger logger = LoggerFactory.getLogger(WechatPayService.class);

    /**
     * 单次分账
     * @param data
     * @return
     */
    public WxSharingOrderResp oncePaySharing(WxSharingOrderWebRequest data) throws Exception{
        WxSharingOrderResp resp = new WxSharingOrderResp();
        WxSharingOrderRequest wxSharingOrderRequest = new WxSharingOrderRequest();
        wxSharingOrderRequest.setAppid(wxBusMiniPayService.getConfig().getAppId());
        wxSharingOrderRequest.setMch_id(wxBusMiniPayService.getConfig().getMchId());
        wxSharingOrderRequest.setTransaction_id(data.getTransaction_id());
        wxSharingOrderRequest.setNonce_str(WxUtils.makeNonStr());
        wxSharingOrderRequest.setOut_order_no(data.getOut_order_no());
        List<WxSharingReceiversVO> list = Lists.newArrayList();
        if(data.getWxSharingReceiversWebVo()!=null && data.getWxSharingReceiversWebVo().size()>0){
            for(WxSharingReceiversWebVo jsfVo : data.getWxSharingReceiversWebVo()){
                WxSharingReceiversVO receiversVO = new WxSharingReceiversVO();
                receiversVO.setAccount(jsfVo.getAccount());
                receiversVO.setType("PERSONAL_OPENID");
                receiversVO.setAmount(Integer.parseInt(String.valueOf(jsfVo.getAmount()*100)));
                receiversVO.setDescription("分到个人");
                list.add(receiversVO);
            }
        }else{
            resp.setReturn_code("FAIL");
            resp.setReturn_msg("分账接收方不能为空");
            return resp;
        }
        wxSharingOrderRequest.setReceivers(JSON.toJSONString(list));
        BeanMap beanMap = BeanMap.create(wxSharingOrderRequest);
        wxSharingOrderRequest.setSign(WxUtils.makeSign( beanMap ,wxBusMiniPayService.getConfig().getMchKey(),"SHA256"));
        String xmlStr = WxUtils.truncateDataToXML(WxSharingOrderRequest.class, wxSharingOrderRequest).replace("&quot;","\"");
        System.out.print(xmlStr);
        String url = "https://api.mch.weixin.qq.com/secapi/pay/profitsharing";
        String result = WxCertHttpUtil.postData(url,xmlStr,wxBusMiniPayService.getConfig().getMchId(),wxBusMiniPayService.getConfig().getKeyPath());
        System.out.println("单次分账接口调用返回："+result);
        Object obj = WxUtils.truncateDataFromXML(WxSharingOrderResp.class, result);
        BeanUtils.copyProperties(resp,obj);
        return resp;

    }

    /**
     * 多次分账
     * @param data
     * @return
     */
    public WxSharingOrderResp multiPaySharing(WxSharingOrderRequest data)throws Exception{
        WxSharingOrderRequest wxSharingOrderRequest = new WxSharingOrderRequest();
        wxSharingOrderRequest.setAppid(wxBusMiniPayService.getConfig().getAppId());
        wxSharingOrderRequest.setMch_id(wxBusMiniPayService.getConfig().getMchId());
        wxSharingOrderRequest.setSub_mch_id(wxBusMiniPayService.getConfig().getSubMchId());
        wxSharingOrderRequest.setSub_appid(wxBusMiniPayService.getConfig().getSubAppId());
        wxSharingOrderRequest.setTransaction_id(data.getTransaction_id());
        wxSharingOrderRequest.setNonce_str(WxUtils.makeNonStr());
        wxSharingOrderRequest.setOut_order_no(data.getOut_order_no());
        List<WxSharingReceiversVO> list = Lists.newArrayList();
        WxSharingReceiversVO receiversVO = new WxSharingReceiversVO();
        receiversVO.setAccount(wxBusMiniPayService.getConfig().getSubMchId());
        receiversVO.setType("MERCHANT_ID");
        receiversVO.setAmount(data.getAmount());
        receiversVO.setDescription("给商家分账");
        list.add(receiversVO);
        wxSharingOrderRequest.setReceivers(JSON.toJSONString(list));

        BeanMap beanMap = BeanMap.create(wxSharingOrderRequest);
        wxSharingOrderRequest.setSign(WxUtils.makeSign(beanMap,wxBusMiniPayService.getConfig().getMchKey(),"SHA256"));
        String xmlStr = WxUtils.truncateDataToXML(WxSharingOrderRequest.class, wxSharingOrderRequest).replace("&quot;","\"");
        String url = "https://api.mch.weixin.qq.com/secapi/pay/multiprofitsharing";

        String result = WxCertHttpUtil.postData(url,xmlStr,wxBusMiniPayService.getConfig().getMchId(),wxBusMiniPayService.getConfig().getKeyPath());
        Object obj = WxUtils.truncateDataFromXML(WxSharingOrderResp.class, result);
        WxSharingOrderResp resp = new WxSharingOrderResp();
        BeanUtils.copyProperties(obj,resp);
        return resp;

    }


    /**
    * @Description 支付
    * @Author  索超超
    * @Date   2021/5/8 11:06
    * @Param
    * @Return
    * @Exception
    */
    public WxZhifuResp payUnifiedorder(WxZhifuRequest wxZhifuRequest) throws Exception {
        WxPayUnifiedorder wxPayUnifiedorder = new WxPayUnifiedorder();
        wxPayUnifiedorder.setAppid(wxBusMiniPayService.getConfig().getAppId());
        wxPayUnifiedorder.setMch_id(wxBusMiniPayService.getConfig().getMchId());
        wxPayUnifiedorder.setSign_type("HMAC-SHA256");
        wxPayUnifiedorder.setProfit_sharing("Y");
        wxPayUnifiedorder.setNonce_str(WxUtils.makeNonStr());
        wxPayUnifiedorder.setBody("扫码支付");
        SimpleDateFormat sdf1=new SimpleDateFormat("yyyyMMddHHmmssSS");//显示20171027格式
        String out_trade_no = sdf1.format(new Date());
        System.out.println("outTradeNo="+out_trade_no);
        wxPayUnifiedorder.setOut_trade_no(out_trade_no);
        wxPayUnifiedorder.setTotal_fee(wxZhifuRequest.getTotal_fee());
        wxPayUnifiedorder.setSpbill_create_ip("1.202.240.210");
//        wxPayUnifiedorder.setSpbill_create_ip("49.233.133.233");
        wxPayUnifiedorder.setNotify_url("http://49.233.133.233:8888/wechart/wxNotice");
        wxPayUnifiedorder.setTrade_type("NATIVE");
        BeanMap beanMap = BeanMap.create(wxPayUnifiedorder);
        wxPayUnifiedorder.setSign(WxUtils.makeSign( beanMap ,wxBusMiniPayService.getConfig().getMchKey(),"SHA256"));
        String xmlStr = WxUtils.truncateDataToXML(WxSharingOrderRequest.class, wxPayUnifiedorder).replace("&quot;","\"");
        System.out.print(xmlStr);
        String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
        String result = WxCertHttpUtil.postData(url,xmlStr,wxBusMiniPayService.getConfig().getMchId(),wxBusMiniPayService.getConfig().getKeyPath());
        System.out.println("支付接口回调："+result);
        Object obj = WxUtils.truncateDataFromXML(WxZhifuResp.class, result);
        WxZhifuResp resp = new WxZhifuResp();
        BeanUtils.copyProperties(resp,obj);
        resp.setOut_trade_no(out_trade_no);
        logger.info(JSONObject.toJSON(resp).toString());
        return resp;
    }

    public void profitsharingaddreceiver(WechatUserVo wechartUserVo) throws Exception{

        WxPayProfitsharingaddreceiver wxPayUnifiedorder = new WxPayProfitsharingaddreceiver();
        wxPayUnifiedorder.setAppid(wxBusMiniPayService.getConfig().getAppId());
        wxPayUnifiedorder.setMch_id(wxBusMiniPayService.getConfig().getMchId());
        wxPayUnifiedorder.setSign_type("HMAC-SHA256");
        wxPayUnifiedorder.setNonce_str(WxUtils.makeNonStr());
        List<WxSharingReceiversJsVO> list = Lists.newArrayList();
        WxSharingReceiversJsVO receiversVO = new WxSharingReceiversJsVO();
        receiversVO.setAccount(wechartUserVo.getOpenId());
        receiversVO.setType("PERSONAL_OPENID");
        receiversVO.setName(wechartUserVo.getUserName());
        receiversVO.setRelation_type("STAFF");
        list.add(receiversVO);
        wxPayUnifiedorder.setReceiver(JSON.toJSONString(receiversVO));
        BeanMap beanMap = BeanMap.create(wxPayUnifiedorder);
        wxPayUnifiedorder.setSign(WxUtils.makeSign( beanMap ,wxBusMiniPayService.getConfig().getMchKey(),"SHA256"));
        String xmlStr = WxUtils.truncateDataToXML(WxSharingOrderRequest.class, wxPayUnifiedorder).replace("&quot;","\"");
        System.out.print(xmlStr);
        String url = "https://api.mch.weixin.qq.com/pay/profitsharingaddreceiver";
        String result = WxCertHttpUtil.postData(url,xmlStr,wxBusMiniPayService.getConfig().getMchId(),wxBusMiniPayService.getConfig().getKeyPath());
        System.out.println("添加分账接收方接口回调："+result);
    }




    public static void main(String[] args) throws Exception{
        String result = "<xml><return_code><![CDATA[SUCCESS]]></return_code>" +
                "<return_msg><![CDATA[OK]]></return_msg>" +
                "<result_code><![CDATA[SUCCESS]]></result_code>" +
                "<mch_id><![CDATA[1608977382]]></mch_id>" +
                "<appid><![CDATA[wx157e60fb00148cff]]></appid>" +
                "<nonce_str><![CDATA[NQY0gBXmuGv7gCho]]></nonce_str>" +
                "<sign><![CDATA[40F8CE13BBA72A9AEFBFF0D8B4EDBFC9D60B03A11D4828C6B665B8FA9AF14C21]]></sign>" +
                "<prepay_id><![CDATA[wx081147354214831b15dea7ebba58650000]]></prepay_id>" +
                "<trade_type><![CDATA[NATIVE]]></trade_type>" +
                "<code_url><![CDATA[weixin://wxpay/bizpayurl?pr=2NNEh2Dzz]]></code_url>" +
                "</xml>";

        Object obj = WxUtils.truncateDataFromXML(WxZhifuResp.class, result);
        WxZhifuResp resp = new WxZhifuResp();
        BeanUtils.copyProperties(resp,obj);
        System.out.println(JSONObject.toJSON(resp));

    }
}
