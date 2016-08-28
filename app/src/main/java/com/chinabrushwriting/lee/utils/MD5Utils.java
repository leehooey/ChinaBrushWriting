package com.chinabrushwriting.lee.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Lee on 2016/7/31.
 */
public class MD5Utils {
    /**
     * 将字符串进行加密
     *
     * @param url 加密前的数据
     * @return 加密后的数据
     */
    public static String encode(String url) {
        url += "com.chinabrushwriting.lee"; //进行加盐处理，增加破解难度
        try {
            //指定加密算法类型
            MessageDigest digest = MessageDigest.getInstance("MD5");
            //将加密字符串转化成byte数组,进行随机哈希
            byte[] bytes = digest.digest(url.getBytes());

            StringBuffer sb = new StringBuffer();
            //遍历数组使其生成32位字符串
            for (byte b : bytes) {
                int i = b & 0xff;
                //转化成16进制
                String str = Integer.toHexString(i);
                if (str.length() < 2) {
                    //补齐位数
                    str = "0" + str;
                }
                sb.append(str);
            }
            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }
}
