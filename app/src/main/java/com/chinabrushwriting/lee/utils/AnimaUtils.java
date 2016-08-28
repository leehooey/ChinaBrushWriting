package com.chinabrushwriting.lee.utils;

import android.animation.ObjectAnimator;
import android.view.ViewPropertyAnimator;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

/**
 * Created by Lee on 2016/7/20.
 */
public class AnimaUtils {
    //移动
    public static TranslateAnimation translate(int startX,int endX,int startY,int endY,int time){
        TranslateAnimation translateAnimation=new TranslateAnimation(startX,endX,startY,endY);
        translateAnimation.setDuration(time);
        return translateAnimation;
    }
    //透明值
    public static AlphaAnimation alpha(int valuse1,int valuse2,int time){
        AlphaAnimation alphaAnimation=new AlphaAnimation(valuse1,valuse2);
        alphaAnimation.setDuration(time);
        return alphaAnimation;
    }
    //位移加淡入淡出动画
    public static AnimationSet translateAndalpa(int startY,int endY,int time){
        AnimationSet set=new AnimationSet(UIUtils.getContext(),null);
        TranslateAnimation translateAnimation=new TranslateAnimation(0,0,startY,endY);
        translateAnimation.setDuration(time);
        set.addAnimation(translateAnimation);

        AlphaAnimation alphaAnimation=new AlphaAnimation(0,1);
        alphaAnimation.setDuration(time);
        set.addAnimation(alphaAnimation);
        return set;
    }

    //缩放加淡入淡出
    public static AnimationSet alphaAndScale(){
        AnimationSet set=new AnimationSet(UIUtils.getContext(),null);

        AlphaAnimation alphaAnimation=new AlphaAnimation(0,1);
        alphaAnimation.setDuration(1000);

       // ScaleAnimation scaleAnimation=new ScaleAnimation();

        return set;
    }
}
