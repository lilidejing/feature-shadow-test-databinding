package com.kye.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;

import androidx.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.lang.reflect.Modifier;


/**
 * 静态handler，用来避免handler leak；对外部对象弱引用，方便使用
 */
public abstract class StaticHandler<T> extends Handler {

    private final WeakReference<T> referent;

    public StaticHandler(T object) {
        super();
        referent = new WeakReference<T>(object);
        checkStatic();
    }

    public StaticHandler(T object, Callback callback) {
        super(callback);
        referent = new WeakReference<T>(object);
        checkStatic();
    }

    public StaticHandler(T object, Looper looper) {
        super(looper);
        referent = new WeakReference<T>(object);
        flushStackLocalLeaks(looper);
        checkStatic();
    }

    public StaticHandler(T object, Looper looper, Callback callback) {
        super(looper, callback);
        referent = new WeakReference<T>(object);
        flushStackLocalLeaks(looper);
        checkStatic();
    }

    private void checkStatic() {
        Class<?> clazz = getClass();
        if (!Modifier.isStatic(clazz.getModifiers()) && clazz.getName().indexOf('$') > 0) {
            // 非静态的内部类
            throw new RuntimeException("handler not static");
        }
    }

    @Override
    public final void handleMessage(@NonNull Message msg) {
        if (null != referent && null != referent.get()) {
            handleMessage(referent.get(), msg);
        }
    }

    @Override
    public final void dispatchMessage(@NonNull Message msg) {
        super.dispatchMessage(msg);
    }

    /**
     * @param object handler引用的对象；可能为null
     * @param msg    消息
     */
    public abstract void handleMessage(T object, Message msg);

    /**
     * 防止message引起的内存泄露
     *
     * @param looper handlerThread
     */
    private static void flushStackLocalLeaks(Looper looper) {
        final Handler handler = new Handler(looper);
        handler.post(new Runnable() {
            @Override
            public void run() {
                Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
                    @Override
                    public boolean queueIdle() {
                        handler.sendMessageDelayed(handler.obtainMessage(), 1000);
                        return true;
                    }
                });
            }
        });
    }
}
