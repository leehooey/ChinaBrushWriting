package com.chinabrushwriting.lee.home.model;


import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import com.chinabrushwriting.lee.R;
import com.chinabrushwriting.lee.db.dao.InfoDao;
import com.chinabrushwriting.lee.domain.NameShowInfo;
import com.chinabrushwriting.lee.domain.PersonInfo;
import com.chinabrushwriting.lee.http.protocol.HomeProtocol;
import com.chinabrushwriting.lee.manager.AsyncTaskManager;
import com.chinabrushwriting.lee.manager.ThreadPoolManager;
import com.chinabrushwriting.lee.ui.view.MyViewPager;
import com.chinabrushwriting.lee.ui.view.ChineseImage;

import com.chinabrushwriting.lee.utils.MyBitmapUtils;
import com.chinabrushwriting.lee.utils.UIUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by Lee on 2016/7/24.
 */
public class MainModel implements IMainModel {
    private InfoDao mControlDB;
    private List<PersonInfo> mIncludeNameFromDB;

    private final int mBordHeight;
    private NameShowInfo nameShowInfo;
    private AsyncTaskManager asyncTaskManager;

    public MainModel() {


        mBordHeight = (int) UIUtils.getDimens(R.dimen.name_border_height);

        nameShowInfo = NameShowInfo.getInstance();
    }

    /**
     * 从本地文件加载准备好的数据
     */
    @Override
    public boolean loadDataFromLocal(final MyViewPager vp) {
        //操作数据库的对象
        mControlDB = InfoDao.getInstance();

        UIUtils.runFromChild2UIThread(new Runnable() {
            @Override
            public void run() {
                //根据数据库找出大师姓名
                mIncludeNameFromDB = mControlDB.query(1);
                int pos = 0;
                for (PersonInfo i : mIncludeNameFromDB) {
                    //创建数据  控件含有的姓名必须在设置该控件的大小之前赋值
                    final ChineseImage nameShow = new ChineseImage(i.getName());
                    //给对象封装数据
                    nameShow.setPosition(pos++);
                    nameShow.setValues(Integer.parseInt(i.getValues()));
                    //为NameShow控件设置图片
                    setPicForView(nameShow, i.getValues(), i.getName(),
                            mBordHeight / 4 + mBordHeight / 36, nameShow.getBordWidth() - nameShow.getPaddingTop(),
                            mBordHeight - nameShow.getPaddingTop());

                    //将nameShow添加到类中
                    nameShowInfo.addView(nameShow);
                }
            }
        });

        if (mControlDB != null && nameShowInfo != null && mIncludeNameFromDB != null) {
            return true;
        }
        return false;
    }

    private AsyncTask asyncTask;

    /**
     * 从网络加载图片给nameShow
     *
     * @param view        加载的网络显示的控件
     * @param values      不同字体的索引
     * @param name        显示的文字
     * @param fontSize    字体大小
     * @param imageWidth  边框宽度
     * @param imageHeight 边框高度
     */
    public void setPicForView(final View view, final String values, final String name,
                              final int fontSize, final int imageWidth, final int imageHeight) {
        String requestBody = getRequestBoy(values, name, fontSize, imageWidth, imageHeight);
        if(asyncTaskManager==null){
            asyncTaskManager = AsyncTaskManager.getInstance();
        }

        asyncTaskManager.addLoadPicAsync(view,requestBody);
    }



    /**
     * 请求网络加载图片的请求体
     *
     * @param index
     * @param str
     * @param fontSize
     * @param imageWidth
     * @param imageHeight
     * @return
     */
    private String getRequestBoy(String index, String str, int fontSize,
                                 int imageWidth, int imageHeight) {

        final StringBuilder sb = new StringBuilder();
        sb.append("FontInfoId=" + index);
        sb.append("&FontSize=" + fontSize);
        sb.append("&FontColor=" + "#000000");
        sb.append("&ImageWidth=" + imageWidth);
        sb.append("&ImageHeight=" + imageHeight);
        sb.append("&ImageBgColor=" + "#FFFFFF");
        try {
            //改变汉子编码格式
            str = URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        sb.append("&Content=" + str);
        sb.append("&ActionCategory=" + 1);

        return sb.toString();
    }


    //存放着所有的nameShow
    public NameShowInfo getNameShowInfo() {
        return nameShowInfo;
    }

    @Override
    public List<PersonInfo> getPersonInfo() {
        if (mIncludeNameFromDB != null) {
            return mIncludeNameFromDB;
        }
        return null;
    }
}
