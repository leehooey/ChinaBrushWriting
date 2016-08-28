package com.chinabrushwriting.lee.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.chinabrushwriting.lee.R;
import com.chinabrushwriting.lee.ui.view.ChineseImage;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * 网络缓存，加载图片
 * Created by Lee on 2016/8/16.
 */
public class NetCacheUtil {
    private LocalCacheUtils mLocalCache;//本地缓存对象
    private MemoryCacheUtil mMemory;
    private MyBitmapUtils mBitmapUtils;


    public NetCacheUtil(MyBitmapUtils bitmapUtils, LocalCacheUtils localCacheUtils, MemoryCacheUtil mMemory) {
        this.mLocalCache = localCacheUtils;
        this.mMemory = mMemory;
        this.mBitmapUtils = bitmapUtils;
    }

    public void getBitmapFromNet(View view, String url) {
        //创建异步任务对象，并执行
        new MyAsyncTask().execute(view, url);

    }

    /**
     * 异步任务的三个泛型
     * 第一个泛型  Object   doInBackground的参数类型，该异步任务对象执行方法execute的实参类型
     * 第二个泛型  Integer  onProgressUpdate的参数类型
     * 第三个泛型  Bitmap   doInBackground的返回值类型，onPostExecute的参数类型
     */
    class MyAsyncTask extends AsyncTask<Object, Integer, Bitmap> {
        private ChineseImage view;
        private String url;

        //在主线程调用，预加载
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        //子线程调用，正在加载
        @Override
        protected Bitmap doInBackground(Object[] params) {
            view = (ChineseImage) params[0];
            url = (String) params[1];

            Bitmap bitmap = getBitmapFromNet(url);//从网络加载位图数据

            return bitmap;
        }

        //主线程调用，加载结束
        @Override
        protected void onPostExecute(Bitmap o) {
            if (o != null) {

                mBitmapUtils.setBitmapForView(view, o, url);

                mLocalCache.writeBitmap(url, o);//写入本地缓存
                mMemory.write(url, o);//写入内存缓存

            } else {
                //加载失败
                mBitmapUtils.loadFailed();
            }
        }

        //主线程调用，更新进度
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }

    /**
     * 从网络加载位图
     *
     * @param url
     */
    Bitmap getBitmapFromNet(String url) {
        Bitmap bitmap = null;
        HttpURLConnection connection = null;
        try {
            URL u = new URL(url);
            connection = (HttpURLConnection) u.openConnection();

            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(8000);

            int code = connection.getResponseCode();
            if (code == 200) {

                InputStream is = connection.getInputStream();

                bitmap = BitmapFactory.decodeStream(is);

                return bitmap;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();//断开连接
            }
        }

        return bitmap;
    }
}

