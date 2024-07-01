package cn.huanzi.qch.baseadmin.util.wx;
import jodd.util.ResourcesUtil;
import org.apache.commons.lang3.RegExUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;

public class WxCertHttpUtil {

    private static int socketTimeout = 10000;// 连接超时时间，默认10秒
    private static int connectTimeout = 30000;// 传输超时时间，默认30秒
    private static RequestConfig requestConfig;// 请求配置
    private static CloseableHttpClient httpClient;// HTTP请求

    /**
     *
     * @param url API地址
     * @param xmlObj 要提交的XML
     * @param mchId 服务商商户ID
     * @param certPath证书路径
     * @return
     */
    public static String postData(String url, String xmlObj, String mchId, String certPath) {
        // 加载证书
        try {
            loadCert(mchId, certPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String result = null;
        HttpPost httpPost = new HttpPost(url);
        StringEntity postEntity = new StringEntity(xmlObj, "UTF-8");
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.setEntity(postEntity);
        requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();

        httpPost.setConfig(requestConfig);
        try {
            HttpResponse response = null;
            try {
                response = httpClient.execute(httpPost);
            } catch (IOException e) {
                e.printStackTrace();
            }
            HttpEntity entity = response.getEntity();
            try {
                result = EntityUtils.toString(entity, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } finally {
            httpPost.abort();
        }
        return result;
    }

    /**
     *加载证书
     *
     * @param mchId 服务商商户ID
     * @param certPath 证书路径
     * @throws Exception
     */
    private static void loadCert(String mchId, String certPath) throws Exception {
        // 证书密码，默认为服务商商戶ID
        String key = mchId;
        // 证书路径
        String path = RegExUtils.removeFirst(certPath, "classpath:");
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        // 指定证书格式为PKCS12
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        // 读取PKCS12证书文件
        InputStream instream = ResourcesUtil.getResourceAsStream(path);
        try {
            // 指定PKCS12的密碼(商戶ID)
            keyStore.load(instream, key.toCharArray());
        } finally {
            instream.close();
        }
        SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, key.toCharArray()).build();
        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslcontext, NoopHostnameVerifier.INSTANCE);
        httpClient = HttpClients.custom().setSSLSocketFactory(socketFactory).build();
    }
}
