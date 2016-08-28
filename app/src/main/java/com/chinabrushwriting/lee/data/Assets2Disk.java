package com.chinabrushwriting.lee.data;

import com.chinabrushwriting.lee.utils.UIUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 将数据从assets存放到本地
 * Created by Lee on 2016/7/21.
 */
public class Assets2Disk {

    /**
     * @param name assets文件中的文件名
     * @param path 本地文件路径
     */
    public static void a2D(String name, File path) {
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            //打开assets文件下的输入流
            is = UIUtils.getContext().getAssets().open("info.db");
            //将输入流中的数据读入本地
            fos = new FileOutputStream(path);
            byte[] bytes = new byte[1024];
            int temp;
            while ((temp = is.read(bytes)) != -1) {
                fos.write(bytes, 0, temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null && fos != null) {
                try {
                    is.close();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
