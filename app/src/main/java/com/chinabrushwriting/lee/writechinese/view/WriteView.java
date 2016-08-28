package com.chinabrushwriting.lee.writechinese.view;
/**
 * import android.app.Activity;
 * import android.os.Bundle;
 * import android.view.View;
 * <p/>
 * import com.chinabrushwriting.lee.R;
 * import com.chinabrushwriting.lee.writechinese.presenter.IWritePresenter;
 * import com.chinabrushwriting.lee.writechinese.presenter.WritePresenter;
 * <p/>
 * <p/>
 * Created by Lee on 2016/7/25.
 * <p/>
 * Created by Lee on 2016/7/25.
 * <p/>
 * Created by Lee on 2016/7/25.
 *//**
 * Created by Lee on 2016/7/25.
 *//*
public class WriteView extends Activity implements IWriteView {

    private View vWriteView;
    private IWritePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_write);
        vWriteView = findViewById(R.id.v_write);


        mPresenter = new WritePresenter(this);

    }


}*/


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

import com.chinabrushwriting.lee.R;
import com.chinabrushwriting.lee.ui.view.PaperView;

public class WriteView extends Activity {
    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.layout_left_menu);


    }
}