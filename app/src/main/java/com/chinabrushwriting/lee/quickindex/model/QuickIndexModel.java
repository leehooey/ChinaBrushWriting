package com.chinabrushwriting.lee.quickindex.model;

import com.chinabrushwriting.lee.db.dao.InfoDao;
import com.chinabrushwriting.lee.domain.PersonInfo;
import com.chinabrushwriting.lee.manager.ThreadPoolManager;
import com.chinabrushwriting.lee.utils.Chinese2Pinyin;
import com.chinabrushwriting.lee.utils.UIUtils;

import java.util.Collections;
import java.util.List;

/**
 * Created by Lee on 2016/7/24.
 */
public class QuickIndexModel implements IQuickIndexModel {
    List<PersonInfo> mDatas;

    public QuickIndexModel() {

    }

    @Override
    public List<PersonInfo> getPersonData() {
        InfoDao infoDao = InfoDao.getInstance();
        mDatas = infoDao.query();
        //将中文解析成拼音
        for (PersonInfo i : mDatas) {
            String pinyin = Chinese2Pinyin.getPinyinFromChinese(i.getName());
            i.setPinyin(pinyin);
        }
        mDatas.remove(0);
        //进行排序
        Collections.sort(mDatas);
        return mDatas;
    }
}
