package com.chinabrushwriting.lee.utils;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.Contacts;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.chinabrushwriting.lee.R;

/**
 * Created by Lee on 2016/7/23.
 */
public class PopupUtils {


    public static void showPopupWindow(View mPopupView, ViewGroup parent, int gravity, int x, int y, final Activity activity) {

        showPopupWindow(mPopupView, parent, gravity, x, y, activity, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

    }


    /**
     *
     * @param mPopupView  popupWindow要显示的布局文件
     * @param parent  popupWindow在那个父控件显示
     * @param gravity popupWindow显示的权重
     * @param x 在权重基础上修改位置
     * @param y
     * @param activity 将当前activity背景设置暗色
     * @param parmsWidth 布局参数的高度
     * @param parmsHeight
     */
    public static void showPopupWindow(View mPopupView, ViewGroup parent, int gravity, int x, int y, final Activity activity, int parmsWidth, int parmsHeight) {

        PopupWindow popupWindow = new PopupWindow(mPopupView,
                parmsWidth,
                parmsHeight, true);

        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//必须设置
        //设置显示位置
        popupWindow.showAtLocation(parent, gravity, x, y);


        //弹出对话框设置背景透明度
        final WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = 0.5f; //0.0-1.0
        activity.getWindow().setAttributes(lp);


        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lp.alpha = 1f; //0.0-1.0
                activity.getWindow().setAttributes(lp);
            }
        });

    }


}
