package cn.huanzi.qch.baseadmin.util;

import java.io.*;
import java.net.*;
import java.util.Map;

public class HttpService {
    public static String sendPost(String url, Map<String, String> map) throws Exception {
        StringBuffer buffer = new StringBuffer(); //用来拼接参数
        StringBuffer result = new StringBuffer(); //用来接受返回值
        //创建URL
        URL httpUrl = new URL(url);
        //建立连接
        URLConnection connection = httpUrl.openConnection();
        connection.setRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        connection.setRequestProperty("connection", "keep-alive");
        connection.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:34.0) Gecko/20100101 Firefox/34.0");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        connection.setDoOutput(true);
        connection.setDoInput(true);
        PrintWriter printWriter = new PrintWriter(connection.getOutputStream());
        String request = "";
        if(map != null && map.size()>0){
            for(String str : map.keySet()){
                buffer.append(str).append("=").append(URLEncoder.encode(map.get(str), "utf-8")).append("&");
            }
            //去掉最后一个&并urlencode
            request = buffer.toString().substring(0,buffer.toString().length() - 1);
        }
        printWriter.print(request);
        printWriter.flush();
        connection.connect();
        //接受连接返回参数
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            result.append(line);
        }
        bufferedReader.close();
        return result.toString();
    }

    public static String sendPost(String url, Map<String, String> map, String body) throws Exception {
        StringBuffer buffer = new StringBuffer(); //用来拼接参数
        StringBuffer result = new StringBuffer(); //用来接受返回值
        String request = "";
        if(map != null && map.size()>0){
            for(String str : map.keySet()){
                buffer.append(str).append("=").append(URLEncoder.encode(map.get(str), "utf-8")).append("&");
            }
            //去掉最后一个&并urlencode
            request = buffer.toString().substring(0,buffer.toString().length() - 1);
        }

        //创建URL
        URL httpUrl = new URL(url + "?" + request);
        //建立连接
        URLConnection connection =  httpUrl.openConnection();
        connection.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
        connection.setDoOutput(true);
        connection.setDoInput(true);

        PrintWriter printWriter = new PrintWriter(connection.getOutputStream());

        printWriter.print(body);
        printWriter.flush();
        connection.connect();
        //接受连接返回参数
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            result.append(line);
        }
        bufferedReader.close();
        return result.toString();
    }


    public static String doGet(String httpurl) {
        HttpURLConnection connection = null;
        InputStream is = null;
        BufferedReader br = null;
        String result = null;// 返回结果字符串
        try {
            // 创建远程url连接对象
            URL url = new URL(httpurl);
            // 通过远程url连接对象打开一个连接，强转成httpURLConnection类
            connection = (HttpURLConnection) url.openConnection();
            // 设置连接方式：get
            connection.setRequestMethod("GET");
            // 设置连接主机服务器的超时时间：15000毫秒
            connection.setConnectTimeout(15000);
            // 设置读取远程返回的数据时间：60000毫秒
            connection.setReadTimeout(60000);
            // 发送请求
            connection.connect();
            // 通过connection连接，获取输入流
            if (connection.getResponseCode() == 200) {
                is = connection.getInputStream();
                // 封装输入流is，并指定字符集
                br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                // 存放数据
                StringBuffer sbf = new StringBuffer();
                String temp = null;
                while ((temp = br.readLine()) != null) {
                    sbf.append(temp);
                    sbf.append("\r\n");
                }
                result = sbf.toString();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            connection.disconnect();// 关闭远程连接
        }

        return result;
    }

}
