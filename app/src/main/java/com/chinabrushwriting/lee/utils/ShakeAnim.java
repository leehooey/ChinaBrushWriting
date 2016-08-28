package com.chinabrushwriting.lee.utils;

import android.content.Context;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.TranslateAnimation;

/**
 * 震动动画
 * Created by Lee on 2016/7/17.
 */
public class ShakeAnim{
    //控件震动
    public static void shake(View view){
        final int value=5;//震动频率
        TranslateAnimation shake=new TranslateAnimation(0,10,0,0);
        shake.setDuration(900);

        //插补器，就是通过数学函数实现
        //CycleInterpolator c=new CycleInterpolator(value);//等价于下面方法的实现
        Interpolator interpolator=new Interpolator() {
            @Override
            public float getInterpolation(float input) {
                return (float)(Math.sin(2 * value * Math.PI * input));
            }
        };
        shake.setInterpolator(interpolator);
        view.startAnimation(shake);
    }

    /**
     * 手机震动
     * @param context
     * @param time 震动时间
     */
    public static void phoneShake(Context context,long time){
            Vibrator v= (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(time);
    }


}
