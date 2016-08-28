package com.chinabrushwriting.lee.http.protocol;


import android.text.TextUtils;

import com.chinabrushwriting.lee.http.HttpHelper;
import com.chinabrushwriting.lee.utils.CacheUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 获取图片的地址
 * Created by Lee on 2016/7/31.
 */
public class HomeProtocol {
    private String TAG = "Base";

    /**
     *获取位图地址
     * @param values 请求体
     * @return 汉字对应的图片地址
     */
    public synchronized String  getData(String values) {
        //先从本地获取数据(位图地址)
        String data = getDataFromLocal(values);

        if (TextUtils.isEmpty(data)) {

            //从网络获取数据
            data=getDateFromHttp(values);
        }
        return data;
    }


    /**
     * 从网络请求数据
     *
     * @param values 请求该网址时传递的内容
     * @return 返回请求对象
     */
    private String getDateFromHttp(String values) {
        //以post方式请求网络
        String data = HttpHelper.post(values);
        return data;
    }

    /**
     * 从本地获取数据
     *
     * @param values 文件名
     * @return 文件内容
     */
    private String getDataFromLocal(String values) {
        return CacheUtils.read(values);
    }

}
