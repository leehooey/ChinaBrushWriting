package com.chinabrushwriting.lee.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

/**
 * 这是一个观察者，用来观察ViewPage状态改变
 * Created by Lee on 2016/8/4.
 */
public class MyHorizontalScrollView extends HorizontalScrollView implements MyViewPager.StateChangedObserver {
    public MyHorizontalScrollView(Context context) {
        super(context);
    }

    public MyHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 当被观察者状态改变时会调用该方法通知观察者
     *
     * @param values
     */
    @Override
    public void pageScrolled(int values) {
        //修改自己的布局
        setScrollX(values);
    }

    @Override
    public void pageSelected(int position) {

    }
}
