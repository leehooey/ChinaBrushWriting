package com.chinabrushwriting.lee.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.chinabrushwriting.lee.R;
import com.chinabrushwriting.lee.data.Assets2Disk;
import com.chinabrushwriting.lee.home.view.MainView;

import java.io.File;


/**
 * 欢迎界面
 * Created by Lee on 2016/5/18.
 */
public class SplashActivity extends AppCompatActivity {


    private File path;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        new Thread() {
            public void run() {
                try {
                    Thread.sleep(3000);
                    Intent intent = new Intent(SplashActivity.this, MainView.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        //判断本地是否存在数据库
        path =new File(getFilesDir(),"info.db");
        if (!path.exists()) {
            Assets2Disk.a2D("info.db",path);
        }
    }
}
