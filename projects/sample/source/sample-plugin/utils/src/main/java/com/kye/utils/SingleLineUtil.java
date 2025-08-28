package com.kye.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SingleLineUtil {
    private static SingleLineUtil mInstance;
    /*
     * 全局单线程池
     * */
    private ExecutorService singleThreadExecutor;

    /*
     * 登陆下的单线程池，如果退出登陆则停止所有任务，相当于退出登陆后，执行结果没有意义
     * */
    private ExecutorService singleThreadExecutorForLogin;

    /**
     * 线程池map
     */
    private Map<String, ExecutorService> singleThreadExecutorMap;

    public static SingleLineUtil getInstance() {
        if (mInstance == null) {
            synchronized (SingleLineUtil.class) {
                if (mInstance == null) {
                    mInstance = new SingleLineUtil();
                }
            }
        }
        return mInstance;
    }

    private SingleLineUtil() {
    }

    public ExecutorService getSingle() {
        if (singleThreadExecutor == null) {
            synchronized (SingleLineUtil.class) {
                if (singleThreadExecutor == null) {
                    singleThreadExecutor = Executors.newSingleThreadExecutor();
                }
            }
        }
        return singleThreadExecutor;
    }

    public void clearSingleThreadExecutor() {
        if (singleThreadExecutor != null && !singleThreadExecutor.isShutdown()) {
            singleThreadExecutor.shutdownNow();
            singleThreadExecutor = null;
        }
    }

    public ExecutorService getSingleForLogin() {
        if (singleThreadExecutorForLogin == null) {
            synchronized (SingleLineUtil.class) {
                if (singleThreadExecutorForLogin == null) {
                    singleThreadExecutorForLogin = Executors.newSingleThreadExecutor();
                }
            }
        }
        return singleThreadExecutorForLogin;
    }

    public void clearThreadExecutorForLogin() {
        if (singleThreadExecutorForLogin != null && !singleThreadExecutorForLogin.isShutdown()) {
            singleThreadExecutorForLogin.shutdownNow();
            singleThreadExecutorForLogin = null;
        }
    }

    public ExecutorService getSingleThreadExecutor(String key) {
        if (singleThreadExecutorMap == null) {
            singleThreadExecutorMap = new HashMap<>();
        }
        ExecutorService threadExecutor = singleThreadExecutorMap.get(key);
        if (threadExecutor == null) {
            synchronized (SingleLineUtil.class) {
                threadExecutor = Executors.newSingleThreadExecutor();
                singleThreadExecutorMap.put(key, threadExecutor);
            }
        }
        return threadExecutor;
    }

    public void clearThreadExecutorMap() {
        if (singleThreadExecutorMap != null) {
            for (ExecutorService entry : singleThreadExecutorMap.values()) {
                if (entry != null && !entry.isShutdown()) {
                    entry.shutdownNow();
                }
            }
            singleThreadExecutorMap.clear();
        }
    }
}
