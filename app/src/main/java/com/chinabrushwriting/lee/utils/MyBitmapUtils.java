package com.chinabrushwriting.lee.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.chinabrushwriting.lee.R;
import com.chinabrushwriting.lee.ui.view.ChineseImage;


/**
 * 模仿Xutils的BitmapUtils,加载图片
 * Created by Lee on 2016/8/16.
 */
public class MyBitmapUtils {
    private String TAG = "MyBitmapUtils";
    private final NetCacheUtil mNet;
    private final LocalCacheUtils mLocal;
    private final MemoryCacheUtil mMemory;
    private MyBitmapLoadCallBack mCallBack;

    public MyBitmapUtils() {

        mMemory = new MemoryCacheUtil();//内存缓存
        mLocal = new LocalCacheUtils();//本地缓存
        mNet = new NetCacheUtil(this, mLocal, mMemory);//网络缓存
    }


    public void display(View view, String bitmapUrl, MyBitmapLoadCallBack callBack) {
        mCallBack = callBack;

        callBack.prepareLoad();//准备加载

        //判断内存是否有该数据
        Bitmap bitmap = mMemory.read(bitmapUrl);
        if (bitmap != null) {

            setBitmapForView(view, bitmap, bitmapUrl);
            Log.i("cache", "内存缓存");
            return;
        }
        //判断本地sdcard是否含有数据
        bitmap = mLocal.readBitmap(bitmapUrl);
        if (bitmap != null) {

            setBitmapForView(view, bitmap, bitmapUrl);
            Log.i("cache", "本地缓存");

            mMemory.write(bitmapUrl, bitmap);//保存缓存到内存
            return;
        }
        //加载网络数据
        mNet.getBitmapFromNet(view, bitmapUrl);
    }

    //加载失败
    public void loadFailed() {
        mCallBack.failed();
    }

    //加载成功
    public void loadSuccess() {
        mCallBack.success();
    }

    /**
     * 为view设置图片
     *
     * @param view
     * @param bitmap
     * @param url
     */
    public void setBitmapForView(View view, Bitmap bitmap, String url) {
        ImageView imageView = (ImageView) view;
        if (view.getTag(R.attr.tag_second) == url) {
            imageView.setImageBitmap(bitmap);

            loadSuccess();//加载成功
        } else {
            imageView.setImageResource(R.drawable.waite_loading);
        }
    }

    public Bitmap getBitmap(String bitmapUrl) {

        //判断内存是否有该数据
        Bitmap bitmap = mMemory.read(bitmapUrl);
        if (bitmap != null) {

            Log.i("cache", "内存缓存");
            return bitmap;
        }
        //判断本地sdcard是否含有数据
        bitmap = mLocal.readBitmap(bitmapUrl);
        if (bitmap != null) {

            Log.i("cache", "本地缓存");

            mMemory.write(bitmapUrl, bitmap);//保存缓存到内存
            return bitmap;
        }
        //加载网络数据
        bitmap=mNet.getBitmapFromNet(bitmapUrl);

        return bitmap;
    }


    /**
     * 回调
     */
    public interface MyBitmapLoadCallBack {
        void success();

        void failed();

        void prepareLoad();
    }
}

