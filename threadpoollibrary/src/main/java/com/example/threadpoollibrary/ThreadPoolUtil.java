package com.example.threadpoollibrary;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 *
 * 线程池工具类
 *
 * 供全局使用
 *
 * */
public class ThreadPoolUtil {

    private static ThreadPoolExecutor mExecutor;

    /**
     * 自定义优先级线程池
     * 基于优先级队列PriorityBlockingQueue
     */
    public final static int TYPE_WITHPRIORTYTHREADPOOL= 1000;
    /**
     * 可缓存线程池
     * 只有非核心线程、线程数量不固定（可无限大）、灵活回收空闲线程；用于执行数量多、耗时少的任务
     * 使用了同步队列：SynchronousQueue
     */
    public final static int TYPE_NEWCACHEDTHREADPOOL = 1001;
    /**
     * 定长线程池
     * 只有核心线程 & 不会被回收、线程数量固定、任务队列无大小限制；用于控制最大并发数
     * 使用了链表阻塞队列：LinkedBlockingQueue
     *
     */
    public final static int TYPE_NEWFIXEDTHREADPOOL = 1002;
    /**
     * 定时线程池
     * 核心线程数量固定、非核心线程数量无限制
     * 只有达到了指定的延时时间，才会执行任务；用于执行定时/周期性任务
     */
    public final static int TYPE_NEWSCHEDULEDTHREADPOOL = 1003;
    /**
     * 单线程化线程池
     * 只有一个核心线程，保证所有任务按照指定顺序在一个线程中执行，不需要处理线程同步的问题；
     * 使用了链表阻塞队列：LinkedBlockingQueue
     */
    public final static int TYPE_NEWSINGLETHREADPOOL = 1004;

    private ThreadPoolUtil(int mType, int mCorePoolSize, int mMaximumPoolSize, int mKeepAliveTime, int mCapacity) {
        switch (mType){
            case TYPE_WITHPRIORTYTHREADPOOL:
                mExecutor = new ThreadPoolExecutor(mCorePoolSize, mMaximumPoolSize,
                        mKeepAliveTime, TimeUnit.SECONDS,
                        new PriorityBlockingQueue<Runnable>(mCapacity, new PriorityComparator()));
                break;
            case TYPE_NEWCACHEDTHREADPOOL:
                mExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
                break;
            case TYPE_NEWFIXEDTHREADPOOL:
                mExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(mCorePoolSize);
                break;
            case TYPE_NEWSCHEDULEDTHREADPOOL:
                mExecutor = (ThreadPoolExecutor) Executors.newScheduledThreadPool(mCorePoolSize);
                break;
            case TYPE_NEWSINGLETHREADPOOL:
                mExecutor = (ThreadPoolExecutor) Executors.newSingleThreadExecutor();
                break;
        }
    }

    public static class Builder{
        private int mType;
        private int mCorePoolSize = 3;//核心线程数
        private int mMaximumPoolSize = 5;//最大线程数
        private int mKeepAliveTime = 30;//非核心线程闲置超时时长（second），默认30秒
        private int mCapacity = 100;//阻塞队列size,默认100

        public Builder withType(int mType) {
            this.mType = mType;
            return this;
        }
        public Builder withCorePoolSize(int mCorePoolSize) {
            this.mCorePoolSize = mCorePoolSize;
            return this;
        }
        public Builder withMaximumPoolSize(int mMaximumPoolSize) {
            this.mMaximumPoolSize = mMaximumPoolSize;
            return this;
        }
        public Builder withKeepAliveTime(int mKeepAliveTime) {
            this.mKeepAliveTime = mKeepAliveTime;
            return this;
        }
        public Builder withCapacity(int mCapacity) {
            this.mCapacity = mCapacity;
            return this;
        }
        public ThreadPoolUtil create(){
            return new ThreadPoolUtil(mType, mCorePoolSize, mMaximumPoolSize, mKeepAliveTime, mCapacity);
        }

    }

    /**
     * 执行任务
     */
    public static void executeTask(Runnable task) throws RejectedExecutionException {
        mExecutor.execute(task);
    }

    /**
     * 移除任务
     */
    public static void removeTask(Runnable task) {
        mExecutor.remove(task);
    }

    /**
     * 关闭线程池
     * 不再接收任务，等待队列中的任务执行完毕后停止
     */
    public static void stopTask(){
        mExecutor.shutdown();
    }

    /**
     * 马上关闭线程池
     * 所有任务都会马上被停止
     */
    public static void stopTaskNow(){
        mExecutor.shutdownNow();
    }

}
