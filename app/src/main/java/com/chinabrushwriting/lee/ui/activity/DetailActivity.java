package com.chinabrushwriting.lee.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.chinabrushwriting.lee.R;

/**
 * 详细信息页
 *  1.显示作者详细信息
 *  2.显示大屏图片
 * Created by Lee on 2016/7/23.
 */
public class DetailActivity extends Activity {
    private static final String TAG = "DetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent=getIntent();
        String index=intent.getStringExtra("value");
        Log.i(TAG,index+"");
        setContentView(R.layout.activity_detai);
    }
}
