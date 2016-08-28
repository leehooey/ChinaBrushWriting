package com.chinabrushwriting.lee.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

/**
 * Created by Lee on 2016/8/4.
 */
public class NetUtils {

    /**
     * 判断是否有网络
     *
     * @return
     */
    public static boolean useableNets() {

        ConnectivityManager manager = (ConnectivityManager) UIUtils.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager != null) {

            //获取所有网络信息
            NetworkInfo[] info = manager.getAllNetworkInfo();

            for (NetworkInfo i : info) {

                //如果发现一个网络可用网络返回true
                if (i.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }
}
