package com.chinabrushwriting.lee.quickindex.presenter;

/**
 * Created by Lee on 2016/7/24.
 */
public interface IQuickIndexPresenter {
    void setLetterInPos(String letter);
    void fogSearch(CharSequence s, int start, int before, int count);
}
