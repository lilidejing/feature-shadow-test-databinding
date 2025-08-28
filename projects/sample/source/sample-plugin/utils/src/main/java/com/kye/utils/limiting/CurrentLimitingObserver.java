package com.kye.utils.limiting;

import java.util.HashMap;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * 当前限流观察者类，用于实现对当前操作的限流管理。这是一个抽象类，需要由具体实现去处理onSuccess方法的逻辑。
 *
 * @param <T> 当前限制泛型，继承自CurrentLimitingBean。
 * @author 刘涛13
 * @date 2024/4/3
 */
public abstract class CurrentLimitingObserver<T extends CurrentLimitingBean> implements Observer<T> {
    private HashMap<String, Disposable> taskMap;
    private Disposable disposable;
    private String taskTag;

    /**
     * 构造函数。
     *
     * @param mDisposableMap 用于存储Disposable对象的HashMap，用于管理任务的订阅状态。
     */
    public CurrentLimitingObserver(HashMap<String, Disposable> mDisposableMap) {
        this.taskMap = mDisposableMap;
        this.taskTag = String.valueOf(this.hashCode());
    }

    /**
     * 订阅时的操作。将当前订阅的Disposable对象添加到任务集合中。
     *
     * @param d 订阅所产生的Disposable对象。
     */
    @Override
    public void onSubscribe(@NonNull Disposable d) {
        this.disposable = d;
        // 将任务添加到任务集合中管理
        if (taskMap != null) {
            taskMap.put(taskTag, d);
        }
    }

    /**
     * 接收到数据时的操作，会调用onSuccess方法处理数据。
     *
     * @param o 接收到的数据对象。
     */
    @Override
    public void onNext(T o) {
        onSuccess(o);
    }

    /**
     * 出现错误时的操作，统一调用onComplete进行清理工作。
     *
     * @param e 出现的错误异常。
     */
    @Override
    public void onError(@NonNull Throwable e) {
        onComplete();
    }

    /**
     * 完成或出错时的清理工作，取消订阅并从任务集合中移除当前任务。
     */
    @Override
    public void onComplete() {
        if (disposable != null) {
            disposable.dispose();
        }
        // 从任务集合中移除当前任务
        if (taskMap != null && taskMap.containsKey(taskTag)) {
            taskMap.remove(taskTag);
        }
        disposable = null;
    }

    /**
     * 当数据接收成功时，具体处理数据的逻辑由子类实现。
     *
     * @param o 成功接收到的数据对象。
     */
    public abstract void onSuccess(T o);
}
