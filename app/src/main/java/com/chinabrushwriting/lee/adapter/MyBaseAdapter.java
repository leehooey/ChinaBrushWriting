package com.chinabrushwriting.lee.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.chinabrushwriting.lee.holder.BaseHolder;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lee on 2016/7/30.
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter {
    List<T> data;

    public MyBaseAdapter(ArrayList<T> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseHolder holder;
        if (convertView == null) {
            //交由子类去获取
            holder = getHolder(parent);
        } else {
            holder = (BaseHolder) convertView.getTag();
        }
        //给view填充数据
        holder.setData(data, position);
        //将holder中创建的View布局返回

        View view = holder.getmView();
        //先缩放
        ViewHelper.setScaleX(view, 0.5f);
        ViewHelper.setScaleY(view, 0.5f);


        //开启缩放属性动画
        ViewPropertyAnimator.animate(view).scaleX(1).setDuration(500).start();
        ViewPropertyAnimator.animate(view).scaleY(1).setDuration(500).start();
        return view;
    }

    /**
     * 根据子类获取的不同数据类型获取不同holder
     *
     * @return
     */
    public abstract BaseHolder getHolder(ViewGroup vp);

}
