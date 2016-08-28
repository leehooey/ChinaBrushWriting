package com.chinabrushwriting.lee.quickindex.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.chinabrushwriting.lee.R;
import com.chinabrushwriting.lee.adapter.MyBaseAdapter;
import com.chinabrushwriting.lee.domain.PersonInfo;
import com.chinabrushwriting.lee.holder.BaseHolder;
import com.chinabrushwriting.lee.holder.QuickListHolder;
import com.chinabrushwriting.lee.quickindex.presenter.IQuickIndexPresenter;
import com.chinabrushwriting.lee.quickindex.presenter.QuickIndexPresenter;
import com.chinabrushwriting.lee.ui.activity.DetailActivity;
import com.chinabrushwriting.lee.home.view.MainView;
import com.chinabrushwriting.lee.ui.view.IndexBar;

import java.util.ArrayList;

/**
 * 将所有书法类型显示
 * Created by Lee on 2016/7/22.
 */
public class  QuickIndexView extends Activity implements IQuickIndexView {


    private static final String TAG = "QuickIndexView";
    private Button btBack;
    private IndexBar ib;
    private TextView tvShowDetail;
    private EditText etSearch;
    private IQuickIndexPresenter presenter;
    private ListView lvAllName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quickindex);

        btBack = (Button) findViewById(R.id.bt_back);
        lvAllName = (ListView) findViewById(R.id.lv_all_name);
        ib = (IndexBar) findViewById(R.id.index_bar);
        tvShowDetail = (TextView) findViewById(R.id.tv_show_letter);
        etSearch = (EditText) findViewById(R.id.et_search);

        presenter = new QuickIndexPresenter(this);
    }

    @Override
    public void setIndexBarTextView() {
        ib.setTextValueListener(new IndexBar.OnTextValueListener() {
            @Override
            public TextView get() {
                return tvShowDetail;
            }
        });
    }

    @Override
    public ListView getListViewData() {
        return lvAllName;
    }

    @Override
    public void setAdapter(final ArrayList<PersonInfo> info) {
        lvAllName.setAdapter(new MyBaseAdapter<PersonInfo>(info) {

            @Override
            public BaseHolder getHolder(ViewGroup vp) {
                return new QuickListHolder();
            }
        });
    }

    @Override
    public void setAddTextChangedListener(ArrayList<PersonInfo> info) {
        //搜索实时栏监听输入框内容 TextWatch实现TextWatcher接口
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //耗时操作在线程中完成
                presenter.fogSearch(s, start, before, count);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void setOnScrollListener(final ArrayList<PersonInfo> info) {
        //根据listView滑动的位置刷新indexBar的显示
        lvAllName.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (info != null) {
                    char c = info.get(firstVisibleItem).getPinyin().toUpperCase().toCharArray()[0];
                    ib.refreshView(c);
                }
            }
        });
    }

    @Override
    public void setOnItemClickListener(final ArrayList<PersonInfo> info) {
        //listView的条目点击事件
        lvAllName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(QuickIndexView.this, DetailActivity.class);
                intent.putExtra("value", info.get(position).getValues());
                QuickIndexView.this.startActivity(intent);
                QuickIndexView.this.finish();
            }
        });
    }

    @Override
    public void setOnClickListener() {
        //返回监听
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(QuickIndexView.this, MainView.class));
            }
        });
    }

    @Override
    public void setPositionChangedListener() {
        //索引条目被点击位置发生改变触该事件
        ib.setPositionChangedListener(new IndexBar.OnPositionChanged() {
            @Override
            public void onPosition(String letter) {
                presenter.setLetterInPos(letter);
                //显示字母的控件
                tvShowDetail.setText(letter);
            }
        });
    }
}
