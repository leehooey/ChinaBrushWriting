package com.chinabrushwriting.lee.home.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.chinabrushwriting.lee.R;
import com.chinabrushwriting.lee.adapter.MyBaseAdapter;
import com.chinabrushwriting.lee.domain.NameShowInfo;
import com.chinabrushwriting.lee.domain.PersonInfo;
import com.chinabrushwriting.lee.holder.BaseHolder;
import com.chinabrushwriting.lee.holder.HomeHolder;
import com.chinabrushwriting.lee.home.model.MainModel;
import com.chinabrushwriting.lee.home.presenter.IMainPresenter;
import com.chinabrushwriting.lee.home.presenter.MainPresenter;
import com.chinabrushwriting.lee.quickindex.view.QuickIndexView;
import com.chinabrushwriting.lee.ui.view.ChineseImage;
import com.chinabrushwriting.lee.ui.view.MyHorizontalScrollView;
import com.chinabrushwriting.lee.ui.view.MyViewPager;
import com.chinabrushwriting.lee.ui.view.SlidingMenu;
import com.chinabrushwriting.lee.utils.ConstantValues;
import com.chinabrushwriting.lee.utils.PopupUtils;
import com.chinabrushwriting.lee.utils.ShakeAnim;
import com.chinabrushwriting.lee.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;


public class MainView extends AppCompatActivity implements View.OnClickListener, IMainView {

    private static int currentTheme = R.style.RedStyle;
    private static final String TAG = "MainView";
    private Button btnFind;
    private EditText etContent;
    private ImageButton ibSlidingMenu;
    private SlidingMenu mSlidingMenu;
    private ImageButton ibMore;
    private LinearLayout llMain;
    private View mPopupView;
    private MyViewPager vpContent;
    private LinearLayout llShowName;
    private MyHorizontalScrollView mHorizontalScrollView;
    private IMainPresenter mPresenter;


    private Button btnMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(currentTheme);//设置主题
        setContentView(R.layout.activity_main);

        //初始化View
        findView();
        //注册监听事件
        setListener();

        mPresenter = new MainPresenter(this, vpContent);
    }

    private void findView() {
        //主页面根布局
        llMain = (LinearLayout) findViewById(R.id.ll_main);
        //整体布局
        mSlidingMenu = (SlidingMenu) findViewById(R.id.sm);
        //侧边栏
//        flLeftMenu = (FrameLayout) findViewById(R.id.fl_left_menu);
        //查找
        btnFind = (Button) findViewById(R.id.btn_find);
        //输入的文本
        etContent = (EditText) findViewById(R.id.et_content);
        //侧边栏按钮
        ibSlidingMenu = (ImageButton) findViewById(R.id.ib_left_menu);
        //弹出更多按钮
        ibMore = (ImageButton) findViewById(R.id.ib_more);
        //PopupWindow
        mPopupView = UIUtils.inflateView(R.layout.popup_memu);
        //大师作品显示
        vpContent = (MyViewPager) findViewById(R.id.vp_content);

        //显示作品的名字
        llShowName = (LinearLayout) findViewById(R.id.ll_show_name);
        //实现linearLayout的横向滑动
        mHorizontalScrollView = (MyHorizontalScrollView) findViewById(R.id.hs_scroll);//观察者
        //注册一个观察者
        vpContent.registerObserver(mHorizontalScrollView);//被观察者

        //显示索引按钮
        btnMore = (Button) findViewById(R.id.btn_more);


    }

    public void setListener() {
        ibSlidingMenu.setOnClickListener(this);
        btnFind.setOnClickListener(this);
        ibSlidingMenu.setOnClickListener(this);
        ibMore.setOnClickListener(this);
        btnMore.setOnClickListener(this);
        mPopupView.findViewById(R.id.tv_setting).setOnClickListener(this);
        mPopupView.findViewById(R.id.tv_share).setOnClickListener(this);
        mPopupView.findViewById(R.id.tv_exit).setOnClickListener(this);
        mPopupView.findViewById(R.id.tv_style).setOnClickListener(this);
    }


    /**
     * 所有按钮点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_find:
                //editText内容为空弹动窗口
                if (TextUtils.isEmpty(etContent.getText().toString())) {
                    ShakeAnim.shake(etContent);
                } else {
                    //重新设置适配器
                    mPresenter.setViewPagerAdapter();
                }
                break;

            case R.id.ib_left_menu:
                mSlidingMenu.show();//侧边栏弹出与隐藏
                break;
            case R.id.ib_more:
                int height = (int) UIUtils.getDimens(R.dimen.title_bar_height);
                PopupUtils.showPopupWindow(mPopupView, llMain, Gravity.RIGHT | Gravity.TOP, ibMore.getWidth() / 2, height, MainView.this);
                break;
            case R.id.btn_more:
                UIUtils.currentPos2OtherAct(MainView.this, QuickIndexView.class);
            case R.id.tv_setting:
                break;
            case R.id.tv_share:
                break;
            case R.id.tv_style:
                Log.i(TAG,"修改主题");
                switchTheme();
                break;
            case R.id.tv_exit:
                System.exit(0);
                break;
        }
    }

    /**
     * 更换主题
     */
    private void switchTheme() {
        switch (currentTheme) {
            case R.style.RedStyle:
                currentTheme=R.style.BlueStyle;
                break;
            case R.style.BlueStyle:
                currentTheme=R.style.RedStyle;
                break;
        }
        llShowName.removeAllViews();

        finish();
        startActivity(getIntent());
    }

    /**
     * 给viewPaper设置适配器
     */
    @Override
    public void vpSetAdapter(final List<PersonInfo> info) {
        MyPagerAdapter adapter = new MyPagerAdapter(info);
        vpContent.setAdapter(adapter);
    }

    /**
     * viewPager
     */
    public class MyPagerAdapter extends PagerAdapter {
        List<PersonInfo> info;
        ViewGroup container;

        public MyPagerAdapter(List<PersonInfo> info) {
            this.info = info;

        }

        @Override
        public int getCount() {
            return info.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        //加载 将要显示的布局,不是页面当前显示的布局
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //将gridView对象填充窗体并添加适配器
            GridView gv = mPresenter.getGridView(position);
            gv.setId(position);
            container.addView(gv);
            this.container = container;

            return gv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);

            this.container = container;
        }

        public GridView getCurrentGridView() {

            int position = vpContent.getCurrentItem();
            for (int i = 0; i < container.getChildCount(); i++) {
                GridView gv = (GridView) container.getChildAt(i);
                if (position == gv.getId()) {
                    return gv;
                }
            }
            return null;
        }

    }

    /**
     * 给ViewPaper中的GridView添加适配器
     *
     * @param gv
     */
    @Override
    public void gvSetAdapter(final GridView gv, final MainModel mainModel) {
        String constant;
        //判断editText是否含有数据
        if (!TextUtils.isEmpty(etContent.getText())) {
            constant = etContent.getText().toString();
        } else {
            //editText为空调用默认字符串
            constant = ConstantValues.strDemo;
        }
        //将字符串转成char类型list
        ArrayList<Character> characterArrayList = str2CharList(constant);

        gv.setAdapter(new MyBaseAdapter<Character>(characterArrayList) {
            @Override
            public BaseHolder getHolder(ViewGroup vp) {

                //获取当前ViewGroup页面应该显示的字体格式的索引
                List<PersonInfo> info = mPresenter.getPeronInfo();
                //根据当前GridView的id获取索引
                String values = info.get(gv.getId()).getValues();

                return new HomeHolder(gv,values, mainModel, llMain, MainView.this);
            }
        });
    }

    private ArrayList<Character> str2CharList(String strDemo) {
        char[] charArray = strDemo.toCharArray();
        ArrayList<Character> characterArrayList = new ArrayList<>();
        for (int i = 0; i < charArray.length; i++) {
            characterArrayList.add(charArray[i]);
        }
        return characterArrayList;
    }

    /**
     * 将显示姓名的ImageButton对象添加到LinearLayout布局中
     *
     * @param info
     */
    @Override
    public void loadLinearLayout(NameShowInfo info) {
        for (ChineseImage nameShow : info.nameShowArrayList) {
            llShowName.addView(nameShow);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        llShowName.removeAllViews();
    }
}
