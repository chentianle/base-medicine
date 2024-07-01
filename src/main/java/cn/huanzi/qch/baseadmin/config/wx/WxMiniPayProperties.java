package cn.huanzi.qch.baseadmin.config.wx;


import antlr.StringUtils;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "wx.mini")
public class WxMiniPayProperties {
    /**
     * apiclient_cert.p12文件的绝对路径，或者如果放在项目中，请以classpath:开头指定
     */
    private String keyPath;

    /**
     * 服务商appid
     */
    private String busAppId;

    /**
     *服务商商户号
     */
    private String busMchId;

    /**
     * 服务商商户密钥
     */
    private String busMchKey;

    /**
     * 调起支付的小程序APPID
     */
    private String busSubAppId;

    /**
     * 微信支付子商户号
     */
    private String busSubMchId;


    @Bean(name = "wxBusMiniPayService")
    public WxPayService wxBusMiniPayService() {
        WxPayConfig payConfig = new WxPayConfig();
        payConfig.setAppId(this.busAppId);
        payConfig.setMchId(this.busMchId);
        payConfig.setMchKey(this.busMchKey);
        payConfig.setSubAppId(this.busSubAppId);
        payConfig.setSubMchId(this.busSubMchId);
        payConfig.setKeyPath(this.keyPath);

        // 可以指定是否使用沙箱环境
        payConfig.setUseSandboxEnv(false);

        WxPayService wxPayService = new WxPayServiceImpl();
        wxPayService.setConfig(payConfig);
        return wxPayService;
    }
}
