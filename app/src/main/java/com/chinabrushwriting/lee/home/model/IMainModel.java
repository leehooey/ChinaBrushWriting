package com.chinabrushwriting.lee.home.model;

import com.chinabrushwriting.lee.domain.NameShowInfo;
import com.chinabrushwriting.lee.domain.PersonInfo;
import com.chinabrushwriting.lee.ui.view.MyViewPager;

import java.util.List;

/**
 * Created by Lee on 2016/7/24.
 */
public interface IMainModel {
    boolean loadDataFromLocal(MyViewPager vp);

    List<PersonInfo> getPersonInfo();

    NameShowInfo getNameShowInfo();

}
