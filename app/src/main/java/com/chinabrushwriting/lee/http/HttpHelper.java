package com.chinabrushwriting.lee.http;

import com.chinabrushwriting.lee.utils.CacheUtils;
import com.chinabrushwriting.lee.utils.IOUtils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Lee on 2016/7/31.
 */
public class HttpHelper {
    private static final String TAG = "HttpHelper";
    private static final String URL_WEB = "http://www.52maobizi.com/";

    /**
     * post方式请求网络
     *
     * @param values 请求体
     * @return 网络返回的数据（这里是.html格式） null 请求失败
     */
    public static String post(String values) {
        //根据网址以post方式将请求到的.html文件中的图片地址解析出来

        try {
            //创建Url对象
            URL url = new URL(URL_WEB);
            //创建HttpUrlConnection对象
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //以post请求方式
            connection.setRequestMethod("POST");
            //设置链接超时时间
            connection.setConnectTimeout(8000);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            //固定格式
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", String.valueOf(values.getBytes().length));
            //请求体中请求的内容
            PrintWriter write = new PrintWriter(connection.getOutputStream());
            write.print(values);
            write.flush();
            write.close();
            //请求成功，获取该站返回的数据
            if (connection.getResponseCode() == 200) {
                //将输入流转化成字符串
                String picUrl = getPicUrl(connection);

                //缓存文字对应的地址信息以请求体为key ,以图片地址为值  缓存数据
                CacheUtils.write(values, picUrl);

                return picUrl;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return null;
    }

    /**
     * 获取文字对应的图片地址
     *
     * @param connection
     */
    private static String getPicUrl(HttpURLConnection connection) {
        InputStream is = null;
        ByteArrayOutputStream os = null;
        BufferedReader br = null;
        //获取输入流
        try {
            is = connection.getInputStream(); //获取字节流对象
            InputStreamReader reader = new InputStreamReader(is);     //将字节流转化成字符流
            br = new BufferedReader(reader); //创建缓冲字符流对象，每次读取一行，进行匹配

            String partHtml; //每行的字符串

            //循环读取每行数据
            while ((partHtml = br.readLine()) != null) {
                String picUrl;
                //判断当前行是否包含需要的地址
                if ((picUrl = parseData(partHtml)) != null) {
                    //找到数据,返回图片地址
                    return picUrl;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭流
            IOUtils.closeIO(br);
            //IOUtils.closeIO(os);
        }
        return null;
    }


    public static String parseData(String partHtmlStr) {

        String picUrl = null;
        String reg = "http://www\\.52maobizi\\.com//Res/Images/Temp/\\d{3}/[0-9a-z]{32}\\.PNG";//正则表达式
        Pattern pattern = Pattern.compile(reg);//获取正则对象
        Matcher matcher = pattern.matcher(partHtmlStr);//使用正则对象获取匹配器对象
        if (matcher.find()) {
            picUrl = matcher.group();
            System.out.println("网络获取的数据："+picUrl);
        }
        return picUrl;
    }
}
