package com.kye.utils.rx;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.ObservableTransformer;

/**
 * Routing
 * Desc    点击事件防双击
 * Source
 * Created by LWS on 2018/7/26 17:14
 * Version 1.0
 */

public class DebounceObservableTransformer<T> implements ObservableTransformer<T, T> {
    public static final long DEBOUNCE = 1000;

    @Override
    public ObservableSource<T> apply(Observable<T> upstream) {
        return upstream.throttleFirst(DEBOUNCE, TimeUnit.MILLISECONDS);
    }
}
