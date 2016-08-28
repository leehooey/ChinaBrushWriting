package com.chinabrushwriting.lee.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.chinabrushwriting.lee.R;
import com.chinabrushwriting.lee.utils.UIUtils;

/**
 * 显示大师姓名
 * Created by Lee on 2016/7/21.
 */
public class ChineseImage extends ImageButton {

    private String TAG = "NameShow";
    //在集合中显示位置
    private int position;
    //该控件代表哪一个风格字体
    private int values;
    //该控件盛放的文字
    private String mChinese;

    private int mImageWith;

    private static int mWidth;


    public boolean isFinsh = false;//是否加载完成

    public ChineseImage(String name) {
        super(UIUtils.getContext());
        mChinese = name;
        initView();
    }

    public ChineseImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public ChineseImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void initView() {
        //图片之间间距
        int imageDis = (int) UIUtils.getDimens(R.dimen.name_border_margin);
        //设置图片宽度和高度
        if (mChinese != null) {
            //汉字数量
            int chineseCount;
            char[] chars = mChinese.toCharArray();
            chineseCount = chars.length;
            //图片高度
            int imageHeight = (int) UIUtils.getDimens(R.dimen.name_border_height);
            //图片宽度由字体长度和图片高度定 字体的大小是边框的1/4
            mImageWith = imageHeight * chineseCount / 4;
            //显示的是文字风格
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mImageWith == 0 ? imageHeight : mImageWith, imageHeight);
            params.setMargins(0, 0, imageDis, 0);
            setLayoutParams(params);
        }
        //设置图片的边框和颜色
        setBordColor(COLOR_NULL);
        setImageResource(R.drawable.waite_loading);
        setScaleType(ScaleType.FIT_XY);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //显示具体汉字的控件宽高一致
        if (mChinese == null) {
            heightMeasureSpec = widthMeasureSpec;
            mWidth = MeasureSpec.getSize(widthMeasureSpec);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public static final int COLOR_NORMAL = 0;
    public static final int COLOR_SELECTED = 1;
    public static final int COLOR_NULL = 2;
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setBordColor(int i) {
        switch (i) {
            case COLOR_NORMAL:
                setBackground(UIUtils.getDrawable(R.drawable.image_border_normal));
                break;
            case COLOR_SELECTED:
                setBackground(UIUtils.getDrawable(R.drawable.image_border_selected));
                break;
            case COLOR_NULL:
                setBackground(UIUtils.getDrawable(R.drawable.image_border_null));
                break;
        }
    }

    public int getBordWidth() {
        return mImageWith;
    }

    public int getImageWidth() {
        return mWidth;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getValues() {
        return values;
    }

    public void setValues(int values) {
        this.values = values;
    }

}
