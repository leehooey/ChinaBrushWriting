package com.chinabrushwriting.lee.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;

import com.chinabrushwriting.lee.global.App;
import com.chinabrushwriting.lee.home.view.MainView;
import com.chinabrushwriting.lee.quickindex.view.QuickIndexView;

/**
 * Created by Lee on 2016/7/12.
 */
public class UIUtils {
    //获取上下文
    public static Context getContext() {
        return App.getContext();
    }

    //获取handler对象
    public static Handler getHandler() {
        return App.getHandler();
    }

    //获取主线程id
    public static int geMainTid() {
        return App.getTid();
    }

    ////////////////////////加载资源文件///////////////////////////////////
    //从资源文件strings.xml中获取String对象
    public static String getString(int id) {
        return getContext().getResources().getString(id);
    }

    //获取字符串数组
    public static String[] getStringArr(int id) {
        return getContext().getResources().getStringArray(id);
    }

    //获取drawable文件夹下图片
    public static Drawable getDrawable(int id) {
        return getContext().getResources().getDrawable(id);
    }

    //加载colors.xml文件中颜色
    public static int getColor(int id) {
        return getContext().getResources().getColor(id);
    }

    //加载dimens.xml尺寸
    public static float getDimens(int id) {
        return getContext().getResources().getDimensionPixelSize(id);//获取像素
    }
    ////////////////////////像素与独立像素之间转换///////////////////////////////////

    public static float px2dp(int px) {
        //获取像素密度
        float density = getContext().getResources().getDisplayMetrics().density;
        return px / density;
    }

    public static int dp2px(float dp) {
        //获取像素密度
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);//0.5f为了四舍五入
    }

    ////////////////////////加载布局文件///////////////////////////////////

    public static View inflateView(int id) {
        return View.inflate(getContext(), id, null);
    }

    //判断当前程序是否在主线程运行
    public static boolean isUIThread() {
        return android.os.Process.myTid() == geMainTid();
    }

    //直接让子线程中的修改主线程的程序运行在主线程
    public static void runFromChild2UIThread(Runnable r) {
        if (isUIThread()) {
            r.run();
        } else {
            getHandler().post(r);//让其运行在主线程
        }
    }


    //////////////////////////获取屏幕宽高////////////////////////////

    public static int getScreenWidth(){
        WindowManager wm= (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }
    public static int getScreenHeight(){
        WindowManager wm= (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();
    }

    //////////////////activity之间跳转//////////////

    public static void currentPos2OtherAct(MainView mainActivity, Class<QuickIndexView> quickIndexClass){
        Intent intent = new Intent(mainActivity, QuickIndexView.class);
        mainActivity.startActivity(intent);
    }
}
