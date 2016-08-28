package com.chinabrushwriting.lee.home.presenter;

import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.GridView;

import com.chinabrushwriting.lee.domain.NameShowInfo;
import com.chinabrushwriting.lee.domain.PersonInfo;
import com.chinabrushwriting.lee.home.model.IMainModel;
import com.chinabrushwriting.lee.home.model.MainModel;
import com.chinabrushwriting.lee.home.view.IMainView;
import com.chinabrushwriting.lee.ui.view.MyViewPager;
import com.chinabrushwriting.lee.utils.UIUtils;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Lee on 2016/7/24.
 */
public class MainPresenter implements IMainPresenter {
    private static final String TAG = "MainPresenter";
    private IMainView mMainView;
    private IMainModel mMainModel;
    private NameShowInfo nameShowInfo;
    private List<PersonInfo> infos;
    private final MyViewPager vpContent;

    public MainPresenter(IMainView mainView, MyViewPager vp) {

        mMainView = mainView;
        mMainModel = new MainModel();
        vpContent = vp;
        initView();
    }

    private void initView() {
        //加载本地数据，返回为true加载成功
        boolean isSuccess = mMainModel.loadDataFromLocal(vpContent);
        if (isSuccess) {
            infos = mMainModel.getPersonInfo();

            nameShowInfo = mMainModel.getNameShowInfo();
            if (nameShowInfo != null) {
                vpContent.registerObserver(nameShowInfo);

            }

            setViewPagerAdapter();

            mMainView.loadLinearLayout(nameShowInfo);
        }
    }

    public List<PersonInfo> getPeronInfo() {
        return infos;
    }

    @Override
    public void setViewPagerAdapter() {
        mMainView.vpSetAdapter(infos);
    }


    /**
     * 创建gridView对象并设置其参数并为其设置适配器
     *
     * @param
     * @return
     */
    @Override
    public GridView getGridView(int position) {
        GridView gv;
        //给ViewPaper将要填充的View
        gv = new GridView(UIUtils.getContext());
        gv.setNumColumns(4);
        //设置子控件之间的间距
        gv.setHorizontalSpacing(7);
        gv.setVerticalSpacing(7);
        gv.setId(position);
        //  list.add(gv);
        //给gridView添加适配器
        setAdapterForGridView(gv);

        return gv;
    }

    /**
     * 为gridView添加适配器
     *
     * @param gv
     */
    @Override
    public void setAdapterForGridView(GridView gv) {
        //从网络加载数据传递给主布局
        mMainView.gvSetAdapter(gv, (MainModel) mMainModel);
    }
}
