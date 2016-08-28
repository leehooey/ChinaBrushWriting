package com.chinabrushwriting.lee.home.presenter;

import android.widget.GridView;

import com.chinabrushwriting.lee.domain.PersonInfo;

import java.util.List;

/**
 * Created by Lee on 2016/7/24.
 */
public interface IMainPresenter {

    GridView getGridView(int position);

    void setAdapterForGridView(GridView gv);
    List<PersonInfo> getPeronInfo();

    void setViewPagerAdapter();
}
