package com.chinabrushwriting.lee.home.view;

import android.widget.GridView;

import com.chinabrushwriting.lee.domain.NameShowInfo;
import com.chinabrushwriting.lee.domain.PersonInfo;
import com.chinabrushwriting.lee.home.model.MainModel;

import java.util.List;

/**
 * Created by Lee on 2016/7/24.
 */
public interface IMainView {
    void vpSetAdapter(List<PersonInfo> info);
    void gvSetAdapter(GridView gv, MainModel model);
    void loadLinearLayout(NameShowInfo info);
}
