package com.chinabrushwriting.lee.utils;

import android.graphics.Bitmap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Lee on 2016/7/31.
 */
public class CacheUtils {
    /**
     * 读缓存
     * @param values
     * @return
     */
    public static String read(String values){
        //先将url md5进行加密
        values = MD5Utils.encode(values);
        //获取信息缓存路径
        File files = UIUtils.getContext().getCacheDir();
        //获取与str对应的文件
        File file = new File(files, values);
        //判断缓存文件夹中是否存在该数据
        if (!file.exists()) {
            return null;
        }
        BufferedReader br = null;
        try {
            //读取文件中的内容
            StringBuffer sb = new StringBuffer();
            //获取输入流
            br = new BufferedReader(new FileReader(file));
            String line;
            //读文件中内容
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭流
            IOUtils.closeIO(br);
        }
        return null;
    }
    /**
     * 写缓存
     * @param values 文件名
     * @param data 内容
     */
    public static void write(String values,String data){
        //写缓存
        values = MD5Utils.encode(values);
        //获取将要写入文件路径
        File files = UIUtils.getContext().getCacheDir();
        //创建将要写入的文件对象
        File file = new File(files, values);
        //如果不存在创建该文件
        FileWriter fwrite = null;
        try {
            fwrite=new FileWriter(file);
            //将子类解析的数据保存到文件
            fwrite.write(data);
            fwrite.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            IOUtils.closeIO(fwrite);
        }
    }



}
