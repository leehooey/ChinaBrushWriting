package com.chinabrushwriting.lee.ui.view;

import android.content.Context;
import android.content.pm.ProviderInfo;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chinabrushwriting.lee.home.view.MainView;
import com.chinabrushwriting.lee.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义主界面控件
 * Created by Lee on 2016/7/25.
 */
public class MainInterfaceView extends LinearLayout {


    private LinearLayout mPersonView;
    private LinearLayout mInputView;
    private RelativeLayout mTitleBarView;
    private int mTitleBarHeight;
    private int mPersonHeight;
    private float startX;
    private float startY;
    private MyViewPager mViewPager;

    //titleBar的状态
    private final int STATE_TITLE_BAR_SHOW = 0;
    private final int STATE_TITLE_BAR_HIDE = 1;
    private int stateTitleBar = STATE_TITLE_BAR_SHOW;

    //容纳书法大师姓名的控件状态
    private final int STATE_PERSON_SHOW = 0;
    private final int STATE_PERSON_HIDE = 1;
    private int statePerson = STATE_PERSON_SHOW;

    //onTouchEvent应该处理的事件
    private final int STATE_TOUCH_UNDO = 0;
    private final int STATE_TOUCH_TITLE_BAR_SHOW = 1;
    private final int STATE_TOUCH_TITLE_BAR_HIDE = 2;
    private final int STATE_TOUCH_PERSON_SHOW = 3;
    private final int STATE_TOUCH_PERSON_HIDE = 4;
    private int stateTouchDo = STATE_TOUCH_UNDO;

    private String TAG = "MainInterfaceView";
    private List<GridView> mGridViewList;
    private LinearLayout.LayoutParams mPersonParams;
    private LinearLayout.LayoutParams mTitleBarParams;

    public MainInterfaceView(Context context) {
        super(context);

    }

    public MainInterfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MainInterfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //显示titleBar控件
        mTitleBarView = (RelativeLayout) getChildAt(0);
        //显示输入框的控件
        mInputView = (LinearLayout) getChildAt(1);
        //显示书法大师的控件
        mPersonView = (LinearLayout) getChildAt(2);
        //显示容纳书法的控件
        mViewPager = (MyViewPager) getChildAt(3);
        //获取布局参数
        mTitleBarParams = (LinearLayout.LayoutParams) mTitleBarView.getLayoutParams();
        mPersonParams = (LinearLayout.LayoutParams) mPersonView.getLayoutParams();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //获取控件高度
        mTitleBarHeight = mTitleBarView.getMeasuredHeight();
        mPersonHeight = mPersonView.getMeasuredHeight();
    }

    /**
     * 判断是否拦截用户触摸事件交由自己的onTouchEvent处理
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = ev.getX();
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float endX = ev.getX();
                float endY = ev.getY();
                //x轴移动的距离
                float disX = Math.abs(endX - startX);
                //y轴移动的距离
                float disY = Math.abs(endY - startY);
                //上下移动拦截事件交由自己的onTouchEvent处理
                if (disX < disY && disY > 5) {
                    //向下滑动
                    if (endY > startY) {
                        if (statePerson == STATE_PERSON_HIDE) {
                            //显示person控件
                            Log.i(TAG, "显示person控件");
                            stateTouchDo = STATE_TOUCH_PERSON_SHOW;
                            return true;
                        } else if (isFirstLineOfGridView() && stateTitleBar == STATE_TITLE_BAR_HIDE) {
                            //判断当前位置是否为GridView第一行且stateTitleBar为隐藏状态
                            Log.i(TAG, "显示titleBar控件");
                            stateTouchDo = STATE_TOUCH_TITLE_BAR_SHOW;
                            return true;
                        }
                    } else {
                        //向上滑动
                        if (stateTitleBar == STATE_TITLE_BAR_SHOW) {
                            //隐藏titleBar
                            Log.i(TAG, "隐藏titleBar控件");
                            stateTouchDo = STATE_TOUCH_TITLE_BAR_HIDE;
                            return true;
                        } else if (statePerson == STATE_PERSON_SHOW) {
                            //隐藏Person控件
                            Log.i(TAG, "隐藏person控件");
                            stateTouchDo = STATE_TOUCH_PERSON_HIDE;
                            return true;
                        }
                    }
                    break;
                }
        }
        return super.onInterceptTouchEvent(ev);//默认不拦截交由子控件处理

    }

    /**
     * 处理onInterceptTouchEvent传过来的事件
     * 在
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float endY = event.getY();

                float removeDis = endY - startY;
                //判断本次事件是处理titleBar的显示或隐藏
                if (stateTouchDo == STATE_TOUCH_TITLE_BAR_HIDE || stateTouchDo == STATE_TOUCH_TITLE_BAR_SHOW) {
                    adjustTitleBarPos(removeDis);
                }
                //判断本次事件是处理personView的显示或隐藏
                if (stateTouchDo == STATE_TOUCH_PERSON_HIDE || stateTouchDo == STATE_TOUCH_PERSON_SHOW) {
                    adjustPersonPos(removeDis);
                }
                startY = endY;
                break;
            case MotionEvent.ACTION_UP:
                stateTouchDo = STATE_TOUCH_UNDO;
                adjustViewPosition();
                break;
        }

        return true;
    }

    /**
     * 手指移开后判断是否显示与隐藏
     */
    private void adjustViewPosition() {
        //显示或隐藏titleBar
        if (Math.abs(mTitleBarView.getY()) > mTitleBarHeight / 2) {
            setPadding(0, -mTitleBarHeight, 0, 0);
            stateTitleBar = STATE_TITLE_BAR_HIDE;
        } else {
            setPadding(0, 0, 0, 0);
            stateTitleBar = STATE_TITLE_BAR_SHOW;
        }
        //显示或隐藏personView
        if (mPersonView.getHeight() > mPersonHeight / 2) {
            mPersonParams.height = mPersonHeight;
            statePerson = STATE_PERSON_SHOW;
            mPersonView.setLayoutParams(mPersonParams);
        } else {
            mPersonParams.height = 0;
            statePerson = STATE_PERSON_HIDE;
            mPersonView.setLayoutParams(mPersonParams);
        }
    }

    /**
     * 移动titleBar位置
     *
     * @param disY
     */
    private void adjustTitleBarPos(float disY) {
        disY *= (5 / 3);//屏幕滑动速度加快
        float titleBarPos = mTitleBarView.getY();
        if (Math.abs(titleBarPos + disY) <= mTitleBarHeight && (titleBarPos + disY) <= 0) {
            int dis = (int) (titleBarPos + disY);
            setPadding(0, dis, 0, 0);
        }
    }

    /**
     * 移动personView位置
     *
     * @param disY
     */
    private void adjustPersonPos(float disY) {
        disY *= 3;//数据加快
        if ((mPersonView.getHeight() + disY) <= mPersonHeight && (mPersonView.getHeight() + disY) >= 0) {
            mPersonParams.height = (int) (mPersonView.getHeight() + disY);
            mPersonView.setLayoutParams(mPersonParams);
        }
    }

    /**
     * gridView是否显示的是第一行
     *
     * @return
     */
    private boolean isFirstLineOfGridView() {
        //获取GridView集合
        // mGridViewList = MainView.getGridView();
        MainView.MyPagerAdapter adapter = (MainView.MyPagerAdapter) mViewPager.getAdapter();
        GridView gv = adapter.getCurrentGridView();

        if (gv != null) {
            //获取当前GridView显示的第一行子View控件的所在的位置
            int position = gv.getFirstVisiblePosition();
            //如果当前显示gridView的第一行显示titleBar控件
            if (position == 0) {
                return true;
            }
        }
        return false;
    }


}
