package com.kye.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;


/**
 * @author BradySun
 * @description: RxJava线程池管理类
 * @date : 2023/5/15 8:50
 */
public class RxSchedulers {
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final int KEEP_ALIVE_SECONDS = 30;

    private static final ExecutorService EXECUTOR_SERVICE = new ThreadPoolExecutor(
            CORE_POOL_SIZE,
            MAXIMUM_POOL_SIZE,
            KEEP_ALIVE_SECONDS,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(),
            new ThreadFactory() {
                private final AtomicInteger mCount = new AtomicInteger(1);

                public Thread newThread(Runnable r) {
                    return new Thread(r, "RxJava #" + mCount.getAndIncrement());
                }
            });

    private static final Scheduler IO_SCHEDULER = Schedulers.io();
    private static final Scheduler COMPUTATION_SCHEDULER = Schedulers.computation();
    private static final Scheduler NEW_THREAD_SCHEDULER = Schedulers.newThread();
    private static final Scheduler MAIN_THREAD_SCHEDULER = AndroidSchedulers.mainThread();

    public static ExecutorService getExecutorService() {
        return EXECUTOR_SERVICE;
    }

    public static Scheduler io() {
        return IO_SCHEDULER;
    }

    public static Scheduler computation() {
        return COMPUTATION_SCHEDULER;
    }

    public static Scheduler newThread() {
        return NEW_THREAD_SCHEDULER;
    }

    public static Scheduler mainThread() {
        return MAIN_THREAD_SCHEDULER;
    }
}
