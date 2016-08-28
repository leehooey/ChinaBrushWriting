package com.chinabrushwriting.lee.utils;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;


/**
 * 内存缓存
 * Created by Lee on 2016/8/16.
 */
public class MemoryCacheUtil {

    //集合中存储的位图是软引用
    // private HashMap<String, SoftReference<Bitmap>> mMemoryCache = new HashMap<>();

    private LruCache<String, Bitmap> mMemoryCache;

    public MemoryCacheUtil() {
        float maxSize = Runtime.getRuntime().maxMemory();//获取程序运行时，系统为程序分配的内存大小
        //LruCache 将最近最少使用的对象回收，保证内存不会超出范围
        mMemoryCache = new LruCache<String, Bitmap>((int) (maxSize / 6)) {

            //每个对象的内存大小
            @Override
            protected int sizeOf(String key, Bitmap value) {

                int size = value.getByteCount();//一个位图的大小
                return size;
            }
        };


    }

    /**
     * 写缓存
     *
     * @param key
     * @param bitmap
     */
    public void write(String key, Bitmap bitmap) {
        key = MD5Utils.encode(key);
        mMemoryCache.put(key, bitmap);
    }

    public Bitmap read(String key) {
        key = MD5Utils.encode(key);
        return mMemoryCache.get(key);
    }
}
