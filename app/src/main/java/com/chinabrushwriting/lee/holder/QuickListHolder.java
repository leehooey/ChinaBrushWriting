package com.chinabrushwriting.lee.holder;

import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.chinabrushwriting.lee.R;
import com.chinabrushwriting.lee.domain.PersonInfo;
import com.chinabrushwriting.lee.utils.UIUtils;

import java.util.ArrayList;

/**
 * Created by Lee on 2016/7/31.
 */
public class QuickListHolder extends BaseHolder {

    private TextView tvContent;
    private TextView tvLetter;

    public QuickListHolder() {
        super();
    }

    @Override
    public View initView() {
        //填充布局文件
        View view = View.inflate(UIUtils.getContext(), R.layout.item_all_data, null);

        tvContent = (TextView) view.findViewById(R.id.tv_content);
        tvLetter = (TextView) view.findViewById(R.id.tv_letter);

        return view;
    }

    @Override
    protected void refreshView(Object data, int position) {

        ArrayList<PersonInfo> mDatas = (ArrayList<PersonInfo>) data;
        String name = mDatas.get(position).getName();
        String letter = mDatas.get(position).getPinyin().toUpperCase().toCharArray()[0] + "";
        tvContent.setText(name);


        if (position > 0) {
            //获取上一个条目的字母
            String lastLetter = mDatas.get(position - 1).getPinyin().toUpperCase().toCharArray()[0] + "";
            //如果当前条目的字母与上一个条目的字母一致则隐藏
            if (lastLetter.equals(letter)) {
                tvLetter.setVisibility(View.GONE);
            } else {
                tvLetter.setVisibility(View.VISIBLE);
                tvLetter.setText(letter);
            }
        } else {
            tvLetter.setVisibility(View.VISIBLE);
            tvLetter.setText(letter);
        }
    }
}
