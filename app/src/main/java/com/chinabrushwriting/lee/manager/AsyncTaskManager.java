package com.chinabrushwriting.lee.manager;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.chinabrushwriting.lee.R;
import com.chinabrushwriting.lee.http.protocol.HomeProtocol;
import com.chinabrushwriting.lee.utils.MyBitmapUtils;

import java.util.HashSet;

/**
 * 异步任务处理大量图片
 * Created by Lee on 2016/8/22.
 */
public class AsyncTaskManager {

    private static AsyncTaskManager asyncTaskManager;
    private static HashSet<LoadPicAsync> set;
    private static HomeProtocol mProtocol;
    private static MyBitmapUtils mBitmapUtils;

    private AsyncTaskManager() {
        mProtocol = new HomeProtocol();
        mBitmapUtils = new MyBitmapUtils();
        set = new HashSet<>();
    }

    public static AsyncTaskManager getInstance() {
        if (asyncTaskManager == null) {
            synchronized ("锁") {
                if (asyncTaskManager == null) {
                    asyncTaskManager = new AsyncTaskManager();
                }
            }
        }
        return asyncTaskManager;
    }

    public void addLoadPicAsync(View view, String requestBody) {

        for(LoadPicAsync a:set){
            if(a.getChineseView()==view){
                return;
            }
        }
        Log.i("show",set.size()+"");
        LoadPicAsync loadPicAsync = new LoadPicAsync();

        loadPicAsync.execute(view, requestBody);

        set.add(loadPicAsync);
    }


    /**
     * 加载图片的异步任务
     */
    class LoadPicAsync extends AsyncTask<Object, Integer, Bitmap> {

        private ImageView mView;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(Object... params) {
            Bitmap bitmap = null;
            mView = (ImageView) params[0];
            String requestBody = (String) params[1];

            final String bitMapUrl = mProtocol.getData(requestBody);
            if (!TextUtils.isEmpty(bitMapUrl)) {
                mView.setTag(R.attr.tag_second, bitMapUrl);//为view控件设置tag标记

                bitmap = mBitmapUtils.getBitmap(bitMapUrl);
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                mView.setImageBitmap(bitmap);
                set.remove(this);
            }

            super.onPostExecute(bitmap);
        }

        public View getChineseView(){
            return mView;
        }
    }

}
