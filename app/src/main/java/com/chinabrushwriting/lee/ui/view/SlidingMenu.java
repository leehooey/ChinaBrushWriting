package com.chinabrushwriting.lee.ui.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Scroller;

import com.chinabrushwriting.lee.utils.UIUtils;


/**
 * Created by Lee on 2016/7/12.
 */
public class SlidingMenu extends ViewGroup {
    private static final String TAG = "SlidingMenu";
    private View mainView;
    private View leftView;
    //侧边栏的宽度
    private int leftViewWidth;
    private float startX;
    //记录用户滑动屏幕移动的总位移
    private int initValue = 0;
    //侧边栏隐藏
    private int STATE_UNSHOW = 0;
    //侧边栏显示
    private int STATE_SHOW = 1;
    //当前状态
    private int current_state = STATE_UNSHOW;
    //设置view最大透明度
    private final int MAX_ALPHA = 150;

    private float startY;
    private MyViewPager mViewPager;
    private int valuesOfAlpha;
    //布局的亮度view
    private View mainViewLight;
    private Scroller mScroller;

    public SlidingMenu(Context context) {
        super(context);
    }

    public SlidingMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //widthMeasureSpec获取父窗口宽度相当于 android:layout_march_parent
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取侧边栏view ,getChildAt(x) x是用户声明子孩子的顺序
        leftViewWidth = leftView.getLayoutParams().width;
        //测量两个ViewGroup
        mainView.measure(widthMeasureSpec, heightMeasureSpec);
        leftView.measure(leftViewWidth, heightMeasureSpec);
        //获取倍数
        valuesOfAlpha = leftViewWidth / MAX_ALPHA;
    }

    /**
     * @param changed
     * @param l       距屏幕左边距离
     * @param t       距屏幕顶边距离
     * @param r       距屏幕左边距离
     * @param b       距屏幕顶边距离
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        mainView.layout(l, t, r, b);
        leftView.layout(-leftViewWidth, 0, 0, b);
    }

    //系统读完.xml文件布局时调用
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mScroller = new Scroller(UIUtils.getContext());

        leftView = getChildAt(0);
        mainView = getChildAt(1);
        MainInterfaceView mainLinearLayout = (MainInterfaceView) ((FrameLayout) mainView).getChildAt(0);

        mViewPager = (MyViewPager) mainLinearLayout.getChildAt(3);
        //将主页面显示亮度为暗
        mainViewLight = ((FrameLayout) mainView).getChildAt(1);
    }

    /**
     * 左右滑动且viewPaper显示第一个页面时判断是否拦截事件
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
                //x轴y轴分别移动的距离
                float disX = Math.abs(ev.getX() - startX);
                float disY = Math.abs(ev.getY() - startY);

                //判断x轴移动距离大于y轴距离且viewPaper显示第一个页面
                if (disX > disY && disX > 5 && mViewPager.getCurrentItem() == 0) {
                    //当前页面显示第一个且向左划不拦截事件
                    if ((ev.getX() - startX) < -5 && current_state == STATE_SHOW) {
                        //不拦截事件交由子控件处理
                        return false;
                    }
                    //拦截事件交由自己的onTouchEvent处理
                    return true;
                }
                break;
        }
        //默认情况不拦截事件
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 屏幕触发事件
     *
     * @param event
     * @return true 表示触发事件由程序员自己解决
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //获取初始触摸点x坐标
                startX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                //获取移动触摸点坐标
                float endX = event.getX();
                float offSet = startX - endX;
                //手指移动的总距离
                initValue += offSet;
                //限制view显示位置
                if (initValue <= -leftViewWidth && offSet < 0) {
                    unShowSlidingMenu();
                    break;
                }
                if (initValue >= 0 && offSet > 0) {
                    showSlidingMenu();
                    break;
                }
                //根据手指滑动距离实现侧滑菜单
                scrollBy((int) offSet, 0);
                //侧滑时根据侧滑距离设置主布局最上面一层view的透明度
                setAlphaOfView(initValue / valuesOfAlpha);
                startX = endX;
                break;
            case MotionEvent.ACTION_UP:
                //根据手指松开位置判断侧边栏是否显示
                if (initValue < (-leftViewWidth / 2)) {
                    unShowSlidingMenu();
                } else {
                    showSlidingMenu();
                }
                break;
        }
        //该控件消该事件
        return true;
    }

    /**
     * 设置主布局透明度
     *
     * @param values
     */
    private void setAlphaOfView(int values) {
        //根据透明度实现淡入淡出效果
        mainViewLight.setBackgroundColor(Color.argb(Math.abs(values), 0, 0, 0));
    }

    /**
     * 显示侧边栏
     */
    private void showSlidingMenu() {
        // scrollTo(0, 0);
        smoothScrollTo(0);
        initValue = 0;
        current_state = STATE_SHOW;
        setAlphaOfView(0);//完全透明
    }

    /**
     * 隐藏侧边栏
     */
    private void unShowSlidingMenu() {
        //   scrollTo(-leftViewWidth, 0);
        int dis = (-leftViewWidth);
        smoothScrollTo(dis);

        initValue = -leftViewWidth;
        current_state = STATE_UNSHOW;
        setAlphaOfView(MAX_ALPHA);//最不透明
    }

    private void smoothScrollTo(int disX) {

        int scrollX = getScrollX();
        int dis = disX - scrollX;
        mScroller.startScroll(getScrollX(), 0, dis, 0, 800);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }

    }

    /**
     * 判断当前侧边栏状态
     *
     * @return
     */
    private boolean isShowSlidingMenu() {
        if (current_state == STATE_UNSHOW) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 显示或隐藏侧边栏
     */
    public void show() {
        if (isShowSlidingMenu()) {
            unShowSlidingMenu();
        } else {
            showSlidingMenu();
        }
    }
}
