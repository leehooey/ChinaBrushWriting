package com.chinabrushwriting.lee.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 将图片缓存到本地
 * Created by Lee on 2016/8/16.
 */
public class LocalCacheUtils {
    /**
     * 将图片写入本地缓存
     *
     * @param key
     * @param bitmap
     */
    public void writeBitmap(String key, Bitmap bitmap) {

        File path = UIUtils.getContext().getFilesDir();
        File file = new File(path, "爱书法");
        if (!file.exists()) {

            file.mkdirs();
        }

        key = MD5Utils.encode(key);

        File f = new File(file, key);
        try {
            f.createNewFile();

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(f));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 读位图
     *
     * @param key
     * @return
     */
    public Bitmap readBitmap(String key) {

        File path = UIUtils.getContext().getFilesDir();
        File file = new File(path, "爱书法");
        if (!file.exists()) {
            file.mkdirs();
        }
        key = MD5Utils.encode(key);

        File f = new File(file, key);
        if (!f.exists()) {
            return null;
        }
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(f));
            return bitmap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
