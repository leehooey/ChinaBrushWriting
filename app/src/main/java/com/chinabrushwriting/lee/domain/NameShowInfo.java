package com.chinabrushwriting.lee.domain;

import android.view.View;

import com.chinabrushwriting.lee.ui.view.MyViewPager;
import com.chinabrushwriting.lee.ui.view.ChineseImage;

import java.util.ArrayList;

/**
 * 存放水平滑动控件中所有的继承自ImageButton 的NameShow
 * 同时他也是一个观察者
 * Created by Lee on 2016/8/5.
 */
public class NameShowInfo implements MyViewPager.StateChangedObserver {
    //存放nameShowArrayList
    public ArrayList<ChineseImage> nameShowArrayList = new ArrayList<>();
    //上一次被选择的NameShow
    int lastSelected;
    //单例模式
    private NameShowInfo() {

    }

    private static NameShowInfo info;

    public static NameShowInfo getInstance() {
        if (info == null) {
            synchronized ("clock") {
                if (info == null) {
                    info = new NameShowInfo();
                }
            }
        }
        return info;
    }
    //添加控件对象到集合中
    public void addView(final ChineseImage nameShow) {
        if (nameShow != null && !nameShowArrayList.contains(nameShow)) {
            nameShowArrayList.add(nameShow);

            //设置被点击事件
            nameShow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //让上一个位置颜色变为原来的
                    nameShowArrayList.get(lastSelected).setBordColor(ChineseImage.COLOR_NORMAL);
                    //点击大师姓名边框变色
                    nameShow.setBordColor(ChineseImage.COLOR_SELECTED);
                    //重新设置最后一次点击位置
                    lastSelected = nameShow.getPosition();

                    notifyListener(nameShow.getPosition());
                }
            });
        }
    }


    @Override
    public void pageScrolled(int values) {

    }

    @Override
    public void pageSelected(int position) {
        //还原上一次被选择的控件颜色
        nameShowArrayList.get(lastSelected).setBordColor(ChineseImage.COLOR_NORMAL);
        //设置当前被选择的颜色
        nameShowArrayList.get(position).setBordColor(ChineseImage.COLOR_SELECTED);
        //修改上一次被点击位置
        lastSelected = position;
    }


    //设置监听状态
    public void setItemOnClickListener(OnItemClickedListener click) {
        this.click = click;
    }

    private OnItemClickedListener click;

    //通知监听者该控件的状态改变了
    public void notifyListener(int position) {
        if (click != null) {
            click.clicked(position);
        }
    }

    //创建一个接口用来回掉条目被点击事件
    public interface OnItemClickedListener {
        void clicked(int position);
    }
}
