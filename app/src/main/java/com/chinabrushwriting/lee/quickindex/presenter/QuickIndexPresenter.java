package com.chinabrushwriting.lee.quickindex.presenter;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.chinabrushwriting.lee.R;
import com.chinabrushwriting.lee.domain.PersonInfo;
import com.chinabrushwriting.lee.quickindex.model.IQuickIndexModel;
import com.chinabrushwriting.lee.quickindex.model.QuickIndexModel;
import com.chinabrushwriting.lee.quickindex.view.IQuickIndexView;
import com.chinabrushwriting.lee.utils.Chinese2Pinyin;
import com.chinabrushwriting.lee.utils.EditDistance;
import com.chinabrushwriting.lee.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lee on 2016/7/24.
 */
public class QuickIndexPresenter implements IQuickIndexPresenter {
    private static final String TAG = "QuickIndexPresenter";
    private ArrayList<PersonInfo> mDatas;//作品信息
    private ListView lvAllName;
    private IQuickIndexView view;
    private final IQuickIndexModel model;
    //记录当前输入框中的内容
    private StringBuilder sb;
    //每一个字符的长度
    private List<Integer> cellLength;

    public QuickIndexPresenter(IQuickIndexView view) {
        this.view = view;
        model = new QuickIndexModel();
        sb = new StringBuilder();
        cellLength = new ArrayList<>();


        initView();
    }

    private void initView() {
        lvAllName = view.getListViewData();
        //获取加载的数据
        mDatas = (ArrayList<PersonInfo>) model.getPersonData();

        if (mDatas != null) {
            view.setAdapter(mDatas);
            view.setAddTextChangedListener(mDatas);
            view.setOnItemClickListener(mDatas);
            view.setPositionChangedListener();
            view.setOnClickListener();
            view.setOnScrollListener(mDatas);
            view.setIndexBarTextView();
        }
    }


    /**
     * 设置显示字母的textView
     *
     * @param letter
     */
    @Override
    public void setLetterInPos(String letter) {
        //条目对应位置显示的字母
        char aim = letter.toCharArray()[0];
        for (int i = 0; i < mDatas.size(); i++) {
            //数据中所有数据第一个汉字的首字母
            char ch = mDatas.get(i).getPinyin().toUpperCase().toCharArray()[0];
            //如果匹配则listView将匹配的条目显示第一行
            if (aim == ch) {
                lvAllName.setSelection(i);
                break;
            }
        }
    }

    /**
     * 模糊查询
     *
     * @param s
     * @param start
     * @param before
     * @param count
     */
    @Override
    public void fogSearch(final CharSequence s, final int start, final int before, final int count) {
        UIUtils.runFromChild2UIThread(new Runnable() {
            @Override
            public void run() {
                int position;
                position = getPosition(s, start, before, count);
                lvAllName.setSelection(position);
            }
        });
    }

    /**
     * 根据输入的文字定位列表位置
     *
     * @param s      新字符串
     * @param start  新加入栈的第一个字母序号
     * @param before 1代表删除 0代表添加
     * @param count  一次输入的文本数量
     * @return
     */
    private int getPosition(CharSequence s, int start, int before, int count) {
        //拼接字符串，节省内存
        if (before == 0) {
            //向栈中添加数据
            CharSequence chars = s.subSequence(start, s.length());//获取新加入的字符串
            for (int i = 0; i < chars.length(); i++) {
                //是汉字
                if (chars.charAt(i) > 127) {
                    //将字符串转成拼音
                    String pinyin = Chinese2Pinyin.getPinyinFromChinese(chars.charAt(i) + "");
                    //记录当前汉字的拼音长度
                    cellLength.add(i, pinyin.length());
                    //记录汉字转化的拼音
                    sb.append(pinyin);
                } else {
                    //不是汉字
                    sb.append(chars.charAt(i));
                    cellLength.add(i, 1);
                }
            }
        } else if (before == 1) {
            //删除数据
            int size = cellLength.get(cellLength.size() - 1);//最后一个文字的长度
            sb.delete(sb.length() - size, sb.length());
            cellLength.remove(cellLength.size() - 1);
        }
        //获取最短编辑距离
        int distance = EditDistance.getSortEditDistance(sb.toString(), mDatas);
        return distance;
    }


}
