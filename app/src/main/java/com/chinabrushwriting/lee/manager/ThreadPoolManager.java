package com.chinabrushwriting.lee.manager;

import android.util.Log;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Lee on 2016/8/4.
 */
public class ThreadPoolManager {
    //单例模式
    private ThreadPoolManager() {
    }

    private static ThreadPool mThreadPool;

    /**
     * 公共接口用来获取ThreadPool对象
     *
     * @return
     */
    public static ThreadPool getThreadPool() {
        if (mThreadPool == null) {
            synchronized ("clock") {//同步代码块，其锁必须是公共的静态的唯一的
                if (mThreadPool == null) {
                    //核心线程数与最大线程数相等代表生产的线程都会工作
                    mThreadPool = new ThreadPool(10, 10, 1);
                }
            }
        }
        return mThreadPool;
    }

    //内部类
    public static class ThreadPool {
        int corePoolSize;
        int maximumPoolSize;
        long keepAliveTime;

        private ThreadPoolExecutor executor;

        private ThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime) {

            this.corePoolSize = corePoolSize;
            this.maximumPoolSize = maximumPoolSize;
            this.keepAliveTime = keepAliveTime;
        }

        /**
         * 用户调用该方法将Runnable任务添加到线程池中
         *
         * @param r
         */
        public void executor(Runnable r) {
            if (executor == null) {
                executor = new ThreadPoolExecutor(
                        corePoolSize,//核心线程数
                        maximumPoolSize,//最大线程数
                        keepAliveTime,//线程休息时间
                        TimeUnit.SECONDS,//休息时间单位
                        new LinkedBlockingQueue<Runnable>(),//线程队列
                        Executors.defaultThreadFactory(),//生产线程的工厂
                        new ThreadPoolExecutor.AbortPolicy());//线程异常处理  系统完成
            }

            executor.execute(r);//执行任务 但不一定立即执行


            Log.i("count", executor.getActiveCount() + " " + executor.getQueue().size());
        }

        public void removeAll() {
            if (executor != null) {
                for (Runnable r : executor.getQueue()) {
                    executor.remove(r);
                }
            }
        }
    }

}
