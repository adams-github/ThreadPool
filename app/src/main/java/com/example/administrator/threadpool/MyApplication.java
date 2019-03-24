package com.example.administrator.threadpool;

import android.app.Application;
import com.example.threadpoollibrary.ThreadPoolUtil;

import java.util.concurrent.PriorityBlockingQueue;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

//        new ThreadPoolUtil.Builder()
//                .withType(ThreadPoolUtil.TYPE_WITHPRIORTYTHREADPOOL)
//                .withCorePoolSize(3)
//                .withMaximumPoolSize(5)
//                .withKeepAliveTime(30)
//                .withCapacity(100)
//                .create();

        new ThreadPoolUtil.Builder()
                .withType(ThreadPoolUtil.TYPE_NEWCACHEDTHREADPOOL)
                .create();
    }


}
