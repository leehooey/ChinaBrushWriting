package com.chinabrushwriting.lee.holder;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.util.Property;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.chinabrushwriting.lee.R;
import com.chinabrushwriting.lee.home.model.MainModel;
import com.chinabrushwriting.lee.ui.view.ChineseImage;
import com.chinabrushwriting.lee.utils.PopupUtils;
import com.chinabrushwriting.lee.utils.UIUtils;
import com.lidroid.xutils.bitmap.PauseOnScrollListener;

import java.util.ArrayList;
import java.util.Properties;


/**
 * Created by Lee on 2016/7/31.
 */
public class HomeHolder extends BaseHolder {

    private static final String TAG = "HomeHolder";
    private Activity mActivity;
    private ChineseImage view;
    private String values;
    private MainModel mMaiModel;
    private final int width;
    private char mChinese;
    private LinearLayout popupParent;

    public static ChineseImage selectedImage;//上一个被点击的图片
    private final GridView mGridView;

    public HomeHolder(GridView gv, String values, MainModel maiModel, LinearLayout llMain, Activity activity) {

        mGridView = gv;
        this.mActivity = activity;
        this.values = values;
        this.mMaiModel = maiModel;
        this.popupParent = llMain;
        //获取显示图片的view的宽度
        width = view.getImageWidth() - view.getPaddingTop();
    }

    @Override
    public View initView() {
        view = new ChineseImage(null);

        //图片被第一次点击
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selectedImage != null) {

                    //根据被点击图片位置设置popupWindow的位置
                    view.setBordColor(ChineseImage.COLOR_NORMAL);

                    if (view != selectedImage) {
                        selectedImage.setBordColor(ChineseImage.COLOR_NULL);
                    } else {
                        //图片再次被点击
                        view.setBordColor(ChineseImage.COLOR_SELECTED);

                        int screenWidth = UIUtils.getScreenWidth();
                        //显示新的试图
                        View popupView = UIUtils.inflateView(R.layout.poup_save_image);
                        ChineseImage image = (ChineseImage) popupView.findViewById(R.id.ci_big_pic);

                        showPopupWindow(popupView, image, screenWidth, Gravity.CENTER, 0, 0);


                    }
                }

                selectedImage = view;
            }
        });

        return view;
    }

    private void showPopupWindow(View view, ChineseImage image, int width, int noGravity, int popupWindowX, int popupWindowY) {
        mMaiModel.setPicForView(image, values, String.valueOf(mChinese), width / 2, width, width);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(500);

        view.setAnimation(alphaAnimation);

        PopupUtils.showPopupWindow(view, popupParent, noGravity, popupWindowX,
                popupWindowY, mActivity, width, width);

    }


    /**
     * 刷新数据
     *
     * @param data
     * @param index
     */
    @Override
    protected void refreshView(final Object data, final int index) {

        final ArrayList<Character> characterArrayList = (ArrayList<Character>) data;
        mChinese = characterArrayList.get(index);

        //加载网络图片
        mMaiModel.setPicForView(view, values, String.valueOf(mChinese), width / 2, width, width);


    }


}
