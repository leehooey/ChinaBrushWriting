package com.chinabrushwriting.lee.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;

import com.chinabrushwriting.lee.R;
import com.chinabrushwriting.lee.domain.NameShowInfo;
import com.chinabrushwriting.lee.manager.ThreadPoolManager;
import com.chinabrushwriting.lee.utils.UIUtils;

import java.util.ArrayList;

/**
 * Created by Lee on 2016/7/22.
 */
public class MyViewPager extends android.support.v4.view.ViewPager {
    private static final String TAG = "ViewPager";

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MyViewPager(Context context) {
        super(context);
        initView();
    }

    private void initView() {

        setPadding(5, 5, 5, 5);

        setOffscreenPageLimit(1);

        final NameShowInfo nameShowInfo=NameShowInfo.getInstance();

        //获取姓名指示器每个姓名之间的的间距
        final int margin = (int) UIUtils.getDimens(R.dimen.name_border_margin);

        /**
         * 当页面改变时通知观察者及时更新自己的状态
         */
        setOnPageChangeListener(new OnPageChangeListener() {
            @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                ChineseImage nameShow=nameShowInfo.nameShowArrayList.get(position);
                //获取当前位置距父控件的右端距离
                int left= nameShow.getLeft();
                int width=nameShow.getBordWidth();
                //根据viewPager移动的百分比计算出当前nameShow移动的百分比
                int sumWidth= (int) (left+(width+margin)*positionOffset);

                notifyObserverPageScrolled(sumWidth);//5.当页面改变时通知观察者
            }

            @Override
            public void onPageSelected(final int position) {
                Log.i("state",position+"");
                notifyObserverPageSelected(position);//5.当页面改变时通知观察者

                //清除线程池中等待加载的线程
                ThreadPoolManager.getThreadPool().removeAll();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


        /**
         * 获取NameShow集合的点击事件，根据点击位置设置当前自定义控件位置
         */

        nameShowInfo.setItemOnClickListener(new NameShowInfo.OnItemClickedListener() {
            @Override
            public void clicked(int position) {
                //根据NameShow集合的点击位置更新当前控件的状态
                MyViewPager.this.setCurrentItem(position);
            }
        });

    }

    //2.观察者  通常是集合
    ArrayList<StateChangedObserver> observers = new ArrayList<>();

    //3.注册观察者 就是将对象添加到集合中
    public void registerObserver(StateChangedObserver observer) {
        if (observers != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }

    //4.注销观察者 就是将观察者从集合中移除
    public void unregisterObserver(StateChangedObserver observer) {

        if (observers != null && observers.contains(observer)) {
            observers.remove(observer);
        }
    }

    //5.通知所有观察者页面滑动了
    public void notifyObserverPageScrolled(int values) {
        if (observers != null) {
            for (StateChangedObserver observer : observers) {
                observer.pageScrolled(values);
            }
        }

    }

    //5.通知所有观察者页面改变了了
    private void notifyObserverPageSelected(int position) {
        if (observers != null) {
            for (StateChangedObserver observer : observers) {
                observer.pageSelected(position);
            }
        }
    }

    /**
     * 1.观察者具有 观察 该被观察者的页面状态改变方法
     * 这就是一个需要观察者实现的接口
     */
    public interface StateChangedObserver {
        //当前页面滑动了
        void pageScrolled(int values);

        //当前页面被选择了
        void pageSelected(int position);
    }
}
