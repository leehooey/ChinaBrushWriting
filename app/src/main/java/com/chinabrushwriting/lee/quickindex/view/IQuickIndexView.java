package com.chinabrushwriting.lee.quickindex.view;

import android.widget.ListView;

import com.chinabrushwriting.lee.domain.PersonInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lee on 2016/7/24.
 */
public interface IQuickIndexView {
  void setIndexBarTextView();
    ListView getListViewData();
    void setAdapter(ArrayList<PersonInfo> info);
    void setAddTextChangedListener(ArrayList<PersonInfo> info);
    void setOnScrollListener(ArrayList<PersonInfo> info);
    void setOnItemClickListener(ArrayList<PersonInfo> info);
    void setOnClickListener();
    void setPositionChangedListener();
}
