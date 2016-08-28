package com.chinabrushwriting.lee.holder;

import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.GridView;

import com.lidroid.xutils.bitmap.PauseOnScrollListener;
import com.lidroid.xutils.task.TaskHandler;
import com.nineoldandroids.view.ViewHelper;

/**
 * Created by Lee on 2016/7/31.
 */
public abstract class BaseHolder<T> {

    ///item根布局
    private final View mView;
    //给根布局填充的数据
    private T data;
    private String TAG = "BaseHolder";

    public BaseHolder() {
        mView = initView();
        //将BaseHolder作为Tag设置给View
        mView.setTag(this);
    }


    /**
     * 让子类根据不同布局文件加载布局
     *
     * @return
     */
    public abstract View initView();

    /**
     * 返回布局文件view
     *
     * @return
     */
    public View getmView() {
        return mView;
    }

    /**
     * 填充数据
     */
    public void setData(final T data, final int index) {
        this.data = data;


        refreshView(data, index);
    }

    /**
     * 刷新数据
     */
    protected abstract void refreshView(T data, int position);

}
