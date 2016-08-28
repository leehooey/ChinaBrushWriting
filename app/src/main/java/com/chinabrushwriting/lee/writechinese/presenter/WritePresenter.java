package com.chinabrushwriting.lee.writechinese.presenter;

import com.chinabrushwriting.lee.writechinese.view.IWriteView;

/**
 * Created by Lee on 2016/7/25.
 */
public class WritePresenter implements IWritePresenter {

    private final IWriteView mView;

    public WritePresenter(IWriteView view){
        mView = view;
    }
}
