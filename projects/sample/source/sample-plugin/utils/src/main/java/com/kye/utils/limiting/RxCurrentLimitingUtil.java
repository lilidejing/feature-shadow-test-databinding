package com.kye.utils.limiting;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import androidx.annotation.NonNull;

import com.kye.pda.utils.ListUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;

/**
 * 限流操作的工具类
 *
 * @author 刘涛13
 * @description: 提供多种限流策略的工具方法，包括防重请求和延时请求等。
 * @date : 2024/4/1
 */
public class RxCurrentLimitingUtil {
    private static RxCurrentLimitingUtil mInstance;
    private static final String TAG = "RxCurrentLimitingUtil";
    /**
     * 防重请求时间间隔（毫秒）
     */
    private static final long THROTTLE_FIRST_MILLISECONDS = 60000;
    /**
     * 延时请求时间间隔（毫秒）
     */
    private static final long DEBOUNCE_TIME_MILLISECONDS = 500;

    /**
     * 防重请求
     */
    public static final int REQUEST_TYPE_THROTTLE_FIRST = 10;
    /**
     * 延时请求
     */
    public static final int REQUEST_TYPE_DEBOUNCE = 20;
    /**
     * 默认请求
     */
    public static final int REQUEST_TYPE_NORMAL = 0;

    /**
     * 请求去重，第一条请求的时间
     */
    private final Map<Integer, Long> firstTimeMap = Collections.synchronizedMap(new ConcurrentHashMap<>());

    private HandlerThread mHandlerThread;
    private Handler mHandler;
    private List<Integer> currentLimitingKeys = Collections.synchronizedList(new ArrayList<>());

    private RxCurrentLimitingUtil() {
        initHandlerThread();
    }

    /**
     * 初始化HandlerThread和Handler。
     */
    private void initHandlerThread() {
        mHandlerThread = new HandlerThread(TAG);
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (currentLimitingKeys.contains(msg.what)) {
                    if (msg.obj instanceof Runnable) {
                        Runnable runnable = (Runnable) msg.obj;
                        runnable.run();
                    }
                }
            }
        };
    }

    public static RxCurrentLimitingUtil getInstance() {
        if (mInstance == null) {
            synchronized (RxCurrentLimitingUtil.class) {
                if (mInstance == null) {
                    mInstance = new RxCurrentLimitingUtil();
                }
            }
        }
        return mInstance;
    }

    public RxCurrentLimitingUtil putCurrentLimitingKeys(int currentLimitingKey) {
        currentLimitingKeys.add(currentLimitingKey);
        return this;
    }

    /**
     * 根据类型执行不同的限流策略。
     *
     * @param bean     包含请求数据和类型的信息
     * @param key      请求的唯一标识
     * @param observer 请求的观察者
     */
    public void executeRequest(CurrentLimitingBean bean, final int key, CurrentLimitingObserver observer) {
        if (bean == null) {
            executeRequestNormal(null, observer);
        } else {
            if (bean.getRefreshType() == REQUEST_TYPE_THROTTLE_FIRST) {
                executeRequestThrottleFirst(bean, key, observer);
            } else if (bean.getRefreshType() == REQUEST_TYPE_DEBOUNCE) {
                executeRequestDebounce(bean, key, observer);
            } else {
                executeRequestNormal(bean, observer);
            }
        }
    }

    /**
     * 限制60秒内不重复请求。
     *
     * @param key
     * @param observer
     */
    private void executeRequestThrottleFirst(CurrentLimitingBean bean, final int key, CurrentLimitingObserver observer) {
        executeRequestThrottleFirst(bean, key, observer, THROTTLE_FIRST_MILLISECONDS, AndroidSchedulers.mainThread());
    }

    /**
     * 根据指定时间防重请求。
     *
     * @param bean      包含请求数据和类型的信息
     * @param key       请求的唯一标识
     * @param observer  请求的观察者
     * @param delayTime 指定时间防重
     * @param scheduler 线程
     */
    private void executeRequestThrottleFirst(CurrentLimitingBean bean, final int key, CurrentLimitingObserver observer, final long delayTime, Scheduler scheduler) {
        if (observer == null || (ListUtil.isNoEmpty(currentLimitingKeys) && !currentLimitingKeys.contains(key))) {
            return;
        }
        if (scheduler == null) {
            scheduler = AndroidSchedulers.mainThread();
        }
        Long firstTime = firstTimeMap.get(key);
        long time = System.currentTimeMillis();
        if (firstTime == null || time - firstTime > delayTime) {
            firstTimeMap.put(key, time);
            executeRequestNormal(bean, observer, scheduler);
        }
    }


    /**
     * 限制500ms内的请求，只触发最后一次。
     *
     * @param bean     包含请求数据和类型的信息
     * @param key      请求的唯一标识
     * @param observer 请求的观察者
     */
    private void executeRequestDebounce(CurrentLimitingBean bean, final int key, CurrentLimitingObserver observer) {
        executeRequestDebounce(bean, key, observer, DEBOUNCE_TIME_MILLISECONDS, AndroidSchedulers.mainThread());
    }

    /**
     * 根据指定时间只触发最后一次请求（连续触发时取最后一次并延时指定时间）。
     *
     * @param bean      包含请求数据和类型的信息
     * @param key       请求的唯一标识
     * @param observer  请求的观察者
     * @param delayTime 指定时间只触发最后一次（连续触发时取最后一次并延时指定时间）
     * @param scheduler 线程
     */
    private void executeRequestDebounce(CurrentLimitingBean bean, final int key, CurrentLimitingObserver observer, final long delayTime, Scheduler scheduler) {
        if (observer == null || (ListUtil.isNoEmpty(currentLimitingKeys) && !currentLimitingKeys.contains(key))) {
            return;
        }
        if (mHandlerThread == null || !mHandlerThread.isAlive()) {
            initHandlerThread();
        }
        if (scheduler == null) {
            scheduler = AndroidSchedulers.mainThread();
        }
        if (mHandler != null) {
            mHandler.removeMessages(key);
            Message message = new Message();
            message.what = key;
            Scheduler finalScheduler = scheduler;
            message.obj = (Runnable) () -> {
                executeRequestNormal(bean, observer, finalScheduler);
            };
            mHandler.sendMessageDelayed(message, delayTime);
        }
    }

    /**
     * 无限制，直接请求。
     *
     * @param bean     包含请求数据和类型的信息
     * @param observer 请求的观察者
     */
    private void executeRequestNormal(CurrentLimitingBean bean, CurrentLimitingObserver observer) {
        executeRequestNormal(bean, observer, AndroidSchedulers.mainThread());
    }

    /**
     * 在指定线程执行请求。
     *
     * @param bean      包含请求数据和类型的信息
     * @param observer  请求的观察者
     * @param scheduler 线程
     */
    private void executeRequestNormal(CurrentLimitingBean bean, CurrentLimitingObserver observer, Scheduler scheduler) {
        if (observer == null) {
            return;
        }
        if (bean == null) {
            bean = new CurrentLimitingBean();
        }
        if (scheduler == null) {
            scheduler = AndroidSchedulers.mainThread();
        }
        Observable.just(bean)
                .observeOn(scheduler)
                .subscribe(observer);
    }

    /**
     * 清理资源，包括清除请求时间和退出HandlerThread。
     */
    public void clear() {
        firstTimeMap.clear();
        if (mHandlerThread != null && mHandlerThread.isAlive()) {
            mHandlerThread.quit();
            mHandlerThread = null;
            mHandler = null;
        }
        currentLimitingKeys.clear();
    }

    /**
     * 清理指定的请求时间
     */
    public void removeLimitForKey(final int key){
        if (mHandler != null) {
            mHandler.removeMessages(key);
        }
        firstTimeMap.remove(key);
    }

}
