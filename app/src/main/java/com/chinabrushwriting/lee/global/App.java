package com.chinabrushwriting.lee.global;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 * 程序入口，初始化常用变量
 * Created by Lee on 2016/7/12.
 */
public class App extends Application {

    private static Context mContext;
    private static Handler mHandler;
    private static int mTid;

    @Override
    public void onCreate() {
        super.onCreate();
        //获取上下文
        mContext = getApplicationContext();
        mHandler = new Handler();

        //主线程id
        mTid = android.os.Process.myTid();
    }
    //获取上下文
    public static Context getContext(){
        return mContext;
    }
    public static Handler getHandler(){
        return mHandler;
    }

    public static int getTid(){
        return mTid;
    }
}


